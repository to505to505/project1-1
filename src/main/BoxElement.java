package main;

import java.util.ArrayList;
import java.util.Arrays;

import utility.Statistics;

public class BoxElement {
       
    private double q1;
    private double q2;
    private double q3;
    private double iqr;
    private double lowerWhisker;
    private double upperWhisker;

    private double[] sortedData;
    ArrayList<Double> outliers = new ArrayList<Double>();

    private double lowerWhisker(double[] data) {
        double lowerWhisker = q1 - 1.5 * iqr;
        double min = q2;
        for (double d : data) {
            if (min > d && d >= lowerWhisker) {
                lowerWhisker = d;
                min = d;
            } else if (d > lowerWhisker) {
                outliers.add(d);
            }
        }
        return lowerWhisker;
    }

    private double upperWhisker(double[] data) {
        double upperWhisker = q3 + 1.5 * iqr;
        double max = q2;
        for (double d : data) {
            if (max < d && d <= upperWhisker) {
                upperWhisker = d;
                max = d;
            } else if (d < upperWhisker) {
                outliers.add(d);
            }
        }
        return upperWhisker;
    }

    private double[] data;

    public BoxElement(double[] data) {
        this.sortedData = data.clone();
        Arrays.sort(sortedData);
        q1 = Statistics.q1(data);
        q2 = Statistics.q2(data);
        q3 = Statistics.q3(data);
        iqr = q3 - q1;
        lowerWhisker = lowerWhisker(data);
        upperWhisker = upperWhisker(data);
    }

    public double getQ1() {
        return q1;
    }

    public double getQ2() {
        return q2;
    }

    public double getQ3() {
        return q3;
    }

    public double getIqr() {
        return iqr;
    }

    public double getLowerWhisker() {
        return lowerWhisker;
    }

    public double getUpperWhisker() {
        return upperWhisker;
    }

    public double[] getSortedData() {
        return sortedData;
    }

    public ArrayList<Double> getOutliers() {
        return outliers;
    }
}
