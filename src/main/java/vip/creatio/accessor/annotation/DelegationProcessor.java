package vip.creatio.accessor.annotation;

import vip.creatio.common.util.ReflectionException;
import vip.creatio.accessor.DelegationType;
import vip.creatio.accessor.Func;
import vip.creatio.accessor.Reflection;
import vip.creatio.accessor.Var;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Annotation processor for @Delegation
 */
public class DelegationProcessor implements AnnotationProcessor<Delegation> {

    private static final Map<Class<?>, Class<?>> GLOBAL = new ConcurrentHashMap<>();

    @Override
    public void process(Delegation an, Class<?> c) {
        Class<?> global = an.clazz();
        if (global == Object.class) {

            if (an.clazzName().equals("")) {
                throw new ReflectionException("Failed to init class " + c.getTypeName()
                        + " : One of clazz and clazzName have to be not-null while marking a class with @Delegation");
            }

            try {
                global = Class.forName(an.clazzName());
            } catch (ClassNotFoundException e) {
                throw new ReflectionException("Failed to init class " + c.getTypeName()
                        + " : Class " + an.clazzName() +
                        " not found while loading annotation of class " + c.getTypeName());
            }
        }
        GLOBAL.put(c, global);
    }

    @Override
    public void process(Delegation instance, Field f) {

        // @Delegation only work on non-final static field;
        if (!Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers()))
            throw new ReflectionException("@Delegation only work on non-final static field!");

        f.setAccessible(true);

        // Field annotation @Delegation overrides class annotation @Delegation
        Class<?> target = instance.clazz();
        if (target == Object.class) {
            if (!instance.clazzName().equals("")) {
                try {
                    target = Class.forName(instance.clazzName());
                } catch (ClassNotFoundException e) {
                    throw new ReflectionException("Failed to init class " + f.getDeclaringClass().getTypeName()
                            + " : Class " + instance.clazzName() +
                            " not found while loading annotation of field " + f);
                }
            } else {
                target = GLOBAL.get(f.getDeclaringClass());
            }
        }

        if (target == null || target == Object.class) {
            throw new ReflectionException("Failed to init field " + f.toGenericString()
                    + " : One of clazz and clazzName have to be not-null while marking a field with @Delegation");
        }

        if (instance.type() == DelegationType.CLASS) {
            throw new ReflectionException("Failed to init field " + f.toGenericString()
                    + " : Only class can mark @Delegation with type DelegationType.CLASS");
        }
        else if (instance.type() == DelegationType.METHOD) {
            if (Func.class.isAssignableFrom(f.getType())) {
                Class<?>[] params = instance.paramsType();

                // Find method in annotation
                Method mth;
                try {
                    mth = target.getDeclaredMethod(instance.name(), params);
                } catch (NoSuchMethodException e) {
                    try {
                        String[] strPar = instance.paramsTypeName();
                        if (strPar.length == 0 && params.length == 0) {
                            throw new NoSuchMethodException();
                        }

                        params = new Class[strPar.length];

                        for (int i = 0; i < strPar.length; i++) {
                            params[i] = Class.forName(strPar[i]);
                        }

                        mth = target.getDeclaredMethod(instance.name(), params);
                    } catch (ClassNotFoundException ee) {
                        throw new ReflectionException("Failed to init field " + f.toGenericString()
                                + " : Class not found: " + ee.getMessage());
                    } catch (NoSuchMethodException ee) {
                        throw new ReflectionException("Failed to init field " + f.toGenericString()
                                + " : Method " + instance.name() + " not found!");
                    }
                }

                // Set new value
                Func<?> func = Reflection.method(mth);
                try {
                    f.set(null, func);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else throw new ReflectionException("Failed to init field " + f.toGenericString()
                    + " : Method-delegated field should have type of Func");
        }
        else if (instance.type() == DelegationType.FIELD) {
            if (Var.class.isAssignableFrom(f.getType())) {
                try {
                    Field field = target.getDeclaredField(instance.name());

                    Var<?> var = Reflection.field(field);
                    f.set(null, var);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new ReflectionException("Failed to init field " + f.toGenericString()
                            + " : Field " + instance.name() + " not found!");
                }
            } else throw new ReflectionException("Failed to init field " + f.toGenericString()
                    + " : Field-delegated field should have type of Var");
        }
    }

    @Override
    public void onProcessEnd(Class<?> c) {
        GLOBAL.remove(c);
    }

    @Override
    public Class<Delegation> getTargetClass() {
        return Delegation.class;
    }
}
