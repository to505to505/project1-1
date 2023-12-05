import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();

    int dataCount = 0; //used to count Data objects
    int removeCount = 1; //number of removed Data objects offset by 1
    ArrayList<Data> dataList = new ArrayList<Data>();


    @Override
    public void start(Stage stage) {

        Image icon = new Image("data/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Data visualization");

        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press ESC when you get bored");
        //stage.setWidth(720); //use scene dimensions
        //stage.setHeight(480); 
        //stage.setResizable(false);
        stage.setX(300);
        stage.setY(100);

        ///Text
        Text text = new Text(20.0, 10.0, "Text");
        text.setFont(Font.font("Segoe UI", 13));
        text.setFill(Color.BLUEVIOLET);

        ///Line
        Line line = new Line();
        line.setStartX(100);
        //line.setStartY(200);
        line.setEndX(200);
        //line.setEndY(200);
        line.setStrokeWidth(5);
        line.setOpacity(.75);

        ///Rectangle
        Rectangle rect = new Rectangle();
        rect.setX(50);
        rect.setY(50);
        rect.setWidth(100);
        rect.setHeight(100);
        rect.setFill(Color.BLUEVIOLET);

        ///Polygon
        Polygon poly = new Polygon();
        poly.getPoints().addAll(
            200.0, 100.0,
            200.0, 200.0,
            100.0, 150.0
        );
        poly.setFill(Color.BLACK);

        ///Circle
        Circle circle = new Circle();
        circle.setCenterX(100.0);
        circle.setCenterY(200.0);
        circle.setRadius(50.0);
        circle.setFill(Color.GREEN);

        ///ImageView
        ImageView imgView = new ImageView(icon);
        imgView.setX(200.0);
        imgView.setY(200);
        imgView.setFitWidth(400);
        imgView.setFitHeight(400);

        ///Group
        Group group = new Group();
        group.getChildren().addAll(text, line, rect, poly, circle, imgView);


        ///Side Menu
        VBox vbox = new VBox();
        vbox.setPrefWidth(100);
        vbox.setStyle("-fx-background-color: #333333;");

        Button button1 = new Button("Visualization Sandbox");
        button1.setPrefWidth(vbox.getPrefWidth());
        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        Button button2 = new Button("Step 1 Findings");
        button2.setPrefWidth(vbox.getPrefWidth());
        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        Button button3 = new Button("Step 2 Findings");
        button3.setPrefWidth(vbox.getPrefWidth());
        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        Button button4 = new Button("Steps 3 & 4 Findings");
        button4.setPrefWidth(vbox.getPrefWidth());
        button4.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        vbox.getChildren().addAll(button1, button2, button3, button4);

        ///Menu Bar
        MenuBar menuBar = new MenuBar();
            Menu dataMenu = new Menu("Data");
            MenuItem addData = new MenuItem("add data");
            
            addData.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    dataImport(stage);
                    int dC = dataCount++;
                    Menu data = new Menu("data" + dC);
                    MenuItem rmData = new MenuItem("remove data");
                    rmData.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent e){
                            dataMenu.getItems().remove(data);
                            dataList.remove(dataCount - removeCount++);
                            System.out.println(dataList.size());
                        }
                    });
                    data.getItems().add(rmData);
                    dataMenu.getItems().add(data);
                    System.out.println(dataList.size());
                }
            });
            
            dataMenu.getItems().addAll(addData);

            Menu preferencesMenu = new Menu("Preferences");
            Menu fontSMenu = new Menu("Font");
                MenuItem font1 = new MenuItem("font1");
            fontSMenu.getItems().addAll(font1);
            Menu sizeSMenu = new Menu("Size");
            Menu styleSMeun = new Menu("Style");
            preferencesMenu.getItems().addAll(fontSMenu, sizeSMenu, styleSMeun);

            Menu menu = new Menu("Chose the plot");
            MenuItem histogramItem = new MenuItem("Histogram of raw data");
            MenuItem piechartItem = new MenuItem("PieChart of cum_laude");
            MenuItem piechartItem1 = new MenuItem("Similarity between 2 (Scatter plot)");
            MenuItem pieCHartItem2 = new MenuItem("Course order ");
            MenuItem pie = new MenuItem("Predictions Evaluation (Table)");
            menu.getItems().addAll(histogramItem, piechartItem, piechartItem1, pieCHartItem2, pie);
        menuBar.getMenus().addAll(dataMenu, preferencesMenu, menu);




        ///Border Pane
        BorderPane root = new BorderPane();
        root.setLeft(vbox);
        root.setTop(menuBar);
        root.setCenter(group);


       
        Scene scene = new Scene(root, 720, 480);
        stage.setScene(scene);
        stage.show();
        

        histogramItem.setOnAction(event -> openHistogramWindow(stage, scene));
        piechartItem.setOnAction(event -> openPieChartWindow(stage, scene));
    }

    private void openHistogramWindow(Stage stage, Scene scene) {
        HistogramChart histogramChart = new HistogramChart(scene);
        histogramChart.start(stage); 
    }
    private void openPieChartWindow(Stage stage, Scene scene) {
        PieChartPlot pieChart = new PieChartPlot(scene);
        pieChart.start(stage); 
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void dataImport(Stage stage){
        ///Read data files
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files", "*.csv");
        fileChooser.setTitle("Import Data");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            dataList.add(new Data(file));
            //openFile(file);
        } else {
            Data data = new Data("data/bugData.csv");
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}
