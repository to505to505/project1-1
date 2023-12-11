import java.util.ArrayList;
import java.util.HashMap;


public class DSNum implements DS {
    private double[][] data;
    private String course_name;
    private String property_name;
    private String[] courses_names; 
    private int course_col_num = 0;
    private int property_col_num = 0;
    public HashMap<Object, HashMap<String, Double>> prediction_dict;
    public double total_variance = 0;
    public double boundry_value;
    public HashMap<String, HashMap<String, Object>> prediction_dict_plots;
    public HashMap<Double, ArrayList<Integer>> users_for_plots;

    public DSNum(double[][] data, String course_name, String property_name, String[]courses_names, int course_col_num, int property_col_num ) {
        this.data = data;
        this.course_name = course_name;
        this.property_name = property_name;
        this.courses_names =courses_names;
        this.course_col_num = course_col_num;
        this.property_col_num = property_col_num;

        /* Predicting  */
        predict();

    }
    public HashMap<Double, ArrayList<Integer>> get_users_for_plots(){
        return users_for_plots;
    }
    public HashMap<Object, HashMap<String, Double>> get_prediction_dict(){
        return prediction_dict;
    }

    public double get_total_variance() {
        return total_variance;
    }
    public String get_property_name() {
        return property_name;
    }
    public int get_property_col_num() {
        return property_col_num;
    }
    public String get_course_name() {
        return course_name;
    }
    public double get_boundry_value() {
        return boundry_value;
    }
    public HashMap<String, HashMap<String, Object>> get_prediction_dict_plots(){
        return prediction_dict_plots;
    }


    public void predict() {
        prediction_dict = new HashMap<>();
        prediction_dict_plots = new HashMap<>();
        ArrayList<Integer> upper = new ArrayList<Integer>();
        ArrayList<Integer> lower = new ArrayList<Integer>();

        // we are both iterating through and sorting the values of Lal count with a
        // frequency vector
        // because, while we treat them as continuous, they are discrete and relatively
        // few in number
        int x;
        // wether the property_col_num represents a course or a student property, this
                // chooses the minimum size for the freq vector
        if (property_col_num == 32)
            x = 101;
        else
            x = 11;
        boolean[] freqVec = new boolean[x];

        boolean ok = false;

        for (int i = 0; i < data.length; i++)
            if (data[i][property_col_num] > 0) {
                ok = true;
                freqVec[(int) data[i][property_col_num]] = true;
            }

        // init i to be the first value of lal count
        int i = 0;
        if (ok)
            while (!freqVec[i]) {
                i++;
            } // while loop only increments i
        else {
            total_variance = 100.0;
        }

        double boundryVal;
        int boundryValCount = 0;
        for (int j = i; j < x; j++)
            if (freqVec[j])
                boundryValCount++;

        // to save space, instead of creating new ArrayLists for every boundry value,
        // going through all boundry values in ascending order,
        // we just pop elements from the upper array and adding them to the lower one
        int gradeCount = 0; // this also counts the total number of grades
        for (int j = 0; j < data.length; j++) // adding all data to the upper array
            if (data[j][course_col_num] > 0) {
                upper.add(j);
                gradeCount++;
            }

        double[][] totalVariancePerBoundryValue = new double[boundryValCount][6];
        int s = 0;

        for (; i < x; i++)
            if (freqVec[i]) {
                boundryVal = i;
                ArrayList<Integer> queue = new ArrayList<Integer>();
                for (Integer index : upper)
                    if (data[index][property_col_num] < boundryVal) {
                        queue.add(index);
                    }
                for (Integer index : queue) {
                    upper.remove(index);
                    lower.add(index);
                }

                // calculate upper mean and variance
                double[] upperMVC = meanVarianceCount(upper, data, course_col_num);
                // calculate lower mean and variance
                double[] lowerMVC = meanVarianceCount(lower, data, course_col_num);
                // calculate the weighted variance of upper and lower

                double totalVariance = 0;
                totalVariance += upperMVC[1] * upperMVC[2] / gradeCount;
                totalVariance += lowerMVC[1] * lowerMVC[2] / gradeCount;

                // for every boundry value we save the properties of the split
                totalVariancePerBoundryValue[s][0] = totalVariance; // total var
                totalVariancePerBoundryValue[s][1] = i; // boundry value
                totalVariancePerBoundryValue[s][2] = lowerMVC[0]; // lower mean
                totalVariancePerBoundryValue[s][3] = lowerMVC[1]; // lower variance
                totalVariancePerBoundryValue[s][4] = upperMVC[0]; // upper mean
                totalVariancePerBoundryValue[s][5] = upperMVC[1]; // upper variance
                s++;

            }

        double minTotalVariance = 100;
        int minTotalVarianceIndex = 0;
        for (int o = 0; o < totalVariancePerBoundryValue.length; o++)
            if (totalVariancePerBoundryValue[o][0] < minTotalVariance) {
                minTotalVariance = totalVariancePerBoundryValue[o][0];
                minTotalVarianceIndex = o;
            }
        
        boundry_value = totalVariancePerBoundryValue[minTotalVarianceIndex][1];
        ArrayList<Integer> upper1 = new ArrayList<Integer>();
        ArrayList<Integer> lower1 = new ArrayList<Integer>();
        ArrayList<Integer> queue1 = new ArrayList<Integer>();
        int gradeCount1 = 0; 
        for (int j = 0; j < data.length; j++) { // adding all data to the upper array
            if (data[j][course_col_num] > 0) {
                upper1.add(j);
                gradeCount1++;
            }
        }
        for (Integer index : upper1) {
                    if (data[index][property_col_num] < boundry_value) {
                        queue1.add(index);
                    }
                }
                for (Integer index : queue1) {
                    upper1.remove(index);
                    lower1.add(index);
                }
        ArrayList<Integer> upper1_grades= new ArrayList<Integer>();
        ArrayList<Integer> lower1_grades= new ArrayList<Integer>();
        for(Integer index: upper1) {
            upper1_grades.add((int)data[index][course_col_num]);
        }
        for(Integer index: lower1) {
            lower1_grades.add((int)data[index][course_col_num]);
        }
        users_for_plots = new HashMap<>();
        users_for_plots.put(1.0, upper1_grades);
        users_for_plots.put(0.0, lower1_grades);
        



        prediction_dict.put(false, new HashMap<>());
        prediction_dict.get(false).put("Mean: ",totalVariancePerBoundryValue[minTotalVarianceIndex][2] );
        prediction_dict.get(false).put("Variance: ",totalVariancePerBoundryValue[minTotalVarianceIndex][3] );
        prediction_dict.put(true, new HashMap<>());
        prediction_dict.get(true).put("Mean: ", totalVariancePerBoundryValue[minTotalVarianceIndex][4]);
        prediction_dict.get(true).put("Variance: ", totalVariancePerBoundryValue[minTotalVarianceIndex][5]);
        total_variance = totalVariancePerBoundryValue[minTotalVarianceIndex][0];
        prediction_dict_plots.put("< "+ boundry_value, new HashMap<>());
        prediction_dict_plots.get("< "+ boundry_value).put("Mean: ",totalVariancePerBoundryValue[minTotalVarianceIndex][2] );
        prediction_dict_plots.get("< "+ boundry_value).put("Variance: ",totalVariancePerBoundryValue[minTotalVarianceIndex][3] );
        prediction_dict_plots.put(">= "+ boundry_value, new HashMap<>());
        prediction_dict_plots.get(">= "+ boundry_value).put("Mean: ", totalVariancePerBoundryValue[minTotalVarianceIndex][4]);
        prediction_dict_plots.get(">= "+ boundry_value).put("Variance: ", totalVariancePerBoundryValue[minTotalVarianceIndex][5]);



    }

    private double[] meanVarianceCount(ArrayList<Integer> array, double[][] data, int course_col_num) {

        double[] mVC = new double[3];
        for (Integer index : array) {
            mVC[0] += data[index][course_col_num];
            mVC[2]++;
        }
        mVC[0] /= mVC[2];
        for (Integer index : array)
            if (index > 0) {
                mVC[1] += Math.pow((data[index][course_col_num] - mVC[0]), 2);
            }
        mVC[1] /= mVC[2];
        return mVC;
    }

    
}

