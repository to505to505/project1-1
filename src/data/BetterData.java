package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BetterData {

    public BetterData(File fin){
        try {
            Scanner fileScanner = new Scanner(fin); //scans file line by line
            String line;                            //holds scanned line

            ArrayList<double[]> tempData = new ArrayList<double[]>(); //temporary dynamic array for data input //prbbly idiotic code

            /// write column names to columnNames[]
            line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            lineScanner.next(); // skip column StudentID //student IDs will be assigned their own array
            
            //copy column names into temporary dynamic array tempColumnNames
            ArrayList<String> tempColumnNames = new ArrayList<String>();
            while (lineScanner.hasNext())
                tempColumnNames.add(lineScanner.next());
            //copy column names from tempColumnNames to final array
            columnNames = new String[tempColumnNames.size()];
            for(int i = 0; i < tempColumnNames.size(); i++)
                columnNames[i] = tempColumnNames.get(i);
            
            lineScanner.close(); //close line scanner
            
            /// create student IDs temporary dynamic array tempStudentIDs
            ArrayList<Integer> tempStudentIDs = new ArrayList<Integer>();

            /// write data to ArrayList<double[]>
            double[] row; //data row
            while (fileScanner.hasNextLine()) {

                line = fileScanner.nextLine();
                if (!line.equals("")){ //deals with empty rows in "bugFreeGraduateGrades.csv"
                    // and one that scans the line entry per entry using the commas as delimiters
                    lineScanner = new Scanner(line);
                    lineScanner.useDelimiter(",");

                    tempStudentIDs.add(lineScanner.nextInt()); //add student ID to tempStudentIDs

                    row = new double[columnNames.length]; //create row for tempData

                    int j = 0;
                    while (lineScanner.hasNext()) {
                        String nextValue = lineScanner.next();
                        try {
                            row[j] = Double.parseDouble(nextValue);
                        } catch (NumberFormatException e) {
                            row[j] = convertStringToNumber(nextValue);
                        } finally {
                            j++;
                        }
                    }
                    
                    tempData.add(row);
                    lineScanner.close();
                }
            }
            
            ///copy contents from tempStudentIDs to studentIDs
            studentIDs = new int[tempStudentIDs.size()];
            for(int i = 0; i < studentIDs.length; i++)
                studentIDs[i] = tempStudentIDs.get(i);

            ///copy contents from tempData to data
            data = new double[tempData.size()][columnNames.length];
            for(int i = 0; i < tempData.size(); i++)
                data[i] = tempData.get(i);
            
            fileScanner.close();
            
        } catch(IOException exc){ //catch exceptions such as FileNotFound
            System.out.println(exc);
        }
    }
}