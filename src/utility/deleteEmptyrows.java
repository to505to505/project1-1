package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class deleteEmptyrows {
    public static void main(String[] args) {
        String inputFilePath = "src/data/bugFreeGraduateGrades.csv";
        String outputFilePath = "src/data/GraduateGradesNew.csv";

        removeEmptyLines(inputFilePath, outputFilePath);
    }

    private static void removeEmptyLines(String inputFilePath, String outputFilePath) {
        List<String> lines = new ArrayList<>();

        // Чтение файла и фильтрация пустых строк
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Проверка на пустую строку
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Запись отфильтрованных строк обратно в файл
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
