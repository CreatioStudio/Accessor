package vip.creatio.accessor;

import vip.creatio.common.ReflectionException;
import vip.creatio.accessor.global.IFunctionObj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

final class FuncReflectConstructorImpl<R> implements IFunctionObj<R> {

    private final Constructor<R> constructor;
    private final int argsCount;

    FuncReflectConstructorImpl(Constructor<R> constructor) {
        this.constructor = constructor;
        this.argsCount = constructor.getParameterCount();
    }

    @Override
    public R invoke() {
        if (argsCount != 0) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1) {
        if (argsCount != 1) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2) {
        if (argsCount != 2) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3) {
        if (argsCount != 3) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4) {
        if (argsCount != 4) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (argsCount != 5) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (argsCount != 6) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        if (argsCount != 7) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        if (argsCount != 8) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        if (argsCount != 9) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10) {
        if (argsCount != 10) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11) {
        if (argsCount != 11) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12) {
        if (argsCount != 12) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13) {
        if (argsCount != 13) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14) {
        if (argsCount != 14) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15) {
        if (argsCount != 15) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16) {
        if (argsCount != 16) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17) {
        if (argsCount != 17) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18) {
        if (argsCount != 18) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19) {
        if (argsCount != 19) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20) {
        if (argsCount != 20) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21) {
        if (argsCount != 21) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22) {
        if (argsCount != 22) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22, Object p23) {
        if (argsCount != 23) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22, Object p23, Object p24) {
        if (argsCount != 24) throw new ReflectionException("Constructor parameters count mismatch!");
        try {
            return constructor.newInstance(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24);
        } catch (IllegalAccessException | InstantiationException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }
}
