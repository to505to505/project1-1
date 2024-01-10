package prediction;

import java.util.ArrayList;
import data.*;

public class DSCat extends DecisionStump {
    
    private int infoIndex;
    private int targetIndex;
    private DataPartition dataPartition;
    private AggregateData aggregateData;

    public DSCat(DataPartition partition,  int infoIndex, int targetIndex) { 
        this.infoIndex = infoIndex;
        this.targetIndex = targetIndex;
        this.dataPartition = partition;
        this.aggregateData = partition.aggregateData;

        

    }
    private void predict() {
        aggregateData.


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
            
    }
}
