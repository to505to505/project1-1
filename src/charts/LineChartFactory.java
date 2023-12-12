package charts;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;



public abstract class LineChartFactory{
    //line chart
    //bar chart
    //histogram
    //scatter plot
    //pie chart

    //battle plan: return the chart vs. return the data to be added to the chart.
    //

    /**
     * @param <X>
     * @param <Y>
     * @param xAxis
     * @param yAxis
     * @param title
     * @param yLabel
     * @param xLabel
     * @param seriesNames
     * @param yArrays
     * @param xArrays
     * @return
     */
    public static <X,Y> LineChart<X,Y> createLineChart(Axis<X> xAxis, Axis<Y> yAxis, String title, String xLabel, String yLabel, String[] seriesNames, X[][] xArrays, Y[][] yArrays){ //we'll just assume the values play nicely with each other
        //if(X )
        //xAxis = new NumberAxis();
        //yAxis = new NumberAxis();

        // Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<X,Y> lineChart = new LineChart<X,Y>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(yArrays.length != 1); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
        }

        return lineChart;
    }

    public static LineChart<Number, Number> createLineChartNumNum(String title, String xLabel, String yLabel, String[] seriesNames, Number[][] xArrays, Number[][] yArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
            
        }

        return lineChart;
    }

    public static LineChart<String, Number> createLineChartCatNum(String title, String xLabel, String yLabel, String[] seriesNames, String[][] xArrays, Number[][] yArrays){ //we'll just assume the values play nicely with each other
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
            
        }

        return lineChart;
    }

    public static LineChart<Number, String> createLineChartNumCat(String title, String xLabel, String yLabel, String[] seriesNames, Number[][] xArrays, String[][] yArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<Number, String> lineChart = new LineChart<Number, String>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number, String> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
            
        }

        return lineChart;
    }

    public static LineChart<String, String> createLineChartNumCat(String title, String xLabel, String yLabel, String[] seriesNames, String[][] xArrays, String[][] yArrays){ //we'll just assume the values play nicely with each other
        CategoryAxis xAxis = new CategoryAxis();
        CategoryAxis yAxis = new CategoryAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        LineChart<String, String> lineChart = new LineChart<String, String>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        lineChart.setTitle(title);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setLegendVisible(true); //false if there is a single series
        lineChart.setLegendSide(Side.BOTTOM);
        lineChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<String, String> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            lineChart.getData().add(series);
            
        }

        return lineChart;
    }
}
