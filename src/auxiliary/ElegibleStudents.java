import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import javafx.scene.input.MouseEvent;
public class ElegibleStudents extends Application{
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new Group());
        stage.setTitle("How many students are elegigle to graduate");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Students not elegile", 82),
            new PieChart.Data("Elegible students", 17.9)
        );

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Prediction of how many students will graduate");

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 16 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override 
                    public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                     }
                });
        }

        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
        stage.setScene(scene);
        stage.show();

       
        
    }
    public static void main(String[] args){
        launch(args);
    }
}