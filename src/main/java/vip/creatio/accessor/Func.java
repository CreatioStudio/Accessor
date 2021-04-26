package vip.creatio.accessor;

public abstract class Func<R> {

    final int hash;

    volatile int usedTime;

    Func(int hash) {
        this.hash = hash;
    }

    public abstract R invoke();

    public abstract R invoke(Object p1);

    public abstract R invoke(Object p1, Object p2);

    public abstract R invoke(Object p1, Object p2, Object p3);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4, Object p5);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

    public abstract R invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object... params /* max 8 params */);

    public int hashCode() {
        return this.hash;
    }
}
