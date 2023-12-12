package prediction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DSCat implements DS  {
    private double[][] data;
    private String course_name;
    private String property_name;
    private String[] courses_names; 
    private int course_col_num = 0;
    private int property_col_num = 0;
    public HashMap<Object, HashMap<String, Double>> prediction_dict;
    public double total_variance = 0;
    public HashMap<String, HashMap<String, Object>> prediction_dict_plots;
    public HashMap<Double, ArrayList<Integer>> users_for_plots;




    public  DSCat(double[][] data, String course_name, String property_name, String[]courses_names, int course_col_num, int property_col_num ) {

        //Initializing variables needed for any class methods
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

    public HashMap<Object, HashMap<String, Double>> get_prediction_dict() {
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
    
    public HashMap<String, HashMap<String, Object>> get_prediction_dict_plots() {
        return prediction_dict_plots;
    }
    

   
    public void predict() {
        prediction_dict = new HashMap<>();
        prediction_dict_plots = new HashMap<>();


        double[] property_array = new double[data.length];
        for (int s = 0; s < data.length; s++) {
            property_array[s] = data[s][property_col_num];
        }
        Double[] propertyArrayobject = Arrays.stream(property_array).boxed().toArray(Double[]::new);

        Set<Double> uniqueSet = new HashSet<>(Arrays.asList(propertyArrayobject));
        Double[] uniqueArr = uniqueSet.toArray(new Double[0]);
        int total_count = total_counter(data, course_col_num);
        ArrayList<Integer> ids_for_plots = new ArrayList<Integer>();
        ArrayList<Integer> array_of_users_for_plots = new ArrayList<Integer>();
        users_for_plots = new HashMap<>();
        for (Double value : uniqueArr) {
            ArrayList<Integer> index_array = new ArrayList<Integer>();
            ids_for_plots = new ArrayList<Integer>();
            for (int i = 0; i < data.length; i++) {

                if (data[i][property_col_num] == value) {
                    index_array.add(i);
                    ids_for_plots.add(i);

                }

            }
            array_of_users_for_plots = new ArrayList<>();
            for(Integer ID: ids_for_plots) {
                array_of_users_for_plots.add((int) data[ID][course_col_num]);
                
            }
            
            users_for_plots.put(value, array_of_users_for_plots);

            double mean = mean_calc(data, index_array, course_col_num);
            int count = count_calc(data, index_array, course_col_num);
            double variance_split = variance_in_split(data, index_array, course_col_num, count, mean);
            total_variance += (variance_split * count / total_count);
            prediction_dict.put(value, new HashMap<>());
            prediction_dict.get(value).put("Mean: ", mean);
            prediction_dict.get(value).put("Variance: ", variance_split);
            String value_name = get_value_names(value, courses_names, property_col_num);
            prediction_dict_plots.put(value_name, new HashMap<>());
            prediction_dict_plots.get(value_name).put("Mean: ", mean);
            prediction_dict_plots.get(value_name).put("Variance: ", variance_split);


        }



    }
    private int total_counter(double[][] data, int course_col_num) {
        int counter = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i][course_col_num] < 0) {
                continue;
            } else {
                counter += 1;

            }
        }
        return counter;

    }

    private double mean_calc(double[][] data, ArrayList<Integer> value_array, int course_col_num) {
        double mean = 0;
        int counter = 0;
        for (int i = 0; i < value_array.size(); i++) {
            if (data[value_array.get(i)][course_col_num] == -1) {
                continue;
            } else {
                counter += 1;
                mean += data[value_array.get(i)][course_col_num];

            }
        }
        mean = mean / counter;
        return mean;
    }

    private int count_calc(double[][] data, ArrayList<Integer> value_array, int course_col_num) {
        int counter = 0;
        for (int i = 0; i < value_array.size(); i++) {
            if (data[value_array.get(i)][course_col_num] == -1) {
                continue;
            } else {
                counter += 1;

            }
        }
        return counter;
    }
    private double variance_in_split(double[][] data, ArrayList<Integer> value_array, int course_col_num, int counter, double mean) {
        double summ = 0;
        for (int i = 0; i < value_array.size(); i++) {
            if (data[value_array.get(i)][course_col_num] == -1) {
                continue;
            } else {
                summ += Math.pow(data[value_array.get(i)][course_col_num] - mean, 2);
            }

        }
        return summ / counter;
    }
    public static String get_value_names(double value, String[] courses_names, int property_col_num) {
        switch (courses_names[property_col_num]) {
            case "Suruna Value":
                switch ((int) value) {
                    case 0:
                        return "nulp";
                    case 1:
                        return "doot";
                    case 2:
                        return "lobi"; 
                    default:
                        return "";
    
                }
            case "Hurni Level":
                switch ((int) value) {
                    case 0:
                        return "nothing";
                    case 1:
                        return "low";
                    case 2:
                        return "medium";
                    case 3:
                        return "high";
                    case 4:
                        return "full";
                    default:
                        return "";
   
                
                }
                
            case "Volta ":
                switch ((int) value) {
                    case 1:
                        return "1 star";
                    case 2:
                        return "2 stars";
                    case 3:
                        return "3 stars";
                    case 4:
                        return "4 stars";
                    case 5:
                        return "5 stars";
                    default:
                        return ""; }
                
            default:
                return "";    }
}
}


        


