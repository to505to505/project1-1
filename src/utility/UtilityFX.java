package utility;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import main.Main;
import java.util.*;
import data.Data;

public abstract class UtilityFX {
    public static double[] linearRegressionLine(Number[] x, Number[] y) {
        double[] line = new double[2]; //[slope, y-intercept]
        double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumXX = 0.0;
        int non_ng_students_count = 0;
        for (int i = 0; i < x.length; i++)
            if(x[i].doubleValue() > 0 && y[i].doubleValue() > 0){
                non_ng_students_count++;
                sumX += x[i].doubleValue();
                sumY += y[i].doubleValue();
                sumXY += x[i].doubleValue() * y[i].doubleValue();
                sumXX += x[i].doubleValue() * x[i].doubleValue();
            }
        line[0] = (non_ng_students_count * sumXY - sumX * sumY) / (non_ng_students_count * sumXX - sumX * sumX);
        line[1] = (sumY - line[0] * sumX) / non_ng_students_count;
        return line;
    }
     private static void swarmPlot(){
        VBox swormBox = new VBox();
        FlowPane filters = new FlowPane();
        Group swarmPlot = new Group();
        swormBox.getChildren().addAll(filters, swarmPlot);
    }

    private static void refreshColumnList(FlowPane filters){ //has a problem when the same column names appear more than once - considers them as different.
        ArrayList<String> temp = new ArrayList<String>();
        for(Data data : Main.dataList)
            for(String s : data.columnNames)
                temp.add(s);
        Main.columnList = FXCollections.observableArrayList(temp);
        System.out.println(Main.columnList);
        ChoiceBox<String> xAxis = new ChoiceBox<String>(Main.columnList);
        xAxis.setValue("------------");
        ChoiceBox<String> yAxis = new ChoiceBox<String>(Main.columnList);
        yAxis.setValue("------------");
        filters.getChildren().remove(0);
        filters.getChildren().add(0, xAxis);
        filters.getChildren().remove(1);
        filters.getChildren().add(1, yAxis);
    }
}
