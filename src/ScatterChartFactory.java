import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ScatterChartFactory {
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

    /**
     * 
     * @param <X>
     * @param <Y>
     * @param title
     * @param yLabel
     * @param xLabel
     * @param xSeriesNames
     * @param yArrays
     * @param xArrays
     * @return
     */
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

            //Calculate Linear Regression Line
            double[] line = UtilityFX.linearRegressionLine(xArrays[i], yArrays[i]);
            double originX = xAxis.getLowerBound();
            double originY = yAxis.getLowerBound();
            double endX = xAxis.getUpperBound();
            Line regLine = new Line(originX, line[1], endX ,line[0] * endX + line[1]);
            
            System.out.println("originX: " + originX);
            System.out.println("originY: " + originY);
            System.out.println("endY: " + endX);

        } 

        return scatterChart;
    }


        /**
     * 
     * @param <X>
     * @param <Y>
     * @param title
     * @param yLabel
     * @param xLabel
     * @param xSeriesNames
     * @param yArrays
     * @param xArrays
     * @return
     */
    public static void createScatterChartNumNum(StackPane stackPane, String title, String yLabel, String xLabel, String[] xSeriesNames, Number[][] yArrays, Number[][] xArrays){ //we'll just assume the values play nicely with each other
        
        

        

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        //Check that the arrays match
        for(int i = 0; i < xArrays.length; i++)
            if(xArrays[i].length != yArrays[i].length)
                return;
        
        

        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        stackPane.getChildren().add(scatterChart);
        // Add the canvas to the StackPane

        // Create a Canvas
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
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

            //Calculate Linear Regression Line
            double[] line = UtilityFX.linearRegressionLine(xArrays[i], yArrays[i]);
            double originX = xAxis.getLowerBound();
            double originY = yAxis.getLowerBound();
            double endX = xAxis.getUpperBound();
            Line lines = new Line(originX, line[1], endX ,line[0] * endX + line[1]);
            stackPane.getChildren().add(lines);

        }
        //Set canvas dimensions
        canvas.widthProperty().bind(scatterChart.widthProperty());
        canvas.heightProperty().bind(scatterChart.heightProperty());
        stackPane.getChildren().add(canvas);
    }
}
