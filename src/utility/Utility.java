package utility;
import java.util.List;

import data.Data;
import prediction.DecisionStumpFactory;

public abstract class Utility {
    public static void main(String[] args) {
        Data bugData = new Data("data/bugData.csv");
        printPearsonCorrelationMatrix(bugData, pearsonCorrelationMatrix(bugData));
        double[] line = linearRegressionLine(bugData, 0, 1);
        printLinearRegressionLine(line, bugData.columnNames[0], bugData.columnNames[1]);
        System.out.println("MSE: " + meanSquaredError(bugData, 0, 1, line));
        double[] meanLine = new double[2]; meanLine[0] = 0; meanLine[1] = mean(bugData, 1);
        System.out.println("MSE: " + meanSquaredError(bugData, 0, 1, meanLine));
        double cor = pearsonCorrelation(bugData, 0, 1);
        System.out.println("Cor: " + cor + ", R2: " + cor*cor);
        System.out.println(weightedEuclideanDistance(bugData, 1, linearRegressionLinePrediction(bugData, 0, line)));
        //System.out.println(weightedEuclideanDistance(bugData, 1, singleStumpPrediction(bugData, 1)));
    }

    public static double[] linearRegressionLine(Data data, int columnIndex1, int columnIndex2) {
        double[] line = new double[2]; //[slope, y-intercept]
        double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumXX = 0.0;
        int non_ng_students_count = 0;
        for (int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex1] > 0 && data.data[i][columnIndex2] > 0){
                non_ng_students_count++;
                sumX += data.data[i][columnIndex1];
                sumY += data.data[i][columnIndex2];
                sumXY += data.data[i][columnIndex1] * data.data[i][columnIndex2];
                sumXX += data.data[i][columnIndex1] * data.data[i][columnIndex1];
            }
        line[0] = (non_ng_students_count * sumXY - sumX * sumY) / (non_ng_students_count * sumXX - sumX * sumX);
        line[1] = (sumY - line[0] * sumX) / non_ng_students_count;
        return line;
    }

    public static double meanSquaredError(Data data, int columnIndex1, int columnIndex2, double[] regLine) {
        double sum = 0; int count = 0;
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex1] > 0 && data.data[i][columnIndex2] > 0){
                sum += (data.data[i][columnIndex2] - (regLine[0]*data.data[i][columnIndex1] + regLine[1]))*(data.data[i][columnIndex2] - (regLine[0]*data.data[i][columnIndex1] + regLine[1]));
                count++;
            }
        return sum/count;
    }

    public static void printLinearRegressionLine(double[] line, String columnName1, String columnName2){
        System.out.println("X: " + columnName1);
        System.out.println("Y: " + columnName2);
        System.out.println("Slope: " + line[0]);
        System.out.println("Y-Intercept: " + line[1]);
    }

    public static double[] linearRegressionLinePrediction(Data data, int columnIndex1, double[] line) {
        double[] prediction = new double[data.studentIDs.length];
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex1] > 0)
                prediction[i] = line[1] + line[0]*data.data[i][columnIndex1];
        return prediction;
    }

    /**
     * Returns the Pearson Correlation of two columns of the dataset, ignoring NG values
     * @param data
     * @param columnName1
     * @param columnName2
     * @return
     */
    public static double pearsonCorrelation(Data data, int columnIndex1, int columnIndex2){
        double mean1 = mean(data, columnIndex1);
        double mean2 = mean(data, columnIndex2);
        double meanDifProd = 0;
        double meanDifSq1 = 0, meanDifSq2 = 0;

        double md1, md2;
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex1] > 0 && data.data[i][columnIndex2] > 0){
                md1 = data.data[i][columnIndex1] - mean1;
                md2 = data.data[i][columnIndex2] - mean2;
                meanDifProd += md1*md2;
                meanDifSq1 += md1*md1;
                meanDifSq2 += md2*md2;
            }
        return meanDifProd/Math.sqrt(meanDifSq1*meanDifSq2);
    }

    public static double[][] pearsonCorrelationMatrix(Data data){
        double[][] correlMatrix = new double[data.columnNames.length][data.columnNames.length];
        for(int i = 0; i < data.columnNames.length; i++)
            for(int j = 0; j < data.columnNames.length; j++)
                correlMatrix[i][j] = pearsonCorrelation(data, i, j);
        return correlMatrix;
    }

        public static void printPearsonCorrelationMatrix(Data data, double[][] correlMatrix){
        for(int i = 0; i < data.columnNames.length; i++)
            for(int j = i; j < data.columnNames.length; j++)
                System.out.println(data.columnNames[j] + " & " + data.columnNames[i] + ": " + correlMatrix[i][j]);
    }

    public static double mean(Data data, int columnName){
        double sum = 0; int count = 0;
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnName] > 0){
                sum += data.data[i][columnName];
                count++;
            }
        if(count == 0) return 0;
        return sum/count;
    }

    public static double weightedEuclideanDistanceFromMean(Data data, int columnIndex){
        double sum = 0; int count = 0;
        double mean = mean(data, columnIndex);
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex] > 0){
                sum += (data.data[i][columnIndex] - mean)*(data.data[i][columnIndex] - mean);
                count++;
            }
        return sum/count;
    }
    public static double cosine(double[][] data, int j, int k) {
        double summ = 0;
        double dot = 0;
        double normx = 0;
        double normy = 0;
        for (int i = 0; i < data.length; i++) {
            
            dot += data[i][j] * data[i][k];
            normx += Math.pow(data[i][j], 2);
            normy += Math.pow(data[i][k], 2);
            
        }
        summ = dot / (Math.sqrt(normx * normy));
        return summ;
    }

    public static double euclidian(double[][] data, int j, int k) {
        double summ = 0;
        for (int i = 0; i < data.length; i++) {
            summ += Math.pow((data[i][j] - data[i][k]), 2);
        }
        summ = Math.sqrt(summ);
        return summ;
    }

    public static double weightedEuclideanDistance(Data data, int columnIndex, double[] predictionArray){
        double sum = 0; int count = 0;
        double mean = mean(data, columnIndex);
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex] > 0){
                sum += (data.data[i][columnIndex] - predictionArray[i])*(data.data[i][columnIndex] - predictionArray[i]);
                count++;
            }
        return sum/count;
    }

    public static double[] singleStumpPrediction(Data data, int columnIndex){
        double[] prediction = new double[data.studentIDs.length];
        for(int i = 0; i < data.studentIDs.length; i++)
            if(data.data[i][columnIndex] > 0){
                prediction[i] = DecisionStumpFactory.createSoloPrediction(data.data, i, data.studentIDs, data.columnNames[columnIndex], data.columnNames).prediction_value;
            }
        return prediction;
    }

    
    public static <T extends Number> T max(List<T> list) {
        T max = list.get(0);
        for (T element : list) {
            if (element.doubleValue() > max.doubleValue()) {
                max = element;
            }
        }
        return max;
    }
}
