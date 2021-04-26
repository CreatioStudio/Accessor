package vip.creatio.accessor;

import vip.creatio.common.ReflectUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Throwable {

        //Unsafe unsafe = Unsafe.getUnsafe();

        //Method mth = ByteCode.class.getDeclaredMethod("test", String.class, int.class);
        //Method mth = Class.class.getDeclaredMethod("privateGetDeclaredMethods", boolean.class);

        Method mth = Test.class.getDeclaredMethod("testtest");
        //mth.invoke(null);
        System.out.println("->" + ReflectUtil.getCallerClass());

        MethodHandle handle = MethodHandles.lookup().unreflect(mth);
        //handle.invoke();

    }

    static final int MAX_POW = 9;
    static void methodSpeedTest(Method mth, Object member, Object... args) {
        try {
            mth.setAccessible(true);
            MethodHandle handle = MethodHandles.lookup().unreflect(mth);

            System.out.println("Now testing speed of method " + mth);

            long[] arr1 = new long[MAX_POW];
            mth.invoke(member, args);
            for (int i = 0; i < MAX_POW; i++) {
                long times = (long) Math.pow(10, i);
                long t0 = System.nanoTime();
                for (long j = 0; j < times; j++) {
                    mth.invoke(member, args);
                }
                arr1[i] = System.nanoTime() - t0;
            }

            long[] arr2 = new long[MAX_POW];
            handle.invoke(member, args[0], args[1]);
            for (int i = 0; i < MAX_POW; i++) {
                long times = (long) Math.pow(10, i);
                long t0 = System.nanoTime();
                for (long j = 0; j < times; j++) {
                    handle.invoke(member, args[0], args[1]);
                }
                arr2[i] = System.nanoTime() - t0;
            }

            long[] arr4 = new long[MAX_POW];
            Func<Integer> func = Reflection.method(mth.getDeclaringClass(), mth.getName(), mth.getParameterTypes());
            func.invoke(member, args[0], "args[1]");
            for (int i = 0; i < MAX_POW; i++) {
                long times = (long) Math.pow(10, i);
                long t0 = System.nanoTime();
                for (long j = 0; j < times; j++) {
                    func.invoke(member, args[0], args[1]);
                }
                arr4[i] = System.nanoTime() - t0;
            }

            System.out.println("Result:");
            System.out.println("Repeating Times  Reflect        MethodHandle   Lambda");
            for (int i = 0; i < MAX_POW; i++) {
                long times = (long) Math.pow(10, i);
                String strT;
                strT = Long.toString(times);
                System.out.print(strT + " ".repeat(17 - strT.length()));
                strT = Float.toString((float) (arr1[i] / 1_000_000D));
                System.out.print(strT + " ".repeat(15 - strT.length()));
                strT = Float.toString((float) (arr2[i] / 1_000_000D));
                System.out.print(strT + " ".repeat(15 - strT.length()));
                strT = Float.toString((float) (arr4[i] / 1_000_000D));
                System.out.println(strT);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
