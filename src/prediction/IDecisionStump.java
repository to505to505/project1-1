package prediction;

import java.util.ArrayList;

public interface IDecisionStump extends INode {
    public String getName();
    public int getColIndex();

    public double getVarianceReduction();
    public double getTotalVariance();
    public double getWeightedVariance();
}