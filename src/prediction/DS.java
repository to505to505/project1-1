package prediction;

import java.util.HashMap;
import java.util.ArrayList;

public interface DS {
    public HashMap<Object, HashMap<String, Double>> get_prediction_dict();
    public double get_total_variance();
    public String get_property_name();
    public int get_property_col_num();
    public String get_course_name();
    public default double get_boundry_value(){
        return -1;
    }
    public HashMap<String, HashMap<String, Object>> get_prediction_dict_plots();
    public HashMap<Double, ArrayList<Integer>> get_users_for_plots();
}
