package charts;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public abstract class BarAndHistogramChartFactory {
    //implement creating a stacked histogram with StackedBarChart

    public static <X,Y> BarChart<X,Y> createBarChart(Axis<X> xAxis, Axis<Y> yAxis, String title, String xLabel, String yLabel, String[] seriesNames, X[][] xArrays, Y[][] yArrays){ //we'll just assume the values play nicely with each other

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<X,Y> barChart = new BarChart<X,Y>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        barChart.setTitle(title);
        barChart.setTitleSide(Side.TOP);
        barChart.setLegendVisible(true); //false if there is a single series
        barChart.setLegendSide(Side.BOTTOM);
        barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<X,Y>(xArrays[i][j], yArrays[i][j]));
            barChart.getData().add(series);
            
        }
        return barChart;
    }

    public static <X,Y> BarChart<X,Y> createBarChart(Axis<X> xAxis, Axis<Y> yAxis, String title, String xLabel, String yLabel, String[] seriesNames, X[] xArrays, Y[][] yArrays){ //we'll just assume the values play nicely with each other

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays.length != yArrays[i].length)
                return null;
        
        BarChart<X,Y> barChart = new BarChart<X,Y>(xAxis, yAxis);
        
        //Chart Title, Legend & Border
        barChart.setTitle(title);
        barChart.setTitleSide(Side.TOP);
        barChart.setLegendVisible(true); //false if there is a single series
        barChart.setLegendSide(Side.BOTTOM);
        barChart.borderProperty().set(Border.stroke(Color.rgb(248, 248, 128)));

        //Create Series
        for(int i = 0; i < xArrays.length; i++){
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays.length; j++)
                series.getData().add(new XYChart.Data<X,Y>(xArrays[i], yArrays[i][j]));
            barChart.getData().add(series);
        }
        return barChart;
    }

    public static <X,Y> BarChart<X,Y> createHistogram(Axis<X> xAxis, Axis<Y> yAxis, String title, String xLabel, String yLabel, String[] seriesNames, X[][] xArrays, Y[][] yArrays){ //we'll just assume the values play nicely with each other

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return null;
        
        BarChart<X,Y> histogramChart = new BarChart<X,Y>(xAxis, yAxis);
        
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
            XYChart.Series<X,Y> series = new XYChart.Series<>();
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<X,Y>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
        }
        return histogramChart;
    }

    public static BarChart<String, Number> createBarChartCatNum(String title, String xLabel, String yLabel, String[] seriesNames, String[][] xArrays, Number[][] yArrays){ //we'll just assume the values play nicely with each other
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
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            barChart.getData().add(series);
            
        }
        return barChart;
    }

    public static BarChart<Number, String> createBarChartCatNum(String title, String xLabel, String yLabel, String[] seriesNames, Number[][] xArrays, String[][] yArrays){ //we'll just assume the values play nicely with each other
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
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            barChart.getData().add(series);
            
        }
        return barChart;
    }

    public static BarChart<Number, Number> createHistogramChartNumNum(String title, String xLabel, String yLabel, String[] seriesNames, Number[][] xArrays, Number[][] yArrays){ //we'll just assume the values play nicely with each other
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
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }

    public static BarChart<String, Number> createHistogramChartCatNum(String title, String xLabel, String yLabel, String[] seriesNames, String[][] xArrays, Number[][] yArrays){ //we'll just assume the values play nicely with each other
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
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }

    public static BarChart<Number, String> createhistogramChartNumCat(String title, String xLabel, String yLabel, String[] seriesNames, Number[][] xArrays, String[][] yArrays){ //we'll just assume the values play nicely with each other
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
            series.setName(seriesNames[i]);
            for(int j = 0; j < xArrays[i].length; j++)
                series.getData().add(new XYChart.Data<>(xArrays[i][j], yArrays[i][j]));
            histogramChart.getData().add(series);
            
        }

        return histogramChart;
    }
}
