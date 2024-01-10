package prediction;

import java.util.ArrayList;

public interface IDecisionStumpNum {
    public ArrayList<Double> getBoundryValues();
    public ArrayList<Double> getBranchVariances();
    public ArrayList<Double> getBranchMeans();
}
