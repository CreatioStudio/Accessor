package vip.creatio.accessor;

import vip.creatio.common.Pair;
import vip.creatio.accessor.annotation.AnnotationProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ReflectiveClassLoader extends URLClassLoader {

    public static final String ACCESSOR_GLOBAL = "vip.creatio.accessor.global";
    public static final String ACCESSOR_ROOT = "vip.creatio.accessor";

    private final Map<byte[], byte[]> bytecodeReplacement = new HashMap<>();

    /** Class under this package will be processed and defined by this class loader */
    private final Set<String> includePackages = new HashSet<>();
    {includePackages.add(ACCESSOR_GLOBAL);
     includePackages.add(ACCESSOR_ROOT);}

    /** Class under this package will be load by parent class loader */
    private final Set<String> excludePackages = new HashSet<>();

    /** Class under this package will be processed by this class loader and defined by bootloader */
    private final Set<String> globalPackages = new HashSet<>();
    {globalPackages.add(ACCESSOR_GLOBAL);}

    private final List<AnnotationProcessor<?>> classProcessor = new LinkedList<>();
    private final List<AnnotationProcessor<?>> methodProcessor = new LinkedList<>();
    private final List<AnnotationProcessor<?>> fieldProcessor = new LinkedList<>();
    private final List<AnnotationProcessor<?>> constructorProcessor = new LinkedList<>();

    /** Cache for loaded global classes */
    private final Map<String, Class<?>> globalClasses = new ConcurrentHashMap<>();
    private final Map<String, Class<?>> loadedClasses = new ConcurrentHashMap<>();

    protected Predicate<String> shouldLoad;

    /*
     * ReflectiveClassLoader used to find class sources by URL, but now it has become
     * JarEntry, because loading file from zip using URL easily causes Invalid LOC Header
     */
    /** Cache for JarFile */
    protected final JarFile jar;

    public ReflectiveClassLoader(URL jarPath, ClassLoader parent) throws RuntimeException {
        super(new URL[]{jarPath}, parent);
        try {
            this.jar = new JarFile(new File(jarPath.toURI()));
            this.shouldLoad = s -> true;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ReflectiveClassLoader(URL jarPath, ClassLoader parent, Predicate<String> shouldLoad) throws RuntimeException {
        super(new URL[]{jarPath}, parent);
        try {
            this.jar = new JarFile(new File(jarPath.toURI()));
            this.shouldLoad = shouldLoad;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Class<?>> getGlobalClasses() {
        return Collections.unmodifiableMap(globalClasses);
    }

    public Map<String, Class<?>> getLoadedClasses() {
        return Collections.unmodifiableMap(loadedClasses);
    }

    protected Class<?> findClass(final String name)
            throws ClassNotFoundException
    {
        final Class<?> result;
        try {
            result = AccessController.doPrivileged(
                    (PrivilegedExceptionAction<Class<?>>) () -> {
                        String path = name.replace('.', '/').concat(".class");
                        JarEntry entry = jar.getJarEntry(path);
                        //URL res = this.getResource(path);
                        if (entry != null) {
                            try {
                                if (isGlobal(name)) {
                                    return defineGlobal(name, entry);
                                } else {
                                    return defineClass(name, entry);
                                }
                            } catch (IOException e) {
                                throw new ClassNotFoundException(name, e);
                            }
                        } else {
                            return null;
                        }
                    });
        } catch (PrivilegedActionException pae) {
            throw (ClassNotFoundException) pae.getException();
        }
        if (result == null) {
            throw new ClassNotFoundException(name);
        }
        return result;
    }

    protected final synchronized void addBytecodeReplacement(byte[] from, byte[] to) {
        bytecodeReplacement.put(from, to);
    }

    protected final synchronized void addIncludePackage(String pkg) {
        includePackages.add(pkg);
    }

    protected final synchronized void addExcludePackage(String pkg) {
        excludePackages.add(pkg);
    }

    protected final synchronized boolean addProcessor(AnnotationProcessor<?> processor) {
        Class<? extends Annotation> annotation = processor.getTargetClass();
        Target target = annotation.getAnnotation(Target.class);

        boolean flag = false;
        for (ElementType type : target.value()) {
            if (type == ElementType.TYPE) {
                classProcessor.add(processor);
                flag = true;
            } else if (type == ElementType.METHOD) {
                methodProcessor.add(processor);
                flag = true;
            } else if (type == ElementType.CONSTRUCTOR) {
                constructorProcessor.add(processor);
                flag = true;
            } else if (type == ElementType.FIELD) {
                fieldProcessor.add(processor);
                flag = true;
            }
        }

        return  flag;
    }

    protected final synchronized void addGlobalPackage(String pkg) {
        globalPackages.add(pkg);
    }

    public final boolean isInclude(String pkg) {
        return includePackages.parallelStream().anyMatch(pkg::startsWith);
    }

    public final boolean isExclude(String pkg) {
        return excludePackages.parallelStream().anyMatch(pkg::startsWith);
    }

    public final boolean isGlobal(String pkg) {
        return globalPackages.parallelStream().anyMatch(pkg::startsWith);
    }

    public final boolean isValid(String pkgOrClass) {
        return isInclude(pkgOrClass) && !isExclude(pkgOrClass);
    }

    /** Load all classes in global package */
    protected final synchronized void loadGlobals() {
        // Get bytecode of all classes in globalPackages set.
        List<Map.Entry<String, byte[]>> sortedList = new LinkedList<>();
        for (String pkg : globalPackages) {
            sortedList.addAll(getSortedClassList(getClassesUnderPackage(pkg, true)));
        }

        for (Map.Entry<String, byte[]> entry : sortedList) {
            Class<?> c;
            try {
                // Prevent global classes from loading again
                c = Class.forName(entry.getKey(), false, String.class.getClassLoader());
            } catch (ClassNotFoundException ignored) {
                c = UnsafeProvider.defineClass(
                        entry.getKey(),
                        entry.getValue(),
                        0,
                        entry.getValue().length,
                        String.class.getClassLoader(),
                        String.class.getProtectionDomain());
            }
            globalClasses.put(entry.getKey(), c);
            loadedClasses.put(entry.getKey(), c);
        }
    }

    /** Load all classes using specific loader and domain */
    protected final synchronized void loadClasses(ClassLoader loader, ProtectionDomain domain) {
        loadGlobals();

        // Get bytecode of all classes in includePackages set.
        List<Map.Entry<String, byte[]>> sortedList = new LinkedList<>();
        for (String pkg : includePackages) {
            sortedList.addAll(getSortedClassList(getClassesUnderPackage(pkg, true))
                    .stream()
                    .filter(e -> !isGlobal(e.getKey()))
                    .collect(Collectors.toList()));
        }

        sortedList.forEach(e -> {
                    try {
                        if (shouldLoad.test(e.getKey())) {
                            loadedClasses.put(e.getKey(), UnsafeProvider.defineClass(e.getKey(), e.getValue(), 0, e.getValue().length, loader, domain));
                        }
                    } catch (RuntimeException ignored) {}
        });
    }

    public final Map<byte[], byte[]> getReplacementPairs() {
        return Collections.unmodifiableMap(bytecodeReplacement);
    }

    public final Set<String> getIncludePackages() {
        return Collections.unmodifiableSet(includePackages);
    }

    public final Set<String> getExcludePackages() {
        return Collections.unmodifiableSet(excludePackages);
    }

    public List<Class<?>> getClassIn(String pkg) {
        return getLoadedClasses()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith(pkg) && e.getKey().lastIndexOf('.') == pkg.length())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<Class<?>> getClassUnder(String pkg) {
        return getLoadedClasses()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith(pkg))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    protected Class<?> loadClass(String name, boolean resolveClass) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {

            Class<?> result = findLoadedClass(name);

            if (result == null) {
                Optional<? extends Class<?>> optional = loadedClasses.entrySet()
                        .parallelStream()
                        .filter(e -> e.getKey().equals(name))
                        .map(Map.Entry::getValue)
                        .findFirst();
                if (optional.isPresent()) return optional.get();

                if (isValid(name)) {
                    result = findClass(name);

                    // preprocess all the annotations.
                    //preprocessAnnotations(result);
                } else {
                    result = getParent().loadClass(name);
                }
            }

            if (result != null) {
                if (resolveClass) {
                    resolveClass(result);
                }

                return result;
            }

            throw new ClassNotFoundException(name);
        }
    }

    protected Class<?> defineClass(String name, JarEntry res) throws IOException {

        int i = name.lastIndexOf('.');
        if (i != -1) {
            String pkgname = name.substring(0, i);
            if (getDefinedPackage(pkgname) == null)
                definePackage(pkgname, null, null, null, null, null, null, null);
        }

        byte[] buf = getModifiedBytecode(res).getKey();

        return defineClass(name, buf, 0, buf.length);
    }

    protected Class<?> defineGlobal(String name, JarEntry res) throws IOException {

        int i = name.lastIndexOf('.');
        if (i != -1) {
            String pkgname = name.substring(0, i);
            if (getDefinedPackage(pkgname) == null)
                definePackage(pkgname, null, null, null, null, null, null, null);
        }

        byte[] buf = getModifiedBytecode(res).getKey();

        return UnsafeProvider.defineClass(name, buf, 0, buf.length, String.class.getClassLoader(), String.class.getProtectionDomain());
    }

    protected final Class<?> findLoadedGlobal(String name) {
        for (Map.Entry<String, Class<?>> global : globalClasses.entrySet()) {
            if (global.getKey().equals(name))
                return global.getValue();
        }
        return null;
    }

    protected Map.Entry<byte[], String[]> getModifiedBytecode(JarEntry res) throws IOException {

        // Read the class bytes and define the class
        byte[] buf;
        try (InputStream stream = jar.getInputStream(res)) {
            buf = new byte[stream.available()];
            stream.read(buf);
        }

        return processByteCode(buf);
    }

    protected final URL getResource(JarEntry entry) {
        return getResource(entry.getRealName());
    }

    protected final Map<String, JarEntry> getClassesUnderPackage(String pkg, boolean checkValidate) {
        Enumeration<JarEntry> entries = jar.entries();
        String pathName = pkg.replace('.', '/');
        Map<String, JarEntry> jarEntries = new HashMap<>();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getRealName();

            if (      !entry.isDirectory()
                    && name.startsWith(pathName)
                    && name.endsWith(".class")
                    && !name.endsWith("-info.class") /* Filter off the module-info.class and package-info.class */) {
                String clsName = name.substring(0, name.length() - 6).replace('/', '.');
                if (checkValidate && !isValid(clsName)) continue;
                jarEntries.put(clsName, entry);
            }
        }

        return jarEntries;
    }

    protected final Map<String, JarEntry> getClassesInPackage(String pkg) {

        Enumeration<JarEntry> entries = jar.entries();
        Map<String, JarEntry> jarEntries = new HashMap<>();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getRealName();
            if (!entry.isDirectory() && name.endsWith(".class")) {
                String clsName = name.substring(0, name.length() - 6).replace('/', '.');
                if (clsName.startsWith(pkg) && clsName.lastIndexOf('.') == pkg.length())
                    jarEntries.put(clsName, entry);
            }
        }

        return jarEntries;
    }

    /** Process annotations of all loaded class */
    public final void processAnnotations() {
        loadedClasses.forEach((n, c) -> processAnnotation(c));
    }

    /** Process all annotations using registered annotation processor, this method will not be called automatically */
    protected final void processAnnotation(Class<?> cls) {
        if (cls.isPrimitive()) return;

        // Apply class processor
        for (AnnotationProcessor<?> processor : classProcessor) {
            Annotation a = cls.getAnnotation(processor.getTargetClass());
            if (a != null) {
                try {
                    ((AnnotationProcessor) processor).process(a, cls);
                } catch (Throwable t) {
                    System.err.println("[CreatioAccessor] Exception generated while processing annotation " + a + " in class " + cls.getName());
                    t.printStackTrace();
                }
            }
        }

        // Apply method processor
        for (Method mth : cls.getDeclaredMethods()) {
            for (AnnotationProcessor<?> processor : methodProcessor) {
                Annotation a = mth.getAnnotation(processor.getTargetClass());
                if (a != null) {
                    try {
                        ((AnnotationProcessor) processor).process(a, mth);
                    } catch (Throwable t) {
                        System.out.println("[CreatioAccessor] Exception generated while processing annotation " + a + " in class " + cls.getName());
                        t.printStackTrace();
                    }
                }
            }
        }

        // Apply constructor processor
        for (Constructor<?> c : cls.getDeclaredConstructors()) {
            for (AnnotationProcessor<?> processor : constructorProcessor) {
                Annotation a = c.getAnnotation(processor.getTargetClass());
                if (a != null) {
                    try {
                        ((AnnotationProcessor) processor).process(a, c);
                    } catch (Throwable t) {
                        System.out.println("[CreatioAccessor] Exception generated while processing annotation " + a + " in class " + cls.getName());
                    }
                }
            }
        }

        // Apply field processor
        for (Field f : cls.getDeclaredFields()) {
            for (AnnotationProcessor<?> processor : fieldProcessor) {
                Annotation a = cls.getAnnotation(processor.getTargetClass());
                if (a != null) {
                    try {
                        ((AnnotationProcessor) processor).process(a, f);
                    } catch (Throwable t) {
                        System.out.println("[CreatioAccessor] Exception generated while processing annotation " + a + " in class " + cls.getName());
                    }
                }
            }
        }

        // Call AnnotationProcessor::onProcessEnd
        Set<AnnotationProcessor<?>> processors = new HashSet<>();

        processors.addAll(classProcessor);
        processors.addAll(methodProcessor);
        processors.addAll(constructorProcessor);
        processors.addAll(fieldProcessor);

        processors.forEach(s -> s.onProcessEnd(cls));
    }



    // <Byte array printer>

    private static char byteToChar(byte b) {
        if (b > 31 && b < 127) {
            return (char) b;
        } else {
            return '.';
        }
    }

    private static final String HEX = "0123456789ABCDEF";
    private static String byteToHexCode(byte b) {
        char[] c = new char[2];
        c[0] = HEX.charAt((b >>> 4) & 0xF);
        c[1] = HEX.charAt(b & 0xF);
        return new String(c);
    }

    private static String intToHexCode(int b) {
        return BigInteger.valueOf(b).toString(16).toUpperCase();
    }

    private static void printBuf(byte[] buf) {
        System.out.println("Bytecode Hex Table:");
        System.out.println("       x0 x1 x2 x3 x4 x5 x6 x7 x8 x9 xA xB xC xD xE xF");
        for (int a = 0, b = buf.length / 16; a < b; a++) {

            String s = intToHexCode(a);
            System.out.print(" ".repeat(4 - s.length()) + s + "   ");

            for (int k = 0; k < 16; k++)
                System.out.print(byteToHexCode(buf[a * 16 + k]) + ' ');

            System.out.print("  |  ");

            for (int k = 0; k < 16; k++)
                System.out.print(byteToChar(buf[a * 16 + k]) + " ");

            System.out.print('\n');
        }
    }

    // </Byte array printer>


    /**
     * Returns a Pair of modified class bytecode
     * and it's super classes' (as well as interfaces) name.
     */
    protected Map.Entry<byte[], String[]> processByteCode(byte[] src) {

        byte[] newArray = src;

        // Apply Byte replacement
        int const_count = readCount(newArray, 8) - 1;
        int cursor = 10;

        LinkedList<UTF8> utf8 = new LinkedList<>();
        LinkedList<ClassInfo> classInfo = new LinkedList<>();

        for (int t = 0; t < const_count; t++) {
            switch (newArray[cursor]) {
                case 0x01:          //UTF8, the target
                    int len = readCount(newArray, ++cursor), size;

                    cursor += 2;
                    utf8.add(new UTF8(t, len, cursor));

                    for (Map.Entry<byte[], byte[]> entry : bytecodeReplacement.entrySet()) {
                        if (len < entry.getKey().length) {
                            continue;
                        }

                        size = newArray.length;
                        newArray = replaceRange(newArray, entry.getKey(), entry.getValue(), cursor, cursor + len);
                        size = newArray.length - size;

                        len += size;
                        setShort(newArray, cursor - 2, (short) len);
                    }

                    cursor += len;
                    continue;
                case 0x07:          //Class info
                    classInfo.add(new ClassInfo(t, readCount(newArray, cursor + 1)));
                case 0x10:          //MethodType
                case 0x08:          //String
                    cursor += 3;
                    continue;
                case 0x09:          //Field ref
                case 0x0A:          //Method ref
                case 0x0B:          //InterfaceMethod ref
                case 0x12:          //InvokeDynamic
                case 0x0C:          //NameAndType
                case 0x03:          //Integer
                case 0x04:          //Float
                    cursor += 5;
                    continue;
                case 0x05:          //Long
                case 0x06:          //Double
                    cursor += 9;
                    //Large numberic continued
                    t++;
                    continue;
                case 0x0F:          //MethodHandle
                    cursor += 4;
                    continue;
                default:
                    System.out.println("\n\n\nException in reforming bytecode: InvalidConstType: " + newArray[cursor]);
                    System.out.println("Loc: 0x" + intToHexCode(cursor) + "  const: " + t);
                    printBuf(newArray);
                    throw new ClassFormatError("Invalid constant pool tag: " + newArray[cursor]);
            }
        }

        cursor += 4;

        int superClassIndex = readCount(newArray, cursor);
        cursor += 2;

        String superClass = getClassName(newArray, superClassIndex, classInfo, utf8);

        if (superClass == null) throw new RuntimeException("Cant find super class!");

        int interfaceCount = readCount(newArray, cursor);
        cursor += 2;

        String[] superClassesName = new String[interfaceCount + 1];
        superClassesName[0] = superClass;

        for (int i = 0; i < interfaceCount; i++) {
            int interfaceIndex = readCount(newArray, cursor);
            cursor += 2;
            String interfaceClass = getClassName(newArray, interfaceIndex, classInfo, utf8);
            if (interfaceClass == null) throw new RuntimeException("Cant find interface!");
            superClassesName[i + 1] = interfaceClass;
        }
        return new Pair<>(newArray, superClassesName);
    }

    private String getClassName(byte[] src, int classIndex, LinkedList<ClassInfo> classInfo, LinkedList<UTF8> utf8) {
        for (ClassInfo info : classInfo) {
            if (info.index == classIndex - 1) {
                for (UTF8 utf : utf8) {
                    if (utf.index == info.name_index - 1) {
                        byte[] superClass = new byte[utf.length];
                        System.arraycopy(src, utf.content_offset, superClass, 0, utf.length);
                        return new String(superClass).replace('/', '.');
                    }
                }
            }
        }
        return null;
    }

    static abstract class Constant {

        final int index;

        Constant(int index) {
            this.index = index;
        }
    }

    static class ClassInfo extends Constant {

        final int name_index;

        ClassInfo(int index, int name_index) {
            super(index);
            this.name_index = name_index;
        }
    }

    static class UTF8 extends Constant {

        final int length;
        final int content_offset;

        UTF8(int index, int length, int content_offset) {
            super(index);
            this.length = length;
            this.content_offset = content_offset;
        }
    }

    /**
     * Get a list of class (in modified byte array form) that has been sorted.
     */
    protected final List<? extends Map.Entry<String, byte[]>> getSortedClassList(Map<String, JarEntry> classMap) {
        List<Pair<String, byte[]>> list = new ArrayList<>();
        for (Map.Entry<String, JarEntry> entry : classMap.entrySet()) {
            addClassListElement(list, classMap, entry);
        }
        return list;
    }

    /*
        Set class loading order, make sure superclass will always load before it's inherited class
     */
    private void addClassListElement(List<Pair<String, byte[]>> list, Map<String, JarEntry> pool, Map.Entry<String, JarEntry> target) {
        // Check if this class is within range
        if (!isValid(target.getKey())) return;

        // Check if this already been loaded.
        for (Pair<String, byte[]> pair : list) {
            if (pair.getKey().equals(target.getKey())) return;
        }

        try {
            Map.Entry<byte[], String[]> bytecodes = getModifiedBytecode(target.getValue());
            for (String clsName : bytecodes.getValue()) {
                JarEntry url = pool.get(clsName);
                if (url == null) continue;

                addClassListElement(list, pool, new Pair<>(clsName, url));
            }
            list.add(new Pair<>(target.getKey(), bytecodes.getKey()));
        } catch (IOException e) {
            System.err.println("[CreatioAccessor] Failed to load class " + target.getKey());
            e.printStackTrace();
        }
    }

    private static byte[] replaceRange(byte[] src, byte[] target, byte[] replacement,
                                       int from, int to) {
        int size = to - from;
        byte[] newArray = Arrays.copyOfRange(src, from, to);
        int i = 0;

        do {
            int cursor = i;

            for(int k = 0; k < target.length; k++) {
                if (newArray[cursor++] != target[k]) break;

                if (cursor - i == target.length) {
                    cursor -= target.length;
                    size += replacement.length - target.length;

                    if (replacement.length > target.length) {
                        byte[] repto = new byte[size];
                        System.arraycopy(newArray, 0, repto, 0, cursor);
                        System.arraycopy(newArray, cursor + target.length, repto, cursor + replacement.length, size - replacement.length - cursor);
                        newArray = repto;
                    } else {
                        System.arraycopy(newArray, cursor + target.length, newArray, cursor + replacement.length, size - replacement.length - cursor);
                    }

                    System.arraycopy(replacement, 0, newArray, cursor, replacement.length);
                    k += replacement.length - 1;
                }
            }

            ++i;
        } while (i <= size - target.length);

        byte[] product = new byte[size + from + src.length - to];
        System.arraycopy(src, 0, product, 0, from);
        System.arraycopy(newArray, 0, product, from, size);
        System.arraycopy(src, to, product, size + from, src.length - to);
        return product;
    }

    private static int readCount(byte[] b, int cursor) {
        return ((b[cursor] << 8) | (b[cursor + 1] & 0xFF)) & 0xFFFF;
    }

    private static void setShort(byte[] b, int cursor, short value) {
        b[cursor] = (byte) ((value >>> 8) & 0xFF);
        b[cursor + 1] = (byte) (value & 0xFF);
    }

    @Override
    public void close() throws IOException {
        super.close();
        jar.close();
    }
}

