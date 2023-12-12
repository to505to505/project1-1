package exceptions;

/**
 * Exception thrown when the value of a variable representing a percentage is outside the range [0.0, 1.0]
 * @param p the value of the percentage
 */
public class PercentageOutOfRange extends Exception{
    private double p;

    public PercentageOutOfRange(double p){
        this.p = p;
    }

    public String toString(){
        return "Percentage Out Of Range Exception: " + p + " out of range [0, 1]";
    }
}
