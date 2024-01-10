package prediction;

import java.util.ArrayList;
import java.util.Arrays;

import data.*;
import utility.Combinations;


/**
 * Class that represents a numerical decision stump.
 * It finds the best split for a given course and target, and stores the split thresholds and branch properties.
 * It also computes the mean squared error of the split.
 * It makes use of a DataPartition object to store the data partition.
 */
public class DSNum extends DecisionStump {
    
    private int courseIndex;
    private int targetIndex;

    private DataPartition dataPartition;

    private double initialVariance = 0;
    private double varianceReduction = 0;
    private double finalVariance = 0;

    private ArrayList<Double> thresholds;
    private ArrayList<double[]> branchProperties; // [0] =count , [1] = mean, [2] = variance
    

    public DSNum(Data data, int courseIndex, int targetIndex){
        this.courseIndex = courseIndex;
        this.targetIndex = targetIndex;

        thresholds = new ArrayList<Double>();
        branchProperties = new ArrayList<double[]>();
    
        dataPartition = new DataPartition(data, (double[] row) -> {return row[courseIndex] > 0 && row[targetIndex] > 0;});

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
        if(courseIndex == 30 || courseIndex == 31 || courseIndex == 33)
            categoricalSplit();
        else
            findBestNSplit(dataPartition, courseIndex, targetIndex, 2);
    }

    private void categoricalSplit(){        
        ArrayList<Double> values = dataPartition.getValuesVector(courseIndex);
      
        //Get split thresholds
        ArrayList<Double> thresholds = new ArrayList<Double>();
        for(int i = 1; i < values.size(); i++)
            thresholds.add((values.get(i) + values.get(i - 1)) / 2);
        //System.out.print(values); System.out.println(thresholds);
        
        //Stores branch properties
        ArrayList<double[]> branches = new ArrayList<double[]>();
        
        //Compute the properties of the first branch
        int count = 0; double mean = 0, variance = 0;
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                count++;
                mean += dataPartition.data.data[j][targetIndex];
            }
        } mean /= count;
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
            }
        } variance /= count - 1;
        //add properties to branch arraylist
        double[] branch = {count, mean, variance};
        //System.out.println(Arrays.toString(branch));
        branches.add(branch); 

        //Compute the properties of the middle branches
        for(int i = 1; i < thresholds.size(); i++){ //for each branch
            count = 0; mean = 0; variance = 0;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                    count++;
                    mean += dataPartition.data.data[j][targetIndex];
                }
            } mean /= count;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                    variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                }
            } variance /= count - 1;
            //add properties to branch arraylist
            branch = new double[3];
            branch[0] = count; branch[1] = mean; branch[2] = variance;
            //System.out.println(Arrays.toString(branch));
            branches.add(branch); 
        }

        //compute the properties of the last branch
        count = 0; mean = 0; variance = 0;
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                count++;
                mean += dataPartition.data.data[j][targetIndex];
            }
        } mean /= count;
        for(int j : dataPartition.studentIndexes) {
            if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
            }
        } variance /= count - 1; //count - 1 because we are estimating the variance of the population from the sample
        //add properties to branch arraylist
        branch = new double[3];
        branch[0] = count; branch[1] = mean; branch[2] = variance;
        //System.out.println(Arrays.toString(branch));
        branches.add(branch);             

        double varianceReduction = 0;
        for (double[] br : branches) {
            double temp = br[0]/dataPartition.studentIndexes.size();
            varianceReduction += temp * br[2]; //branch variance weighted by percentage of students in branch
        }

        //If the split has a higher variance reduction than the current best split, replace the current best split
        if(varianceReduction >= this.varianceReduction) {
            this.finalVariance = initialVariance - varianceReduction;
            this.varianceReduction = varianceReduction;
            
            this.thresholds = thresholds;
            this.branchProperties = branches;
        }

        /*//print each split
        this.finalVariance = initialVariance - varianceReduction;
        this.varianceReduction = varianceReduction;
        this.thresholds = thresholds;
        this.branchProperties = branches;  
        System.out.println(this);
        */
    }

    /**
     * Find the best n splits for the given course and target
     * It creates a frequency vector of grades for the course, from which it finds all possible combinations of n splits.
     * For each combination, it finds the variance of the target for each subgroup, and the weighted variance of the split.
     * It then finds the split with the lowest weighted variance, and returns the split.
     * 
     * @param part
     * @param courseIndex
     * @param targetIndex
     * @param n
     */
    private void findBestNSplit(DataPartition part, int courseIndex, int targetIndex, int n){
        // Get the frequency vector of grades for the course
        ArrayList<Double> values = dataPartition.getValuesVector(courseIndex);
        
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
            
            //Stores branch properties
            ArrayList<double[]> branches = new ArrayList<double[]>();
            
            //Compute the properties of the first branch
            int count = 0; double mean = 0, variance = 0;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                    count++;
                    mean += dataPartition.data.data[j][targetIndex];
                }
            } mean /= count;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] < thresholds.get(0)) {
                    variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                }
            } variance /= count - 1;
            //add properties to branch arraylist
            double[] branch = {count, mean, variance};
            //System.out.println(Arrays.toString(branch));
            branches.add(branch); 

            //Compute the properties of the middle branches
            for(int i = 1; i < thresholds.size(); i++){ //for each branch
                count = 0; mean = 0; variance = 0;
                for(int j : dataPartition.studentIndexes) {
                    if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                        count++;
                        mean += dataPartition.data.data[j][targetIndex];
                    }
                } mean /= count;
                for(int j : dataPartition.studentIndexes) {
                    if(dataPartition.data.data[j][courseIndex] >= thresholds.get(i-1) && dataPartition.data.data[j][courseIndex] < thresholds.get(i)) {
                        variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                    }
                } variance /= count - 1;
                //add properties to branch arraylist
                branch = new double[3];
                branch[0] = count; branch[1] = mean; branch[2] = variance;
                //System.out.println(Arrays.toString(branch));
                branches.add(branch); 
            }

            //compute the properties of the last branch
            count = 0; mean = 0; variance = 0;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                    count++;
                    mean += dataPartition.data.data[j][targetIndex];
                }
            } mean /= count;
            for(int j : dataPartition.studentIndexes) {
                if(dataPartition.data.data[j][courseIndex] >= thresholds.get(thresholds.size()-1)) {
                    variance += Math.pow(dataPartition.data.data[j][targetIndex] - mean, 2);
                }
            } variance /= count - 1; //count - 1 because we are estimating the variance of the population from the sample
            //add properties to branch arraylist
            branch = new double[3];
            branch[0] = count; branch[1] = mean; branch[2] = variance;
            //System.out.println(Arrays.toString(branch));
            branches.add(branch);             

            double varianceReduction = 0;
            for (double[] br : branches) {
                double temp = br[0]/dataPartition.studentIndexes.size();
                varianceReduction += temp * br[2]; //branch variance weighted by percentage of students in branch
            }

            //If the split has a higher variance reduction than the current best split, replace the current best split
            if(varianceReduction >= this.varianceReduction) {
                this.finalVariance = initialVariance - varianceReduction;
                this.varianceReduction = varianceReduction;
                
                this.thresholds = thresholds;
                this.branchProperties = branches;
            }

            ///*//print each split
            this.finalVariance = initialVariance - varianceReduction;
            this.varianceReduction = varianceReduction;
            this.thresholds = thresholds;
            this.branchProperties = branches;  
            System.out.println(this);
            //*/
        }
    }

    /**
     * Returns a string representation of the split.
     */
    public String toString(){
        String s = "Indexes: " + "{" + courseIndex + " -> "+targetIndex + "}; " + "Thresholds: [";
        for(int i = 0; i < thresholds.size(); i++){
            s += thresholds.get(i);
            if (i < thresholds.size() - 1)
                s += ", ";
        }
        s+= "]\n";
        s += "Initial variance: "+ initialVariance + "; " + "Variance reduction: " + varianceReduction + "; " + "Final variance: " + finalVariance + "\n";
        for(int i = 0; i <= thresholds.size(); i++){
            if(i==0)
                s += "Branch " + i + ": <0.0 - " + thresholds.get(0) +"> " + Arrays.toString(branchProperties.get(i)) + "\n";
            else if(i==thresholds.size())
                s += "Branch " + i + ": <" + thresholds.get(i-1) + " - 10.> " + Arrays.toString(branchProperties.get(i)) + "\n";
            else
                s += "Branch " + i + ": <" + thresholds.get(i-1) + " - " + thresholds.get(i) +"> " + Arrays.toString(branchProperties.get(i)) + "\n";
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
            if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(0))
                prediction = branchProperties.get(0)[1];
            else if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] > thresholds.get(thresholds.size() - 1))
                prediction = branchProperties.get(thresholds.size())[1];
            else{
                for(int j = 1; j < thresholds.size(); j++){
                    if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(j)){
                        prediction = branchProperties.get(j)[1];
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
            if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(0))
                prediction = branchProperties.get(0)[1];
            else if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] > thresholds.get(thresholds.size() - 1))
                prediction = branchProperties.get(thresholds.size())[1];
            else{
                for(int j = 1; j < thresholds.size(); j++){
                    if(dataPartition.data.data[dataPartition.studentIndexes.get(i)][courseIndex] <= thresholds.get(j)){
                        prediction = branchProperties.get(j)[1];
                        break;
                    }
                }
            }
            absEr += Math.abs(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex] - prediction);
        }
        return absEr/dataPartition.studentIndexes.size();
    }

    /**
     * Test method
     * @param args
     */
    public static void main(String[] args) { //!TSO-010 12/10, PLO-132 18/16, BKO-801 21/19

        Data data = new Data(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv"));
        DSNum ds = new DSNum(data, 32, 13);
        System.out.println(ds);
        /*for(int i = 0; i < data.columnNames.length; i++){
            for(int j = 0; j < data.columnNames.length; j++){
                if(i != 10 && j != 10 && i != 16 && j != 16 && i != 19 && j != 19){
                    DSNum da = new DSNum(data, i, j);
                    System.out.println(da);
                }
            }
            System.out.println();
        }
        */
    }
}
