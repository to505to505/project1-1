import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public abstract class PieChartFactory {
    public static PieChart createPieChart(String title, String[] catArray, Number[] xArray) {
        
        if(catArray.length != xArray.length)
            return null;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(int i = 0 ; i < catArray.length; i++)
            pieChartData.add(new PieChart.Data(catArray[i], xArray[i].doubleValue()));

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle(title);
        pieChart.setTitleSide(Side.TOP);
        pieChart.setLegendVisible(true); //false if there is a single series
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        return pieChart;
    }
}
