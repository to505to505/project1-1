import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.lang.Math;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.beans.binding.IntegerBinding;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;

public class MainFunc {
    public static String[] cNames0 = new String[30];
    public static  int[] sIDs0 = new int[1128];
    public static  double[][] data0 = new double[1128][30];
    public static String[] cNames1 = new String[30];
    public static int[] sIDs1 = new int[18321];
    public static  double[][] data1 = new double[18321][30];
    public static  String[] cNames2 = new String[4];
    public static int[] sIDs2 = new int[1128];
    public static double[][] data2 = new double[1128][4];
    public static  String[] cNames_t = new String[34];
    public static int[] sIDs_t = new int[1128];
    public static double[][] data_t = new double[1128][34];
    public static ArrayList<String> all_courses = new ArrayList<>(Arrays.asList("JTE-234", "ATE-003", "TGL-013", "PPL-239", "WDM-974", "GHL-823", "HLU-200", "MON-014", "FEA-907", "LPG-307", "TSO-010", "LDE-009", "JJP-001", "MTE-004", "LUU-003", "LOE-103", "PLO-132", "BKO-800", "SLE-332", "BKO-801", "DSE-003", "DSE-005", "ATE-014", "JTW-004", "ATE-008", "DSE-007", "ATE-214", "JHF-101", "KMO-007", "WOT-104"));
    

    public MainFunc() {
        // the arrays containing the column names and data
        
        // change the last argument from false to true if you want to print the array
        converter(cNames0, sIDs0, data0, "src/data/CurrentGrades.csv", false);
    
        // change the last argument from false to true if you want to print the array
        converter(cNames1, sIDs1, data1, "src/data/GraduateGradesNew.csv", false);

        // change the last argument from false to true if you want to print the array
        converter(cNames2, sIDs2, data2, "src/data/StudentInfo.csv", false);

        for (int i = 0; i < 30; i++) {
            cNames_t[i] = cNames0[i];
            sIDs_t[i] = sIDs0[i];
            for (int j = 0; j < data_t.length; j++) {
                data_t[j][i] = data0[j][i];
            }
        }
        for (int k = 0; k < 4; k++) {
            cNames_t[30 + k] = cNames2[k];
            for (int y = 0; y < data_t.length; y++) {
                data_t[y][k + 30] = data2[y][k];
            }
        }

    }

    
    public static void  main(String[] args) {
        MainFunc MainFunc = new MainFunc();
        System.out.println(data0[101][13]);
    }
    

    /* METHODS FOR VISUALIZATIION */ 
    public static Map<String, Integer> raw_data_hist(String data, String course) {
        MainFunc MainFunc = new MainFunc();
        int course_col_num = 0;
        double [][] data_num = null;
        String[] cNames_total = null;
        switch (data) {
            case "CurrentGrades":
                for (int k = 0; k < cNames0.length; k++) {
                    if (cNames0[k].equals(course)) {
                        course_col_num = k;
                    }
                }
                data_num = data0;
                cNames_total = cNames0;
            
                break;
            case "GraduateGrades":
                for (int k = 0; k < cNames1.length; k++) {
                    if (cNames1[k].equals(course)) {
                        course_col_num = k;
                    }
                }
                data_num = data1;
                cNames_total = cNames1;

                break;
            case "Current+StudentInfo":
                for (int k = 0; k < cNames_t.length; k++) {
                    if (cNames_t[k].equals(course)) {
                        course_col_num = k;
                    }
                }
                data_num = data_t;
                cNames_total = cNames_t;

                break;
            case "StudentInfo":
                for (int k = 0; k < cNames2.length; k++) {
                    if (cNames2[k].equals(course)) {
                        course_col_num = k;
                    }
                }
                data_num = data2;
                cNames_total = cNames2;
                break;
        }

        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for(int i =0; i<data_num.length; i++) {
            for(int j = 0; j<data_num[i].length; j++) {
                if(j==course_col_num)
                    frequencyMap.put(String.valueOf(Math.round(data_num[i][j])), frequencyMap.getOrDefault(String.valueOf(Math.round(data_num[i][j])), 0) + 1);  
            }
        }
        return frequencyMap;
    
    }


    public static ArrayList<Integer> cum(String data, String type) {
        MainFunc MainFunc = new MainFunc();
        double [][] data_num = null;
        switch (data) {
            case "CurrentGrades":
                
                data_num = data0;
            
            
                break;
            case "GraduateGrades":
            
                data_num = data1;
               

                break;
            case "Current+StudentInfo":
                
                data_num = data_t;
    

                break;
            case "StudentInfo":
                
                data_num = data2;
        
                break;
        }


        ArrayList<Integer> users = new ArrayList<Integer>();
        ArrayList<Integer> not_cum = new ArrayList<Integer>();    
        if(type.equals("GPA greater than 7.5")) {
            for (int i = 0; i < data_num.length; i++) {
                double summ = 0;
                int counter = 0;
                for (int j = 0; j < data_num[i].length; j++) {
                    if(data_num[i][j]!=-1)  {
                        summ += data_num[i][j];
                        counter++;
                    }
                }
                /// cum-laude is GPA better than 8
                if (summ / counter >= 7.5) {
                    users.add(i);
                }
            }
        } else {
            for (int i = 0; i < data_num.length; i++) {
                for (int l = 0; l < data_num[i].length; l++) {
                    if(data_num[i][l]<7 && data_num[i][l]!=-1) {
                        not_cum.add(i);
                        break;
                        
                    }
                }
            }
                
                for(int k =0; k<data_num.length; k++) {
                    if(!not_cum.contains(k)) {
                        users.add(k);
                }}
            }
        

        return users;
    }



    public static List<String> LalSorted() {
        MainFunc MainFunc = new MainFunc();
        int Lal_count_col_n = 0;
        List<Integer> originalList = new ArrayList<>();
        for(int k=0; k<cNames_t.length; k++) {
            if(cNames_t[k].equals("Lal Count")) {
                Lal_count_col_n = k;
            }
            }
        
        for(int i =0; i < data_t.length; i++) {
            for(int j =0; j <data_t[i].length; j++) {
                if(j==Lal_count_col_n) {
                    originalList.add((int)data_t[i][j]);
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



    public static int data_size(String data) {
        MainFunc MainFunc = new MainFunc();
        switch (data) {
            case "CurrentGrades":
                
                return data0.length;
            
            
            case "GraduateGrades":
            
                return data1.length;
               

                
            case "Current+StudentInfo":
                
                return data_t.length;
    

               
            case "StudentInfo":
                
                return data2.length;
        
                
        }
        return 0;
    }

    public static XYChart.Series<Number, Number> getScatter(String course1, String course2, String data) {
        MainFunc MainFunc = new MainFunc();
        XYChart.Series<Number, Number> series  = new XYChart.Series<>();

        double [][] data_num = null;
        switch (data) {
            case "CurrentGrades":
                
                data_num = data0;

            
                break;
            case "GraduateGrades":
            
                data_num = data1;
                break;
        }
        int course_col_num1 = 0;
        int course_col_num2  = 0;
        for (int k = 0; k < cNames0.length; k++) {
                    if (cNames0[k].equals(course1)) {
                        course_col_num1= k;
                    }
                    if (cNames0[k].equals(course2)) {
                        course_col_num2= k;
                    }
                }

        double[][] indexes_array = new double[11][11];
        for(int i =0; i<data_num.length; i++) { 
            if(data_num[i][course_col_num1]!=-1 && data_num[i][course_col_num2]!=-1) {
                int property1 = (int) data_num[i][course_col_num1];
                int property2 = (int) data_num[i][course_col_num2];
                indexes_array[property1][property2]+=0.2;
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

    public static void converter(String[] cNames, int[] sIDs, double[][] data, String fileName, boolean print) {
        try {
            File file = new File(fileName);

            // This code uses two Scanners, one which scans the file line per line
            Scanner fileScanner = new Scanner(file);

            int linesDone = 0; // keeps count of the line

            // write column names to cNames[]
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            lineScanner.next(); // skip StudentID, as the student IDs will be assigned their own array
            int k = 0;
            while (lineScanner.hasNext())
                cNames[k++] = lineScanner.next();
            lineScanner.close();

            // write data to array[][]
            int l = 0; // sIDs[] index
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();

                // and one that scans the line entry per entry using the commas as delimiters
                lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");

                sIDs[l++] = lineScanner.nextInt();

                int j = 0;
                while (lineScanner.hasNext()) {
                    String nextValue = lineScanner.next();
                    try {
                        data[linesDone][j] = Double.parseDouble(nextValue);
                    } catch (NumberFormatException e) {
                        data[linesDone][j] = convertStringToNumber(nextValue);
                    }
                    j++;
                }

                linesDone++;
                lineScanner.close();
            }

            if (print) {
                System.out.println(Arrays.deepToString(data));
                System.out.println(Arrays.toString(sIDs));
                System.out.println(Arrays.toString(cNames));
            }

            fileScanner.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    



    private static double convertStringToNumber(String value) {
        switch (value) {
            case "NG":
                return -1;
            case "nulp":
                return 0;
            case "doot":
                return 1;
            case "lobi":
                return 2;
            case "nothing":
                return 0;
            case "low":
                return 1;
            case "medium":
                return 2;
            case "high":
                return 3;
            case "full":
                return 4;
            case "1 star":
                return 1;
            case "2 stars":
                return 2;
            case "3 stars":
                return 3;
            case "4 stars":
                return 4;
            case "5 stars":
                return 5;
            default:
                return -2;
        }
    }
}