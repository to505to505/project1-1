import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import java.util.Arrays;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Random;

public class DataFunc {
    private static ArrayList<Data> dataList = new ArrayList<Data>();
    private static ObservableList<String> columnList;
    public static ArrayList<String> all_courses = new ArrayList<>(Arrays.asList("JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"));

    
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
                correlation_array[i] = Utility.pearsonCorrelation(right_data, col_number, i);
            }
            return correlation_array;


        }

       

    }
    
        
    
  



