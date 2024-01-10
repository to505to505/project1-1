package prediction;

import java.util.ArrayList;

public interface IDecisionStump extends INode {
    public String getName();
    public int getColIndex();

    public double getInitialVariance();
    public double getVarianceReduction();
    public double getFinalVariance();
    public double getInformationGain();

    public ArrayList<ArrayList<Double>> getBranchProperties();
}