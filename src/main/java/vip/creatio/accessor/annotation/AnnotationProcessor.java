package vip.creatio.accessor.annotation;

import vip.creatio.accessor.ReflectiveClassLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface AnnotationProcessor<T extends Annotation> {

    Class<T> getTargetClass();

    default void process(T instance, Method mth) {}

    default void process(T instance, Field f) {}

    default void process(T instance, Constructor<?> c) {}

    default void process(T instance, Class<?> c) {}

    default void onProcessEnd(Class<?> c) {}

    default void onRegister(ReflectiveClassLoader classLoader) {}
}
