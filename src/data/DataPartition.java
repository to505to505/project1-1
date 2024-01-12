package data;

import java.util.ArrayList;

public class DataPartition {
    public Data data;
    public AggregateData aggregateData;
    public ArrayList<Integer> initialStudentIndexes;
    public ArrayList<Integer> studentIndexes;
    public ArrayList<Integer> courseIndexes;
    public ArrayList<Integer> infoIndexes;

    public DataPartition(Data data){
        this.data = data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.data.length; i++)
            studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.columnNames.length; i++)
            courseIndexes.add(i);
    }

    public DataPartition(AggregateData data){
        this.aggregateData = data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.data.length; i++)
            studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.columnNames.length; i++)
            courseIndexes.add(i);
        infoIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.infoColumnNames.length; i++)
            infoIndexes.add(i);
    }

    public DataPartition(Data data, Condition condition){
        this.data = data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.data.length; i++)
            if(condition.compare(data.data[i]))
                studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.columnNames.length; i++)
            courseIndexes.add(i);
    }

    public DataPartition(DataPartition dataPartition, Condition condition){
        this.data = dataPartition.data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < dataPartition.studentIndexes.size(); i++)
            if(condition.compare(data.data[dataPartition.studentIndexes.get(i)]))
                studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < dataPartition.courseIndexes.size(); i++)
            courseIndexes.add(i);
    }

    public DataPartition(DataPartition dataPartition, Condition condition1, Condition condition2){
        this.data = dataPartition.data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < dataPartition.studentIndexes.size(); i++)
            if(condition1.compare(data.data[dataPartition.studentIndexes.get(i)]))
                studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < dataPartition.courseIndexes.size(); i++)
            if(condition2.compare(data.data[dataPartition.courseIndexes.get(i)]))
                courseIndexes.add(i);
    }

    public DataPartition(AggregateData data, Condition condition1, Condition condition2){
        this.aggregateData = data;
        studentIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.data.length; i++)
            if(condition1.compare(data.data[i]) && condition2.compare(data.infoData[i]))
                studentIndexes.add(i);
        courseIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.columnNames.length; i++)
            courseIndexes.add(i);
        infoIndexes = new ArrayList<Integer>();
        for(int i = 0; i < data.infoColumnNames.length; i++)
            infoIndexes.add(i);
    }

    /**
     * Returns a sorted ArrayList of the individual values of students for a feature.
     * It makes use of a frequency vector and has an optimization for differentiating between a course and the Lal count feature (which has a range of >10-100).
     * @param courseIndex
     * @return
     */
    public ArrayList<Double> getValuesVector(int courseIndex){

    if (courseIndex == 30 || courseIndex == 31 || courseIndex == 33) {
            switch (courseIndex) {
                case 30:
                    ArrayList<Double> values = new ArrayList<>();
                    values.add(0.0);
                    values.add(1.0);
                    values.add(2.0);
                    return values;
                case 31:
                    ArrayList<Double> values1 = new ArrayList<>();
                    values1.add(0.0);
                    values1.add(1.0);
                    values1.add(2.0);
                    values1.add(3.0);
                    values1.add(4.0);
                    return values1;
                case 33:
                    ArrayList<Double> values2 = new ArrayList<>();
                    values2.add(1.0);
                    values2.add(2.0);
                    values2.add(3.0);
                    values2.add(4.0);
                    values2.add(5.0);
                    return values2;
            };
        }
        ArrayList<Double> values = new ArrayList<Double>();
        
        boolean[] freq;
        if(data.data[studentIndexes.get(0)][courseIndex] > 10)
            freq = new boolean[101];
        else
            freq = new boolean[11];
    
        for(int i = 0; i < studentIndexes.size(); i++){
            freq[(int)data.data[studentIndexes.get(i)][courseIndex]] = true;
        }
        for(int i = 0; i < freq.length; i++){
            if(freq[i])
                values.add((double)i);
        }

        return values;
    }

    public void sortByCourse(Data data, int courseIndex){
        for(int i = 0; i < studentIndexes.size(); i++){
            for(int j = i + 1; j < studentIndexes.size(); j++){
                if(data.data[studentIndexes.get(i)][courseIndex] > data.data[studentIndexes.get(j)][courseIndex]){
                    int temp = studentIndexes.get(i);
                    studentIndexes.set(i, studentIndexes.get(j));
                    studentIndexes.set(j, temp);
                }
            }
        }
    }

    public int[] getFreqVec(int courseIndex){
        int[] freq = new int[11];
        for(int i = 0; i < studentIndexes.size(); i++){
            freq[(int)data.data[studentIndexes.get(i)][courseIndex]]++;
        }
        return freq;
    }

    public static void main(String[] args) {
        AggregateData dataa = new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv");
        Data data = new Data(dataa);
        DataPartition partition = new DataPartition(dataa, (double[] row) -> row[1] > 3, (double[] row) -> row[2] > 3);
        DataPartition partitio1n = new DataPartition(data, (double[] row) -> row[1] > 0);
        System.out.println(partition.studentIndexes.size());
        System.out.println(partition.courseIndexes.size());
    }
}
