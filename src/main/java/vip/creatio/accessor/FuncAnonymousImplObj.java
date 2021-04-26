package vip.creatio.accessor;

import vip.creatio.common.ReflectionException;
import vip.creatio.accessor.global.IFunctionObj;

class FuncAnonymousImplObj<R> extends Func<R> {

    private final IFunctionObj<R> func;
    private final int paramsCount;

    public FuncAnonymousImplObj(IFunctionObj<R> func, int paramsCount, int hash) {
        super(hash);
        this.func = func;
        this.paramsCount = paramsCount;
    }

    @Override
    public R invoke() {
        if (paramsCount != 0) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke();
    }

    @Override
    public R invoke(Object p1) {
        if (paramsCount != 1) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1);
    }

    @Override
    public R invoke(Object p1, Object p2) {
        if (paramsCount != 2) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3) {
        if (paramsCount != 3) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4) {
        if (paramsCount != 4) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3, p4);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (paramsCount != 5) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3, p4, p5);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (paramsCount != 6) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3, p4, p5, p6);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        if (paramsCount != 7) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        if (paramsCount != 8) throw new ReflectionException("Method parameters count mismatch!");
        return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object... params) {
        if (paramsCount != 8 + params.length) throw new ReflectionException("Method parameters count mismatch!");
        switch (params.length) {
            case 0:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8);
            case 1:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0]);
            case 2:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1]);
            case 3:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2]);
            case 4:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3]);
            case 5:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4]);
            case 6:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5]);
            case 7:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
            case 8:
                return func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        }
        throw new RuntimeException("Unsupported amount of arguments: " + (8 + params.length));
    }
}
