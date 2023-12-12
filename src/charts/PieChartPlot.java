package charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainFunc;

import java.util.ArrayList;
import java.util.Arrays;

public class PieChartPlot  extends Application {
    public Scene previous_scene;

    public PieChartPlot(Scene scene) {
        this.previous_scene = scene;
    }
    @Override
    public void start(Stage stage) {

        VBox vBox = new VBox();

        Button back = new Button("Get back");
        back.setOnAction(e -> stage.setScene(previous_scene));

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.setItems(FXCollections.observableArrayList("GPA greater than 7.5", "No grades lower than 7"));
        filterComboBox.getSelectionModel().selectFirst(); // Выбор первого элемента по умолчанию


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades"));
        dataSelector.setValue("GraduateGrades"); // Установите значение по умолчанию
        // Creating a Pie chart

        PieChart pieChart = new PieChart();


        filterComboBox.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue()));
        dataSelector.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue()));





        updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue());
        
        vBox.getChildren().addAll(dataSelector, filterComboBox, pieChart, back);


        // Setting the title of the Pie chart
        pieChart.setTitle("Pie Chart");

        // Creating a scene object
        Scene scene = new Scene(vBox, 600, 400);

        // Setting the title to Stage
        stage.setTitle("Pie Chart");
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void updatePieChart(String selectedData, PieChart PieChart, String selectedFilter) {
        PieChart.getData().clear(); // Очистка предыдущих данных
        ArrayList<Integer> cum_students = MainFunc.cum(selectedData, selectedFilter);
        int all_students_length = MainFunc.data_size(selectedData);
        PieChart.Data slice1 = new PieChart.Data("Cum-Laude", cum_students.size());
        PieChart.Data slice2 = new PieChart.Data("Others", all_students_length-cum_students.size());
        PieChart.getData().add(slice1);
        PieChart.getData().add(slice2);
        PieChart.setLabelsVisible(true);
        PieChart.layout();
        



}
}
