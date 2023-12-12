package exceptions;

/**
 * Exception thrown when the value of variable that is meant to be positive is negative.
 * @param n the value of the variable
 */
public class SampleNumberOutOfRange extends Exception{
    private double n;

    public SampleNumberOutOfRange(int n){
        this.n = n;
    }

    public String toString(){
        return "Sample Number Out Of Range Exception: " + n + " is negative";
    }
}