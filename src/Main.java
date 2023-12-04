import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data visualization");

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

        

       
        VBox vBox = new VBox(menuBar);
        Scene scene = new Scene(vBox, 500, 430);
        primaryStage.setScene(scene);
        primaryStage.show();


        histogramItem.setOnAction(event -> openHistogramWindow(primaryStage, scene));
        piechartItem.setOnAction(event -> openPieChartWindow(primaryStage, scene));
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
}
