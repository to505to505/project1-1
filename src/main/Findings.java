package main;

import java.util.Arrays;

import charts.BarAndHistogramChartFactory;
import data.Data;
import utility.Statistics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
                barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

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
        BarChart<String, Number> barChart2 = new BarChart<String, Number>(xAxis2, yAxis2);
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
                xAxis2.setLabel("Course");
                yAxis2.setLabel("NG count");
                barChart2.setLegendSide(Side.BOTTOM);
                barChart2.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

                //Create Series
                XYChart.Series<String,Number> series = new XYChart.Series<>();
                series.setName("NGs");
                for(int i = 0; i < selectedStudentsArray.length; i++){
                    series.getData().add(new XYChart.Data<String,Number>(selectedStudentsArray[i], cGrades.data[0].length - Statistics.countOf(cGrades.data[i], -1)));
                }
                barChart2.getData().add(series);

                showGrades2.setOnAction(e -> {
                if(showGrades2.isSelected()) {
                        XYChart.Series<String,Number> s = new XYChart.Series<>();
                    s.setName("NGs");
                    for(int i = 0; i < selectedStudentsArray.length; i++){
                        s.getData().add(new XYChart.Data<String,Number>(selectedStudentsArray[i], cGrades.data.length - Statistics.countOf(cGrades.data, -1, selectedIndices[i])));
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
}
