import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.chart.Chart;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;



public class LineChartFactory{
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
    public static <X, Y> LineChart<X,Y> createLineChart(Axis<X> xAxis, Axis<Y> yAxis, String title, String yLabel, String xLabel, String[] xSeriesNames, Y[][] yArrays, X[][] xArrays){ //we'll just assume the values play nicely with each other
        //xAxis = new NumberAxis();
        //yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<X,Y> lineChart = new LineChart<X,Y>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
        }

        return lineChart;
    }

    public static LineChart<Number, Number> createLineChartNumNum(Axis<Number> xAxis, Axis<Number> yAxis, String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        //xAxis = new NumberAxis();
        //yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
            
        }

        return lineChart;
    }

    public class createLineChartNumNum<T1, T2> {
    }
}
