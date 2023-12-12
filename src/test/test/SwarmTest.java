package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SwarmTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        // Данные для двух категорий
        List<Double> category1 = Arrays.asList(1.0, 2.5, 3.2, 2.8, 1.5);
        List<Double> category2 = Arrays.asList(3.0, 2.2, 2.9, 3.1, 1.8);

        double spacing = 80; // расстояние между категориями
        double pointRadius = 5; // радиус точки

        // Добавляем точки первой категории
        addCategoryPoints(root, category1, 100, Color.BLUE, spacing, pointRadius);

        // Добавляем точки второй категории
        addCategoryPoints(root, category2, 200, Color.RED, spacing, pointRadius);

        primaryStage.setTitle("Swarm Plot Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addCategoryPoints(Pane root, List<Double> categoryData, double startX, Color color, double spacing, double radius) {
        Random rand = new Random();
        for (int i = 0; i < categoryData.size(); i++) {
            Circle circle = new Circle();
            circle.setCenterX(startX + rand.nextDouble() * spacing - spacing / 2); // добавляем немного случайности
            circle.setCenterY(300 - categoryData.get(i) * 50); // высота основана на значении данных
            circle.setRadius(radius);
            circle.setFill(color);

            root.getChildren().add(circle);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}