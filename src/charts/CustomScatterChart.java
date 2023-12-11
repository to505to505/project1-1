package charts;
import javafx.scene.chart.ScatterChart;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;

public class CustomScatterChart extends ScatterChart<Number, Number> {

    public CustomScatterChart(NumberAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);
    }

    
    
    public void addLine(double[] series1) {
        getPlotChildren().clear();
        Polyline polyline = new Polyline();
        NumberAxis XAxis = (NumberAxis) getXAxis();
        NumberAxis YAxis = (NumberAxis) getYAxis();
        double x_upper = XAxis.getUpperBound();
        double x_lower = XAxis.getLowerBound();
    
       
        
        
        double x0 = XAxis.getDisplayPosition(0);
        double y0 = YAxis.getDisplayPosition(series1[1]);

        double x1= XAxis.getDisplayPosition(11);
        double y1 = YAxis.getDisplayPosition((11) * series1[0] + series1[1]);
        // Преобразуем координаты данных в координаты на графике
       
        polyline.getPoints().addAll(x0, y0, x1, y1);


        
        getPlotChildren().add(polyline);
        

    }
    }
        
        
        
    



    


