package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A primitive DataFilter method takes a matrix 'inputData' and extract data based of simple filtering rule.
 * 'selectRow' returns from the 'imputData' a single column, identified by its index 'row'.
 * 'selectColumn' returns from the 'imputData' a single row, identified by its index 'column'.
 * 'selectRows' returns from the 'inpudData' the rows identified by indexes passed in 'rows'.
 * 'selectColumns' returns from the 'inpudData' the columns identified by indexes passed in 'columns'.
 * 'selectData' returns from the 'inputData' only the elements that have row indexes included in the 'rows' array and column indexes included in the 'columns' array.
 */     
public class DataFilters{

    /**
     * Clean out missing values for a Data object
     * @param inputData
     * @param propertyIndexes
     * @param properties
     * @return
     */
    public static Double[][] cleanMissingValues (Data inputData, int... indeces){

        ArrayList<Integer> rows = new ArrayList<Integer>();

        boolean ok;
        for (int j = 0; j < inputData.data.length; j++) {
            ok = true;
            for(int i : indeces){
                if(inputData.data[j][i] == -1){
                    ok = false; break;
                }
                if (ok)
                    rows.add(j);
            }
        }
        
        return selectRows(inputData, rows);
    }

    /**
     * Clean out missing values for a double[][] object
     * @param inputData
     * @param propertyIndexes
     * @param properties
     * @return
     */
    public static Double[] cleanMissingValues (Double[] inputData){

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int j = 0; j < inputData.length; j++) {
            if(inputData[j] != -1)
                rows.add(j);
        }
        
        return selectElements(inputData, rows);
    }

    /**
     * Returns the array rows with certain properties values
     */
    public static Double[][] selectRowsByPropertes (AggregateData inputData, int[] propertyIndexes, String[]... properties) {

        if(propertyIndexes.length != properties.length)
            throw new IllegalArgumentException("propertyIndexes and properties must have the same length");

        ArrayList<Integer> rows = new ArrayList<Integer>();

        boolean ok;
        for (int i = 0; i < inputData.data.length; i++)
            for (int j = 0; j < propertyIndexes.length; j++){
                ok = true;
                for(String property : properties[j]){
                    if(inputData.infoData[i][propertyIndexes[j]] == propertyDict.get(property)) {
                        ok = false; break;
                    }
                }
                if(ok)
                    rows.add(i);
            }
        
        return selectRows(inputData, rows);
    }

    /**
     * Returns the array rows with certain property values
     */
    public static Double[][] selectRowsByProperty (AggregateData inputData, int propertyIndex, String[] properties) {

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int i = 0; i < inputData.data.length; i++)
            for (int j = 0; j < properties.length; j++)
                if(inputData.infoData[i][propertyIndex] == propertyDict.get(properties[j]))
                    rows.add(i);
        
        return selectRows(inputData, rows);
    }

    /**
     * Returns the array rows with Lal Count between given bounds
     */
    public static Double[][] selectRowsByLalCount (AggregateData inputData, int courseIndex, double lowerBound, double upperBound) {

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int i = 0; i < inputData.data.length; i++)
            if(inputData.infoData[i][2] >= lowerBound && inputData.infoData[i][2] <= upperBound)
                rows.add(i);
        
        return selectRows(inputData, rows);
    }

    /**
     * Returns the array rows with grades in between given bounds for the given course
     */
    public static Double[][] selectRowsByCourseGrade (Data inputData, int courseIndex, double lowerBound, double upperBound) {

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int i = 0; i < inputData.data.length; i++)
            if(inputData.data[i][courseIndex] >= lowerBound && inputData.data[i][courseIndex] <= upperBound)
                rows.add(i);
        
        return selectRows(inputData, rows);
    }

    /**
     * Returns the array rows with IDs between given bounds
     */
    public static Double[][] selectRowsByStudentID (Data inputData, int courseIndex, int lowerBound, int upperBound) {

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int i = 0; i < inputData.data.length; i++)
            if(inputData.studentIDs[i] >= lowerBound && inputData.studentIDs[i] <= upperBound)
                rows.add(i);
        
        return selectRows(inputData, rows);
    }

    /**
     * Returns the array rows with grades in between given bounds for the given course
     */
    public static Double[][] selectRowsByPropertyValue (Data inputData, int courseIndex, double lowerBound, double upperBound) {

        ArrayList<Integer> rows = new ArrayList<Integer>();

        for (int i = 0; i < inputData.data.length; i++)
            if(inputData.data[i][courseIndex] >= lowerBound && inputData.data[i][courseIndex] <= upperBound)
                rows.add(i);
        
        return selectRows(inputData, rows);
    }


    //----------------------------------------------------------------------------
    //Primiteves

    /**
     * Returns the complete row at index 'row' from 'inputData'
     * @param inputData
     * @param row
     * @return
     */
    public static Double[] selectRow (Data inputData, int row) {
        int colCount = inputData.data[0].length;
        Double[] result = new Double[colCount];
        for (int i=0; i< colCount; i++)
            result[i] = inputData.data[row][i];
        return result;
    }

    /**
     * Returns the complete column at index 'column' from 'inputData'
     * @param inputData
     * @param column
     * @return
     */
    public static Double[] selectColumn (Data inputData, int column) {
        int rowCount = inputData.data.length;
        Double[] result = new Double[rowCount];
        for (int i=0; i< rowCount; i++)
            result[i] = inputData.data[i][column];
        return result;
    }

    /**
     * Returns all the complete rows at indexes in 'rows' from 'inputData'
     */
    public static Double[][] selectRows (Data inputData, int[] rows) {
        int colCount = inputData.data[0].length;
        int k = rows.length;
        Double[][] result = new Double[k][colCount];

        for (int j = 0; j < colCount; j++)
            for (int i = 0; i < k; i++)
                result[i][j] = inputData.data[rows[i]][j];

        return result;
    }

    /**
     * Returns all the complete rows at indexes in 'rows' from 'inputData'
     */
    public static Double[][] selectRows (Data inputData, ArrayList<Integer> rows) {
        int colCount = inputData.data[0].length;
        int k = rows.size();
        Double[][] result = new Double[k][colCount];

        for (int j = 0; j < colCount; j++)
            for (int i = 0; i < k; i++)
                result[i][j] = inputData.data[rows.get(i)][j];
        return result;
    }

    /**
     * Returns all the row elements at indexes in 'indeces' from 'inputData'
     */
    public static Double[] selectElements (Double[] inputData, int[] indeces) {
        int k = indeces.length;
        Double[] result = new Double[k];

        for (int i = 0; i < k; i++)
            result[i] = inputData[indeces[i]];

        return result;
    }

    /**
     * Returns all the row elements at indexes in 'indeces' from 'inputData'
     */
    public static Double[] selectElements (Double[] inputData, ArrayList<Integer> indeces) {
        int k = indeces.size();
        Double[] result = new Double[k];

        for (int i = 0; i < k; i++)
            result[i] = inputData[indeces.get(i)];

        return result;
    }

     /**
     * Returns all the complete columns at indexes in 'columns' from 'inputData'
     */
    public static Double[][] selectColums (Data inputData, int[] columns) {
        int rowCount = inputData.data.length;
        int k = columns.length;
        Double[][] result = new Double[rowCount][k];

        for (int j = 0; j < k; j++) {
            for (int i = 0; i < rowCount; i++) {
                result[i][j] = inputData.data[i][columns[j]];
            }
        }

        return result;
    }
    

    /**
     * Returns all the elements at indexes in 'rows' and 'columns' from 'inputData'
     */
    public static Double[][] selectData (Data inputData, int[] rows, int[] columns) {
        int rowCount = rows.length;
        int colCount = columns.length;
        Double[][] result = new Double[rowCount][colCount];

        for (int j=0; j < colCount; j++) {
            for (int i=0; i < rowCount; i++) {
                result[i][j] = inputData.data[rows[i]][columns[j]];
            }
        }

        return result;
    }

    public static final HashMap<String,Integer> propertyDict = new HashMap<String,Integer>();
    static {
        // Preload dictionary
        propertyDict.put("NG", -1);
        propertyDict.put("nulp", 0);
        propertyDict.put("doot", 1);
        propertyDict.put("lobi", 2);
        propertyDict.put("nothing", 0);
        propertyDict.put("low", 1);
        propertyDict.put("medium", 2);
        propertyDict.put("high", 3);
        propertyDict.put("full", 4);
        propertyDict.put("1 star", 1);
        propertyDict.put("2 stars", 2);
        propertyDict.put("3 stars", 3);
        propertyDict.put("4 stars", 4);
        propertyDict.put("5 stars", 5);
    }
}
