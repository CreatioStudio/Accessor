package vip.creatio.accessor;

import vip.creatio.common.ReflectionException;

class VarReflectImpl<R> extends Var<R> {

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private final Object target;

    private final long offset;

    private final boolean isVolatile;

    private final boolean isStatic;

    VarReflectImpl(Object target, long offset, boolean isVolatile, boolean isStatic, int type, int hash) {
        super(hash, type);
        this.target = target;
        this.offset = offset;
        this.isVolatile = isVolatile;
        this.isStatic = isStatic;
    }

    @Override
    public int getInt(Object member) {
        if (type != 0) throw new ReflectionException("Wrong type of field: int");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getIntVolatile(target, offset);
            else
                return unsafe.getInt(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getIntVolatile(member, offset);
            else
                return unsafe.getInt(member, offset);
        }
    }


    @Override
    public void setInt(Object member, int value) {
        if (type != 0) throw new ReflectionException("Wrong type of field: int");
        if (isStatic) {
            if (isVolatile)
                unsafe.putIntVolatile(target, offset, value);
            else
                unsafe.putInt(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putIntVolatile(member, offset, value);
            else
                unsafe.putInt(member, offset, value);
        }
    }

    @Override
    public void setIntAtomic(Object member, int value) {
        if (type != 0) throw new ReflectionException("Wrong type of field: int");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetInt(target, offset, unsafe.getInt(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetInt(member, offset, unsafe.getInt(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public byte getByte(Object member) {
        if (type != 1) throw new ReflectionException("Wrong type of field: byte");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getByteVolatile(target, offset);
            else
                return unsafe.getByte(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getByteVolatile(member, offset);
            else
                return unsafe.getByte(member, offset);
        }
    }

    @Override
    public void setByte(Object member, byte value) {
        if (type != 1) throw new ReflectionException("Wrong type of field: byte");
        if (isStatic) {
            if (isVolatile)
                unsafe.putByteVolatile(target, offset, value);
            else
                unsafe.putByte(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putByteVolatile(member, offset, value);
            else
                unsafe.putByte(member, offset, value);
        }
    }

    @Override
    public void setByteAtomic(Object member, byte value) {
        if (type != 1) throw new ReflectionException("Wrong type of field: byte");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetByte(target, offset, unsafe.getByte(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetByte(member, offset, unsafe.getByte(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public short getShort(Object member) {
        if (type != 2) throw new ReflectionException("Wrong type of field: short");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getShortVolatile(target, offset);
            else
                return unsafe.getShort(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getShortVolatile(member, offset);
            else
                return unsafe.getShort(member, offset);
        }
    }

    @Override
    public void setShort(Object member, short value) {
        if (type != 2) throw new ReflectionException("Wrong type of field: short");
        if (isStatic) {
            if (isVolatile)
                unsafe.putShortVolatile(target, offset, value);
            else
                unsafe.putShort(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putShortVolatile(member, offset, value);
            else
                unsafe.putShort(member, offset, value);
        }
    }

    @Override
    public void setShortAtomic(Object member, short value) {
        if (type != 2) throw new ReflectionException("Wrong type of field: short");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetShort(target, offset, unsafe.getShort(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetShort(member, offset, unsafe.getShort(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public char getChar(Object member) {
        if (type != 7) throw new ReflectionException("Wrong type of field: char");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getCharVolatile(target, offset);
            else
                return unsafe.getChar(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getCharVolatile(member, offset);
            else
                return unsafe.getChar(member, offset);
        }
    }

    @Override
    public void setChar(Object member, char value) {
        if (type != 7) throw new ReflectionException("Wrong type of field: char");
        if (isStatic) {
            if (isVolatile)
                unsafe.putCharVolatile(target, offset, value);
            else
                unsafe.putChar(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putCharVolatile(member, offset, value);
            else
                unsafe.putChar(member, offset, value);
        }
    }

    @Override
    public void setCharAtomic(Object member, char value) {
        if (type != 7) throw new ReflectionException("Wrong type of field: char");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetChar(target, offset, unsafe.getChar(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetChar(member, offset, unsafe.getChar(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public long getLong(Object member) {
        if (type != 3) throw new ReflectionException("Wrong type of field: long");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getLongVolatile(target, offset);
            else
                return unsafe.getLong(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getLongVolatile(member, offset);
            else
                return unsafe.getLong(member, offset);
        }
    }

    @Override
    public void setLong(Object member, long value) {
        if (type != 3) throw new ReflectionException("Wrong type of field: long");
        if (isStatic) {
            if (isVolatile)
                unsafe.putLongVolatile(target, offset, value);
            else
                unsafe.putLong(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putLongVolatile(member, offset, value);
            else
                unsafe.putLong(member, offset, value);
        }
    }

    @Override
    public void setLongAtomic(Object member, long value) {
        if (type != 3) throw new ReflectionException("Wrong type of field: long");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetLong(target, offset, unsafe.getLong(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetLong(member, offset, unsafe.getLong(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public boolean getBoolean(Object member) {
        if (type != 4) throw new ReflectionException("Wrong type of field: boolean");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getBooleanVolatile(target, offset);
            else
                return unsafe.getBoolean(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getBooleanVolatile(member, offset);
            else
                return unsafe.getBoolean(member, offset);
        }
    }

    @Override
    public void setBoolean(Object member, boolean value) {
        if (type != 4) throw new ReflectionException("Wrong type of field: boolean");
        if (isStatic) {
            if (isVolatile)
                unsafe.putBooleanVolatile(target, offset, value);
            else
                unsafe.putBoolean(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putBooleanVolatile(member, offset, value);
            else
                unsafe.putBoolean(member, offset, value);
        }
    }

    @Override
    public void setBooleanAtomic(Object member, boolean value) {
        if (type != 4) throw new ReflectionException("Wrong type of field: boolean");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetBoolean(target, offset, unsafe.getBoolean(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetBoolean(member, offset, unsafe.getBoolean(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public float getFloat(Object member) {
        if (type != 5) throw new ReflectionException("Wrong type of field: float");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getFloatVolatile(target, offset);
            else
                return unsafe.getFloat(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getFloatVolatile(member, offset);
            else
                return unsafe.getFloat(member, offset);
        }
    }

    @Override
    public void setFloat(Object member, float value) {
        if (type != 5) throw new ReflectionException("Wrong type of field: float");
        if (isStatic) {
            if (isVolatile)
                unsafe.putFloatVolatile(target, offset, value);
            else
                unsafe.putFloat(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putFloatVolatile(member, offset, value);
            else
                unsafe.putFloat(member, offset, value);
        }
    }

    @Override
    public void setFloatAtomic(Object member, float value) {
        if (type != 5) throw new ReflectionException("Wrong type of field: float");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetFloat(target, offset, unsafe.getFloat(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetFloat(member, offset, unsafe.getFloat(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    public double getDouble(Object member) {
        if (type != 6) throw new ReflectionException("Wrong type of field: double");
        if (isStatic) {
            if (isVolatile)
                return unsafe.getDoubleVolatile(target, offset);
            else
                return unsafe.getDouble(target, offset);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                return unsafe.getDoubleVolatile(member, offset);
            else
                return unsafe.getDouble(member, offset);
        }
    }

    @Override
    public void setDouble(Object member, double value) {
        if (type != 6) throw new ReflectionException("Wrong type of field: double");
        if (isStatic) {
            if (isVolatile)
                unsafe.putDoubleVolatile(target, offset, value);
            else
                unsafe.putDouble(target, offset, value);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            if (isVolatile)
                unsafe.putDoubleVolatile(member, offset, value);
            else
                unsafe.putDouble(member, offset, value);
        }
    }

    @Override
    public void setDoubleAtomic(Object member, double value) {
        if (type != 6) throw new ReflectionException("Wrong type of field: double");
        if (isStatic) {
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetDouble(target, offset, unsafe.getDouble(member, offset), value);
            } while (!succeed);
        } else {
            if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
            boolean succeed;
            do {
                succeed = unsafe.compareAndSetDouble(member, offset, unsafe.getDouble(member, offset), value);
            } while (!succeed);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public R get(Object member) {
        if (type == -1) {
            if (isStatic) {
                if (isVolatile)
                    return (R) unsafe.getObjectVolatile(target, offset);
                else
                    return (R) unsafe.getObject(target, offset);
            } else {
                if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
                if (isVolatile)
                    return (R) unsafe.getObjectVolatile(member, offset);
                else
                    return (R) unsafe.getObject(member, offset);
            }
        } else {
            switch (type) {
                case 0:
                    return (R) (Integer) getInt(member);
                case 1:
                    return (R) (Byte) getByte(member);
                case 2:
                    return (R) (Short) getShort(member);
                case 3:
                    return (R) (Long) getLong(member);
                case 4:
                    return (R) (Boolean) getBoolean(member);
                case 5:
                    return (R) (Float) getFloat(member);
                case 6:
                    return (R) (Double) getDouble(member);
                case 7:
                    return (R) (Character) getChar(member);
                default:
                    throw new ReflectionException("Unknown type id: " + type);
            }
        }
    }

    @Override
    public void set(Object member, R value) {
        if (type == -1) {
            if (isStatic) {
                if (isVolatile)
                    unsafe.putObjectVolatile(target, offset, value);
                else
                    unsafe.putObject(target, offset, value);
            } else {
                if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
                if (isVolatile)
                    unsafe.putObjectVolatile(member, offset, value);
                else
                    unsafe.putObject(member, offset, value);
            }
        } else {
            switch (type) {
                case 0:
                    setInt(member, (Integer) value);
                case 1:
                    setByte(member, (Byte) value);
                case 2:
                    setShort(member, (Short) value);
                case 3:
                    setLong(member, (Long) value);
                case 4:
                    setBoolean(member, (Boolean) value);
                case 5:
                    setFloat(member, (Float) value);
                case 6:
                    setDouble(member, (Double) value);
                case 7:
                    setChar(member, (Character) value);
                default:
                    throw new ReflectionException("Unknown type id: " + type);
            }
        }
    }

    @Override
    public void setAtomic(Object member, R value) {
        if (type == -1) {
            if (isStatic) {
                boolean succeed;
                do {
                    succeed = unsafe.compareAndSetObject(target, offset, unsafe.getObject(member, offset), value);
                } while (!succeed);
            } else {
                if (member == null) throw new NullPointerException("Member cannot be null for non-static field!");
                boolean succeed;
                do {
                    succeed = unsafe.compareAndSetObject(member, offset, unsafe.getObject(member, offset), value);
                } while (!succeed);
            }
        } else {
            switch (type) {
                case 0:
                    setIntAtomic(member, (Integer) value);
                case 1:
                    setByteAtomic(member, (Byte) value);
                case 2:
                    setShortAtomic(member, (Short) value);
                case 3:
                    setLongAtomic(member, (Long) value);
                case 4:
                    setBooleanAtomic(member, (Boolean) value);
                case 5:
                    setFloatAtomic(member, (Float) value);
                case 6:
                    setDoubleAtomic(member, (Double) value);
                case 7:
                    setCharAtomic(member, (Character) value);
                default:
                    throw new ReflectionException("Unknown type id: " + type);
            }
        }
    }
}
