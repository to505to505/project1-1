package auxiliary;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

public class TestDataFilters {

    private static Double[][] makeSampleData(int rowCount, int colCount, Double minValue) {
        Double[][] result = new Double[rowCount][colCount];

        Double val = 0.0;

        for (int i=0; i < rowCount; i++)
            for (int j=0; j < colCount; j++) {
                val = (double) (i*colCount + j + 1) + minValue;
                result[i][j] = val;
            }
        return result;
    }

    private static void Print (Double[] data) {
        int colCount = data.length;

        String line1 = "|           |";
        String line2 = "+-----------+";
        for (int j=0; j < colCount; j++) {
            line1 = line1 + String.format(" %5d    |", j+1);
            line2 = line2 + "----------+";
        }
        System.out.println(line2);
        System.out.println(line1);
        System.out.println(line2);

        line1 = String.format("| %5d     |", 1);
        for (int j=0; j < colCount; j++) {
               line1 = line1 + String.format("    %5.2f |", data[j]);
        }
        System.out.println(line1);
        System.out.println(line2);
        System.out.println("");
    }

    private static void Print (Double[][] data) {
        int rowCount = data.length;
        int colCount = data[0].length;

        String line1 = "|           |";
        String line2 = "+-----------+";
        for (int j=0; j < colCount; j++) {
            line1 = line1 + String.format(" %5d    |", j+1);
            line2 = line2 + "----------+";
        }
        System.out.println(line2);
        System.out.println(line1);
        System.out.println(line2);

        for (int i=0; i < rowCount; i++) {
        line1 = String.format("| %5d     |", i+1);
            for (int j=0; j < colCount; j++) {
                line1 = line1 + String.format("    %5.2f |", data[i][j]);
            }
            System.out.println(line1);
        }
        System.out.println(line2);
        System.out.println("");
    }




    public static void main(String[] args) {

        DataFilters filter = new DataFilters();

        // Clear the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Crate sample 'data'
        Double[][] data = makeSampleData(6, 8, 10.0);

        // Show options
        String option = "1";
        String[] options = {"1", "2", "3", "4", "5"};

        while (Arrays.stream(options).anyMatch(option::equals)) {
            String txt = "Please select your option:" + '\n' +
                " [1] Filter sample data by selecting a single row" + '\n' +
                " [2] Filter sample data by selecting a single column" + '\n' +
                " [3] Filter sample data by selecting only some rows" + '\n' +
                " [4] Filter sample data by selecting only some columns" + '\n' +
                " [5] Filter sample data by selecting only items from some rows and some columns" + '\n' +
                " [Q]  Exit" + '\n' +
                "Enter your option: ";
                System.out.println(txt);

                option = (String)(System.console().readLine());

                // Print 'data'
                System.out.println("Sample data");
                Print (data);

                switch (option) {
                    case "1":
                        // Filter only one row
                        int i = 2;
                        Double [] filteredRow = filter.selectRow(data, i);
                        System.out.println("Filtered data - select row number " + (i+1));
                        Print(filteredRow);
                        break;

                    case "2":
                        // Filter only one column
                        int j = 4;
                        Double[] filterColumn = filter.selectColumn(data, j);
                        System.out.println("Filtered data - select column number " + (j+1));
                        Print(filterColumn);
                        break;

                    case "3":
                        // Filter only some rows
                        int[] rows = new int[3];
                        rows[0] = 0;
                        rows[1] = 2;
                        rows[2] = 5;
                        String rowsList = "";
                        for (int ix=0; ix < rows.length; ix++) {
                            rowsList = rowsList + String.format(" %s", rows[ix]+1);
                            if (ix < rows.length-1) rowsList = rowsList + ",";
                        }
                        System.out.println("Filtered data - select rows number: " + rowsList);
                        Double [][] filteredRows = filter.selectRows(data, rows);
                        Print(filteredRows);
                        break;

                    case "4":
                        // Filter only some columns
                        int[] columns = new int[3];
                        columns[0] = 1;
                        columns[1] = 2;
                        columns[2] = 4;
                        String columsList = "";
                        for (int ix=0; ix < columns.length; ix++) {
                            columsList = columsList + String.format(" %s", columns[ix]+1);
                            if (ix < columns.length-1) columsList = columsList + ",";
                        }
                        System.out.println("Filtered data - select colums number: " + columsList);
                        Double [][] filteredColumns = filter.selectColums(data, columns);
                        Print(filteredColumns);

                        // Print 'data'
                        System.out.println("Sample data");
                        Print (data);
                        break;

                    case "5":
                        // Filter only some rows and some columns
                        int[] dataRows = new int[3];
                        dataRows[0] = 0;
                        dataRows[1] = 2;
                        dataRows[2] = 5;
                        String dataRowsList = "";
                        for (int ix=0; ix < dataRows.length; ix++) {
                            dataRowsList = dataRowsList + String.format(" %s", dataRows[ix]+1);
                            if (ix < dataRows.length-1) dataRowsList = dataRowsList + ",";
                        }

                        int[] dataColumns = new int[4];
                        dataColumns[0] = 1;
                        dataColumns[1] = 2;
                        dataColumns[2] = 4;
                        dataColumns[3] = 6;
                        String dataColumnsList = "";
                        for (int ix=0; ix < dataColumns.length; ix++) {
                            dataColumnsList = dataColumnsList + String.format(" %s", dataColumns[ix]+1);
                            if (ix < dataColumns.length-1) dataColumnsList = dataColumnsList + ",";
                        }
                        System.out.println("Filter data - select rows number: " + dataRowsList + " and columns number: " + dataColumnsList);

                        Double[][] filterData = filter.selectData(data, dataRows, dataColumns);
                        Print(filterData);
                        break;

                    default:
                        System.out.println("See you soon !");
                }
            }
    }
}
