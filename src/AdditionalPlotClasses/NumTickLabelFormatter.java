package AdditionalPlotClasses;
import javafx.scene.chart.NumberAxis;
import prediction.*;
import prediction.deprecated.DS;


public class NumTickLabelFormatter extends NumberAxis.DefaultFormatter {
    public String[] courses_names;
    public int property_col_num;
    public DS ds;
    public int boundary_value;
    public NumTickLabelFormatter(NumberAxis axis, String[] courses_names, int property_col_num, DS ds) {
        super(axis);
        this.courses_names = courses_names;
        this.property_col_num = property_col_num;
        this.ds = ds;
        this.boundary_value = (int) ds.get_boundry_value();
    }

    @Override
    public String toString(Number object) {
        if(object.intValue() == 3.0) {
            return ">="+boundary_value;
        } else if (object.intValue() == 2.0) {
            return "<"+boundary_value;
        } else {
            return "";
        }
    }
}
