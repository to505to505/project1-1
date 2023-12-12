package main;

import java.util.Arrays;

import charts.BarAndHistogramChartFactory;
import data.AggregateData;
import data.Data;
import utility.Statistics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class Findings {
    public static BorderPane NumberOfNGsCourses(Data cGrades) {
        //create root pane
        BorderPane root = new BorderPane();

        //create left pane
        BorderPane leftPane = new BorderPane();
        root.setLeft(leftPane);

        //set root style
        root.setStyle("-fx-background-color: #f5f5ee");

        //create left flow pane
        FlowPane leftFlow = new FlowPane();
        leftFlow.setStyle("-fx-background-color: #ccbbaa");
        leftFlow.setHgap(10);
        leftFlow.setVgap(10);
        leftFlow.setPadding(new Insets(10, 10, 10, 10));
        

        //create left vbox
        VBox leftVBox = new VBox();

        //create left label
        Label leftLabel = new Label("Course selection");
        leftVBox.getChildren().add(leftLabel);

        //create course selection list
        ObservableList<String> courses = FXCollections.observableArrayList(cGrades.columnNames);
        ListView<String> courseSelection = new ListView<String>(courses);
        courseSelection.setPrefSize(80, 100);
        leftVBox.getChildren().add(courseSelection);
        
        //create course selection model        
        MultipleSelectionModel<String> courseSelectionModel = courseSelection.getSelectionModel();
        courseSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //create left select all button
        Button selectAllButton = new Button("Select All");
        selectAllButton.setOnAction(e -> courseSelection.getSelectionModel().selectAll());
        leftVBox.getChildren().add(selectAllButton);

        //add show grades button
        ToggleButton showGrades = new ToggleButton("Show grades for comparison");
        leftVBox.getChildren().add(showGrades);

        //create course bar chart
        Axis<String> xAxis = new CategoryAxis(); Axis<Number> yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setAnimated(false);
        
        //add listener to course selection model
        courseSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                
                //clear bar chart
                barChart.getData().clear();

                //get selected course column indices
                ObservableList<Integer> selectedItems = courseSelectionModel.getSelectedIndices();
                ObservableList<String> selectedCourses = courseSelectionModel.getSelectedItems();
                String[] selectedCoursesArray = new String[selectedCourses.size()];
                selectedCourses.toArray(selectedCoursesArray);
                Integer[] selectedIndices = new Integer[selectedItems.size()];
                selectedItems.toArray(selectedIndices);

                //Chart Title, Legend & Border
                barChart.setTitle("Number of students without grades per course");
                barChart.setLegendVisible(false);
                xAxis.setLabel("Course");
                yAxis.setLabel("NG count");
                barChart.setLegendSide(Side.BOTTOM);
                //barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

                //Create Series
                XYChart.Series<String,Number> series = new XYChart.Series<>();
                series.setName("NGs");
                for(int i = 0; i < selectedCoursesArray.length; i++){
                    series.getData().add(new XYChart.Data<String,Number>(selectedCoursesArray[i], Statistics.countOf(cGrades.data, -1, selectedIndices[i])));
                }
                barChart.getData().add(series);

                //add select all button action
                showGrades.setOnAction(e -> {
                if(showGrades.isSelected()) {
                        XYChart.Series<String,Number> s = new XYChart.Series<>();
                    s.setName("NGs");
                    for(int i = 0; i < selectedCoursesArray.length; i++){
                        s.getData().add(new XYChart.Data<String,Number>(selectedCoursesArray[i], cGrades.data.length - Statistics.countOf(cGrades.data, -1, selectedIndices[i])));
                    }
                    barChart.getData().add(s);
                } else {
                    barChart.getData().remove(1);}
                });
                
                leftPane.setCenter(barChart);
            }
        });

        leftFlow.getChildren().add(leftVBox);

        leftPane.setTop(leftFlow);
        leftPane.setCenter(barChart);
        
        leftPane.setTop(leftFlow);
       
        return leftPane;
    }

    public static BorderPane NumberOfNGsStudents(Data cGrades) {
        
        //create right pane
        BorderPane rightPane = new BorderPane();

        //create right flow pane
        FlowPane rightFlow = new FlowPane();
        rightFlow.setStyle("-fx-background-color: #ccbbaa");

        //create right vbox
        VBox rightVBox = new VBox();

        //create right label
        Label rightLabel = new Label("Student selection");
        rightVBox.getChildren().add(rightLabel);

        //create student selection array
        String[] studentIDs = new String[cGrades.data.length];
        for(int i = 0; i < cGrades.data.length; i++) {
            studentIDs[i] = String.valueOf(cGrades.studentIDs[i]);
        }

        //create student selection list
        ObservableList<String> students = FXCollections.observableArrayList(studentIDs);
        ListView<String> studentSelection = new ListView<String>(students);
        studentSelection.setPrefSize(80, 100);
        rightVBox.getChildren().add(studentSelection);

        //create student selection all button
        Button selectAllButton2 = new Button("Select All");
        selectAllButton2.setOnAction(e -> studentSelection.getSelectionModel().selectAll());
        rightVBox.getChildren().add(selectAllButton2);

        //create student selection model
        ToggleButton showGrades2 = new ToggleButton("Show grades for comparison");
        rightVBox.getChildren().add(showGrades2);

        //create student selection model
        MultipleSelectionModel<String> studentSelectionModel = studentSelection.getSelectionModel();
        studentSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //create student bar chart
        Axis<String> xAxis2 = new CategoryAxis(); Axis<Number> yAxis2 = new NumberAxis();
        BarChart<Number, String> barChart2 = new BarChart<Number, String>(yAxis2, xAxis2);
        barChart2.setAnimated(false);

        //add listener to student selection model
        studentSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                
                //clear bar chart
                barChart2.getData().clear();

                //get selected course column indices
                ObservableList<Integer> selectedItems = studentSelectionModel.getSelectedIndices();
                ObservableList<String> selectedStudents = studentSelectionModel.getSelectedItems();
                String[] selectedStudentsArray = new String[selectedStudents.size()];
                selectedStudents.toArray(selectedStudentsArray);
                Integer[] selectedIndices = new Integer[selectedItems.size()];
                selectedItems.toArray(selectedIndices);

                //Chart Title, Legend & Border
                barChart2.setTitle("Number of missing grades per students");
                barChart2.setLegendVisible(false);
                yAxis2.setLabel("NG Count");
                xAxis2.setLabel("Student");
                barChart2.setLegendSide(Side.BOTTOM);
                //barChart2.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

                //Create Series
                XYChart.Series<Number, String> series = new XYChart.Series<>();
                series.setName("NGs");
                for(int i = 0; i < selectedStudentsArray.length; i++){
                    series.getData().add(new XYChart.Data<Number, String>(cGrades.data[0].length - Statistics.countOf(cGrades.data[i], -1),selectedStudentsArray[i]));
                }
                barChart2.getData().add(series);

                showGrades2.setOnAction(e -> {
                if(showGrades2.isSelected()) {
                        XYChart.Series<Number,String> s = new XYChart.Series<>();
                    s.setName("NGs");
                    for(int i = 0; i < selectedStudentsArray.length; i++){
                        s.getData().add(new XYChart.Data<Number, String>(cGrades.data.length - Statistics.countOf(cGrades.data, -1, selectedIndices[i]),selectedStudentsArray[i]));
                    }
                    barChart2.getData().add(s);
                } else {
                    barChart2.getData().remove(1);}
                });
                
                rightPane.setCenter(barChart2);
            }
        });

        rightFlow.getChildren().add(rightVBox);

        rightPane.setTop(rightFlow);
        rightPane.setCenter(barChart2);
        
        rightPane.setTop(rightFlow);
       
        return rightPane;
    }

    public static BorderPane NumberOfNGsCoursesSorted() {

        BorderPane root = new BorderPane();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Sorted Ungraded Student Count per course");

        xAxis.setLabel("Courses");
        yAxis.setLabel("Ungraded student count");
           
        XYChart.Series<String, Number> year1 = new XYChart.Series<>();
        XYChart.Series<String, Number> year2 = new XYChart.Series<>();
        XYChart.Series<String, Number> year3 = new XYChart.Series<>();

        year1.setName("First year");
        year1.getData().add(new XYChart.Data<>("ATE-003", 2));
        year1.getData().add(new XYChart.Data<>("JJP-001", 2));
        year1.getData().add(new XYChart.Data<>("LUU-003", 2));
        year1.getData().add(new XYChart.Data<>("BKO-801", 2));
        year1.getData().add(new XYChart.Data<>("DSE-007", 2));
        year1.getData().add(new XYChart.Data<>("PLO-132", 5));
        year1.getData().add(new XYChart.Data<>("ATE-008", 6));
        year1.getData().add(new XYChart.Data<String, Number>("FEA-907", 530));
        year1.getData().add(new XYChart.Data<String, Number>("TGL-013", 530));
        year1.getData().add(new XYChart.Data<String, Number>("MON-014", 531));

        year2.setName("Second year");
        year2.getData().add(new XYChart.Data<String, Number>("WOT-104", 532));
        year2.getData().add(new XYChart.Data<String, Number>("BKO-800", 532));
        year2.getData().add(new XYChart.Data<String, Number>("KMO-007", 534));
        year2.getData().add(new XYChart.Data<String, Number>("ATE-014", 543));
        year2.getData().add(new XYChart.Data<String, Number>("HLU-200", 543));
        year2.getData().add(new XYChart.Data<String, Number>("JTW-004", 561));
        year2.getData().add(new XYChart.Data<String, Number>("MTE-004", 595));
        year2.getData().add(new XYChart.Data<String, Number>("LDE-009", 880));
        year2.getData().add(new XYChart.Data<String, Number>("DSE-003", 880));
        year2.getData().add(new XYChart.Data<String, Number>("ATE-214", 907));

        year3.setName("Third year");
        year3.getData().add(new XYChart.Data<String, Number>("WDM-974", 920));
        year3.getData().add(new XYChart.Data<String, Number>("JHF-101", 921));
        year3.getData().add(new XYChart.Data<String, Number>("TSO-010", 921));
        year3.getData().add(new XYChart.Data<String, Number>("DSE-005", 922));
        year3.getData().add(new XYChart.Data<String, Number>("JTE-234", 922));
        year3.getData().add(new XYChart.Data<String, Number>("PPL-239", 922));
        year3.getData().add(new XYChart.Data<String, Number>("GHL-823", 933));
        year3.getData().add(new XYChart.Data<>("LPG-307", 1128));
        year3.getData().add(new XYChart.Data<>("LOE-103", 1128));
        year3.getData().add(new XYChart.Data<>("SLE-332", 1128));
        

        bc.getData().addAll (year1, year2, year3);
        bc.setBarGap(-10);

        root.setCenter(bc);
        return root;
    }

    public static BorderPane gradeAveragePerPropertyValue(AggregateData fullGrades) {
        //create root pane
        BorderPane root = new BorderPane();

        //create flow pane
        FlowPane flow = new FlowPane();
        flow.setStyle("-fx-background-color: #ccbbaa");
        flow.setHgap(10);
        flow.setVgap(10);
        flow.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(flow);

        //create property selection vbox
        VBox propertyVBox = new VBox();
        flow.getChildren().add(propertyVBox);

        //create property label
        Label propertyLabel = new Label("Property selection");
        propertyVBox.getChildren().add(propertyLabel);

        //create property selection list
        ObservableList<String> properties = FXCollections.observableArrayList(fullGrades.infoColumnNames);
        ListView<String> propertySelection = new ListView<String>(properties);
        propertySelection.setPrefSize(80, 100);
        propertyVBox.getChildren().add(propertySelection);

        //create property selection model
        MultipleSelectionModel<String> propertySelectionModel = propertySelection.getSelectionModel();
        propertySelectionModel.setSelectionMode(SelectionMode.SINGLE);


        //create course vbox
        VBox courseVBox = new VBox();
        flow.getChildren().add(courseVBox);

        //create label
        Label courseLabel = new Label("Course selection");
        courseVBox.getChildren().add(courseLabel);

        //create course selection list
        ObservableList<String> courses = FXCollections.observableArrayList(fullGrades.columnNames);
        ListView<String> courseSelection = new ListView<String>(courses);
        courseSelection.setPrefSize(80, 100);
        courseVBox.getChildren().add(courseSelection);
        
        //create course selection model        
        MultipleSelectionModel<String> courseSelectionModel = courseSelection.getSelectionModel();
        courseSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //create select all button
        Button selectAllButton = new Button("Select All");
        selectAllButton.setOnAction(e -> courseSelection.getSelectionModel().selectAll());
        courseVBox.getChildren().add(selectAllButton);
        
        //create course line chart
        Axis<String> xAxis = new CategoryAxis(); Axis<Number> yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setAnimated(false);
        
        //add listener to course selection model
        courseSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                
                //clear bar chart
                lineChart.getData().clear();

                //get selected property column index
                Integer selectedPropertyIndex = propertySelectionModel.getSelectedIndex();
                String selectedPropertyName = propertySelectionModel.getSelectedItem();

                //System.out.println(selectedPropertyIndex);

                if(selectedPropertyIndex >= 0) {

                    //get selected course column indices
                    ObservableList<Integer> selectedCoursesIndeces = courseSelectionModel.getSelectedIndices();
                    ObservableList<String> selectedCoursesNames = courseSelectionModel.getSelectedItems();
                    String[] selectedCoursesNamesArray = new String[selectedCoursesNames.size()];
                    selectedCoursesNames.toArray(selectedCoursesNamesArray);
                    Integer[] selectedIndices = new Integer[selectedCoursesIndeces.size()];
                    selectedCoursesIndeces.toArray(selectedIndices);

                    //System.out.println(Arrays.toString(selectedIndices));

                    //Chart Title, Legend & Border
                    lineChart.setTitle("Grade average per category in " + selectedPropertyName);
                    lineChart.setLegendVisible(true);
                    xAxis.setLabel("Course");
                    yAxis.setLabel("Grade average");
                    lineChart.setLegendSide(Side.BOTTOM);
                    //lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

                    //Create Series
                    if(selectedPropertyIndex!=2){
                        for(int j = 0; j < propertyDict[selectedPropertyIndex].length; j++){ //for each property value
                            XYChart.Series<String,Number> series = new XYChart.Series<>();
                            series.setName(propertyDict[selectedPropertyIndex][j]);
                            for(int i = 0; i < selectedIndices.length; i++){ //for each course
                                series.getData().add(new XYChart.Data<String,Number>(selectedCoursesNamesArray[i], Statistics.averageOf(fullGrades, i, selectedPropertyIndex, propertyDict[selectedPropertyIndex][j])));
                            }
                            lineChart.getData().add(series);
                        }
                    } else {
                        for(int j = 0; j < lalBoundries.length-1; j++){ //for each set of boundry values
                            XYChart.Series<String,Number> series = new XYChart.Series<>();
                            series.setName("Lal Count");
                            for(int i = 0; i < selectedIndices.length; i++){ //for each course
                                series.getData().add(new XYChart.Data<String,Number>(selectedCoursesNamesArray[i], Statistics.averageOf(fullGrades, i, 2, lalBoundries[j], lalBoundries[j+1])));
                            }
                            lineChart.getData().add(series);
                        }
                    }
                }
                
                root.setCenter(lineChart);
            }
        });

        return root;
    }

    //Suruna Value,Hurni Level,Lal Count,Volta 
    public static String[][] propertyDict = {
        {"nulp", "doot", "lobi"},
        {"nothing", "low", "medium", "high", "full"},
        {},
        {"1 star", "2 stars", "3 stars", "4 stars", "5 stars"}
    };
    public static double[] lalBoundries = {0, 20, 40, 60, 80, 100};
}
