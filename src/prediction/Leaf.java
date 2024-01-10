package prediction;

public class Leaf extends Node implements ILeaf{
    private double value;

    public Leaf(Node parent, double value){
        super(parent);
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
