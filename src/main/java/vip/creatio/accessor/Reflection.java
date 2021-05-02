package vip.creatio.accessor;

import vip.creatio.common.util.ReflectUtil;
import vip.creatio.common.util.ReflectionException;
import vip.creatio.accessor.global.IFunction;
import vip.creatio.accessor.global.IFunctionObj;
import vip.creatio.accessor.global.IFunctionVoid;
import vip.creatio.common.util.SysUtil;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;

public final class Reflection {

    // No default constructor
    private Reflection() {}

    private static final ClassLoader[] LOADER_CHAIN = SysUtil.exec(() -> {
        ClassLoader loader = Reflection.class.getClassLoader();
        List<ClassLoader> chain = new ArrayList<>();
        while (loader != null) {
            chain.add(loader);
            loader = loader.getParent();
        }
        return chain.toArray(ClassLoader[]::new);
    });

    static Unsafe unsafe;

    private static final int JAVA_VER = SysUtil.exec(() -> {
        String ver = System.getProperty("java.specification.version");
        if (ver.startsWith("1.")) ver = ver.substring(2);

        return Integer.parseInt(ver);
    });



    private static MethodType toType(Class<?> rType, Class<?>... ptype) {
        for (int i = 0; i < ptype.length; i++) {
            ptype[i] = ReflectUtil.toWrapper(ptype[i]);
        }
        return MethodType.methodType(ReflectUtil.toWrapper(rType), ptype);
    }

    private static Unsafe getUnsafe() {
        if (unsafe == null) unsafe = Unsafe.getUnsafe();
        return unsafe;
    }

    static void implicitlyThrow(Throwable e) {
        getUnsafe().throwException(e);
    }



    private static final Method CLASS_GET_METHODS = ReflectUtil.method(Class.class, "privateGetDeclaredMethods", boolean.class);
    private static final Method CLASS_GET_FIELDS = ReflectUtil.method(Class.class, "privateGetDeclaredFields", boolean.class);
    private static final Method CLASS_GET_CONSTRUCTORS = ReflectUtil.method(Class.class, "privateGetDeclaredConstructors", boolean.class);

    public static Method[] getMethods(Class<?> cls, boolean publicOnly) {
        try {
            return (Method[]) CLASS_GET_METHODS.invoke(cls, publicOnly);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(e.getTargetException());
        }
    }

    public static Field[] getFields(Class<?> cls, boolean publicOnly) {
        try {
            return (Field[]) CLASS_GET_FIELDS.invoke(cls, publicOnly);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(e.getTargetException());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> cls, boolean publicOnly) {
        try {
            return (Constructor<T>[]) CLASS_GET_CONSTRUCTORS.invoke(cls, publicOnly);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(e.getTargetException());
        }
    }



    private static final int MAX_CACHE_THRESHOLD = 200;
    private static final TreeSet<Func<?>> methodCache = new TreeSet<>(Comparator.comparingInt(f -> f.usedTime));
    private static long METHOD_USED_TIME_OFFSET;

    @SuppressWarnings("unchecked")
    public static <R> Func<R> method(Method mth) {
        int hash = mth.getDeclaringClass().hashCode();
        for (Class<?> c : mth.getParameterTypes()) {
            hash = hash * 31 + c.hashCode();
        }

        if (METHOD_USED_TIME_OFFSET == 0) METHOD_USED_TIME_OFFSET = getUnsafe().objectFieldOffset(Func.class, "usedTime");

        for (Func<?> f : methodCache) {
            if (f.hashCode() == hash) {
                int newVal;
                do {
                    newVal = f.usedTime;
                } while (getUnsafe().compareAndSetInt(f, METHOD_USED_TIME_OFFSET, newVal - 1, newVal + 1));
                return (Func<R>) f;
            }
        }

        IFunction<R> fi = (IFunction<R>) getMethodIFunc(mth);
        Func<R> newFunc;
        int paramsCount = mth.getParameterCount() + (Modifier.isStatic(mth.getModifiers()) ? 0 : 1);
        if (fi instanceof IFunctionObj) {
            newFunc = new FuncAnonymousImplObj<>((IFunctionObj<R>) fi, paramsCount, hash);
        } else {
            newFunc = (Func<R>) new FuncAnonymousImplVoid((IFunctionVoid) fi, paramsCount, hash);
        }
        methodCache.add(newFunc);

        if (methodCache.size() > MAX_CACHE_THRESHOLD) methodCache.pollFirst();

        return newFunc;
    }

    public static <R> Func<R> method(Class<?> cls, String name, Class<?>... ptype) {
        return method(ReflectUtil.method(cls, name, ptype));
    }



    private static final TreeSet<Var<?>> fieldCache = new TreeSet<>(Comparator.comparingInt(f -> f.usedTime));
    private static long FIELD_USED_TIME_OFFSET;

    @SuppressWarnings("unchecked")
    public static <R> Var<R> field(Field f) {
        int mod = f.getModifiers();
        int hash = f.getType().hashCode() * 31 + f.getDeclaringClass().hashCode();

        if (FIELD_USED_TIME_OFFSET == 0) FIELD_USED_TIME_OFFSET = getUnsafe().objectFieldOffset(Var.class, "usedTime");

        for (Var<?> field : fieldCache) {
            if (field.hashCode() == hash) {
                int newVal;
                do {
                    newVal = field.usedTime;
                } while (getUnsafe().compareAndSetInt(f, METHOD_USED_TIME_OFFSET, newVal - 1, newVal + 1));
                return (Var<R>) field;
            }
        }

        Var<R> newField;
        if (Modifier.isStatic(mod)) {
            newField = new VarReflectImpl<>(getUnsafe().staticFieldBase(f), getUnsafe().staticFieldOffset(f), Modifier.isVolatile(mod),
                    true, Var.getTypeId(f.getType()), hash);
        } else {
            newField = new VarReflectImpl<>(null, getUnsafe().objectFieldOffset(f), Modifier.isVolatile(mod),
                    false, Var.getTypeId(f.getType()), hash);
        }

        fieldCache.add(newField);

        if (fieldCache.size() > MAX_CACHE_THRESHOLD) fieldCache.pollFirst();

        return newField;

    }

    public static <R> Var<R> field(Class<?> cls, String name) {
        return field(ReflectUtil.field(cls, name));
    }

    public static <R> Var<R> field(Class<?> cls, int index) {
        return field(ReflectUtil.field(cls, index));
    }




    @SuppressWarnings("unchecked")
    public static <R> Func<R> constructor(Constructor<R> constructor) {
        int hash = constructor.getDeclaringClass().hashCode();
        for (Class<?> c : constructor.getParameterTypes()) {
            hash = hash * 31 + c.hashCode();
        }

        if (METHOD_USED_TIME_OFFSET == 0) METHOD_USED_TIME_OFFSET = getUnsafe().objectFieldOffset(Func.class, "usedTime");

        for (Func<?> f : methodCache) {
            if (f.hashCode() == hash) {
                int newVal;
                do {
                    newVal = f.usedTime;
                } while (getUnsafe().compareAndSetInt(f, METHOD_USED_TIME_OFFSET, newVal - 1, newVal + 1));
                return (Func<R>) f;
            }
        }

        IFunctionObj<R> fi = getConstructorIFunc(constructor);
        Func<R> newFunc = new FuncAnonymousImplObj<>(fi, constructor.getParameterCount(), hash);
        methodCache.add(newFunc);

        if (methodCache.size() > MAX_CACHE_THRESHOLD) methodCache.pollFirst();

        return newFunc;
    }

    public static <R> Func<R> constructor(Class<R> cls, Class<?>... ptype) {
        return constructor(ReflectUtil.constructor(cls, ptype));
    }



    private static boolean visibleTo(Class<?> cls) {
        ClassLoader loader = cls.getClassLoader();
        if (loader == null) return false;
        for (ClassLoader l : LOADER_CHAIN) {
            if (loader == l) return true;
        }
        return false;
    }


    static IFunction<?> getMethodRestricted(int javaVer, Class<?> cls, String name, Class<?>... ptype) {
        if (javaVer > JAVA_VER) return null;
        return getMethodIFunc(cls, name, ptype);
    }

    static IFunction<?> getMethodIFunc(Class<?> cls, String name, Class<?>... ptype) {
        try {
            Method mth = cls.getDeclaredMethod(name, ptype);
            return getMethodIFunc(mth);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    static IFunction<?> getMethodIFunc(Method mth) {
        try {
            mth.setAccessible(true);
            MethodHandles.Lookup caller;
            if (visibleTo(mth.getDeclaringClass())) {
                caller = ReflectUtil.lookupIn(mth.getDeclaringClass());
            } else {
                if (Modifier.isPublic(mth.getModifiers())) {
                    caller = ReflectUtil.lookup();
                } else {
                    return new FuncReflectMethodImpl<>(mth);
                }
            }

            MethodHandle handle = caller.unreflect(mth);
            MethodType funcType = toType(handle.type().returnType(), handle.type().parameterArray());

            if (funcType.returnType() == void.class) {
                return (IFunctionVoid) LambdaMetafactory.metafactory(
                        caller,
                        "invoke",
                        MethodType.methodType(IFunctionVoid.class),
                        handle.type().generic().changeReturnType(void.class),
                        handle,
                        funcType).getTarget().invokeExact();
            } else {
                return (IFunctionObj<?>) LambdaMetafactory.metafactory(
                        caller,
                        "invoke",
                        MethodType.methodType(IFunctionObj.class),
                        handle.type().generic(),
                        handle,
                        funcType).getTarget().invokeExact();
            }
        } catch (LambdaConversionException e) {
            throw new ReflectionException("[CreatioAccessor] ConversionException @ " +
                    mth.getDeclaringClass().getSimpleName() + "." + mth.getName() + " : " + e.getMessage());
        } catch (Throwable e) {
            throw new ReflectionException(e);
        }
    }


    static <T> IFunctionObj<T> getConstructorIFunc(Class<T> cls, Class<?>... ptype) {
        try {
            Constructor<T> constructor = cls.getDeclaredConstructor(ptype);
            return getConstructorIFunc(constructor);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> IFunctionObj<T> getConstructorIFunc(Constructor<T> constructor) {
        try {
            MethodHandles.Lookup caller;
            if (visibleTo(constructor.getDeclaringClass())) {
                caller = ReflectUtil.lookupIn(constructor.getDeclaringClass());
            } else {
                if (Modifier.isPublic(constructor.getModifiers())) {
                    caller = ReflectUtil.lookup();
                } else {
                    return new FuncReflectConstructorImpl<>(constructor);
                }
            }

            MethodHandle handle = caller.unreflectConstructor(constructor);
            MethodType funcType = toType(handle.type().returnType(), handle.type().parameterArray());

            return (IFunctionObj<T>) LambdaMetafactory.metafactory(
                    caller,
                    "invoke",
                    MethodType.methodType(IFunctionObj.class),
                    handle.type().generic(),
                    handle,
                    funcType).getTarget().invokeExact();
        } catch (LambdaConversionException e) {
            e.printStackTrace();
            throw new ReflectionException("[CreatioAccessor] ConversionException @ " +
                    constructor.getDeclaringClass().getSimpleName() + "." + constructor.getName() + " : " + e.getMessage());
        } catch (Throwable e) {
            throw new ReflectionException(e);
        }
    }
}
