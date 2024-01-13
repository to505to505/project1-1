package prediction;

import java.lang.reflect.Array;
import java.util.ArrayList;

import data.*;
import data.DataPartition;

public class DecisionTree {
    
    public int MAX_DEPTH = 10;
    public int MIN_SIZE = 10;
    public double MIN_INFORMATION_GAIN = 1; /// by information gain meant variance reduction or information gain, based on the type of the problem;
    public int MAX_LEAFS = 100; ///max amount of leafs in the tree 
    public boolean PROBLEM_TYPE = true; //true for regression, false for classification
    
    //TODO: find other hyperparameters of the decision tree
    //     and add them here
    //    (e.g. using initial variance for the number of branches of a node)
    //    (e.g. using a minimum number of students for a node to be split)
    //    (e.g. using different metrics for finding the best split i.e. Gini, Entropy, MSE, MAE etc.)

    public DataPartition dataPartition;
    public int targetIndex;
    public int leafCount = 0;

    public DecisionStump root;

    public static void main(String[] args) {
        int target = 1;
        DataPartition dataPartition = new DataPartition(new Data(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv")));
        ArrayList<Integer> courseIndexes = new ArrayList<Integer>();
        for(int i = 30; i < 34; i++)
            courseIndexes.add(i);
        courseIndexes.add(1);
        dataPartition.courseIndexes = courseIndexes;
        System.out.println(dataPartition.courseIndexes);
        
        //DecisionStump root = new DecisionStump(dataPartition, target);
        //System.out.println(root);
        DecisionTree decisionTree = new DecisionTree(dataPartition, target);
        System.out.println(decisionTree);
    }

    /**
     * Constructor for a DecisionTree that uses for each split all the target features in the data partition
     * @param dataPartition
     * @param targetIndex
     */
    public DecisionTree(DataPartition dataPartition, int targetIndex) {
        this.dataPartition = dataPartition;
        this.targetIndex = targetIndex;

        root = new DecisionStump(dataPartition, targetIndex);
        exploreNode(root, 1);
        
    }

    /**
     * Constructor for a DecisionTree that uses for each split a random subset of the target features in the data partition
     * @param dataPartition
     * @param targetIndex
     * @param problemType true if the problem is regression, false if it is classification
     */
    public DecisionTree(DataPartition dataPartition, int targetIndex, boolean problemType) {
        this.dataPartition = dataPartition;
        this.targetIndex = targetIndex;
        this.PROBLEM_TYPE = problemType;

        root = new DecisionStump(dataPartition, targetIndex);
        exploreNode(root, 1);
    }


    private void exploreNode(Node node, int depth) {
        if (node.dataPartition.studentIndexes.size() <= MIN_SIZE || depth >= MAX_DEPTH) {
            node.makeLeaf(PROBLEM_TYPE, targetIndex);
            leafCount++;
            return;
        }

        ((DecisionStump)node).procreate();

        System.out.println("Node: {" + ((DecisionStump)node).getCourseIndex() + ", " + dataPartition.data.columnNames[((DecisionStump)node).getCourseIndex()] + "}; " + ((DecisionStump)node).getThresholds());

        for (Node child : node.getChildren()) {
            exploreNode(child, depth + 1);
        }
    }

    public String toString() {
        String s = "DecisionTree: " + leafCount + " leafs" + "\n";
        s += printTree(root, "");
        return s;
    }

    public String printTree(Node node, String indent) {
        if(node.isLeaf()){
            return indent + "Leaf: " + node.getValue() + "\n";
        }
        else{
            String s = indent + "Node: {" + ((DecisionStump)node).getCourseIndex() + ", " + dataPartition.data.columnNames[((DecisionStump)node).getCourseIndex()] + "}; " + ((DecisionStump)node).getThresholds() + "\n";
            for(Node child : node.getChildren()){
                s += printTree(child, indent + "  ");
            }
            return s;
        }
    }
}