package prediction;

import java.util.ArrayList;

public class DecisionStump implements IDecisionStump{
    private Node parent;
    private ArrayList<Node> children;

    private String name;
    private int colIndex;

    private double varianceReduction;
    private double totalVariance;
    private double weightedVariance;

    public Node getParent(){
        return parent;
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public String getName(){
        return name;
    }

    public int getColIndex(){
        return colIndex;
    }

    public double getVarianceReduction(){
        return varianceReduction;
    }

    public double getTotalVariance(){
        return totalVariance;
    }

    public double getWeightedVariance(){
        return weightedVariance;
    }

    public double getValue(){
        return 0;
    }
}
