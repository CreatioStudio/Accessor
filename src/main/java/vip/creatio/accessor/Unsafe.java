package vip.creatio.accessor;

import vip.creatio.accessor.global.IFunctionObj;
import vip.creatio.accessor.global.IFunctionVoid;

import java.lang.reflect.Field;
import java.security.ProtectionDomain;

/* Mirror class of sun.misc.Unsafe or jdk.internal.Unsafe */

/**
 * A collection of methods for performing low-level, unsafe operations.
 * Although the class and all methods are public, use of this class is
 * limited because only trusted code can obtain instances of it.
 *
 * <em>Note:</em> It is the resposibility of the caller to make sure
 * arguments are checked before methods of this class are
 * called. While some rudimentary checks are performed on the input,
 * the checks are best effort and when performance is an overriding
 * priority, as when methods of this class are optimized by the
 * runtime compiler, some or all checks (if any) may be elided. Hence,
 * the caller must not rely on the checks and corresponding
 * exceptions!
 *
 * @author John R. Rose
 * @see #getUnsafe
 */
@SuppressWarnings({"unchecked", "unused"})
public class Unsafe {

    private static final Class<?> unsafeClass = UnsafeProvider.getUnsafeClass();
    private static final Object jdkUnsafe = UnsafeProvider.getUnsafe();
    private static final Unsafe theUnsafe = new Unsafe();

    private Unsafe() {
        Reflection.unsafe = this;
    }


    /**
     * Provides the caller with the capability of performing unsafe
     * operations.
     *
     * <p>The returned {@code Unsafe} object should be carefully guarded
     * by the caller, since it can be used to read and write data at arbitrary
     * memory addresses.  It must never be passed to untrusted code.
     *
     * <p>Most methods in this class are very low-level, and correspond to a
     * small number of hardware instructions (on typical machines).  Compilers
     * are encouraged to optimize these methods accordingly.
     *
     * <p>Here is a suggested idiom for using unsafe operations:
     *
     * <pre> {@code
     * class MyTrustedClass {
     *   private static final Unsafe unsafe = Unsafe.getUnsafe();
     *   ...
     *   private long myCountAddress = ...;
     *   public int getCount() { return unsafe.getByte(myCountAddress); }
     * }}</pre>
     *
     * (It may assist compilers to make the local variable {@code final}.)
     */
    public static Unsafe getUnsafe() {
        return theUnsafe;
    }


    /// peek and poke operations
    /// (compilers should optimize these to memory ops)

    // These work on object fields in the Java heap.
    // They will not work on elements of packed arrays.

    /**
     * Fetches a value from a given Java variable.
     * More specifically, fetches a field or array element within the given
     * object {@code o} at the given offset, or (if {@code o} is null)
     * from the memory address whose numerical value is the given offset.
     * <p>
     * The results are undefined unless one of the following cases is true:
     * <ul>
     * <li>The offset was obtained from {@link #objectFieldOffset} on
     * the {@link java.lang.reflect.Field} of some Java field and the object
     * referred to by {@code o} is of a class compatible with that
     * field's class.
     *
     * <li>The offset and object reference {@code o} (either null or
     * non-null) were both obtained via {@link #staticFieldOffset}
     * and {@link #staticFieldBase} (respectively) from the
     * reflective {@link Field} representation of some Java field.
     *
     * <li>The object referred to by {@code o} is an array, and the offset
     * is an integer of the form {@code B+N*S}, where {@code N} is
     * a valid index into the array, and {@code B} and {@code S} are
     * the values obtained by {@link #arrayBaseOffset} and {@link
     * #arrayIndexScale} (respectively) from the array's class.  The value
     * referred to is the {@code N}<em>th</em> element of the array.
     *
     * </ul>
     * <p>
     * If one of the above cases is true, the call references a specific Java
     * variable (field or array element).  However, the results are undefined
     * if that variable is not in fact of the type returned by this method.
     * <p>
     * This method refers to a variable by means of two parameters, and so
     * it provides (in effect) a <em>double-register</em> addressing mode
     * for Java variables.  When the object reference is null, this method
     * uses its offset as an absolute address.  This is similar in operation
     * to methods such as {@link #getInt(long)}, which provide (in effect) a
     * <em>single-register</em> addressing mode for non-Java variables.
     * However, because Java variables may have a different layout in memory
     * from non-Java variables, programmers should not assume that these
     * two addressing modes are ever equivalent.  Also, programmers should
     * remember that offsets from the double-register addressing mode cannot
     * be portably confused with longs used in the single-register addressing
     * mode.
     *
     * @param o Java heap object in which the variable resides, if any, else
     *        null
     * @param offset indication of where the variable resides in a Java heap
     *        object, if any, else a memory address locating the variable
     *        statically
     * @return the value fetched from the indicated Java variable
     * @throws RuntimeException No defined exceptions are thrown, not even
     *         {@link NullPointerException}
     */
    public int getInt(Object o, long offset) {
        return _getInt.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Integer> _getInt =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "getInt",
                    Object.class, long.class);


    /**
     * Stores a value into a given Java variable.
     * <p>
     * The first two parameters are interpreted exactly as with
     * {@link #getInt(Object, long)} to refer to a specific
     * Java variable (field or array element).  The given value
     * is stored into that variable.
     * <p>
     * The variable must be of the same type as the method
     * parameter {@code x}.
     *
     * @param o Java heap object in which the variable resides, if any, else
     *        null
     * @param offset indication of where the variable resides in a Java heap
     *        object, if any, else a memory address locating the variable
     *        statically
     * @param x the value to store into the indicated Java variable
     * @throws RuntimeException No defined exceptions are thrown, not even
     *         {@link NullPointerException}
     */
    public void putInt(Object o, long offset, int x) {
        _putInt.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putInt
            = (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putInt",
            Object.class, long.class, int.class);


    /**
     * Fetches a reference value from a given Java variable.
     * @see #getInt(Object, long)
     */
    public Object getObject(Object o, long offset) {
        return _getObject.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Object> _getObject
            = (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "getObject",
            Object.class, long.class);


    /**
     * Stores a reference value into a given Java variable.
     * <p>
     * Unless the reference {@code x} being stored is either null
     * or matches the field type, the results are undefined.
     * If the reference {@code o} is non-null, card marks or
     * other store barriers for that object (if the VM requires them)
     * are updated.
     * @see #putInt(Object, long, int)
     */
    public void putObject(Object o, long offset, Object x) {
        _putObject.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putObject =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putObject",
                    Object.class, long.class, Object.class);


    /** @see #getInt(Object, long) */
    public boolean getBoolean(Object o, long offset) {
        return _getBoolean.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Boolean> _getBoolean =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "getBoolean",
                    Object.class, long.class);

    /** @see #putInt(Object, long, int) */
    public void putBoolean(Object o, long offset, boolean x) {
        _putBoolean.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putBoolean =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putBoolean",
                    Object.class, long.class, boolean.class);


    /** @see #getInt(Object, long) */
    public byte getByte(Object o, long offset) {
        return _getByte.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Byte> _getByte =
            (IFunctionObj<Byte>) Reflection.getMethodIFunc(unsafeClass, "getByte",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putByte(Object o, long offset, byte x) {
        _putByte.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putByte =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putByte",
                    Object.class, long.class, byte.class);


    /** @see #getInt(Object, long) */
    public short getShort(Object o, long offset) {
        return _getShort.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Short> _getShort =
            (IFunctionObj<Short>) Reflection.getMethodIFunc(unsafeClass, "getShort",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putShort(Object o, long offset, short x) {
        _putShort.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putShort =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putShort",
                    Object.class, long.class, short.class);


    /** @see #getInt(Object, long) */
    public char getChar(Object o, long offset) {
        return _getChar.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Character> _getChar =
            (IFunctionObj<Character>) Reflection.getMethodIFunc(unsafeClass, "getChar",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putChar(Object o, long offset, char x) {
        _putChar.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putChar =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putChar",
                    Object.class, long.class, char.class);


    /** @see #getInt(Object, long) */
    public long getLong(Object o, long offset) {
        return _getLong.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Long> _getLong =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "getLong",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putLong(Object o, long offset, long x) {
        _putLong.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putLong =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putLong",
                    Object.class, long.class, long.class);


    /** @see #getInt(Object, long) */
    public float getFloat(Object o, long offset) {
        return _getFloat.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Float> _getFloat =
            (IFunctionObj<Float>) Reflection.getMethodIFunc(unsafeClass, "getFloat",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putFloat(Object o, long offset, float x) {
        _putFloat.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putFloat =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putFloat",
                    Object.class, long.class, float.class);


    /** @see #getInt(Object, long) */
    public double getDouble(Object o, long offset) {
        return _getDouble.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Double> _getDouble =
            (IFunctionObj<Double>) Reflection.getMethodIFunc(unsafeClass, "getDouble",
                    Object.class, long.class);

    /** @see #getInt(Object, long) */
    public void putDouble(Object o, long offset, double x) {
        _putDouble.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putDouble =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putDouble",
                    Object.class, long.class, double.class);


    /**
     * Fetches a native pointer from a given memory address.  If the address is
     * zero, or does not point into a block obtained from {@link
     * #allocateMemory}, the results are undefined.
     *
     * <p>If the native pointer is less than 64 bits wide, it is extended as
     * an unsigned number to a Java long.  The pointer may be indexed by any
     * given byte offset, simply by adding that offset (as a simple integer) to
     * the long representing the pointer.  The number of bytes actually read
     * from the target address may be determined by consulting {@link
     * #addressSize}.
     *
     * @see #allocateMemory
     * @see #getInt(Object, long)
     */
    public long getAddress(Object o, long offset) {
        return _getAddress.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Long> _getAddress =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "getAddress",
                    Object.class, long.class);

    /**
     * Stores a native pointer into a given memory address.  If the address is
     * zero, or does not point into a block obtained from {@link
     * #allocateMemory}, the results are undefined.
     *
     * <p>The number of bytes actually written at the target address may be
     * determined by consulting {@link #addressSize}.
     *
     * @see #allocateMemory
     * @see #putInt(Object, long, int)
     */
    public void putAddress(Object o, long offset, long x) {
        _putAddress.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putAddress =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putAddress",
                    Object.class, long.class, long.class);


    /**
     * Fetches a value from a given memory address.  If the address is zero, or
     * does not point into a block obtained from {@link #allocateMemory}, the
     * results are undefined.
     *
     * @see #allocateMemory
     */
    public int getInt(long offset) {
        return getInt(null, offset);
    }

    /**
     * Stores a value into a given memory address.  If the address is zero, or
     * does not point into a block obtained from {@link #allocateMemory}, the
     * results are undefined.
     *
     * @see #getInt(long)
     */
    public void putInt(long offset, int x) {
        putInt(null, offset, x);
    }


    /** @see #getInt(long) */
    public Object getObject(long offset) {
        return getObject(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putObject(long offset, Object x) {
        putObject(null, offset, x);
    }


    /** @see #getInt(long) */
    public boolean getBoolean(long offset) {
        return getBoolean(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putBoolean(long offset, boolean x) {
        putBoolean(null, offset, x);
    }


    /** @see #getInt(long) */
    public byte getByte(long offset) {
        return getByte(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putByte(long offset, byte x) {
        putByte(null, offset, x);
    }


    /** @see #getInt(long) */
    public short getShort(long offset) {
        return getShort(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putShort(long offset, short x) {
        putShort(null, offset, x);
    }


    /** @see #getInt(long) */
    public char getChar(long offset) {
        return getChar(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putChar(long offset, char x) {
        putChar(null, offset, x);
    }


    /** @see #getInt(long) */
    public long getLong(long offset) {
        return getLong(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putLong(long offset, long x) {
        putLong(null, offset, x);
    }


    /** @see #getInt(long) */
    public float getFloat(long offset) {
        return getFloat(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putFloat(long offset, float x) {
        putFloat(null, offset, x);
    }


    /** @see #getInt(long) */
    public double getDouble(long offset) {
        return getDouble(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putDouble(long offset, double x) {
        putDouble(null, offset, x);
    }


    /** @see #getInt(long) */
    public long getAddress(long offset) {
        return getAddress(null, offset);
    }

    /** @see #putInt(long, int) */
    public void putAddress(long offset, long x) {
        putAddress(null, offset, x);
    }



    // These read VM internal data.

    /**
     * Fetches an uncompressed reference value from a given native variable
     * ignoring the VM's compressed references mode.
     *
     * @param address a memory address locating the variable
     * @return the value fetched from the indicated native variable
     */
    public Object getUncompressedObject(long address) {
        return _getUncompressedObject.invoke(jdkUnsafe, address);
    }
    private static final IFunctionObj<Object> _getUncompressedObject =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "getUncompressedObject",
                    long.class);



    /// wrappers for malloc, realloc, free:

    /**
     * Allocates a new block of native memory, of the given size in bytes.  The
     * contents of the memory are uninitialized; they will generally be
     * garbage.  The resulting native pointer will never be zero, and will be
     * aligned for all value types.  Dispose of this memory by calling {@link
     * #freeMemory}, or resize it with {@link #reallocateMemory}.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if the size is negative or too large
     *         for the native size_t type
     *
     * @throws OutOfMemoryError if the allocation is refused by the system
     *
     * @see #getByte(long)
     * @see #putByte(long, byte)
     */
    public long allocateMemory(long bytes) {
        return _allocateMemory.invoke(jdkUnsafe, bytes);
    }
    private static final IFunctionObj<Long> _allocateMemory =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "allocateMemory",
                    long.class);

    /**
     * Resizes a new block of native memory, to the given size in bytes.  The
     * contents of the new block past the size of the old block are
     * uninitialized; they will generally be garbage.  The resulting native
     * pointer will be zero if and only if the requested size is zero.  The
     * resulting native pointer will be aligned for all value types.  Dispose
     * of this memory by calling {@link #freeMemory}, or resize it with {@link
     * #reallocateMemory}.  The address passed to this method may be null, in
     * which case an allocation will be performed.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if the size is negative or too large
     *         for the native size_t type
     *
     * @throws OutOfMemoryError if the allocation is refused by the system
     *
     * @see #allocateMemory
     */
    public long reallocateMemory(long address, long bytes) {
        return _reallocateMemory.invoke(jdkUnsafe, address, bytes);
    }
    private static final IFunctionObj<Long> _reallocateMemory =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "reallocateMemory",
                    long.class, long.class);


    /**
     * Sets all bytes in a given block of memory to a fixed value
     * (usually zero).
     *
     * <p>This method determines a block's base address by means of two parameters,
     * and so it provides (in effect) a <em>double-register</em> addressing mode,
     * as discussed in {@link #getInt(Object,long)}.  When the object reference is null,
     * the offset supplies an absolute base address.
     *
     * <p>The stores are in coherent (atomic) units of a size determined
     * by the address and length parameters.  If the effective address and
     * length are all even modulo 8, the stores take place in 'long' units.
     * If the effective address and length are (resp.) even modulo 4 or 2,
     * the stores take place in units of 'int' or 'short'.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if any of the arguments is invalid
     *
     * @since 1.7
     */
    public void setMemory(Object o, long offset, long bytes, byte value) {
        _setMemory.invoke(jdkUnsafe, o, offset, bytes, value);
    }
    private static final IFunctionVoid _setMemory =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "setMemory",
                    Object.class, long.class, long.class, byte.class);

    /**
     * Sets all bytes in a given block of memory to a fixed value
     * (usually zero).  This provides a <em>single-register</em> addressing mode,
     * as discussed in {@link #getInt(Object,long)}.
     *
     * <p>Equivalent to {@code setMemory(null, address, bytes, value)}.
     */
    public void setMemory(long offset, long bytes, byte value) {
        _setMemory2.invoke(jdkUnsafe, offset, bytes, value);
    }
    private static final IFunctionVoid _setMemory2 =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "setMemory",
                    long.class, long.class, byte.class);


    /**
     * Sets all bytes in a given block of memory to a copy of another
     * block.
     *
     * <p>This method determines each block's base address by means of two parameters,
     * and so it provides (in effect) a <em>double-register</em> addressing mode,
     * as discussed in {@link #getInt(Object,long)}.  When the object reference is null,
     * the offset supplies an absolute base address.
     *
     * <p>The transfers are in coherent (atomic) units of a size determined
     * by the address and length parameters.  If the effective addresses and
     * length are all even modulo 8, the transfer takes place in 'long' units.
     * If the effective addresses and length are (resp.) even modulo 4 or 2,
     * the transfer takes place in units of 'int' or 'short'.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if any of the arguments is invalid
     *
     * @since 1.7
     */
    public void copyMemory(Object srcBase, long srcOffset,
                           Object destBase, long destOffset,
                           long bytes) {
        _copyMemory.invoke(jdkUnsafe, srcBase, srcOffset, destBase, destOffset, bytes);
    }
    private static final IFunctionVoid _copyMemory =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "copyMemory",
                    Object.class, long.class, Object.class, long.class, long.class);

    /**
     * Sets all bytes in a given block of memory to a copy of another
     * block.  This provides a <em>single-register</em> addressing mode,
     * as discussed in {@link #getInt(Object,long)}.
     *
     * Equivalent to {@code copyMemory(null, srcAddress, null, destAddress, bytes)}.
     */
    public void copyMemory(long srcAddress, long destAddress, long bytes) {
        _copyMemory2.invoke(jdkUnsafe, srcAddress, destAddress, bytes);
    }
    private static final IFunctionVoid _copyMemory2 =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "copyMemory",
                    long.class, long.class, long.class);


    /**
     * Copies all elements from one block of memory to another block,
     * *unconditionally* byte swapping the elements on the fly.
     *
     * <p>This method determines each block's base address by means of two parameters,
     * and so it provides (in effect) a <em>double-register</em> addressing mode,
     * as discussed in {@link #getInt(Object,long)}.  When the object reference is null,
     * the offset supplies an absolute base address.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if any of the arguments is invalid
     *
     * @since 9
     */
    public void copySwapMemory(Object srcBase, long srcOffset,
                               Object destBase, long destOffset,
                               long bytes) {
        _copySwapMemory.invoke(jdkUnsafe, srcBase, srcOffset, destBase, destOffset, bytes);
    }
    private static final IFunctionVoid _copySwapMemory =
            (IFunctionVoid) Reflection.getMethodRestricted(9, unsafeClass, "copyMemory",
                    Object.class, long.class, Object.class, long.class, long.class);

    /**
     * Copies all elements from one block of memory to another block, byte swapping the
     * elements on the fly.
     *
     * This provides a <em>single-register</em> addressing mode, as
     * discussed in {@link #getInt(Object,long)}.
     *
     * Equivalent to {@code copySwapMemory(null, srcAddress, null, destAddress, bytes, elemSize)}.
     */
    public void copySwapMemory(long srcAddress, long destAddress, long bytes) {
        copySwapMemory(null, srcAddress, null, destAddress, bytes);
    }


    /**
     * Disposes of a block of native memory, as obtained from {@link
     * #allocateMemory} or {@link #reallocateMemory}.  The address passed to
     * this method may be null, in which case no action is taken.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if any of the arguments is invalid
     *
     * @see #allocateMemory
     */
    public void freeMemory(long address) {
        _freeMemory.invoke(jdkUnsafe, address);
    }
    private static final IFunctionVoid _freeMemory =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "freeMemory",
                    long.class);



    /// random queries

    /**
     * This constant differs from all results that will ever be returned from
     * {@link #staticFieldOffset}, {@link #objectFieldOffset},
     * or {@link #arrayBaseOffset}.
     */
    public static final int INVALID_FIELD_OFFSET = -1;


    /**
     * Reports the location of a given field in the storage allocation of its
     * class.  Do not expect to perform any sort of arithmetic on this offset;
     * it is just a cookie which is passed to the unsafe heap memory accessors.
     *
     * <p>Any given field will always have the same offset and base, and no
     * two distinct fields of the same class will ever have the same offset
     * and base.
     *
     * <p>As of 1.4.1, offsets for fields are represented as long values,
     * although the Sun JVM does not use the most significant 32 bits.
     * However, JVM implementations which store static fields at absolute
     * addresses can use long offsets and null base pointers to express
     * the field locations in a form usable by {@link #getInt(Object,long)}.
     * Therefore, code which will be ported to such JVMs on 64-bit platforms
     * must preserve all bits of static field offsets.
     * @see #getInt(Object, long)
     */
    public long objectFieldOffset(Field f) {
        return _objectFieldOffset.invoke(jdkUnsafe, f);
    }
    private static final IFunctionObj<Long> _objectFieldOffset =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "objectFieldOffset",
                    Field.class);

    /**
     * Reports the location of the field with a given name in the storage
     * allocation of its class.
     *
     * @throws NullPointerException if any parameter is {@code null}.
     * @throws InternalError if there is no field named {@code name} declared
     *         in class {@code c}, i.e., if {@code c.getDeclaredField(name)}
     *         would throw {@code java.lang.NoSuchFieldException}.
     *
     * @see #objectFieldOffset(Field)
     */
    public long objectFieldOffset(Class<?> c, String name) {
        return _objectFieldOffset2.invoke(jdkUnsafe, c, name);
    }
    private static final IFunctionObj<Long> _objectFieldOffset2 =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "objectFieldOffset",
                    Class.class, String.class);


    /**
     * Reports the location of a given static field, in conjunction with {@link
     * #staticFieldBase}.
     * <p>Do not expect to perform any sort of arithmetic on this offset;
     * it is just a cookie which is passed to the unsafe heap memory accessors.
     *
     * <p>Any given field will always have the same offset, and no two distinct
     * fields of the same class will ever have the same offset.
     *
     * <p>As of 1.4.1, offsets for fields are represented as long values,
     * although the Sun JVM does not use the most significant 32 bits.
     * It is hard to imagine a JVM technology which needs more than
     * a few bits to encode an offset within a non-array object,
     * However, for consistency with other methods in this class,
     * this method reports its result as a long value.
     * @see #getInt(Object, long)
     */
    public long staticFieldOffset(Field f) {
        return _staticFieldOffset.invoke(jdkUnsafe, f);
    }
    private static final IFunctionObj<Long> _staticFieldOffset =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "staticFieldOffset",
                    Field.class);

    /**
     * Reports the location of a given static field, in conjunction with {@link
     * #staticFieldOffset}.
     * <p>Fetch the base "Object", if any, with which static fields of the
     * given class can be accessed via methods like {@link #getInt(Object,
     * long)}.  This value may be null.  This value may refer to an object
     * which is a "cookie", not guaranteed to be a real Object, and it should
     * not be used in any way except as argument to the get and put routines in
     * this class.
     */
    public Object staticFieldBase(Field f) {
        return _staticFieldBase.invoke(jdkUnsafe, f);
    }
    private static final IFunctionObj<Object> _staticFieldBase =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "staticFieldBase",
                    Field.class);


    /**
     * Detects if the given class may need to be initialized. This is often
     * needed in conjunction with obtaining the static field base of a
     * class.
     * @return false only if a call to {@code ensureClassInitialized} would have no effect
     */
    public boolean shouldBeInitialized(Class<?> c) {
        return _shouldBeInitialized.invoke(jdkUnsafe, c);
    }
    private static final IFunctionObj<Boolean> _shouldBeInitialized =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "shouldBeInitialized",
                    Class.class);

    /**
     * Ensures the given class has been initialized. This is often
     * needed in conjunction with obtaining the static field base of a
     * class.
     */
    public void ensureClassInitialized(Class<?> c) {
        _ensureClassInitialized.invoke(jdkUnsafe, c);
    }
    private static final IFunctionVoid _ensureClassInitialized =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "ensureClassInitialized",
                    Class.class);


    /**
     * Reports the offset of the first element in the storage allocation of a
     * given array class.  If {@link #arrayIndexScale} returns a non-zero value
     * for the same class, you may use that scale factor, together with this
     * base offset, to form new offsets to access elements of arrays of the
     * given class.
     *
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public int arrayBaseOffset(Class<?> arrayClass) {
        return _arrayBaseOffset.invoke(jdkUnsafe, arrayClass);
    }
    private static final IFunctionObj<Integer> _arrayBaseOffset =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "arrayBaseOffset",
                    Class.class);

    /** The value of {@code arrayBaseOffset(boolean[].class)} */
    public static final int ARRAY_BOOLEAN_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(boolean[].class);

    /** The value of {@code arrayBaseOffset(byte[].class)} */
    public static final int ARRAY_BYTE_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(byte[].class);

    /** The value of {@code arrayBaseOffset(short[].class)} */
    public static final int ARRAY_SHORT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(short[].class);

    /** The value of {@code arrayBaseOffset(char[].class)} */
    public static final int ARRAY_CHAR_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(char[].class);

    /** The value of {@code arrayBaseOffset(int[].class)} */
    public static final int ARRAY_INT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(int[].class);

    /** The value of {@code arrayBaseOffset(long[].class)} */
    public static final int ARRAY_LONG_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(long[].class);

    /** The value of {@code arrayBaseOffset(float[].class)} */
    public static final int ARRAY_FLOAT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(float[].class);

    /** The value of {@code arrayBaseOffset(double[].class)} */
    public static final int ARRAY_DOUBLE_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(double[].class);

    /** The value of {@code arrayBaseOffset(Object[].class)} */
    public static final int ARRAY_OBJECT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(Object[].class);

    /**
     * Reports the scale factor for addressing elements in the storage
     * allocation of a given array class.  However, arrays of "narrow" types
     * will generally not work properly with accessors like {@link
     * #getByte(Object, long)}, so the scale factor for such classes is reported
     * as zero.
     *
     * @see #arrayBaseOffset
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public int arrayIndexScale(Class<?> arrayClass) {
        return _arrayIndexScale.invoke(jdkUnsafe, arrayClass);
    }
    private static final IFunctionObj<Integer> _arrayIndexScale =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "arrayIndexScale",
                    Class.class);

    /** The value of {@code arrayIndexScale(boolean[].class)} */
    public static final int ARRAY_BOOLEAN_INDEX_SCALE
            = theUnsafe.arrayIndexScale(boolean[].class);

    /** The value of {@code arrayIndexScale(byte[].class)} */
    public static final int ARRAY_BYTE_INDEX_SCALE
            = theUnsafe.arrayIndexScale(byte[].class);

    /** The value of {@code arrayIndexScale(short[].class)} */
    public static final int ARRAY_SHORT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(short[].class);

    /** The value of {@code arrayIndexScale(char[].class)} */
    public static final int ARRAY_CHAR_INDEX_SCALE
            = theUnsafe.arrayIndexScale(char[].class);

    /** The value of {@code arrayIndexScale(int[].class)} */
    public static final int ARRAY_INT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(int[].class);

    /** The value of {@code arrayIndexScale(long[].class)} */
    public static final int ARRAY_LONG_INDEX_SCALE
            = theUnsafe.arrayIndexScale(long[].class);

    /** The value of {@code arrayIndexScale(float[].class)} */
    public static final int ARRAY_FLOAT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(float[].class);

    /** The value of {@code arrayIndexScale(double[].class)} */
    public static final int ARRAY_DOUBLE_INDEX_SCALE
            = theUnsafe.arrayIndexScale(double[].class);

    /** The value of {@code arrayIndexScale(Object[].class)} */
    public static final int ARRAY_OBJECT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(Object[].class);


    /**
     * Reports the size in bytes of a native pointer, as stored via {@link
     * #putAddress}.  This value will be either 4 or 8.  Note that the sizes of
     * other primitive types (as stored in native memory blocks) is determined
     * fully by their information content.
     */
    public int addressSize() {
        return _addressSize.invoke(jdkUnsafe);
    }
    private static final IFunctionObj<Integer> _addressSize =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "addressSize");

    /**
     * Reports the size in bytes of a native memory page (whatever that is).
     * This value will always be a power of two.
     */
    public int pageSize() {
        return _pageSize.invoke(jdkUnsafe);
    }
    private static final IFunctionObj<Integer> _pageSize =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "pageSize");



    /// random trusted operations from JNI:

    /**
     * Tells the VM to define a class, without security checks.  By default, the
     * class loader and protection domain come from the caller's class.
     */
    public Class<?> defineClass(String name, byte[] b, int off, int len,
                           ClassLoader loader,
                           ProtectionDomain protectionDomain) {
        return _defineClass.invoke(jdkUnsafe, name, b, off, len, loader, protectionDomain);
    }
    private static final IFunctionObj<Class<?>> _defineClass =
            (IFunctionObj<Class<?>>) Reflection.getMethodIFunc(unsafeClass, "defineClass",
                    String.class, byte[].class, int.class, int.class, ClassLoader.class, ProtectionDomain.class);

    /**
     * Defines a class but does not make it known to the class loader or system dictionary.
     * <p>
     * For each CP entry, the corresponding CP patch must either be null or have
     * the a format that matches its tag:
     * <ul>
     * <li>Integer, Long, Float, Double: the corresponding wrapper object type from java.lang
     * <li>Utf8: a string (must have suitable syntax if used as signature or name)
     * <li>Class: any java.lang.Class object
     * <li>String: any object (not just a java.lang.String)
     * <li>InterfaceMethodRef: (NYI) a method handle to invoke on that call site's arguments
     * </ul>
     * @param hostClass context for linkage, access control, protection domain, and class loader
     * @param data      bytes of a class file
     * @param cpPatches where non-null entries exist, they replace corresponding CP entries in data
     */
    public Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches) {
        return _defineAnonymousClass.invoke(jdkUnsafe, hostClass, data, cpPatches);
    }
    private static final IFunctionObj<Class<?>> _defineAnonymousClass =
            (IFunctionObj<Class<?>>) Reflection.getMethodIFunc(unsafeClass, "defineAnonymousClass",
                    Class.class, byte[].class, Object[].class);


    /**
     * Allocates an instance but does not run any constructor.
     * Initializes the class if it has not yet been.
     */
    public Object allocateInstance(Class<?> cls) throws InstantiationException {
        return _allocateInstance.invoke(jdkUnsafe, cls);
    }
    private static final IFunctionObj<Object> _allocateInstance =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "allocateInstance",
                    Class.class);

    /**
     * Allocates an array of a given type, but does not do zeroing.
     * <p>
     * This method should only be used in the very rare cases where a high-performance code
     * overwrites the destination array completely, and compilers cannot assist in zeroing elimination.
     * In an overwhelming majority of cases, a normal Java allocation should be used instead.
     * <p>
     * Users of this method are <b>required</b> to overwrite the initial (garbage) array contents
     * before allowing untrusted code, or code in other threads, to observe the reference
     * to the newly allocated array. In addition, the publication of the array reference must be
     * safe according to the Java Memory Model requirements.
     * <p>
     * The safest approach to deal with an uninitialized array is to keep the reference to it in local
     * variable at least until the initialization is complete, and then publish it <b>once</b>, either
     * by writing it to a <em>volatile</em> field, or storing it into a <em>final</em> field in constructor,
     * or issuing a {@link #storeFence} before publishing the reference.
     * <p>
     * @implnote This method can only allocate primitive arrays, to avoid garbage reference
     * elements that could break heap integrity.
     *
     * @param componentType array component type to allocate
     * @param length array size to allocate
     * @throws IllegalArgumentException if component type is null, or not a primitive class;
     *                                  or the length is negative
     */
    public Object allocateUninitializedArray(Class<?> componentType, int length) {
        return _allocateUninitializedArray.invoke(jdkUnsafe, componentType, length);
    }
    private static final IFunctionObj<Object> _allocateUninitializedArray =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "allocateUninitializedArray",
                    Class.class, int.class);


    /** Throws the exception without telling the verifier. */
    public void throwException(Throwable ee) {
        _throwException.invoke(jdkUnsafe, ee);
    }
    private static final IFunctionVoid _throwException =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "throwException",
                    Throwable.class);


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetObject(Object o, long offset,
                                       Object expected,
                                       Object x) {
        return _compareAndSetObject.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Boolean> _compareAndSetObject =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "compareAndSetObject",
                    Object.class, long.class, Object.class, Object.class);

    public Object compareAndExchangeObject(Object o, long offset,
                                            Object expected,
                                            Object x) {
        return _compareAndExchangeObject.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Object> _compareAndExchangeObject =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "compareAndExchangeObject",
                    Object.class, long.class, Object.class, Object.class);


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetInt(Object o, long offset,
                                    int expected,
                                    int x) {
        return _compareAndSetInt.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Boolean> _compareAndSetInt =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "compareAndSetInt",
                    Object.class, long.class, int.class, int.class);

    public int compareAndExchangeInt(Object o, long offset,
                                     int expected,
                                     int x) {
        return _compareAndExchangeInt.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Integer> _compareAndExchangeInt =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "compareAndExchangeInt",
                    Object.class, long.class, int.class, int.class);


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetByte(Object o, long offset,
                                     byte expected,
                                     byte x) {
        return compareAndExchangeByte(o, offset, expected, x) == expected;
    }

    public byte compareAndExchangeByte(Object o, long offset,
                                       byte expected,
                                       byte x) {
        return _compareAndExchangeByte.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Byte> _compareAndExchangeByte =
            (IFunctionObj<Byte>) Reflection.getMethodIFunc(unsafeClass, "compareAndExchangeByte",
                    Object.class, long.class, byte.class, byte.class);


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetShort(Object o, long offset,
                                      short expected,
                                      short x) {
        return compareAndExchangeShort(o, offset, expected, x) == expected;
    }

    public short compareAndExchangeShort(Object o, long offset,
                                        short expected,
                                        short x) {
        return _compareAndExchangeShort.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Short> _compareAndExchangeShort =
            (IFunctionObj<Short>) Reflection.getMethodIFunc(unsafeClass, "compareAndExchangeShort",
                    Object.class, long.class, short.class, short.class);


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetChar(Object o, long offset,
                                     char expected,
                                     char x) {
        return compareAndSetShort(o, offset, (short) expected, (short) x);
    }

    public char compareAndExchangeChar(Object o, long offset,
                                      char expected,
                                      char x) {
        return (char) compareAndExchangeShort(o, offset, (short) expected, (short) x);
    }


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetBoolean(Object o, long offset,
                                        boolean expected,
                                        boolean x) {
        return compareAndSetByte(o, offset,
                expected ? (byte) 1 : (byte) 0,
                x ? (byte) 1 : (byte) 0);
    }

    public boolean compareAndExchangeBoolean(Object o, long offset,
                                             boolean expected,
                                             boolean x) {
        return compareAndExchangeByte(o, offset,
                expected ? (byte) 1 : (byte) 0,
                x ? (byte) 1 : (byte) 0) != 0;
    }


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetFloat(Object o, long offset,
                                      float expected,
                                      float x) {
        return compareAndSetInt(o, offset,
                Float.floatToRawIntBits(expected),
                Float.floatToRawIntBits(x));
    }

    public float compareAndExchangeFloat(Object o, long offset,
                                         float expected,
                                         float x) {
        return Float.intBitsToFloat(compareAndExchangeInt(o, offset,
                Float.floatToRawIntBits(expected),
                Float.floatToRawIntBits(x)));
    }


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetDouble(Object o, long offset,
                                             double expected,
                                             double x) {
        return compareAndSetLong(o, offset,
                Double.doubleToRawLongBits(expected),
                Double.doubleToRawLongBits(x));
    }

    public double compareAndExchangeDouble(Object o, long offset,
                                                 double expected,
                                                 double x) {
        return Double.longBitsToDouble(compareAndExchangeLong(o, offset,
                Double.doubleToRawLongBits(expected),
                Double.doubleToRawLongBits(x)));
    }


    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetLong(Object o, long offset,
                                     long expected,
                                     long x) {
        return _compareAndSetLong.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Boolean> _compareAndSetLong =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "compareAndSetLong",
                    Object.class, long.class, long.class, long.class);

    public long compareAndExchangeLong(Object o, long offset,
                                      long expected,
                                      long x) {
        return _compareAndExchangeLong.invoke(jdkUnsafe, o, offset, expected, x);
    }
    private static final IFunctionObj<Long> _compareAndExchangeLong =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "compareAndExchangeLong",
                    Object.class, long.class, long.class, long.class);


    /**
     * Fetches a reference value from a given Java variable, with volatile
     * load semantics. Otherwise identical to {@link #getObject(Object, long)}
     */
    public Object getObjectVolatile(Object o, long offset) {
        return _getObjectVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Object> _getObjectVolatile =
            (IFunctionObj<Object>) Reflection.getMethodIFunc(unsafeClass, "getObjectVolatile",
                    Object.class, long.class);

    /**
     * Stores a reference value into a given Java variable, with
     * volatile store semantics. Otherwise identical to {@link #putObject(Object, long, Object)}
     */
    public void putObjectVolatile(Object o, long offset, Object x) {
        _putObjectVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putObjectVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putObjectVolatile",
                    Object.class, long.class, Object.class);


    /** Volatile version of {@link #getInt(Object, long)}  */
    public int getIntVolatile(Object o, long offset) {
        return _getIntVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Integer> _getIntVolatile =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "getIntVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putInt(Object, long, int)}  */
    public void putIntVolatile(Object o, long offset, int x) {
        _putIntVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putIntVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putIntVolatile",
                    Object.class, long.class, int.class);


    /** Volatile version of {@link #getBoolean(Object, long)}  */
    public boolean getBooleanVolatile(Object o, long offset) {
        return _getBooleanVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Boolean> _getBooleanVolatile =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "getBooleanVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putBoolean(Object, long, boolean)}  */
    public void putBooleanVolatile(Object o, long offset, boolean x) {
        _putBooleanVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putBooleanVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putBooleanVolatile",
                    Object.class, long.class, boolean.class);


    /** Volatile version of {@link #getByte(Object, long)}  */
    public byte getByteVolatile(Object o, long offset) {
        return _getByteVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Byte> _getByteVolatile =
            (IFunctionObj<Byte>) Reflection.getMethodIFunc(unsafeClass, "getByteVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putByte(Object, long, byte)}  */
    public void putByteVolatile(Object o, long offset, byte x) {
        _putByteVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putByteVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putByteVolatile",
                    Object.class, long.class, byte.class);


    /** Volatile version of {@link #getShort(Object, long)}  */
    public short getShortVolatile(Object o, long offset) {
        return _getShortVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Short> _getShortVolatile =
            (IFunctionObj<Short>) Reflection.getMethodIFunc(unsafeClass, "getShortVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putShort(Object, long, short)}  */
    public void putShortVolatile(Object o, long offset, short x) {
        _putShortVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putShortVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putShortVolatile",
                    Object.class, long.class, short.class);


    /** Volatile version of {@link #getChar(Object, long)}  */
    public char getCharVolatile(Object o, long offset) {
        return _getCharVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Character> _getCharVolatile =
            (IFunctionObj<Character>) Reflection.getMethodIFunc(unsafeClass, "getCharVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putChar(Object, long, char)}  */
    public void putCharVolatile(Object o, long offset, char x) {
        _putCharVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putCharVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putCharVolatile",
                    Object.class, long.class, char.class);


    /** Volatile version of {@link #getLong(Object, long)}  */
    public long getLongVolatile(Object o, long offset) {
        return _getLongVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Long> _getLongVolatile =
            (IFunctionObj<Long>) Reflection.getMethodIFunc(unsafeClass, "getLongVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putLong(Object, long, long)}  */
    public void putLongVolatile(Object o, long offset, long x) {
        _putLongVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putLongVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putLongVolatile",
                    Object.class, long.class, long.class);


    /** Volatile version of {@link #getFloat(Object, long)}  */
    public float getFloatVolatile(Object o, long offset) {
        return _getFloatVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Float> _getFloatVolatile =
            (IFunctionObj<Float>) Reflection.getMethodIFunc(unsafeClass, "getFloatVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putFloat(Object, long, float)}  */
    public void putFloatVolatile(Object o, long offset, float x) {
        _putFloatVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putFloatVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putFloatVolatile",
                    Object.class, long.class, float.class);


    /** Volatile version of {@link #getDouble(Object, long)}  */
    public double getDoubleVolatile(Object o, long offset) {
        return _getDoubleVolatile.invoke(jdkUnsafe, o, offset);
    }
    private static final IFunctionObj<Double> _getDoubleVolatile =
            (IFunctionObj<Double>) Reflection.getMethodIFunc(unsafeClass, "getDoubleVolatile",
                    Object.class, long.class);

    /** Volatile version of {@link #putDouble(Object, long, double)}  */
    public void putDoubleVolatile(Object o, long offset, double x) {
        _putDoubleVolatile.invoke(jdkUnsafe, o, offset, x);
    }
    private static final IFunctionVoid _putDoubleVolatile =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "putDoubleVolatile",
                    Object.class, long.class, double.class);


    /**
     * Unblocks the given thread blocked on {@code park}, or, if it is
     * not blocked, causes the subsequent call to {@code park} not to
     * block.  Note: this operation is "unsafe" solely because the
     * caller must somehow ensure that the thread has not been
     * destroyed. Nothing special is usually required to ensure this
     * when called from Java (in which there will ordinarily be a live
     * reference to the thread) but this is not nearly-automatically
     * so when calling from native code.
     *
     * @param thread the thread to unpark.
     */
    public void unpark(Thread thread) {
        _unpark.invoke(jdkUnsafe, thread);
    }
    private static final IFunctionVoid _unpark =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "unpark",
                    Object.class);

    /**
     * Blocks current thread, returning when a balancing
     * {@code unpark} occurs, or a balancing {@code unpark} has
     * already occurred, or the thread is interrupted, or, if not
     * absolute and time is not zero, the given time nanoseconds have
     * elapsed, or if absolute, the given deadline in milliseconds
     * since Epoch has passed, or spuriously (i.e., returning for no
     * "reason"). Note: This operation is in the Unsafe class only
     * because {@code unpark} is, so it would be strange to place it
     * elsewhere.
     */
    public void park(boolean isAbsolute, long time) {
        _park.invoke(jdkUnsafe, isAbsolute, time);
    }
    private static final IFunctionVoid _park =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "park",
                    boolean.class, long.class);


    /**
     * Gets the load average in the system run queue assigned
     * to the available processors averaged over various periods of time.
     * This method retrieves the given {@code nelem} samples and
     * assigns to the elements of the given {@code loadavg} array.
     * The system imposes a maximum of 3 samples, representing
     * averages over the last 1,  5,  and  15 minutes, respectively.
     *
     * @param loadavg an array of double of size nelems
     * @param nelems the number of samples to be retrieved and
     *        must be 1 to 3.
     *
     * @return the number of samples actually retrieved; or -1
     *         if the load average is unobtainable.
     */
    public int getLoadAverage(double[] loadavg, int nelems) {
        return _getLoadAverage.invoke(jdkUnsafe, loadavg, nelems);
    }
    private static final IFunctionObj<Integer> _getLoadAverage =
            (IFunctionObj<Integer>) Reflection.getMethodIFunc(unsafeClass, "getLoadAverage",
                    double[].class, int.class);



    /**
     * Ensures that loads before the fence will not be reordered with loads and
     * stores after the fence; a "LoadLoad plus LoadStore barrier".
     *
     * Corresponds to C11 atomic_thread_fence(memory_order_acquire)
     * (an "acquire fence").
     *
     * A pure LoadLoad fence is not provided, since the addition of LoadStore
     * is almost always desired, and most current hardware instructions that
     * provide a LoadLoad barrier also provide a LoadStore barrier for free.
     * @since 1.8
     */
    public void loadFence() {
        _loadFence.invoke(jdkUnsafe);
    }
    private static final IFunctionVoid _loadFence =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "loadFence");

    /**
     * Ensures that loads and stores before the fence will not be reordered with
     * stores after the fence; a "StoreStore plus LoadStore barrier".
     *
     * Corresponds to C11 atomic_thread_fence(memory_order_release)
     * (a "release fence").
     *
     * A pure StoreStore fence is not provided, since the addition of LoadStore
     * is almost always desired, and most current hardware instructions that
     * provide a StoreStore barrier also provide a LoadStore barrier for free.
     * @since 1.8
     */
    public void storeFence() {
        _storeFence.invoke(jdkUnsafe);
    }
    private static final IFunctionVoid _storeFence =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "storeFence");

    /**
     * Ensures that loads and stores before the fence will not be reordered
     * with loads and stores after the fence.  Implies the effects of both
     * loadFence() and storeFence(), and in addition, the effect of a StoreLoad
     * barrier.
     *
     * Corresponds to C11 atomic_thread_fence(memory_order_seq_cst).
     * @since 1.8
     */
    public void fullFence() {
        _fullFence.invoke(jdkUnsafe);
    }
    private static final IFunctionVoid _fullFence =
            (IFunctionVoid) Reflection.getMethodIFunc(unsafeClass, "fullFence");


    /**
     * @return Returns true if the native byte ordering of this
     * platform is big-endian, false if it is little-endian.
     */
    public boolean isBigEndian() {
        return _isBigEndian.invoke(jdkUnsafe);
    }
    private static final IFunctionObj<Boolean> _isBigEndian =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "isBigEndian");


    /**
     * @return Returns true if this platform is capable of performing
     * accesses at addresses which are not aligned for the type of the
     * primitive type being accessed, false otherwise.
     */
    public boolean unalignedAccess() {
        return _unalignedAccess.invoke(jdkUnsafe);
    }
    private static final IFunctionObj<Boolean> _unalignedAccess =
            (IFunctionObj<Boolean>) Reflection.getMethodIFunc(unsafeClass, "unalignedAccess");
}
