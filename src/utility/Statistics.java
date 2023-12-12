package utility;

import java.util.Arrays;

public abstract class Statistics {

    public static int count (double[] data) {
        int count = 0;
        for(double d : data)
            if(d > 0) count++;
        return data.length;
    }

    public static int count (Double[] data) {
        int count = 0;
        for(double d : data)
            if(d > 0) count++;
        return data.length;
    }

    public static int countOf (double[] data, double value) {
        int count = 0;
        for(double d : data)
            if(d == value) count++;
        return count;
    }

    public static int countOf (Double[] data, double value) {
        int count = 0;
        for(double d : data)
            if(d == value) count++;
        return count;
    }

    public static int countOf (double[][] data, double value, int column) {
        int count = 0;
        for (int i = 0; i < data.length; i++)
                if(data[i][column] == value) count++;
        return count;
    }

    public static int countOf (Double[][] data, double value) {
        int count = 0;
        for (Double[] row : data)
            for (double d : row)
                if(d == value) count++;
        return count;
    }

    public static int mode (double[] data) {
        int max = 0;
        int mode = -1;
        int[] freqVec = new int[11];
        for (double d : data)
            if(d > 0) freqVec[(int) d]++;
        for (int i = 0; i < freqVec.length; i++)
            if(freqVec[i] > max) {
                mode = i;
                max = freqVec[i];
            }
        return mode;
    }

    public static int mode (Double[] data) {
        int max = 0;
        int mode = -1;
        int[] freqVec = new int[11];
        for (double d : data)
            if(d > 0) freqVec[(int) d]++;
        for (int i = 0; i < freqVec.length; i++)
            if(freqVec[i] > max) {
                mode = i;
                max = freqVec[i];
            }
        return mode;
    }

    public static int mode (Double[][] data) {
        int max = 0;
        int mode = -1;
        int[] freqVec = new int[11];
        for (Double[] row : data)
            for (double d : row)
                if(d > 0) freqVec[(int) d]++;
        for (int i = 0; i < freqVec.length; i++)
            if(freqVec[i] > max) {
                mode = i;
                max = freqVec[i];
            }
        return mode;
    }

    public static double sum (double[] data) {
        double sum = 0;
        for (double d : data)
            if(d > 0) sum += d;
        return sum;
    }

    public static double sum (Double[] data) {
        double sum = 0;
        for (double d : data)
            if(d > 0) sum += d;
        return sum;
    }

    public static double sum (double[][] data) {
        double sum = 0;
        for (double[] row : data)
            for (double d : row)
                if(d > 0) sum += d;
        return sum;
    }

    public static double sum (Double[][] data) {
        double sum = 0;
        for (Double[] row : data)
            for (double d : row)
                if(d > 0) sum += d;
        return sum;
    }
    
    public static double mean (double[] data) {
        double sum = 0;
        for (double d : data)
            if (d > 0) sum += d;
        return sum / data.length;
    }
    
    public static double mean (Double[] data) {
        double sum = 0;
        for (double d : data)
            if(d > 0) sum += d;
        return sum / data.length;
    }
    
    public static double mean (Double[][] data) {
        double sum = 0;
        int count = 0;
        for (Double[] row : data) {
            for (double d : row)
                if(d > 0) {
                    sum += d;
                    count++;
                }
        }
        return sum / count;
    }
    
    public static double variance (double[] data) {
        double mean = mean(data);
        double sum = 0;
        for (double d : data)
            if(d > 0) sum += Math.pow(d - mean, 2);
        return sum / data.length;
    }
    
    public static double variance (Double[] data) {
        double mean = mean(data);
        double sum = 0;
        for (double d : data)
            if(d > 0) sum += Math.pow(d - mean, 2);
        return sum / data.length;
    }
    
    public static double variance (Double[][] data) {
        double mean = mean(data);
        double sum = 0;
        int count = 0;
        for (Double[] row : data) {
            for (double d : row)
                if(d > 0) {
                    sum += Math.pow(d - mean, 2);
                    count++;
                }
        }
        return sum / count;
    }
    
    public static double standardDeviation (double[] data) {
        return Math.sqrt(variance(data));
    }
    
    public static double standardDeviation (Double[] data) {
        return Math.sqrt(variance(data));
    }
    
    public static double standardDeviation (Double[][] data) {
        return Math.sqrt(variance(data));
    }
    
    public static double covariance (double[] data1, double[] data2) {
        double mean1 = mean(data1);
        double mean2 = mean(data2);
        double sum = 0;
        for (int i = 0; i < data1.length; i++) 
            if(data1[i] > 0 && data2[i] > 0)
                sum += (data1[i] - mean1) * (data2[i] - mean2);
        return sum / data1.length;
    }

    public static double covariance (Double[] data1, Double[] data2) {
        double mean1 = mean(data1);
        double mean2 = mean(data2);
        double sum = 0;
        for (int i = 0; i < data1.length; i++) 
            if(data1[i] > 0 && data2[i] > 0)
                sum += (data1[i] - mean1) * (data2[i] - mean2);
        return sum / data1.length;
    }

    public static double covariance (Double[][] data1, Double[][] data2) {
        double mean1 = mean(data1);
        double mean2 = mean(data2);
        double sum = 0;
        int count = 0;
        for (int i = 0; i < data1.length; i++) {
            for (int j = 0; j < data1[i].length; j++) 
                if(data1[i][j] > 0 && data2[i][j] > 0) {
                    sum += (data1[i][j] - mean1) * (data2[i][j] - mean2);
                    count++;
                }
        }
        return sum / count;
    }

    public static double correlation (double[] data1, double[] data2) {
        return covariance(data1, data2) / (standardDeviation(data1) * standardDeviation(data2));
    }

    public static double correlation (Double[] data1, Double[] data2) {
        return covariance(data1, data2) / (standardDeviation(data1) * standardDeviation(data2));
    }

    public static double correlation (Double[][] data1, Double[][] data2) {
        return covariance(data1, data2) / (standardDeviation(data1) * standardDeviation(data2));
    }

    public static double[] normalize (double[] data) {
        double[] result = new double[data.length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++)
            if(data[i] > 0)
                result[i] = (data[i] - mean) / std;
        return result;
    }

    public static Double[] normalize (Double[] data) {
        Double[] result = new Double[data.length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++)
            if(data[i] > 0)
                result[i] = (data[i] - mean) / std;
        return result;
    }

    public static Double[][] normalize (Double[][] data) {
        Double[][] result = new Double[data.length][data[0].length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; i++) 
                if(data[i][j] > 0)
                    result[i][j] = (data[i][j] - mean) / std;
        }
        return result;
    }

    public static double[] standardize (double[] data) {
        double[] result = new double[data.length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++)
            if(data[i] > 0)
                result[i] = (data[i] - mean) / std;
        return result;
    }

    public static Double[] standardize (Double[] data) {
        Double[] result = new Double[data.length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++) 
            if(data[i] > 0)
                result[i] = (data[i] - mean) / std;
        return result;
    }

    public static Double[][] standardize (Double[][] data) {
        Double[][] result = new Double[data.length][data[0].length];
        double mean = mean(data);
        double std = standardDeviation(data);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; i++) 
                if(data[i][j] > 0)
                    result[i][j] = (data[i][j] - mean) / std; 
        }
        return result;
    }

    public static double min (double[] data) {
        double min = 101;
        for (double d : data)
            if (d > 0)
                if (d < min)
                    min = d;
        if (min == 101)
            return 0;
        return min;
    }

    public static double min (Double[] data) {
        double min = 101;
        for (double d : data)
            if (d > 0)
                if (d < min) 
                    min = d;
        if (min == 101)
            return 0;
        return min;
    }

    public static double min (Double[][] data) {
        double min = 101;
        for (Double[] row : data)
            for (double d : row)
                if(d > 0)
                    if (d < min)
                        min = d;
        if (min == 101)
            return 0;
        return min;
    }

    public static double max (double[] data) {
        double max = 0;
        for (double d : data)
            if (d > 0)
                if (d > max) 
                    max = d;
        if (max == 0)
            return 0;
        return max;
    }

    public static double max (Double[] data) {
        double max = 0;
        for (double d : data)
            if (d > 0)
                if (d > max) 
                    max = d;
        if (max == 0)
            return 0;
        return max;
    }

    public static double max (Double[][] data) {
        double max = 0;
        for (Double[] row : data)
            for (double d : row)
                if(d > 0)
                    if (d > max)
                        max = d;
        if (max == 0)
            return 0;
        return max;
    }

    public static double[] range (double[] data) {
        double[] result = new double[2];
        result[0] = min(data);
        result[1] = max(data);
        return result;
    }

    public static double[] range (Double[] data) {
        double[] result = new double[2];
        result[0] = min(data);
        result[1] = max(data);
        return result;
    }

    public static double[] range (Double[][] data) {
        double[] result = new double[2];
        result[0] = min(data);
        result[1] = max(data);
        return result;
    }

    //-------------------------------------------
    // percentile methods are mean to be used with data without missing values -> use DataFilter.removeMissingValues() first

    public static double percentile (double[] data, double percentile) {
        double[] sortedData = data.clone();
        Arrays.sort(sortedData);
        int index = (int) Math.ceil(percentile * data.length);
        return sortedData[index];
    }

    public static double percentile (Double[] data, double percentile) {
        Double[] sortedData = data.clone();
        Arrays.sort(sortedData);
        int index = (int) Math.ceil(percentile * data.length);
        return sortedData[index];
    }

    public static double quartile (double[] data, double percentile) {
        return percentile(data, percentile);
    }

    public static double quartile (Double[] data, double percentile) {
        return percentile(data, percentile);
    }

    public static double q1 (double[] data) {
        return percentile(data, .25);
    }

    public static double q1 (Double[] data) {
        return percentile(data, .25);
    }

    public static double q2 (double[] data) {
        return percentile(data, .5);
    }

    public static double q2 (Double[] data) {
        return percentile(data, .5);
    }

    public static double q3 (double[] data) {
        return percentile(data, .75);
    }

    public static double q3 (Double[] data) {
        return percentile(data, .75);
    }

    public static double iqr (double[] data) {
        return q3(data) - q1(data);
    }

    public static double iqr (Double[] data) {
        return q3(data) - q1(data);
    }
}
