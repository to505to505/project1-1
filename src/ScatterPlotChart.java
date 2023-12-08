import java.util.Random;
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class ScatterPlotChart extends Application {

    public Scene previous_scene;

    public ScatterPlotChart(Scene scene) {
        this.previous_scene = scene;
    }

    @Override
    public void start(Stage stage) {
       VBox vBox = new VBox();
       stage.setTitle("Scatter Chart");
       Button back = new Button("Get back");
       back.setOnAction(e -> stage.setScene(previous_scene));

       ComboBox<String> filter1ComboBox = new ComboBox<>();
       filter1ComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
       filter1ComboBox.getSelectionModel().selectFirst(); // Выбор первого элемента по умолчанию


       ComboBox<String> filter2ComboBox= new ComboBox<>();
       filter2ComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
       filter2ComboBox.getSelectionModel().selectLast(); // Установите значение по умолчанию

       ComboBox<String> dataSelector = new ComboBox<>();
       dataSelector.setItems(FXCollections.observableArrayList("GraduateGrades", "CurrentGrades"));
       dataSelector.getSelectionModel().selectFirst(); // Установите значение по умолчанию

    
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(filter1ComboBox.getValue());
        yAxis.setLabel(filter2ComboBox.getValue());
        
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        
        
        
        
     
        updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue() );


        filter1ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));
        filter2ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(),scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));

        vBox.getChildren().addAll(dataSelector, filter1ComboBox,filter2ComboBox, scatterChart, back);
        Scene scene = new Scene(vBox, 900, 750);
        stage.setScene(scene);
        stage.setTitle("Scatter");
        stage.show();
    }
    private void updateScatter(String selectedData, ScatterChart<Number, Number> ScatterChart, String selectedFilter1, String selectedFilter2) {
        ScatterChart.getData().clear();
        
        XYChart.Series<Number, Number> series =  MainFunc.getScatter(selectedFilter1, selectedFilter2,  selectedData);
        ScatterChart.getData().add(series);
        ScatterChart.layout();

    }
    public static void main(String[] args) {
        launch(args);
    }
}

        

