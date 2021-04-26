package vip.creatio.accessor.annotation;

import vip.creatio.accessor.DelegationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Delegation {

    DelegationType type() default DelegationType.CLASS;

    String clazzName() default "";

    Class<?> clazz() default Object.class;

    String name();

    /** Parameters Type */
    Class<?>[] paramsType() default {};

    /** Replacement for paramsType, class type name */
    String[] paramsTypeName() default {};

}
