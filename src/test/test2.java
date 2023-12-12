public class test2 {
       public static void main(String[] args) {
              Data combinedData = new Data(new AggregateData("src/data/CurrentGrades.csv", "src/data/StudentInfo.csv"));
              for(String s : combinedData.columnNames){
                     System.out.println(s);
       }
}
}