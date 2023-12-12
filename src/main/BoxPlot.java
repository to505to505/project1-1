package main;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


/**
 * For every category, a box plot is drawn. The categories are listed on the x-axis and the values on the y-axis.
 * For every category String, the object takes in a collection of values used to calculate the quartiles and the median.
 */
public class BoxPlot {

    private double OUTLIER_RADIUS = 2;
    private double BOX_WIDTH = 30;

    private Color BOX_COLOR = Color.LIGHTBLUE;
    
    ArrayList<BoxElement> boxElements = new ArrayList<BoxElement>();
    ObservableList<XYChart.Data<String, BoxElement>> data = FXCollections.observableArrayList();

    /**
     * Box Plot constructor
     */
    public BoxPlot() { //assumes data doesn't contain empty values
        
    }

    public void draw(){
        for (BoxElement boxElement : boxElements) {
            
            // Create box
            Rectangle box = new Rectangle(BOX_WIDTH, boxElement.getQ3() - boxElement.getQ1());
            box.setY(boxElement.getQ1());
            box.setFill(Color.BLUE); // Custom fill color

            // Create median line
            Line medianLine = new Line(0, boxElement.getQ2(), BOX_WIDTH, boxElement.getQ2());
            medianLine.setStroke(Color.RED); // Custom stroke color
            medianLine.setStrokeWidth(2.0); // Custom stroke width

            // Create whiskers
            Line lowerWhisker = new Line(BOX_WIDTH / 2, boxElement.getLowerWhisker(), BOX_WIDTH / 2, boxElement.getQ1());
            Line upperWhisker = new Line(BOX_WIDTH / 2, boxElement.getQ3(), BOX_WIDTH / 2, boxElement.getUpperWhisker());
            lowerWhisker.setStroke(Color.RED); // Custom stroke color
            upperWhisker.setStroke(Color.RED); // Custom stroke color
            lowerWhisker.setStrokeWidth(2.0); // Custom stroke width
            upperWhisker.setStrokeWidth(2.0); // Custom stroke width

            // Add outliers
            for (Double datum : boxElement.getOutliers()) {
                if (datum < boxElement.getLowerWhisker() || datum > boxElement.getUpperWhisker()) {
                    Circle outlier = new Circle(BOX_WIDTH / 2, datum, OUTLIER_RADIUS);
                    outlier.setFill(Color.GREEN); // Custom fill color
                    outlier.setRadius(5.0); // Custom radius
                    //this.getChildren().add(outlier);
                }
            }
        }
    }

    public void add(BoxElement boxElement){
        boxElements.add(boxElement);
    }

    public void clear(){
        boxElements.clear();
    }


    /*
    @Override
    protected void layoutChartChildren(double top, double left, double width, double height) {
        for (BoxElement boxElement : boxElements) {
            
            // Create box
            Rectangle box = new Rectangle(width, height);
            box.setY(boxElement.getQ1());
            box.setFill(Color.BLUE); // Custom fill color

            // Create median line
            Line medianLine = new Line(0, boxElement.getQ2(), BOX_WIDTH, boxElement.getQ2());
            medianLine.setStroke(Color.RED); // Custom stroke color
            medianLine.setStrokeWidth(2.0); // Custom stroke width

            // Create whiskers
            Line lowerWhisker = new Line(BOX_WIDTH / 2, boxElement.getLowerWhisker(), BOX_WIDTH / 2, boxElement.getQ1());
            Line upperWhisker = new Line(BOX_WIDTH / 2, boxElement.getQ3(), BOX_WIDTH / 2, boxElement.getUpperWhisker());
            lowerWhisker.setStroke(Color.RED); // Custom stroke color
            upperWhisker.setStroke(Color.RED); // Custom stroke color
            lowerWhisker.setStrokeWidth(2.0); // Custom stroke width
            upperWhisker.setStrokeWidth(2.0); // Custom stroke width

            // Add outliers
            for (Double datum : boxElement.getOutliers()) {
                if (datum < boxElement.getLowerWhisker() || datum > boxElement.getUpperWhisker()) {
                    Circle outlier = new Circle(BOX_WIDTH / 2, datum, OUTLIER_RADIUS);
                    outlier.setFill(Color.GREEN); // Custom fill color
                    outlier.setRadius(5.0); // Custom radius
                    this.getChildren().add(outlier);
                }
            }
        }
    }
    */
}
