import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.awt.Desktop;
import java.awt.Label;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.*;

public class DataFunc {
    private static ArrayList<Data> dataList = new ArrayList<Data>();
    private static ObservableList<String> columnList;
    public static ArrayList<String> all_courses = new ArrayList<>(Arrays.asList("JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"));
    public static ArrayList<String> all_courses_and_info = new ArrayList<>(Arrays.asList("Suruna Value", "Hurni Level", "Volta ","Lal Count",  "JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"));

    
    // init our basic datasets
    private static void dataInit(){
        Data data_current = new Data("src/data/CurrentGrades.csv");
        data_current.name = "CurrentGrades";
        dataList.add(data_current);
        Data data_graduate = new Data("src/data/bugData.csv");
        data_graduate.name = "GraduateGrades";
        dataList.add(data_graduate);
        Data data_student_info = new Data("src/data/StudentInfo.csv");
        data_student_info.name = "StudentInfo";
        dataList.add(data_student_info);
        Data combinedData = new Data(new AggregateData("src/data/CurrentGrades.csv", "src/data/StudentInfo.csv"));
        combinedData.name = "Current+StudentInfo";
        dataList.add(combinedData);
        
    }

    /// data methods for cum laude pie chart 
    public static ArrayList<Integer> cum(String name, String type) {
        dataInit();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
        ArrayList<Integer> users = new ArrayList<Integer>();
        ArrayList<Integer> not_cum = new ArrayList<Integer>();    
        if(type.equals("GPA greater than 7.5")) {
            for (int i = 0; i < right_data.data.length; i++) {
                double summ = 0;
                int counter = 0;
                for (int j = 0; j < right_data.data[i].length; j++) {
                    if(right_data.data[i][j]!=-1)  {
                        summ += right_data.data[i][j];
                        counter++;
                    }
                }
                /// cum-laude is GPA better than 8
                if (summ / counter >= 7.5) {
                    users.add(i);
                }
            }
        } else {
            for (int i = 0; i < right_data.data.length; i++) {
                for (int l = 0; l < right_data.data[i].length; l++) {
                    if(right_data.data[i][l]<7 && right_data.data[i][l]!=-1) {
                        not_cum.add(i);
                        break;
                        
                    }
                }
            }
                
                for(int k =0; k<right_data.data.length; k++) {
                    if(!not_cum.contains(k)) {
                        users.add(k);
                }}
            }
        

        return users;
    }

    /// getting the amount of rows in a dataset
    public static int data_size(String name) {
        dataInit();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
        return right_data.data.length;
    }


    /// data methods for histogram chart\

    // sorting LAL so it will appear in a correct order on the histogram
    public static List<String> LalSorted(String name) {
        dataInit();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
        int Lal_count_col_n = 0;
        List<Integer> originalList = new ArrayList<>();
        for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals("Lal Count")) {
                Lal_count_col_n = k;
            }
            }
        
        for(int i =0; i < right_data.data.length; i++) {
            for(int j =0; j <right_data.data[i].length; j++) {
                if(j==Lal_count_col_n) {
                    originalList.add((int)right_data.data[i][j]);
                }
                    
            }
        }
        Set<Integer> uniqueSet = new HashSet<>(originalList);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);
        List<Integer> sortedIntList = uniqueList.stream().sorted().collect(Collectors.toList());
        List<String> stringList = new ArrayList<>();    
        stringList = sortedIntList.stream()
                                               .map(Object::toString)
                                               .collect(Collectors.toList());
        return stringList;
    }

    //create frequency map for histogram
    public static Map<String, Integer> raw_data_hist(String name, String course) {
        dataInit();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
        int course_col_num = 0;
        for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(course)) {
                course_col_num = k;
            }
            }
    
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for(int i =0; i<right_data.data.length; i++) {
            for(int j = 0; j<right_data.data[i].length; j++) {
                if(j==course_col_num)
                    frequencyMap.put(String.valueOf(Math.round(right_data.data[i][j])), frequencyMap.getOrDefault(String.valueOf(Math.round(right_data.data[i][j])), 0) + 1);  
            }
        }
        return frequencyMap;
    
    }



    /// data methods for scatter plot chart


    // getting the data for scatter plot chart
    public static XYChart.Series<Number, Number> getScatter(String course1, String course2, String name) {
        dataInit();

        XYChart.Series<Number, Number> series  = new XYChart.Series<>();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
            }
        
        int course_col_num1 = 0;
        int course_col_num2  = 0;

        for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(course2)) {
                course_col_num2= k;
            }
            if(right_data.columnNames[k].equals(course1)) {
                course_col_num1 = k;
            }
        }
        double[] line = Utility.linearRegressionLine(right_data, course_col_num1, course_col_num2);

        double[][] indexes_array = new double[11][11];
        for(int i =0; i<right_data.data.length; i++) { 
            int counter = 0;
            if(right_data.data[i][course_col_num1]!=-1 && right_data.data[i][course_col_num2]!=-1) {
                int property1 = (int) right_data.data[i][course_col_num1];
                int property2 = (int) right_data.data[i][course_col_num2];
                indexes_array[property1][property2]+=1;
                counter++;
            }
        }
        int max =1;
        int min = 100000;
        for(int j =0; j<indexes_array.length; j++) {
            for(int k =0 ;k<indexes_array[j].length; k++) {
                if(indexes_array[j][k]!=0) {
                    if(indexes_array[j][k]>max) {
                        max = (int) indexes_array[j][k];
                    }
                    if(indexes_array[j][k]<min) {
                        min = (int) indexes_array[j][k];
                    }
                }
            }
        }
        for(int j =0; j<indexes_array.length; j++) {
            for(int k =0 ;k<indexes_array[j].length; k++) {
                if(indexes_array[j][k]!=0) {
                    indexes_array[j][k] = (indexes_array[j][k]-min)/(max-min)*15;
                }
            }
        }


        for(int j =0; j<indexes_array.length; j++) {
            for(int k =0 ;k<indexes_array[j].length; k++) {
                if(indexes_array[j][k]!=0) {
                XYChart.Data<Number, Number> data_piece = new XYChart.Data<>(j, k);
                Circle circle = new Circle(indexes_array[j][k]);
                data_piece.setNode(circle);
                series.getData().add(data_piece); 
                }
            }
        }
        return series;

        }

        public static ArrayList<ArrayList<XYChart.Series<String, Number>>> getJoint(String course1, String course2, String property,String name) {
            dataInit();

            ArrayList<XYChart.Series<String, Number>> series_for_x = new ArrayList<>();
            ArrayList<XYChart.Series<String, Number>> series_for_y = new ArrayList<>();


            HashMap<String, XYChart.Series<Number, Number>> series = new HashMap<>();
            Data right_data = dataList.get(3);
            
            int course_col_num1 = 0;
            int course_col_num2  = 0;
            int property_col_num = 0;
    
            for(int k =0; k<right_data.columnNames.length; k++) {
                if(right_data.columnNames[k].equals(course2)) {
                    course_col_num2= k;
                }
                if(right_data.columnNames[k].equals(course1)) {
                    course_col_num1 = k;
                }
                if(right_data.columnNames[k].equals(property)) {
                    property_col_num = k;
                }
            }
            
            HashMap<Double, double[][]> data_joint = new HashMap<>();

            XYChart.Series<String, Number> series_iter = new XYChart.Series<>();



            double[] property_array = new double[right_data.data.length];
            for (int s = 0; s < right_data.data.length; s++) {
                property_array[s] = right_data.data[s][property_col_num];
            }
            Double[] propertyArrayobject = Arrays.stream(property_array).boxed().toArray(Double[]::new);

            Set<Double> uniqueSet = new HashSet<>(Arrays.asList(propertyArrayobject));
            Double[] uniqueArr = uniqueSet.toArray(new Double[0]);



            for(Double value: uniqueArr) {
            double[][] indexes_array = new double[11][11];
            for(int i =0; i<right_data.data.length; i++) { 
                int counter = 0;
                if(right_data.data[i][property_col_num]==value) {
                if(right_data.data[i][course_col_num1]!=-1 && right_data.data[i][course_col_num2]!=-1) {
                    int property1 = (int) right_data.data[i][course_col_num1];
                    int property2 = (int) right_data.data[i][course_col_num2];
                    indexes_array[property1][property2]+=1;
                    counter++;
                }
            }
            }
            int max =1;
            int min = 100000;
            for(int j =0; j<indexes_array.length; j++) {
                for(int k =0 ;k<indexes_array[j].length; k++) {
                    if(indexes_array[j][k]!=0) {
                        if(indexes_array[j][k]>max) {
                            max = (int) indexes_array[j][k];
                        }
                        if(indexes_array[j][k]<min) {
                            min = (int) indexes_array[j][k];
                        }
                    }
                }
            }
            for(int j =0; j<indexes_array.length; j++) {
                for(int k =0 ;k<indexes_array[j].length; k++) {
                    if(indexes_array[j][k]!=0) {
                        indexes_array[j][k] = (indexes_array[j][k]-min)/(max-min)*15;
                    }
                }
            }


            String name_variable = DSCat.get_value_names(value, right_data.columnNames, property_col_num);

            XYChart.Series<String, Number> series_for_x_iter = new XYChart.Series<>();
            XYChart.Series<String, Number> series_for_y_iter = new XYChart.Series<>();
            series_for_x_iter.setName(name_variable);
            series_for_y_iter.setName(name_variable);
            
            for(int j =0; j<indexes_array.length; j++) {
                double sum_Y = 0;
                for(int k =0 ;k<indexes_array[j].length; k++) {
                    sum_Y+=indexes_array[j][k];
                    }
                    if(sum_Y!=0) {
                        XYChart.Data<String, Number> data_piece = new XYChart.Data<>(String.valueOf(j), sum_Y);
        
                        series_for_x_iter.getData().add(data_piece);
                    }
                }
            for(int l =0; l<indexes_array[0].length; l++ ) {
                double sum_X = 0;
                for(int u =0; u<indexes_array.length; u++) {
                    sum_X+=indexes_array[u][l];
                }
                if(sum_X!=0) {
    
                        XYChart.Data<String, Number> data_piece = new XYChart.Data<>(String.valueOf(l), sum_X);
                        series_for_y_iter.getData().add(data_piece);
                        
                }
            }
            series_for_y.add(series_for_y_iter);
            series_for_x.add(series_for_x_iter);
            }
            ArrayList<ArrayList<XYChart.Series<String, Number>>> total_list = new ArrayList<>();
            total_list.add(series_for_x);
            //total_list.add(series_for_y);


            return total_list;
            
            
        }

        
    
    
            
        



        ///correlation matrix methods
        public static double[] correlationResNew(String name, String column_name) {
            dataInit();
            Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
            }
            int col_number = 0;
            for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(column_name)) {
                col_number = k;
            }
            }
            double[] correlation_array = new double[right_data.columnNames.length];
            for(int i =0; i<right_data.columnNames.length; i++) {
                //double roundedNumber = Double.parseDouble(String.format("%.1f", Utility.pearsonCorrelation(right_data, col_number, i)));
                double roundedNumber = Math.round(Utility.pearsonCorrelation(right_data, col_number, i) * 100) / 100.0;
                correlation_array[i] = roundedNumber;
            }
            return correlation_array;
        }
        public static double[] euclidianResNew(String name, String column_name){
            dataInit();
            Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
        int col_number = 0;
            for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(column_name)) {
                col_number = k;
            }
            }
            double[] euclidian_array = new double[right_data.columnNames.length];
            for(int i =0; i<right_data.columnNames.length; i++) {
                double roundedNumber = Math.round(Utility.euclidian(right_data.data, col_number, i) * 10000) / 10000.0;
                euclidian_array[i] = roundedNumber;
            }
            return euclidian_array;

        }
        public static double[] similarityResNew(String name, String column_name) {
            dataInit();
            Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(name)) {
                    right_data = data;
            }
        }
            int col_number = 0;
            for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(column_name)) {
                col_number = k;
            }
            }
            double[] similarity_array = new double[right_data.columnNames.length];
            for(int i =0; i<right_data.columnNames.length; i++) {
                double roundedNumber = Math.round(Utility.cosine(right_data.data, col_number, i) * 1000) / 1000.0;
                similarity_array[i] = roundedNumber;
            }
            return similarity_array;
        }


        /// getting data for swarm plot
        // getting the data for scatter plot chart
        public static XYChart.Series<Number, Number> getSwarm(HashMap<Double, ArrayList<Integer>> users_for_plots, String property_name) {
            XYChart.Series<Number, Number> series  = new XYChart.Series<>();
            double[][] indexes_array = new double[users_for_plots.size()][11];
            for (Map.Entry<Double, ArrayList<Integer>> entry : users_for_plots.entrySet()) {
                ArrayList<Integer> ii = entry.getValue();
                for(Integer grade : ii) {
                    if(grade!=-1) {
                        if(property_name.equals("Volta ")){ 
                            indexes_array[((int) Math.round(entry.getKey()))-1][grade]++;
                        } else {
                        indexes_array[(int) Math.round(entry.getKey())][grade]++;
                    }}
                }
                
            }
        
        ArrayList<ArrayList<Integer>> index_array_max_min = new ArrayList<ArrayList<Integer>>();
        for(int j =0; j<indexes_array.length; j++) {
            int max = 0;
            int min = 10000;
            for(int k =0 ;k<indexes_array[j].length; k++) {
                
                if(indexes_array[j][k]!=0) {
                    if(indexes_array[j][k]>max) {
                        max = (int) indexes_array[j][k];
                    }
                    if(indexes_array[j][k]<min) {
                        min = (int) indexes_array[j][k];
                    }
                }
            }
                index_array_max_min.add(new ArrayList<Integer>(Arrays.asList(min, max)));
            

        }
        for(int j =0; j<indexes_array.length; j++) {
            for(int k =0 ;k<indexes_array[j].length; k++) {
                if(indexes_array[j][k]!=0) {
                    indexes_array[j][k] = (indexes_array[j][k]-index_array_max_min.get(j).get(0))/(index_array_max_min.get(j).get(1)-index_array_max_min.get(j).get(0))*18;
                }
            }
        }


        for(int j =0; j<indexes_array.length; j++) {
            for(int k =0 ;k<indexes_array[j].length; k++) {
                if(indexes_array[j][k]!=0) {
                XYChart.Data<Number, Number> data_piece = new XYChart.Data<>(j+2, k);
                Circle circle = new Circle(indexes_array[j][k]);
                data_piece.setNode(circle);
                series.getData().add(data_piece); 
                }
            }
        }
            return series;
        }
        public static LinkedHashMap<String, Double> courses_diffculty(String data_name, boolean ascending) {
            dataInit();
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(data_name)) {
                    right_data = data;
            }
        }
        
    
        
        Map<String, Double> frequencyMap = new HashMap<>();
        for(int i =0; i<right_data.columnNames.length; i++) {
                //average_grades[i] = Utility.mean(right_data, i);
            frequencyMap.put(right_data.columnNames[i], Utility.mean(right_data, i));  
        }
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
        if(ascending) {
            sortedMap = frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        } else {
            sortedMap = frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        }
        return sortedMap;
    
        


    }
}