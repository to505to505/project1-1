package data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import exceptions.PercentageOutOfRange;
import exceptions.SampleNumberOutOfRange;

/**
 * Stores data from two different table with the same primary key (identification number) as the first column. The ID and column names are held in separate arrays from the data, while the data is held in an 2d array. Also has static methods for splitting data.
 */
public class AggregateData extends Data {

    public String[] infoColumnNames;
    public double[][] infoData;

    public HashMap<String, Integer> infoColumnIndices = new HashMap<String, Integer>();

    /**
     * Constructor reading files of tables with matching student ID columns (the order of student IDs).
     * 
     * @param gradesFileName
     * @param infoFileName
     */
    public AggregateData(String gradesFileName, String infoFileName){
        
        super(gradesFileName);

        /// Read the StudentInfo file and fill in the rest of the fields
        try {
            File fin = new File(infoFileName);      //data file
            Scanner fileScanner = new Scanner(fin); //scans file line by line
            String line;                            //holds scanned line

            /// write info column names to infoColumnNames[]
            line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            lineScanner.next(); // skip column StudentID //student IDs have been assigned their own array

            //copy column names into temporary dynamic array tempInfoColumnNames
            ArrayList<String> tempInfoColumnNames = new ArrayList<String>();
            while (lineScanner.hasNext())
                tempInfoColumnNames.add(lineScanner.next());
            //copy column names from tempInfoColumnNames to final array
            infoColumnNames = new String[tempInfoColumnNames.size()];
            for(int i = 0; i < tempInfoColumnNames.size(); i++)
                infoColumnNames[i] = tempInfoColumnNames.get(i);
            
            lineScanner.close(); //close line scanner
            
            //create infoNotation with the role of category dictionary
            //infoNotation = new String[studentIDs.length][infoColumnNames.length];

            /// write data to infoData
            infoData = new double[super.studentIDs.length][infoColumnNames.length];

            int i = 0;    //line counter //infoData index
            double[] row; //data row
            while (fileScanner.hasNextLine()) {

                line = fileScanner.nextLine();
                if (!line.equals("")){ //deals with empty rows in "bugFreeGraduateGrades.csv"

                    // and one that scans the line entry per entry using the commas as delimiters
                    lineScanner = new Scanner(line);
                    lineScanner.useDelimiter(",");

                    lineScanner.nextInt(); //skip StudentID //read from grades data file

                    row = new double[infoColumnNames.length]; //create row

                    int j = 0;
                    while (lineScanner.hasNext()) {
                        String nextValue = lineScanner.next();
                        try {
                            row[j] = Double.parseDouble(nextValue);
                        } catch (NumberFormatException e) {
                            row[j] = super.convertStringToNumber(nextValue);
                        } finally {
                            j++;
                        }
                    }
                    
                    infoData[i++] = row;
                    lineScanner.close();
                }
            }

            fileScanner.close();

            for(int j = 0; j < infoColumnNames.length; j++)
                columnIndices.put(columnNames[j], j);

        } catch(IOException exc){ //catch exceptions such as FileNotFound
            System.out.println(exc);
        }
    }
    
    public static void storeAggregateSamples(int numberOfSamples, double percentage) {
        AggregateData aggregateData = new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv");
        Data fullData = new Data(aggregateData);
        Data.outputCSV(fullData, "data/data.csv");
        try{
            Data[] samples = Data.createSamples(fullData, numberOfSamples, percentage);
            for(int i = 0; i < samples.length; i++) outputCSV(samples[i], "data/fullSamples/sample "+ (i+1));
        } catch (SampleNumberOutOfRange exc) {
            System.out.println(exc);
        }
    }

    public static void splitAggregateDataAndStoreSamples(int numberOfSamples, double percentage) {
        try{
            AggregateData aggregateData = new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv");
            Data fullData = new Data(aggregateData);
            Data[] split = Data.splitTestingData(fullData, percentage);
            Data.outputCSV(split[0], "data/splitSamples/trainingData.csv");
            Data.outputCSV(split[1], "data/splitSamples/testingData.csv");
            
            Data[] samples = Data.createSamples(split[0], numberOfSamples, percentage);
            for(int i = 0; i < samples.length; i++) outputCSV(samples[i], "data/splitSamples/sample "+ (i+1));
        } catch (PercentageOutOfRange exc) {
            System.out.println(exc);
        } catch (SampleNumberOutOfRange exc) {
            System.out.println(exc);
        }
    }
}
