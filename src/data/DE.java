package data;

//import java.lang.reflect.Field;
import javax.sound.sampled.AudioFileFormat.Type;

import exceptions.TypeMismatchException;

/**
 * Data Element class with permanent type.
 */
public class DE<T> {

    public static void main(String[] args) {
        Double d = null;
        try {
            DE<Double> i = new DE<Double>(d);
            System.out.println(i.getType());
        } catch (Exception e) {
            DE<Double> i = new DE<Double>(Double.class);
            System.out.println(i.getType());
        }
        //System.out.println(i.getClass());
        //int d = i.getValue(Integer.class);
        //System.out.println(d);

        //DE<Double> f = new DE<>(1222.62);
        //System.out.println(f.getType());
        //System.out.println(f.getDouble());

        //DE<Integer> e = new DE(100L);
        //Integer d = f.getDouble().intValue();
        //System.out.println(d);
        //Double d = 100.0;
        //System.out.println(d.getClass());
        //System.out.println(e.getType());
        //i.setValue(null);
        //System.out.println(i.getValue().getClass().getName());
        //System.out.println(i.getValue());
        //i.setValue(123);
        //i.setValue(null);
        //Field f = i.getValue();
        //if(i.get() == Integer.class)
        //System.out.println("Y");
    }

    private T value;
    private final Class<T> type;
    //private static boolean[][] conversionMatrix;
    
    public DE(T value) throws NullPointerException{ //what if value is null?
        if(value==null) throw new NullPointerException("Value cannot be null; If value is null, handle error by calling the constructor specifying the type of the value");
            this.value = value;
            this.type = (Class<T>)value.getClass();
    }

    public DE(Class<T> type){
        this.type = type;
    }

    /**
     * Conversions from double to other nums cut the tail end. Either throw an exception or make sure the tail end is 0.
     * @param <V>
     * @param aType
     * @return
     */
    public <V> V getValue(Class<V> aType) throws TypeMismatchException {
        if(value == null)
            return null;
        try {
            if(Number.class.isAssignableFrom(aType) && Number.class.isAssignableFrom(this.type))
                if(aType == Double.class){
                    //assert aType == Double.class;
                    return (V)aType.cast(((Number)value).doubleValue());
                } else if(aType == Integer.class) {
                    //assert aType == Integer.class;
                    return (V)aType.cast(((Number)value).intValue());
                } else if (aType == Long.class) {
                    //assert aType == Long.class;
                    return (V)aType.cast(((Number)value).longValue());
                } else if (aType == Float.class) {
                    //assert aType == Float.class;
                    return (V)aType.cast(((Number)value).floatValue());
                } else if (aType == Byte.class) {
                    //assert aType == Byte.class;
                    return (V)aType.cast(((Number)value).byteValue());
                } else if (aType == Short.class) {
                    //assert aType == Short.class;
                    return (V)aType.cast(((Number)value).shortValue());
                }
            else if(String.class.isAssignableFrom(aType) && String.class.isAssignableFrom(this.type)){
                if (aType == String.class)
                    return (V)aType.cast(String.valueOf(value));
            } else if (Boolean.class.isAssignableFrom(aType) && Boolean.class.isAssignableFrom(this.type)) {
                if (aType == Boolean.class)
                    return (V)aType.cast((boolean)value);
            } else if (Character.class.isAssignableFrom(aType) && Character.class.isAssignableFrom(this.type)) {
                if (aType == Character.class)
                    return (V)aType.cast((char)value);
            } else
                throw new TypeMismatchException(aType, this.type);
                return null;
        } catch (ClassCastException ex) {
            System.out.println(ex);
            throw new TypeMismatchException(aType, this.type);
        }
    }

    public <V> void setValue(Class<V> aType) throws TypeMismatchException{
        if (value == null && aType == this.type)
            this.value = null;
        try {
            if(Number.class.isAssignableFrom(aType) && Number.class.isAssignableFrom(this.type))
                if(aType == Double.class){
                    //assert aType == Double.class;
                    this.value = (T)aType.cast(((Number)value).doubleValue());
                } else if(aType == Integer.class) {
                    //assert aType == Integer.class;
                    this.value = (T)aType.cast(((Number)value).intValue());
                } else if (aType == Long.class) {
                    //assert aType == Long.class;
                    this.value = (T)aType.cast(((Number)value).longValue());
                } else if (aType == Float.class) {
                    //assert aType == Float.class;
                    this.value = (T)aType.cast(((Number)value).floatValue());
                } else if (aType == Byte.class) {
                    //assert aType == Byte.class;
                    this.value = (T)aType.cast(((Number)value).byteValue());
                } else if (aType == Short.class) {
                    //assert aType == Short.class;
                    this.value = (T)aType.cast(((Number)value).shortValue());
                }
            else if(String.class.isAssignableFrom(aType) && String.class.isAssignableFrom(this.type)){
                if (aType == String.class)
                    this.value = (T)aType.cast(String.valueOf(value));
            } else if (Boolean.class.isAssignableFrom(aType) && Boolean.class.isAssignableFrom(this.type)) {
                if (aType == Boolean.class)
                    this.value = (T)aType.cast((boolean)value);
            } else if (Character.class.isAssignableFrom(aType) && Character.class.isAssignableFrom(this.type)) {
                if (aType == Character.class)
                    this.value = (T)aType.cast((char)value);
            } else
                throw new TypeMismatchException(aType, this.type);
        } catch (ClassCastException ex) {
            System.out.println(ex);
            throw new TypeMismatchException(aType, this.type);
        }
    }

    public boolean isEmpty(){
        return value == null;
    }

    public Class<T> getType() {
        return type;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /*
    public <E> E getValue(Class<E> type){
        if(value == null)
                return null;
        try {
            if(Number.class.isAssignableFrom(type) && Number.class.isAssignableFrom(this.type))
                return type.cast(value);
            else
            if(type == Integer.class || Integer.class.isAssignableFrom(type))
                return (E)Integer.valueOf((int)value);
            else if(type == Double.class ||Double.class.isAssignableFrom(type))
                return (E)Double.valueOf((double)value);
            else if(type == String.class || String.class.isAssignableFrom(type))
                return (E)String.valueOf(value);
            else if (type == Long.class || Long.class.isAssignableFrom(type))
                return (E)Long.valueOf((long)value);
            else if (type == Float.class || Float.class.isAssignableFrom(type))
                return (E)Float.valueOf((float)value);
            else if (type == Boolean.class || Boolean.class.isAssignableFrom(type))
                return (E)Boolean.valueOf((boolean)value);
            else if (type == Character.class || Character.class.isAssignableFrom(type))
                return (E)Character.valueOf((char)value);
            else if (type == Byte.class || Byte.class.isAssignableFrom(type))
                return (E)Byte.valueOf((byte)value);
            else if (type == Short.class || Short.class.isAssignableFrom(type))
                return (E)Short.valueOf((short)value);
            else
                return null;
        } catch (ClassCastException ex) {
            System.out.println(ex);
            return null;
        }
    }
    */
}
