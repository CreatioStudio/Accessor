package vip.creatio.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

class UnsafeProvider {

    private static final Class<?> unsafeClass;
    private static final Object jdkUnsafe;
    private static final Method DEFINE_CLASS;

    static {

        // Java 9 reflective warning patch
        try {

            // Get sun.misc unsafe
            Class<?> sunUnsafeClass = Class.forName("sun.misc.Unsafe");
            Object sunUnsafe;

            Field f = sunUnsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sunUnsafe = f.get(null);

            // Patch illegal reflective access warning
            Method putObjVolatile = sunUnsafeClass.getMethod("putObjectVolatile", Object.class,
                    long.class,
                    Object.class);
            Method staticFieldOffset = sunUnsafeClass.getMethod("staticFieldOffset", Field.class);

            Class<?> loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = loggerClass.getDeclaredField("logger");

            putObjVolatile.invoke(sunUnsafe, loggerClass,  (Long) staticFieldOffset.invoke(sunUnsafe, logger), null);

            // Add module exports
            Method mth = Module.class.getDeclaredMethod("implAddExports", String.class, Module.class);
            mth.setAccessible(true);

            Module present = UnsafeProvider.class.getModule();

            // Get jdk unsafe
            unsafeClass = Class.forName("jdk.internal.misc.Unsafe");
            mth.invoke(unsafeClass.getModule(), "jdk.internal.misc", present);
            mth.invoke(unsafeClass.getModule(), "jdk.internal.module", present);

            jdkUnsafe = unsafeClass.getMethod("getUnsafe").invoke(null);

            DEFINE_CLASS = UnsafeProvider.getUnsafeClass().getMethod("defineClass",
                    String.class,
                    byte[].class,
                    int.class,
                    int.class,
                    ClassLoader.class,
                    ProtectionDomain.class);
            DEFINE_CLASS.setAccessible(true);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unsupported JDK version, no unsafe class found!");
        } catch (IllegalAccessException e) {
            throw new SecurityException("Module jdk.internal is not open! " +
                    "add --add-opens java.base/jdk.internal.misc=ALL-UNNAMED" +
                    " to JVM arguments.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Class<?> getUnsafeClass() {
        return unsafeClass;
    }

    static Object getUnsafe() {
        return jdkUnsafe;
    }

    static Class<?> defineClass(String name,
                                byte[] bytes,
                                int off,
                                int len,
                                ClassLoader loader,
                                ProtectionDomain domain) {
        try {
            return (Class<?>) DEFINE_CLASS.invoke(jdkUnsafe, name, bytes, off, len, loader, domain);
        } catch (InvocationTargetException e) {
            System.err.println("[CreatioAccessor] Unable to define class " + name + "!");
            throw new RuntimeException(e.getTargetException());
        } catch (IllegalAccessException e) {
            System.err.println("[CreatioAccessor] Unable to define class " + name + "!");
            throw new RuntimeException(e);
        }
    }
}
