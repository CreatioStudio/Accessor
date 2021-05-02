package vip.creatio.accessor;

import vip.creatio.common.util.ReflectionException;
import vip.creatio.accessor.global.IFunctionVoid;

class FuncAnonymousImplVoid extends Func<Void> {

    private final IFunctionVoid func;
    private final int paramsCount;

    public FuncAnonymousImplVoid(IFunctionVoid func, int paramsCount, int hash) {
        super(hash);
        this.func = func;
        this.paramsCount = paramsCount;
    }

    @Override
    public Void invoke() {
        if (paramsCount != 0) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke();
        return null;
    }

    @Override
    public Void invoke(Object p1) {
        if (paramsCount != 1) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2) {
        if (paramsCount != 2) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3) {
        if (paramsCount != 3) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4) {
        if (paramsCount != 4) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3, p4);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (paramsCount != 5) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3, p4, p5);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (paramsCount != 6) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3, p4, p5, p6);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        if (paramsCount != 7) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3, p4, p5, p6, p7);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        if (paramsCount != 8) throw new ReflectionException("Method parameters count mismatch!");
        func.invoke(p1, p2, p3, p4, p5, p6, p7, p8);
        return null;
    }

    @Override
    public Void invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object... params) {
        if (paramsCount != 8 + params.length) throw new ReflectionException("Method parameters count mismatch!");
        switch (params.length) {
            case 0:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8);
                return null;
            case 1:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0]);
                return null;
            case 2:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1]);
                return null;
            case 3:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2]);
                return null;
            case 4:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3]);
                return null;
            case 5:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4]);
                return null;
            case 6:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5]);
                return null;
            case 7:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
                return null;
            case 8:
                func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
                return null;
        }
        throw new RuntimeException("Unsupported amount of arguments: " + (8 + params.length));
    }
}
