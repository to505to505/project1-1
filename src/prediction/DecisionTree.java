package prediction;

import data.DataPartition;

public class DecisionTree {
    
    public static int MAX_DEPTH = 10;
    
    //TODO: find other hyperparameters of the decision tree
    //     and add them here
    //    (e.g. using initial variance for the number of branches of a node)
    //    (e.g. using a minimum number of students for a node to be split)
    //    (e.g. using different metrics for finding the best split i.e. Gini, Entropy, MSE, MAE etc.)

    public DataPartition dataPartition;
    public int targetIndex;

    public DecisionStump root;


    /**
     * Constructor for a DecisionTree that uses for each split all the target features in the data partition
     * @param dataPartition
     * @param targetIndex
     */
    public DecisionTree(DataPartition dataPartition, int targetIndex) {
        this.dataPartition = dataPartition;
        this.targetIndex = targetIndex;

        root = new DecisionStump(dataPartition, targetIndex);
        
    }
}
