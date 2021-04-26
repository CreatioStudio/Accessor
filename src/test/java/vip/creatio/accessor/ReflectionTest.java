package vip.creatio.accessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    public static void main(String[] args) throws Throwable {
        //Method mth = Unreachable.class.getDeclaredMethod("test");
        Method mth = Class.class.getDeclaredMethod("getDeclaredMethods");

        mth.setAccessible(true);

        reflectionTest(mth, ReflectionTest.class);

        handleTest(mth, ReflectionTest.class);

        long to = System.nanoTime();

        for (int i = 0; i < 15; i++) {
            ReflectionTest.class.getDeclaredMethods();
        }

        System.out.println("Direct :: 15 times invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();

        for (int i = 0; i < 10_000_000; i++) {
            ReflectionTest.class.getDeclaredMethods();
        }

        System.out.println("Direct :: 10_000_000 times invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        String pid = name.substring(0, name.indexOf('@' ));

        //System.out.println(":: Current PID: " + pid);

        //Thread.sleep(300000);

    }

    public static void reflectionTest(Method mth, Object member, Object... args) throws InvocationTargetException, IllegalAccessException {

        long to = System.nanoTime();

        mth.invoke(member, args);

        System.out.println("Reflection :: Single method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();

        mth.invoke(member, args);

        System.out.println("Reflection :: Second method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();

        mth.invoke(member, args);

        System.out.println("Reflection :: Third method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 15; i++) {
            mth.invoke(member, args);
        }

        System.out.println("Reflection :: 15 times invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 15; i++) {
            mth.invoke(member, args);
        }

        System.out.println("Reflection :: 15 times more invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            mth.invoke(member, args);
        }

        System.out.println("Reflection :: 10_000_000 times more invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

    }

    public static void handleTest(Method mth, Object member) throws Throwable {

        MethodHandle handle = MethodHandles.lookup().unreflect(mth);

        long to = System.nanoTime();

        handle.invoke(member);

        System.out.println("Handle :: Single method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();

        handle.invoke(member);

        System.out.println("Handle :: Second method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();

        handle.invoke(member);

        System.out.println("Handle :: Third method invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 15; i++) {
            handle.invoke(member);
        }

        System.out.println("Handle :: 15 times invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 15; i++) {
            handle.invoke(member);
        }

        System.out.println("Handle :: 15 times more invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

        to = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            handle.invoke(member);
        }

        System.out.println("Handle :: 10_000_000 times more invocation: " + (System.nanoTime() - to) / 100_000d + "ms");

    }

    private static void test() {
        //new Exception().printStackTrace();
    }

}
