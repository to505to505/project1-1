package exceptions;

/**
 * Exception thrown when the type of a data element (DE) object does not match the type of the data column (DC) object
 */  
public class TypeMismatchException extends Exception{
    private Class<?> type1;
    private Class<?> type2;

    public TypeMismatchException(Class<?> type1, Class<?> type2){
        this.type1 = type1;
        this.type2 = type2;
    }

    public String toString(){
        return "Type mismatch Exception: " + type1 + " is incompatible with " + type2;
    }
}