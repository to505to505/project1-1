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
}
