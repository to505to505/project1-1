package auxiliary;

// A DataFilter method takes a matrix 'inputData' and extract data based of simple filtering rule
// 
// 'selectRow' returns from the 'imputData' a single column, identified by its index 'row'
// 'selectColumn' returns from the 'imputData' a single row, identified by its index 'column'
// 'selectRows' returns from the 'inpudData' the rows identified by indexes passed in 'rows'
// 'selectColumns' returns from the 'inpudData' the columns identified by indexes passed in 'columns'
// 'selectData' returns from the 'inputData' only the elements that have row indexes included 
//     in the 'rows' array and column indexes included in the 'columns' array
public class DataFilters implements DataFilterTypes {

    // Returns the complete row at index 'row' from 'inputData'
    public Double[] selectRow (Double[][] inputData, int row) {
        int colCount = inputData[0].length;
        Double[] result = new Double[colCount];
        for (int i=0; i< colCount; i++)
            result[i] = inputData [row][i];
        return result;
    }

    // Returns the complete column at index 'column' from 'inputData'
    public Double[] selectColumn (Double[][] inputData, int column) {
        int rowCount = inputData.length;
        Double[] result = new Double[rowCount];
        for (int i=0; i< rowCount; i++)
            result[i] = inputData [i][column];
        return result;
    }

    // Returns all the complete rowss at indexes in 'rows' from 'inputData'
    public Double[][] selectRows (Double[][] inputData, int[] rows) {
        int colCount = inputData[0].length;
        int k = rows.length;
        Double[][] result = new Double[k][colCount];

        for (int j=0; j < colCount; j++)
            for (int i=0; i < k; i++)
                result[i][j] = inputData[rows[i]][j];

        return result;
    }

    // Returns all the complete columns at indexes in 'columns' from 'inputData'
    public Double[][] selectColums (Double[][] inputData, int[] columns) {
        int rowCount = inputData.length;
        int k = columns.length;
        Double[][] result = new Double[rowCount][k];

        for (int j=0; j < k; j++) {
            for (int i=0; i < rowCount; i++) {
                result[i][j] = inputData[i][columns[j]];
            }
        }
        return result;
    }

    // Returns partial data at rows at indexes in 'rows' and columns at indexes in 'columns' from 'inputData'
    public Double[][] selectData (Double[][] inputData, int[] rows, int[] columns) {
        int rowCount = rows.length;
        int colCount = columns.length;
        Double[][] result = new Double[rowCount][colCount];

        for (int j=0; j < colCount; j++) {
            for (int i=0; i < rowCount; i++) {
                result[i][j] = inputData[rows[i]][columns[j]];
            }
        }
        return result;
    }
}
