import java.io.File;
import java.io.IOException;

import java.awt.Desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data visualization");

        
        /*
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Chose the plot");
        MenuItem histogramItem = new MenuItem("Histogram of raw data");
        MenuItem piechartItem = new MenuItem("PieChart of cum_laude");
        MenuItem piechartItem1 = new MenuItem("Similarity between 2 (Scatter plot)");
        MenuItem pieCHartItem2 = new MenuItem("Course order ");
        MenuItem pie = new MenuItem("Predictions Evaluation (Table)");
        menu.getItems().add(histogramItem);
        menu.getItems().add(piechartItem);
        menu.getItems().add(piechartItem1);
        menu.getItems().add(pieCHartItem2);
        menu.getItems().add(pie);
        menuBar.getMenus().add(menu);
        */

        ///Side Menu
        VBox vbox = new VBox();
        vbox.setPrefWidth(100);
        vbox.setStyle("-fx-background-color: #333333;");

        Button button1 = new Button("Button 1");
        button1.setPrefWidth(vbox.getPrefWidth());
        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        Button button2 = new Button("Button 2");
        button2.setPrefWidth(vbox.getPrefWidth());
        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        Button button3 = new Button("Button 3");
        button3.setPrefWidth(vbox.getPrefWidth());
        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        vbox.getChildren().addAll(button1, button2, button3);

        ///Border Pane
        BorderPane border = new BorderPane();
        border.setLeft(vbox);

        ///Read data files
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files", "*.csv");
        fileChooser.setTitle("Import Data");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            Data data = new Data(file);
            openFile(file);
        } else {
            Data data = new Data("src/data/bugData.csv");
        }


       
        //VBox vBox = new VBox(menuBar);
        Scene scene = new Scene(border, 500, 430);
        primaryStage.setScene(scene);
        primaryStage.show();
        

        //histogramItem.setOnAction(event -> openHistogramWindow(primaryStage, scene));
        //piechartItem.setOnAction(event -> openPieChartWindow(primaryStage, scene));
    }

    private void openHistogramWindow(Stage primaryStage, Scene scene) {
        HistogramChart histogramChart = new HistogramChart(scene);
        histogramChart.start(primaryStage); 
    }
    private void openPieChartWindow(Stage primaryStage, Scene scene) {
        PieChartPlot pieChart = new PieChartPlot(scene);
        pieChart.start(primaryStage); 
    }

    public static void main(String[] args) {
        launch(args);
    }

     private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}
