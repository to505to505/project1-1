import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Stores data in the format of a table, with a primary key (identification number) as the first column. The ID and column names are held in separate arrays from the data, while the data is held in an 2d array. Also has static methods for splitting data.
 */
public class Data {
    public static void main(String[] args) {//testing purposes
        //AggregateData.storeAggregateSamples(10, 1.0);
        //AggregateData.splitAggregateDataAndStoreSamples(10, .75);
        Data bugData = new Data("data/bugFreeGraduateGrades.csv");
        outputCSV(bugData, "data/bugData.csv");
    }

    public String[] columnNames;
    public int[] studentIDs;
    public double[][] data;

    private static final long SAMPLE_SEED = 1617316862; //RNG seed used to create forest samples
    private static final long SHUFFLE_SEED = SAMPLE_SEED+1; //RNG seed used to shuffle data before splitting it

    //will be arrays of Data objects
    //public static double[][] trainingData;
    //public static double testingData;
    //public static double[][][] samples;

    /**
     * Constructor used for the empty initialization of AggregateData
     */
    public Data(){
        //NULL
    }

    public Data(int entryNumber, int colNumber){
        columnNames = new String[colNumber];
        studentIDs = new int[entryNumber];
        data = new double[entryNumber][colNumber];
    }

    /**
     * Stores the data contained in a CSV style file into a new Data object. Stores the names of the columns in String[] this.columnNames, the first column of the table into int[] this.studentIDs, and the bulk of the data in double[][] this.data.
     * 
     * @param fileName
     */
    public Data(String fileName){
        try {
            File fin = new File(fileName);          //data file
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

    /**
     * Creates a copy of an existend Data object. Used to construct AggregateData from a given data object.
     * @param data
     */
    public Data(Data data) {
        this.data = data.data;
        this.columnNames = data.columnNames;
        this.studentIDs = data.studentIDs;
    }

    public Data(Data initialData, double percentage) throws PercentageOutOfRange{
        this(initialData, percentage, SAMPLE_SEED);
    }

    /**
     * Creates a new data sample with bootstrapping from a give data object. Lets you specify the size of the sample as a percentage of the initial data.
     * @param initialData
     */
    public Data(Data initialData, double percentage, long sampleSeed) throws PercentageOutOfRange{
        if(percentage > 1 || percentage < 0) throw new PercentageOutOfRange(percentage); //deals with the nonsensical percentage case
        int sampleSize = (int) (percentage * initialData.studentIDs.length);

        //initialize fields
        columnNames = initialData.columnNames;
        studentIDs = new int[sampleSize];
        data = new double[sampleSize][initialData.columnNames.length];

        //set up RNG
        Random rand = new Random(sampleSeed); //randomly generated seed
        int bound = initialData.studentIDs.length; //ints generated in interval [0, bound]
        
        //create sample
        int j;
        for(int i = 0; i < sampleSize; i++){
            j = rand.nextInt(bound);
            studentIDs[i] = initialData.studentIDs[j];
            data[i] = initialData.data[j];
        }
    }

    /**
     * Constructor that builds a single table Data object from an AggregateData object
     * @param aggregateData
     */
    public Data(AggregateData aggregateData) {
        columnNames = new String[aggregateData.columnNames.length+aggregateData.infoColumnNames.length];
        data = new double[aggregateData.studentIDs.length][columnNames.length];
        studentIDs = aggregateData.studentIDs;

        /// Combine column names from aggregateData
        /* */
        System.arraycopy(aggregateData.columnNames, 0, columnNames, 0, aggregateData.columnNames.length);
        System.arraycopy(aggregateData.infoColumnNames, 0, columnNames, aggregateData.columnNames.length, aggregateData.infoColumnNames.length);

        /// Combine column names from aggregateData
        for(int i = 0; i < data.length; i++){
            System.arraycopy(aggregateData.data[i], 0, data[i], 0, aggregateData.columnNames.length);
            System.arraycopy(aggregateData.infoData[i], 0, data[i], aggregateData.columnNames.length, aggregateData.infoColumnNames.length);
        }
    }



    /**
     * Creates a number of random samples from the initial data of size a certain proportion of it and returns them as an array of Data.
     * 
     * @param initalData
     * @param numberOfSamples
     * @return
     * @throws SampleNumberOutOfRange
     */
    public static Data[] createSamples(Data initalData, int numberOfSamples, double percentage) throws SampleNumberOutOfRange {
        if(numberOfSamples < 0) throw new SampleNumberOutOfRange(numberOfSamples);

        Data[] samples = new Data[numberOfSamples];
        try {
            for(int i = 0; i < numberOfSamples; i++)
                samples[i] = new Data(initalData, percentage, SAMPLE_SEED+i);
        } catch (PercentageOutOfRange exc) {
            System.out.println(exc);
        }

        return samples;
    }

    /**
     * Splits the initial data into a training dataset of a given proportion to the initial data and a testing set that is the remaining data. For a more arbitrary split it randomly permutes the initial data.
     * The shuffling algorithm is taken from the Java Collections.shuffle.
     * 
     * @param initialData
     * @param percentage
     * @return An array of the two Data objects: The training set[0] and the testing set[1].
     */
    public static Data[] splitTestingData(Data initialData, double percentage) throws PercentageOutOfRange {
        if(percentage > 1 || percentage < 0) throw new PercentageOutOfRange(percentage);
        int sampleSize = (int) (percentage * initialData.studentIDs.length);

        ///Shuffle the initial dataset
        Random rand = new Random(SHUFFLE_SEED);
        int size = initialData.studentIDs.length;
        //System.out.println(rand.nextInt(size));

        //copy initial data into temporary dataset so as to keep initial dataset unchanged
        int[] tempStudentIDs = initialData.studentIDs.clone();
        double[][] tempData = initialData.data.clone();
        int j;
        for (int i=size; i > 1; i--){
            j = rand.nextInt(i);
            swap(tempStudentIDs, i-1, j);
            swap(tempData, i-1, j);
        }

        //Create data array
        Data[] split = new Data[2]; //holds the two datasets
        split[0] = new Data(sampleSize, initialData.columnNames.length);
        split[1] = new Data(initialData.studentIDs.length - sampleSize, initialData.columnNames.length);

        //fill in columnNames
        split[0].columnNames = initialData.columnNames;
        split[1].columnNames = initialData.columnNames;
        
        ///Split the data
        int i;
        for(i = 0; i < sampleSize; i++){
            split[0].studentIDs[i] = tempStudentIDs[i];
            split[0].data[i] = tempData[i];
        }
        
        for(; i < tempStudentIDs.length; i++){
            split[1].studentIDs[i-sampleSize] = tempStudentIDs[i];
            split[1].data[i-sampleSize] = tempData[i];
        }

        return split;
    }

    /**
     * Creates a .csv file containing the contentents of a Data object at the specified location.
     * @param data
     * @param touchFile
     */
    public static void outputCSV(Data data, String touchFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(touchFile);
            PrintStream printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream);
            
            System.out.print("StudentID,");
            for(int i = 0; i < data.columnNames.length; i++) {
                System.out.print(data.columnNames[i]);
                if(i != data.columnNames.length-1) System.out.print(","); //Don't print a ',' at the end of the line
                else System.out.println();
            }
            for(int i = 0; i < data.data.length; i++){
                System.out.print("" + data.studentIDs[i] + ",");
                for(int j = 0; j < data.columnNames.length; j++){
                    System.out.print((int) data.data[i][j]); 

                    if(j != data.columnNames.length - 1) System.out.print(",");
                    else System.out.println();
                }
            }
            printStream.close();
            System.setOut(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    /**
     * Swaps the objects in an int array at the indeces a and b
     * 
     * @param elements
     * @param a
     * @param b
     */
    private static void swap(int[] elements, int a, int b) {
        int tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }

    /**
     * Swaps the objects in a double array at the indeces a and b
     * 
     * @param elements
     * @param a
     * @param b
     */
    private static void swap(double[][] elements, int a, int b) {
        double[] tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }

    public int columnIndex(String columnName){
        for(int i = 0; i < columnNames.length; i++)
            if(columnName.equals(columnNames[i]))
                return i;
        return -1;
    }

    public int studentIndex(int studentID){
        for(int i = 0; i < studentIDs.length; i++)
            if(studentID == studentIDs[i])
                return i;
        return -1;
    }

    /**
     * Helper method used to represent null values (NG values) in the same data structure as grades.
     * It gives NG values the number -1. Gives other String values the number -2.
     * @param value
     * @return
     */
    protected static double convertStringToNumber(String value) {
        switch (value) {
            case "NG":
                return -1;
            case "nulp":
                return 0;
            case "doot":
                return 1;
            case "lobi":
                return 2;
            case "nothing":
                return 0;
            case "low":
                return 1;
            case "medium":
                return 2;
            case "high":
                return 3;
            case "full":
                return 4;
            case "1 star":
                return 1;
            case "2 stars":
                return 2;
            case "3 stars":
                return 3;
            case "4 stars":
                return 4;
            case "5 stars":
                return 5;
            default:
                return -2;
        }
    }
}
