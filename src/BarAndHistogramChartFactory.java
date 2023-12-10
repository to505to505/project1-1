import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public class BarAndHistogramChartFactory {

    public static BarChart<String, Number> createBarChartCatNum(String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, String[][] xArrays){ //we'll just assume the values play nicely with each other
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        barChart.setTitle(title);
        barChart.setTitleSide(Side.TOP);
        barChart.setLegendVisible(true); //false if there is a single series
        barChart.setLegendSide(Side.BOTTOM);
        barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            barChart.getData().add(series);
            
        }
        return barChart;
    }

    public static BarChart<Number, String> createBarChartCatNum(String title, String yLabel, String xLabel, String[] xSeriesNames, String[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        barChart.setTitle(title);
        barChart.setTitleSide(Side.TOP);
        barChart.setLegendVisible(true); //false if there is a single series
        barChart.setLegendSide(Side.BOTTOM);
        barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number,String> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            barChart.getData().add(series);
            
        }
        return barChart;
    }

    public static BarChart<Number, Number> createHistogramChartNumNum(String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<Number, Number> histogramChart = new BarChart<Number, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        histogramChart.setTitle(title);
        histogramChart.setTitleSide(Side.TOP);
        histogramChart.setLegendVisible(true); //false if there is a single series
        histogramChart.setLegendSide(Side.BOTTOM);
        histogramChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }

    public static BarChart<String, Number> createHistogramChartCatNum(String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, String[][] xArrays){ //we'll just assume the values play nicely with each other
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<String, Number> histogramChart = new BarChart<String, Number>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        histogramChart.setTitle(title);
        histogramChart.setTitleSide(Side.TOP);
        histogramChart.setLegendVisible(true); //false if there is a single series
        histogramChart.setLegendSide(Side.BOTTOM);
        histogramChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));
        histogramChart.setBarGap(0);
        histogramChart.setCategoryGap(0);

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }

    public static BarChart<Number, String> createhistogramChartNumCat(String title, String yLabel, String xLabel, String[] xSeriesNames, String[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<Number, String> histogramChart = new BarChart<Number, String>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        histogramChart.setTitle(title);
        histogramChart.setTitleSide(Side.TOP);
        histogramChart.setLegendVisible(true); //false if there is a single series
        histogramChart.setLegendSide(Side.BOTTOM);
        histogramChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));
        histogramChart.setBarGap(0);
        histogramChart.setCategoryGap(0);

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<Number, String> series = new XYChart.Series<>();
            series.setName(xSeriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }
}
