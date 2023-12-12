import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test_test extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(10);

        // Первая "папка"
        VBox firstFolder = new VBox(new Button("Кнопка 1"), new Button("Кнопка 2"));
        TitledPane firstTitledPane = new TitledPane("Папка 1", firstFolder);
        firstTitledPane.setExpanded(false);

        // Вторая "папка"
        VBox secondFolder = new VBox(new Button("Кнопка 3"), new Button("Кнопка 4"));
        TitledPane secondTitledPane = new TitledPane("Папка 2", secondFolder);

        // Добавляем сворачиваемые панели в основной VBox
        root.getChildren().addAll(firstTitledPane, secondTitledPane);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Пример с папками и кнопками");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
