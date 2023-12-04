import java.util.ArrayList;
import java.util.HashMap;

public class Prediction {
    public HashMap<String, HashMap<String, Object>> prediction_info_dict;
    public int prediction_value;
    public ArrayList<DecisionStump> used_splits;
    public int student_index;
    public int student_id;
    public double[][] data;
    ArrayList<HashMap<String, HashMap<String, Object>>> used_splits_plot_dict;

    public Prediction(double[][] data, ArrayList<DecisionStump> used_splits, int student_index, int student_id ) { 
        this.student_id = student_id;
        this.student_index = student_index;
        this.used_splits = used_splits;
        this.data = data;
    
        this.prediction_value = predict_value();
        this.used_splits_plot_dict = used_split_plot();

    }


    private ArrayList<HashMap<String, HashMap<String, Object>>> used_split_plot() {
        used_splits_plot_dict = new ArrayList<>();
        for(DecisionStump item: used_splits) {
            HashMap<String, HashMap<String, Object>> plot_info_dict = item.get_prediction_dict_plots();
        
            plot_info_dict.put("Info", new HashMap<>());
            plot_info_dict.get("Info").put("Total Variance", item.get_total_variance());
            plot_info_dict.get("Info").put("Property name", item.get_property_name());

            int property_col_num = item.get_property_col_num();
            double student_value_of_property = data[student_index][property_col_num];
            plot_info_dict.get("Info").put("Student's value for this property",student_value_of_property);



            used_splits_plot_dict.add(plot_info_dict);
        }
        return  used_splits_plot_dict;
    }

    private int predict_value() {
        double weighted_mean_sum = 0;
        double reverse_variance_sum = 0;
        for(DecisionStump item: used_splits) {
            int property_col_num = item.get_property_col_num();
            double student_value_of_property = data[student_index][property_col_num];
            double weighted_variance_of_split = item.get_total_variance();
            HashMap<String, Double> split_info;

            if(item instanceof decisionStumpCategorical) {
                split_info = ((decisionStumpCategorical)item).prediction_dict.get(student_value_of_property);
            } else {
                double boundry_value = ((decisionStumpNumer)item).get_boundry_value();
                split_info = ((decisionStumpNumer)item).prediction_dict.get(student_value_of_property>=boundry_value);
            }   

            double iteration_mean = split_info.get("Mean: ");
            weighted_mean_sum += weighted_mean_sum + (iteration_mean/weighted_variance_of_split);
            reverse_variance_sum += reverse_variance_sum + (1/weighted_variance_of_split);
        }
        double final_prediction = weighted_mean_sum /reverse_variance_sum;
        return (int) Math.round(final_prediction);
    }
}
