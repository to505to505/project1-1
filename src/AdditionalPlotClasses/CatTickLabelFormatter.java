package AdditionalPlotClasses;
import javafx.scene.chart.NumberAxis;
import prediction.*;
import prediction.deprecated.DS;
import prediction.deprecated.DSCat_old;    

public class CatTickLabelFormatter extends NumberAxis.DefaultFormatter {
    public String[] courses_names;
    public int property_col_num;
    public DS ds;
    public CatTickLabelFormatter(NumberAxis axis, String[] courses_names, int property_col_num, DS ds) {
        super(axis);
        this.courses_names = courses_names;
        this.property_col_num = property_col_num;
        this.ds = ds;
    }

    @Override
    public String toString(Number object) {
        if(ds.get_property_name().equals("Volta ")){
            return(DSCat.get_value_names(((double) object) - 1, courses_names, property_col_num));
        }
        return(DSCat.get_value_names(((double) object) -2, courses_names, property_col_num));
    }
}
