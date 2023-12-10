import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public class ScatterPlotFactory {
    /**
     * 
     * @param <X>
     * @param <Y>
     * @param xAxis
     * @param yAxis
     * @param title
     * @param yLabel
     * @param xLabel
     * @param xSeriesNames
     * @param yArrays
     * @param xArrays
     * @return
     */
    public static <X, Y> ScatterChart<X,Y> createScatterChart(Axis<X> xAxis, Axis<Y> yAxis, String title, String yLabel, String xLabel, String[] xSeriesNames, Y[][] yArrays, X[][] xArrays){ //we'll just assume the values play nicely with each other
        //if(X )
        //xAxis = new NumberAxis();
        //yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        ScatterChart<X,Y> scatterChart = new ScatterChart<X,Y>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        scatterChart.setTitle(title);
        scatterChart.setTitleSide(Side.TOP);
        scatterChart.setLegendVisible(true); //false if there is a single series
        scatterChart.setLegendSide(Side.BOTTOM);
        scatterChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            scatterChart.getData().add(series);
        }

        return scatterChart;
    }

    public static ScatterChart<Number, Number> createScatterChartNumNum(String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        scatterChart.setTitle(title);
        scatterChart.setTitleSide(Side.TOP);
        scatterChart.setLegendVisible(true); //false if there is a single series
        scatterChart.setLegendSide(Side.BOTTOM);
        scatterChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            scatterChart.getData().add(series);
            
        }

        return scatterChart;
    }
}
