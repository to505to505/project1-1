import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import javafx.scene.layout.VBox;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Random;


import javafx.scene.layout.StackPane;

public class HistogramChart  {
    public Scene previous_scene;

    public HistogramChart(Scene scene) {
        this.previous_scene = scene;
    }

    @Override
    public void start(Stage stage) {

        VBox vBox = new VBox();

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
        filterComboBox.getSelectionModel().selectFirst(); // Выбор первого элемента по умолчанию


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades", "StudentInfo"));
        dataSelector.setValue("GraduateGrades"); // Установите значение по умолчанию

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    

        EventHandler<ActionEvent> gii = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Hello World");
            }
        };

        
        filterComboBox.setOnAction(e -> updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue()));
        dataSelector.setOnAction(e -> {
            // Предположим, метод getNewItemsForFilterComboBox возвращает новый список элементов для filterComboBox
            if(dataSelector.getValue().equals("StudentInfo")) {

                List<String> newItems = Arrays.asList("Suruna Value", "Hurni Level", "Volta ", "Lal Count");
            
                // Обновление элементов в filterComboBox
                filterComboBox.setItems(FXCollections.observableArrayList(newItems));
                filterComboBox.getSelectionModel().selectFirst();
            } else {
                filterComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
                filterComboBox.getSelectionModel().selectFirst();
            }
            // Если нужно, выполните дополнительные действия, например обновление гистограммы
            updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue());
        });
        
    
        // Начальное заполнение гистограммы данными
        updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue());

        vBox.getChildren().addAll(dataSelector, filterComboBox, barChart);
        
    }
   

    public static void updateHistogram(String selectedData, BarChart<String, Number> barChart, String selectedFilter) {
        barChart.getData().clear(); // Очистка предыдущих данных
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.getCategories().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(selectedData);


        //Getting data to draw hist
        Map<String, Integer> freqMap = MainFunc.raw_data_hist(selectedData, selectedFilter);
        ArrayList<String> all_categories= new ArrayList<>();
        if (selectedData.equals("GraduateGrades")) {
            List<String> dataToAdd = Arrays.asList("6", "7", "8", "9", "10");
            all_categories.addAll(dataToAdd);
            xAxis.setCategories(FXCollections.observableArrayList(dataToAdd));

        } else if (selectedData.equals("CurrentGrades")) {
            List<String> dataToAdd1 = Arrays.asList("-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            all_categories.addAll(dataToAdd1);
            List<String> dataToAddtoShow = Arrays.asList("Missing values", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            xAxis.setCategories(FXCollections.observableArrayList(dataToAddtoShow));
        } else if(selectedData.equals("StudentInfo"))
         {
            if(selectedFilter.equals("Lal Count")) {
                List<String> dataToAdd3 = new ArrayList<>(MainFunc.LalSorted());
                all_categories.addAll(dataToAdd3);
                xAxis.setCategories(FXCollections.observableArrayList(dataToAdd3));
            } else {
            List<String> dataToAdd2 = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
                dataToAdd2.add(get_value_names(entry.getKey(), selectedFilter));
                all_categories.add(entry.getKey());
            }
            xAxis.setCategories(FXCollections.observableArrayList(dataToAdd2));
         } }
        for (String category: all_categories) {
            Integer value = freqMap.get(category);
            if(selectedFilter.equals("Hurni Level") || selectedFilter.equals("Suruna Value") || selectedFilter.equals("Volta ")) {
                String categoryString = get_value_names(category, selectedFilter);
                series.getData().add(new XYChart.Data<>(categoryString, (value != null) ? value : 0));
            }
            if(category.equals("-1")) 
                series.getData().add(new XYChart.Data<>("Missing values", (value != null) ? value : 0));
            else
                series.getData().add(new XYChart.Data<>(category, (value != null) ? value : 0));
        
    }
}

        
        

    
    public static String get_value_names(String value, String course_name) {
        switch (course_name) {
            case "Suruna Value":
                switch (value) {
                    case "0":
                        return "nulp";
                    case "1":
                        return "doot";
                    case "2":
                        return "lobi"; 
                    default:
                        return "unknown";
    
                }
            case "Hurni Level":
                switch (value) {
                    case "0":
                        return "nothing";
                    case "1":
                        return "low";
                    case "2":
                        return "medium";
                    case "3":
                        return "high";
                    case "4":
                        return "full";
                    default:
                        return "unknown";
   
                
                }
                
            case "Volta ":
                switch (value) {
                    case "1":
                        return "1 star";
                    case "2":
                        return "2 stars";
                    case "3":
                        return "3 stars";
                    case "4":
                        return "4 stars";
                    case "5":
                        return "5 stars";
                    default:
                        return "unknown"; }
                
            default:
                return "unknown";    }
}

}

