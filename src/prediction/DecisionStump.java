package prediction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collection;
import java.util.Collections;

import data.*;
import utility.Combinations;


/**
 * Class that represents a numerical decision stump.
 * It finds the best split for a given course and target, and stores the split thresholds and branch properties.
 * It also computes the mean squared error of the split.
 * It makes use of a DataPartition object to store the data partition.
 */
public class DecisionStump extends Node implements IDecisionStump {


    private static final int STUMP_WIDTH = 3; //number of branches //does not influence categorical splits
    
    private Node parent;

    private String name;
    private int colIndex;

    private int courseIndex;
    private int targetIndex;

    //remember to first call computeChildrenDataPartitions() before calling getChildrenDataPartitions()
    private ArrayList<DataPartition> childrenDataPartitions;

    private double initialVariance;
    private double varianceReduction = 0;
    private double finalVariance;

    private double informationGain = 0;

    private double initialMAE;
    private double maeReduction = 0; 
    private double finalMAE;

    private ArrayList<Double> thresholds;
    private ArrayList<ArrayList<Double>> branchProperties; // [0] =count , [1] = mean, [2] = variance
    private int[][] rightPredictions;
    private double[] entropy;


    // select which functions we use to find best split
    public String branchFunction = "mse";



    /**
     * Test method
     * @param args
     */
    public static void main(String[] args) { //!TSO-010 12/10, PLO-132 18/16, BKO-801 21/19

        Data data = new Data(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv"));
        DecisionStump ds = new DecisionStump(data, 31, 1);
        System.out.println(ds);
        ds.computeChildrenDataPartitions();
        
        /*
        for(int i = 0; i < data.columnNames.length-4; i++){
            for(int j = 0; j < data.columnNames.length-4; j++){
                if(i != 10 && j != 10 && i != 16 && j != 16 && i != 19 && j != 19){
                    DecisionStump da = new DecisionStump(data, i, j);
                    System.out.println(da);
                }
            }
            System.out.println();
        }
        */
    }

    /**
     * Constructs a decision stump that finds the best split for a target feature given a DataPartition, and stores the split thresholds and branch properties.
     * @param dataPartition
     * @param targetIndex
     */
    public DecisionStump(DataPartition dataPartition, int targetIndex) {
        super();

        this.targetIndex = targetIndex;

        this.dataPartition = dataPartition;

        double mean = 0; int count = 0;
        for(int i : dataPartition.studentIndexes){
            count++;
            mean += dataPartition.data.data[i][targetIndex];
        }
        mean /= count;

        for(int i : dataPartition.studentIndexes)
            initialVariance += Math.pow(dataPartition.data.data[i][targetIndex] - mean, 2);
        initialVariance /= count - 1;

        //Finds the best set of thresholds to split the given course in order to maximize the variance reduction on the target
        for(int i : dataPartition.courseIndexes)
            if(i != targetIndex) { 
                if(true && i == 30 || i == 31 || i == 33)
                    categoricalSplit(i);
                else
                    findBestNSplit(dataPartition, i, targetIndex, STUMP_WIDTH -1);
            }
    }

    /**
     * Constructs a decision stump that finds the best random split for a target feature given a DataPartition, and stores the split thresholds and branch properties.
     * The random split is a random subset of the target features in the data partition.
     * whether the problem is regression or classification, the amount of features considered changes from n/3 for a regression problem to sqrt(n) for a classification one.
     * @param dataPartition
     * @param targetIndex
     * @param problemType true if the problem is regression, false if it is classification
     */
    public DecisionStump(DataPartition dataPartition, int targetIndex, boolean problemType) {
        super();

        this.targetIndex = targetIndex;

        this.dataPartition = dataPartition;

        double mean = 0; int count = 0;
        for(int i : dataPartition.studentIndexes){
            count++;
            mean += dataPartition.data.data[i][targetIndex];
        }
        mean /= count;

        for(int i : dataPartition.studentIndexes)
            initialVariance += Math.pow(dataPartition.data.data[i][targetIndex] - mean, 2);
        initialVariance /= count - 1;

        int featureCount;
        if(problemType)
            featureCount = dataPartition.courseIndexes.size()/3;
        else
            featureCount = (int)Math.sqrt(dataPartition.courseIndexes.size());
            
        ArrayList<Integer> featureIndexes = new ArrayList<Integer>();
        Random rand = new Random();
        for(int i = 0; i < featureCount; i++)
            featureIndexes.add(dataPartition.studentIndexes.get(rand.nextInt(dataPartition.courseIndexes.size())));

        //Finds the best set of thresholds to split the given course in order to maximize the variance reduction on the target
        for(int i : dataPartition.courseIndexes)
            if(i != targetIndex)
                if(true && i == 30 || i == 31 || i == 33)
                    categoricalSplit(i);
                else
                    findBestNSplit(dataPartition, i, targetIndex, STUMP_WIDTH -1);
    }

    /**
     * Constructs a decision stump that finds the best random split for a target feature given a DataPartition, and stores the split thresholds and branch properties.
     * The random split is a random subset of the target features in the data partition.
     * whether the problem is regression or classification, the amount of features considered changes from n/3 for a regression problem to sqrt(n) for a classification one.
     * @param dataPartition
     * @param targetIndex
     * @param problemType true if the problem is regression, false if it is classification
     * @param branch_function the function which will select the best split ("mse" or "mae" for regression problem; "entropy" or "gini" for classification problem)
     */
    public DecisionStump(DataPartition dataPartition, int targetIndex, boolean problemType, String branchFunction) {
        super();

        this.targetIndex = targetIndex;
        this.branchFunction = branchFunction;
        this.dataPartition = dataPartition;

        // we need this array list to calculate median
        ArrayList<Double> gradesForACourse = new ArrayList<>();

        double mean = 0; int count = 0; double median = 0; 
        for(int i : dataPartition.studentIndexes){
            count++;
            mean += dataPartition.data.data[i][targetIndex];
            gradesForACourse.add(dataPartition.data.data[i][targetIndex]);
        }
        mean /= count;
        median = gradesForACourse.get(count/2);

        for(int i : dataPartition.studentIndexes) { 
            initialVariance += Math.pow(dataPartition.data.data[i][targetIndex] - mean, 2);
            initialMAE += Math.abs(dataPartition.data.data[i][targetIndex]- median);
        }  
        initialVariance /= count - 1;
        initialMAE /= count ;


        
       

        int featureCount;
        if(problemType)
            featureCount = dataPartition.courseIndexes.size()/3;
        else
            featureCount = (int)Math.sqrt(dataPartition.courseIndexes.size());
            
        ArrayList<Integer> featureIndexes = new ArrayList<Integer>();
        Random rand = new Random();
        for(int i = 0; i < featureCount; i++)
            featureIndexes.add(dataPartition.studentIndexes.get(rand.nextInt(dataPartition.courseIndexes.size())));

        //Finds the best set of thresholds to split the given course in order to maximize the variance reduction on the target
        for(int i : dataPartition.courseIndexes)
            if(true && i == 30 || i == 31 || i == 33)
                categoricalSplit(i);
            else
                findBestNSplit(dataPartition, i, targetIndex, STUMP_WIDTH -1);
    }

    /**
     * Constructs a decision stump that finds the best split for a given course and target, and stores the split thresholds and branch properties.
     * It also computes the mean absolute error of the split.
     * It makes use of a DataPartition object to store the data partition.
     * @param data
     * @param courseIndex
     * @param targetIndex
     */
    public DecisionStump(Data data, int courseIndex, int targetIndex){
        super();

        this.targetIndex = targetIndex;

        thresholds = new ArrayList<Double>();
        branchProperties = new ArrayList<ArrayList<Double>>();
    
        dataPartition = new DataPartition(data, (double[] row) -> {return row[courseIndex] >= 0 && row[targetIndex] >= 0;});

        double mean = 0; int count = 0;
        for(int i : dataPartition.studentIndexes){
            count++;
            mean += dataPartition.data.data[i][targetIndex];
        }
        mean /= count;

        for(int i : dataPartition.studentIndexes)
            initialVariance += Math.pow(dataPartition.data.data[i][targetIndex] - mean, 2);
        initialVariance /= count - 1;

        //Finds the best set of thresholds to split the given course in order to maximize the variance reduction on the target
        if(true && courseIndex == 30 || courseIndex == 31 || courseIndex == 33)
            categoricalSplit(courseIndex);
        else
            findBestNSplit(dataPartition, courseIndex, targetIndex, STUMP_WIDTH -1 );
    }

    private void categoricalSplit(int courseIndex){        
        ArrayList<Double> values = dataPartition.getCategoryVector(courseIndex);
      
        //Get split thresholds
        ArrayList<Double> thresholds = new ArrayList<Double>();
        for(int i = 1; i < values.size(); i++)
            thresholds.add((values.get(i) + values.get(i - 1)) / 2);
        //System.out.print(values); System.out.println(thresholds);
        
        //Stores branch properties
        ArrayList<ArrayList<Double>> branches = computeBranchProperties(thresholds, courseIndex);            

        double varianceReduction = initialVariance;
        for (ArrayList<Double> br : branches) {
            if(Double.isNaN(br.get(2)))
                continue;
            varianceReduction -= br.get(0)/dataPartition.studentIndexes.size() * br.get(2); //branch variance weighted by percentage of students in branch
        }

        //If the split has a higher variance reduction than the current best split, replace the current best split
        if(varianceReduction > this.varianceReduction) {
            this.finalVariance = initialVariance - varianceReduction;
            this.varianceReduction = varianceReduction;
            
            this.courseIndex = courseIndex;
            this.thresholds = thresholds;
            this.branchProperties = branches;

            rightPredictions(thresholds);
            computeEntropy();
        }
                

        /*//print each split
        this.finalVariance = initialVariance - varianceReduction;
        this.varianceReduction = varianceReduction;
        this.thresholds = thresholds;
        this.branchProperties = branches;
        rightPredictions(thresholds);
        computeEntropy(rightPredictions);
        System.out.println(this);
        */
    }

    /**
     * Find the best split into n+1 branches for the given course and target
     * It creates a frequency vector of grades for the course, from which it finds all possible combinations of n splits.
     * For each combination, it finds the variance of the target for each subgroup, and the weighted variance of the split.
     * It then finds the split with the lowest weighted variance, and returns the split.
     * 
     * @param part
     * @param courseIndex
     * @param targetIndex
     * @param n
     */
    private void findBestNSplit(DataPartition part, int courseIndex, int targetIndex, int n ){
        // Get the frequency vector of grades for the course
        ArrayList<Double> values = dataPartition.getValuesVector(courseIndex);

        //boolean ok = true; //use the first split as a baseline //maybe not
        
        //check edge case
        if (n >= values.size())
            return;

        /// Go thtough all possible splits
        // each split is a set of thresholds situated in between adjacent values of the course
        // to compute them we will find the combinations of m-1 elements from the set of values choose n
        for(int[] split : Combinations.nChooseK(values.size() - 1, n)){ //ignore the first index of each combination, which is 0 every time

            //Get split thresholds
            ArrayList<Double> thresholds = new ArrayList<Double>();
            for(int i = 1; i < split.length; i++)
                thresholds.add((values.get(split[i]) + values.get(split[i] - 1)) / 2);
            //System.out.print(Arrays.toString(split)); System.out.print(values); System.out.println(thresholds);
            
            //Compute the properties of each branch
            ArrayList<ArrayList<Double>> branches = computeBranchProperties(thresholds, courseIndex);
            
            double varianceReductionIteration = initialVariance;
            double maeReductionIteration = initialMAE;
            

            if(branchFunction.equals("mse")) { 
            for (ArrayList<Double> br : branches) {
                if(Double.isNaN(br.get(2)))
                    continue;
                varianceReductionIteration -= br.get(0)/dataPartition.studentIndexes.size() * br.get(2); //branch variance weighted by percentage of students in branch
            }

            //If the split has a higher variance reduction than the current best split, replace the current best split
            if(varianceReductionIteration > this.varianceReduction) {
                
                this.finalVariance = initialVariance - varianceReductionIteration;
                this.varianceReduction = varianceReductionIteration;
                
                this.courseIndex = courseIndex;
                this.thresholds = thresholds;
                this.branchProperties = branches;

                rightPredictions(thresholds);
                computeEntropy();
            }
            } else if(branchFunction.equals("mae")) { 
                for (ArrayList<Double> br : branches) {
                if(Double.isNaN(br.get(4)))
                    continue;
                maeReductionIteration -= br.get(0)/dataPartition.studentIndexes.size() * br.get(4); //branch variance weighted by percentage of students in branch
            }
            if(maeReductionIteration> this.maeReduction) {
                
                this.finalMAE = initialMAE - maeReductionIteration;
                this.maeReduction = maeReductionIteration;
                
                this.courseIndex = courseIndex;
                this.thresholds = thresholds;
                this.branchProperties = branches;

                rightPredictions(thresholds);
                computeEntropy();
            }

            /*//print each split
            this.finalVariance = initialVariance - varianceReduction;
            this.varianceReduction = varianceReduction;
            this.thresholds = thresholds;
            this.branchProperties = branches;
            int[][] rightPredictions = rightPredictions(thresholds);
            double[] entropy = getEntropy(rightPredictions);
            System.out.println(this);
            */
        }
        }
    }

    public ArrayList<ArrayList<Double>> computeBranchProperties(ArrayList<Double> thresholds, int courseIndex) {
        //Stores branch properties
        ArrayList<ArrayList<Double>> branches = new ArrayList<ArrayList<Double>>();
        
        //Compute the properties of the first branch
        int count = 0; double mean = 0, variance = 0, median = 0, mae = 0; //  mae is averaged sum of |yi - median(Y)|. So, when we are using MAE to evaluate our predictions, we should predict median 
        ArrayList<Double> gradesForACourse = new ArrayList<>();
        
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] >= 0 && dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                count++;
                mean += dataPartition.data.data[j][targetIndex];
                gradesForACourse.add(dataPartition.data.data[j][courseIndex]);
            }
        } mean /= count;
        Collections.sort(gradesForACourse);
        median = gradesForACourse.get(count/2);
        
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                mae += Math.abs(dataPartition.data.data[j][targetIndex] - median);
            }

        } variance /= count - 1;
        mae /= count;
        //add properties to branch arraylist
        ArrayList<Double> branch = new ArrayList<Double>();
        branch.add((double)count); branch.add(mean); branch.add(variance); branch.add(median); branch.add(mae);
        branches.add(branch); 

        //Compute the properties of the middle branches
        for(int i = 1; i < thresholds.size(); i++){ //for each branch
            count = 0; mean = 0; variance = 0; median = 0; mae = 0;
            gradesForACourse = new ArrayList<>();
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                    count++;
                    mean += dataPartition.data.data[j][targetIndex];
                    gradesForACourse.add(dataPartition.data.data[j][courseIndex]);
        
                }
            } mean /= count;
            Collections.sort(gradesForACourse);
            median = gradesForACourse.get(count/2);
            
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                    variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                    mae += Math.abs(dataPartition.data.data[j][targetIndex] - median);

                }
            } variance /= count - 1;
            mae /=count;
            //add properties to branch arraylist
            branch = new ArrayList<Double>();
            branch.add((double)count); branch.add(mean); branch.add(variance); branch.add(median); branch.add(mae);
            branches.add(branch); 
        }

        //compute the properties of the last branch
        count = 0; mean = 0; variance = 0; median = 0; mae = 0;
        gradesForACourse = new ArrayList<>();
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                count++;
                mean += dataPartition.data.data[j][targetIndex];
                gradesForACourse.add(dataPartition.data.data[j][courseIndex]);

            }
        } mean /= count;
        Collections.sort(gradesForACourse);
        median =  gradesForACourse.get(count/2);
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                mae += Math.abs(dataPartition.data.data[j][targetIndex] - median);
            }
        } variance /= count - 1;  //count - 1 because we are estimating the variance of the population from the sample
        //add properties to branch arraylist
        mae /=count;
        branch = new ArrayList<Double>();
        branch.add((double)count); branch.add(mean); branch.add(variance); branch.add(median); branch.add(mae);
        branches.add(branch);

        return branches;
    }

    /**
     * Returns a string representation of the split.
     */
    public String toString(){
        String s = "\nIndexes: " + "{" + courseIndex + " -> "+targetIndex + "}; " + "Thresholds: [";
        for(int i = 0; i < thresholds.size(); i++){
            s += thresholds.get(i);
            if (i < thresholds.size() - 1)
                s += ", ";
        }
        s+= "]\n";
        s += "Initial variance: "+ initialVariance + "; " + "Variance reduction: " + varianceReduction + "; " + "Final variance: " + finalVariance + "; Information Gain: " + informationGain + "\n";
        for(int i = 0; i <= thresholds.size(); i++){
            if(i==0)
                s += "Branch " + i + ": <0.0 - " + thresholds.get(0) +"> " + branchProperties.get(i) + " +: " + rightPredictions[i][0] + " -: " + rightPredictions[i][1] + "; Entropy: " + entropy[i] + "\n";
            else if(i==thresholds.size())
                s += "Branch " + i + ": <" + thresholds.get(i-1) + " - 10.> " + branchProperties.get(i) + " +: " + rightPredictions[i][0] + " -: " + rightPredictions[i][1] + "; Entropy: " + entropy[i] + "\n";
            else
                s += "Branch " + i + ": <" + thresholds.get(i-1) + " - " + thresholds.get(i) +"> " + branchProperties.get(i) + " +: " + rightPredictions[i][0] + " -: " + rightPredictions[i][1] + "; Entropy: " + entropy[i] + "\n";
        }
        s+= "Total row count: " + dataPartition.studentIndexes.size() + "\n";
        s+= "Mean Absolute Error: " + MAE();
        return s;
    }

    /**
     * Returns the mean squared error of the split.
     * It computes the prediction for each student in the data partition as the average of the grades in the branch, and compares it to the actual grade.
     * @return
     */
    public double MSE(){
        double sqEr = 0, prediction = 0;
        for(int i = 0; i < dataPartition.studentIndexes.size(); i++){
            if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] > thresholds.get(thresholds.size() - 1))
                prediction = branchProperties.get(thresholds.size()).get(1);
            else{
                for(int j = 0; j < thresholds.size(); j++){
                    if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(j)){
                        prediction = branchProperties.get(j).get(1);
                        break;
                    }
                }
            }
            sqEr += Math.pow(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex] - prediction, 2);
        }
        return sqEr/dataPartition.studentIndexes.size();
    }

    /**
     * Returns the mean absolute error of the split.
     * It computes the prediction for each student in the data partition as the average of the grades in the branch, and compares it to the actual grade.
     * @return
     */
    public double MAE(){
        double absEr = 0, prediction = 0;
        for(int i = 0; i < dataPartition.studentIndexes.size(); i++){
            if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] > thresholds.get(thresholds.size() - 1))
                prediction = branchProperties.get(thresholds.size()).get(1);
            else{
                for(int j = 0; j < thresholds.size(); j++){
                    if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(j)){
                        prediction = branchProperties.get(j).get(1);
                        break;
                    }
                }
            }
            absEr += Math.abs(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex] - prediction);
        }
        return absEr/dataPartition.studentIndexes.size();
    }

    public void rightPredictions(ArrayList<Double> thresholds){
        int[][] rightPredictions = new int[branchProperties.size()][2];
        double prediction = 0;
        for(int i = 0; i < dataPartition.studentIndexes.size(); i++){
            if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] > thresholds.get(thresholds.size() - 1)) {
                prediction = Math.round(branchProperties.get(thresholds.size()).get(1));
                if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex] == prediction)
                    rightPredictions[thresholds.size()][0]++;
                else
                    rightPredictions[thresholds.size()][1]++;
            } else{
                for(int j = 0; j < thresholds.size(); j++){
                    if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(j)){
                        prediction = Math.round(branchProperties.get(j).get(1));
                        if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex] == prediction)
                            rightPredictions[j][0]++;
                        else
                            rightPredictions[j][1]++;
                        break;
                    }
                }
            }
        }
        this.rightPredictions = rightPredictions;
    }

    public void computeEntropy(){
        double[] entropy = new double[branchProperties.size()];
        double informationGain = 0;
        for(int i = 0; i < rightPredictions.length; i++){
            double p = (double)rightPredictions[i][0]/(rightPredictions[i][0] + rightPredictions[i][1]);
            if(p != 0 && p != 1)
                entropy[i] = (-p * Math.log(p) - (1 - p) * Math.log(1 - p));
                if(Double.isNaN(entropy[i]))
                    continue;
                informationGain += entropy[i] * (rightPredictions[i][0] + rightPredictions[i][1]) / dataPartition.studentIndexes.size();
            }
        this.entropy = entropy;
        this.informationGain = informationGain;
    }

    public void computeChildrenDataPartitions() {

        childrenDataPartitions = new ArrayList<DataPartition>();

        childrenDataPartitions.add(new DataPartition(dataPartition, (double[] row) -> {return row[courseIndex] >= 0 && row[courseIndex] < thresholds.get(0);}));
        
        for (int i = 1; i < thresholds.size(); i++){
            final int j = i;
            childrenDataPartitions.add(new DataPartition(dataPartition, (double[] row) -> {return row[courseIndex] >= thresholds.get(j-1) && row[courseIndex] < thresholds.get(j);}));
        }

        childrenDataPartitions.add(new DataPartition(dataPartition, (double[] row) -> {return row[courseIndex] >= thresholds.get(thresholds.size()-1);}));
    }

    public void procreate(){
        children = new ArrayList<Node>();
        if(childrenDataPartitions == null)
            computeChildrenDataPartitions();
        for (DataPartition childDataPartition : childrenDataPartitions) {
            children.add(new DecisionStump(childDataPartition, targetIndex));
        }
    }

    public ArrayList<DataPartition> getChildrenDataPartitions() {
        if (childrenDataPartitions == null)
            computeChildrenDataPartitions();
        return childrenDataPartitions;
    }

    public Node getParent(){
        return parent;
    }
    public ArrayList<Node> getChildren(){
        return children;
    }
    public String getName(){
        return name;
    }
    public double getInitialVariance(){
        return initialVariance;
    }
    public double getVarianceReduction(){
        return varianceReduction;
    }
    public double getFinalVariance(){
        return finalVariance;
    }
    public double getInformationGain(){
        return informationGain;
    }
    public int getColIndex(){
        return colIndex;
    }
    public ArrayList<ArrayList<Double>> getBranchProperties(){
        return branchProperties;
    }
    public double getValue(){
        return 0;
    }
    public double[] getEntropy(){
        return entropy;
    }
    public int[][] getRightPredictions(){
        return rightPredictions;
    }
    public double getMAE(){
        return finalMAE;
    }

    public int getCourseIndex(){
        return courseIndex;
    }

    public ArrayList<Double> getThresholds(){
        return thresholds;
    }

    public DataPartition getDataPartition(){
        return dataPartition;
    }
}
