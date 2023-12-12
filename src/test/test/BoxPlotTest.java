package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.BoxElement;
import main.BoxPlot;

public class BoxPlotTest extends Application{
    
    public void start(Stage stage){
        double[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        BoxElement boxElement = new BoxElement(data);
        BoxPlot boxPlot = new BoxPlot();
        //boxPlot.getData().add(boxElement);
        
        BorderPane root = new BorderPane();
        Axis<Number> xAxis = new NumberAxis(0, 10, 1);
        Axis<String> yAxis = new CategoryAxis(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        xAxis.setLabel("xAxis");
        yAxis.setLabel("yAxis");
        //xAxis.setTickLabelRotation(90);
        xAxis.setMinSize(100, 100);
        xAxis.setTranslateX(100);
        xAxis.setTranslateY(100);
        xAxis.setScaleX(.5);
        xAxis.setScaleY(.5);
        //xAxis.set
        root.setCenter(xAxis);
        //.setCenter(boxPlot);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);

        boxPlot.draw();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
