package vip.creatio.accessor;

public abstract class Var<R> {

    /**
     * 0 - int
     * 1 - byte
     * 2 - short
     * 3 - long
     * 4 - boolean
     * 5 - float
     * 6 - double
     * 7 - char
     * -1 - reference
     */
    final int type;

    public static int getTypeId(Class<?> primitives) {
        switch (primitives.getName()) {
            case "int":
                return 0;
            case "byte":
                return 1;
            case "short":
                return 2;
            case "long":
                return 3;
            case "boolean":
                return 4;
            case "float":
                return 5;
            case "double":
                return 6;
            case "char":
                return 7;
        }
        return -1;
    }

    final int hash;

    volatile int usedTime;

    Var(int hash, int type) {
        this.hash = hash;
        this.type = type;
    }
    
    public int getInt() {
        return getInt(null);
    }
    public abstract int getInt(Object member);
    public void setInt(int value) {
        setInt(null, value);
    }
    public abstract void setInt(Object member, int value);
    public void setIntAtomic(int value) {
        setIntAtomic(null, value);
    }
    public abstract void setIntAtomic(Object member, int value);

    
    public byte getByte() {
        return getByte(null);
    }
    public abstract byte getByte(Object member);
    public void setByte(byte value) {
        setByte(null, value);
    }
    public abstract void setByte(Object member, byte value);
    public void setByteAtomic(byte value) {
        setByteAtomic(null, value);
    }
    public abstract void setByteAtomic(Object member, byte value);

    
    public short getShort() {
        return getShort(null);
    }
    public abstract short getShort(Object member);
    public void setShort(short value) {
        setShort(null, value);
    }
    public abstract void setShort(Object member, short value);
    public void setShortAtomic(short value) {
        setShortAtomic(null, value);
    }
    public abstract void setShortAtomic(Object member, short value);

    
    public char getChar() {
        return getChar(null);
    }
    public abstract char getChar(Object member);
    public void setChar(char value) {
        setChar(null, value);
    }
    public abstract void setChar(Object member, char value);
    public void setCharAtomic(char value) {
        setCharAtomic(null, value);
    }
    public abstract void setCharAtomic(Object member, char value);

    
    public long getLong() {
        return getLong(null);
    }
    public abstract long getLong(Object member);
    public void setLong(long value) {
        setLong(null, value);
    }
    public abstract void setLong(Object member, long value);
    public void setLongAtomic(long value) {
        setLongAtomic(null, value);
    }
    public abstract void setLongAtomic(Object member, long value);

    
    public boolean getBoolean() {
        return getBoolean(null);
    }
    public abstract boolean getBoolean(Object member);
    public void setBoolean(boolean value) {
        setBoolean(null, value);
    }
    public abstract void setBoolean(Object member, boolean value);
    public void setBooleanAtomic(boolean value) {
        setBooleanAtomic(null, value);
    }
    public abstract void setBooleanAtomic(Object member, boolean value);

    
    public float getFloat() {
        return getFloat(null);
    }
    public abstract float getFloat(Object member);
    public void setFloat(float value) {
        setFloat(null, value);
    }
    public abstract void setFloat(Object member, float value);
    public void setFloatAtomic(float value) {
        setFloatAtomic(null, value);
    }
    public abstract void setFloatAtomic(Object member, float value);

    
    public double getDouble() {
        return getDouble(null);
    }
    public abstract double getDouble(Object member);
    public void setDouble(double value) {
        setDouble(null, value);
    }
    public abstract void setDouble(Object member, double value);
    public void setDoubleAtomic(double value) {
        setDoubleAtomic(null, value);
    }
    public abstract void setDoubleAtomic(Object member, double value);

    
    public R get() {
        return get(null);
    }
    public abstract R get(Object member);
    public void set(R value) {
        set(null, value);
    }
    public abstract void set(Object member, R value);
    public void setAtomic(R value) {
        setAtomic(null, value);
    }
    public abstract void setAtomic(Object member, R value);
    
}
