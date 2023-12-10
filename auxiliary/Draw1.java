
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Draw1 extends Application  {

    public void start (Stage stage) {
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("JavaFX Scene Graph Demo");
        

        Group root = new Group();
        BorderPane topPanel = new BorderPane();
        //BorderPane drawPanel = new BorderPane();
        
        topPanel.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));
        //drawPanel.borderProperty().set(Border.stroke(Color.rgb(200, 200, 200)));

        NumberAxis hAxis = new NumberAxis();
        hAxis.setLabel("Grade");
        hAxis.autosize();
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Student count");

        LineChart<Number, Number> studentsPerGrade = new LineChart<> (hAxis, vAxis);
        studentsPerGrade.setTitle("Studets per Grade");
        studentsPerGrade.setLegendVisible(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Students per Grade");
        series.getData().add(new XYChart.Data<>(5, 40));
        series.getData().add(new XYChart.Data<>(6, 23));
        series.getData().add(new XYChart.Data<>(7, 33));
        series.getData().add(new XYChart.Data<>(8, 67));
        series.getData().add(new XYChart.Data<>(9, 25));
        series.getData().add(new XYChart.Data<>(10, 14));
        studentsPerGrade.getData().add(series);

        root.getChildren().add(topPanel);

        int[][] y = {
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2},
        };

        int [][] x = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
        };

        String[] s = {"", "", ""};

        LineChart<Integer, Integer> lineChart = LineChartFactory.createLineChart<Integer, Integer>(new NumberAxis(), new NumberAxis(), "", "", "", s, y, x);

        root.getChildren().add(studentsPerGrade);
        //root.getChildren().add(drawPanel);

        Scene newScene = new Scene(root, Color.rgb(200, 248, 248));
        stage.setScene(newScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}