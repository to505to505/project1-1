import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.awt.FlowLayout;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;
import javafx.stage.Stage;

public class ExamHistogram extends Application{

    @Override
    public void start(Stage stage) throws Exception {
       stage.setTitle("How many students haven't taken each exam");
       final CategoryAxis xAxis = new CategoryAxis();
       final NumberAxis yAxis = new NumberAxis();
       final BarChart<String, Number> bc = new BarChart<String,Number>(xAxis,yAxis);
       xAxis.setLabel("Exams");
       yAxis.setLabel("Number of NG");
       
        XYChart.Series serie = new XYChart.Series();
        serie.getData().add(new XYChart.Data("JTE-234", 922));
        serie.getData().add(new XYChart.Data("ATE-003", 2));
        serie.getData().add(new XYChart.Data("TGL-013", 530));
        serie.getData().add(new XYChart.Data("PPL-239", 922));
        serie.getData().add(new XYChart.Data("WDM-974", 920));
        serie.getData().add(new XYChart.Data("GHL-823", 933));
        serie.getData().add(new XYChart.Data("HLU-200", 543));
        serie.getData().add(new XYChart.Data("MON-014", 531));
        serie.getData().add(new XYChart.Data("FEA-907", 530));
        serie.getData().add(new XYChart.Data("LPG-307", 1128));

        serie.getData().add(new XYChart.Data("TSO-010", 921));
        serie.getData().add(new XYChart.Data("LDE-009", 880));
        serie.getData().add(new XYChart.Data("JJP-001", 2));
        serie.getData().add(new XYChart.Data("MTE-004", 595));
        serie.getData().add(new XYChart.Data("LUU-003", 2));
        serie.getData().add(new XYChart.Data("LOE-103", 1128));
        serie.getData().add(new XYChart.Data("PLO-132", 5));
        serie.getData().add(new XYChart.Data("BKO-800", 532));
        serie.getData().add(new XYChart.Data("SLE-332", 1128));
        serie.getData().add(new XYChart.Data("BKO-801", 2));
        
        serie.getData().add(new XYChart.Data("DSE-003", 880));
        serie.getData().add(new XYChart.Data("DSE-005", 922));
        serie.getData().add(new XYChart.Data("ATE-014", 543));
        serie.getData().add(new XYChart.Data("JTW-004", 561));
        serie.getData().add(new XYChart.Data("ATE-008", 6));
        serie.getData().add(new XYChart.Data("DSE-007", 2));
        serie.getData().add(new XYChart.Data("ATE-214", 907));
        serie.getData().add(new XYChart.Data("JHF-101", 921));
        serie.getData().add(new XYChart.Data("KMO-007", 534));
        serie.getData().add(new XYChart.Data("WOT-104", 532));
        


        Scene scene = new Scene(bc, 800, 700);
        bc.getData().addAll(serie);
        for (XYChart.Series<String, Number> series : bc.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #567BD1;");
            }
        }
        stage.setScene(scene);
        stage.show();

        


    }
    public static void main(String[]args){
       
        launch(args);
    }
}