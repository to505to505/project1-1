/**
 * Exception thrown when the value of a variable representing a percentage is outside the range [0.0, 1.0]
 */
class PercentageOutOfRange extends Exception{
    private double p;

    public PercentageOutOfRange(double p){
        this.p = p;
    }

    public String toString(){
        return "Percentage " + p + " out of range [0, 1]";
    }
}

class SampleNumberOutOfRange extends Exception{
    private double n;

    public SampleNumberOutOfRange(int n){
        this.n = n;
    }

    public String toString(){
        return "Sample number " + n + " is negative";
    }
}