public class test3 {

    public static void main(String[] args) {
        Data data_test = new Data("src/data/GraduateGrades.csv");
        double[][] euclid_data = new double[30][30];

        double[][] data = data_test.data;
        for(int j=0; j<data_test.columnNames.length; j++){
            for(int k=0; k<data_test.columnNames.length; k++){
                euclid_data[j][k] = euclidian(data, j, k);
            }
        }
        System.out.println(findMax(euclid_data));


        /* CorrelationClass class1 = new CorrelationClass("GraduateGrades", "ATE-003", "Cosine similarity");
        System.out.println(class1.KMO_007);
        CorrelationClass class2 = new CorrelationClass("GraduateGrades", "TGL-013", "Cosine similarity");
        System.out.println(class2.KMO_007);*/
    }   
    public static double findMax(double[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым или null");
        }
    
        double max = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] > max) {
                    max = array[i][j];
                }
            }
        }
        return max;
    }
    
    public static double euclidian(double[][] data, int j, int k) {
        double summ = 0;
        for (int i = 0; i < data.length; i++) {
            summ += Math.pow((data[i][j] - data[i][k]), 2);
        }
        summ = Math.sqrt(summ);
        return summ;
    }
}