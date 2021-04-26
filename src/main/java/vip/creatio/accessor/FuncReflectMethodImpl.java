package vip.creatio.accessor;

import vip.creatio.common.ReflectionException;
import vip.creatio.accessor.global.IFunctionObj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
final class FuncReflectMethodImpl<R> implements IFunctionObj<R> {

    private final Method mth;
    private final int argsCount;
    private final boolean isStatic;

    FuncReflectMethodImpl(Method mth) {
        this.mth = mth;
        this.isStatic = Modifier.isStatic(mth.getModifiers());
        this.argsCount = mth.getParameterCount() + (isStatic ? 0 : 1);
    }

    @Override
    public R invoke() {
        if (argsCount != 0) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null);
            }
            throw new ReflectionException("Method " + mth + " is not static, requires a member!");
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1) {
        if (argsCount != 1) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1);
            } else {
                return (R) mth.invoke(p1);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2) {
        if (argsCount != 2) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2);
            } else {
                return (R) mth.invoke(p1, p2);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3) {
        if (argsCount != 3) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3);
            } else {
                return (R) mth.invoke(p1, p2, p3);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4) {
        if (argsCount != 4) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (argsCount != 5) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (argsCount != 6) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        if (argsCount != 7) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        if (argsCount != 8) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        if (argsCount != 9) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10) {
        if (argsCount != 10) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11) {
        if (argsCount != 11) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12) {
        if (argsCount != 12) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13) {
        if (argsCount != 13) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14) {
        if (argsCount != 14) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15) {
        if (argsCount != 15) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16) {
        if (argsCount != 16) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17) {
        if (argsCount != 17) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18) {
        if (argsCount != 18) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19) {
        if (argsCount != 19) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20) {
        if (argsCount != 20) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21) {
        if (argsCount != 21) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22) {
        if (argsCount != 22) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22, Object p23) {
        if (argsCount != 23) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16, Object p17, Object p18, Object p19, Object p20, Object p21, Object p22, Object p23, Object p24) {
        if (argsCount != 24) throw new ReflectionException("Method parameters count mismatch!");
        try {
            if (isStatic) {
                return (R) mth.invoke(null, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24);
            } else {
                return (R) mth.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24);
            }
        } catch (IllegalAccessException e) {
            Reflection.implicitlyThrow(e);
            return null;
        } catch (InvocationTargetException e) {
            Reflection.implicitlyThrow(e.getTargetException());
            return null;
        }
    }
}
