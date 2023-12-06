import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;
import java.awt.Label;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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

    //private final Desktop desktop = Desktop.getDesktop();

    int dataCount = 0; //used to count Data objects
    int removeCount = 1; //number of removed Data objects offset by 1
    ArrayList<Data> dataList = new ArrayList<Data>();
    ObservableList<String> columnList;

    private void refreshColumnList(FlowPane filters){ //has a problem when the same column names appear more than once - considers them as different.
        ArrayList<String> temp = new ArrayList<String>();
        for(Data data : dataList)
            for(String s : data.columnNames)
                temp.add(s);
        columnList = FXCollections.observableArrayList(temp);
        ChoiceBox<String> xAxis = new ChoiceBox<String>(columnList);
        xAxis.setValue("------------");
        ChoiceBox<String> yAxis = new ChoiceBox<String>(columnList);
        yAxis.setValue("------------");
    }

    @Override
    public void start(Stage stage) {

        Image icon = new Image("data/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Data visualization");

        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press ESC when you get bored");
        stage.setWidth(1080);
        stage.setHeight(720); 
        //stage.setResizable(false);

        ///Root
        BorderPane root = new BorderPane();

        ///Sandbox Pane
        BorderPane sandbox = new BorderPane();
        sandbox.setStyle("-fx-background-color: #fff5ee");
        //sandbox.setAlignment(graph, Pos.CENTER);
        //Sandbox Filter Bar FlowPane
        FlowPane filters = new FlowPane(10, 10);
        //Label xAxisLab = new Label("X Axis: ");
        ComboBox<String> xAxis = new ComboBox<String>();
        xAxis.setValue("----------");
        //Label yAxisLab = new Label("X Axis: ");
        ComboBox<String> yAxis = new ComboBox<String>();
        yAxis.setValue("----------");
        filters.getChildren().addAll(xAxis, yAxis);
        filters.getChildren().remove(0);
        filters.getChildren().add(0, xAxis);
        sandbox.setTop(filters);

        ///Side Menu
        VBox vbox = new VBox();
        vbox.setPrefWidth(200);
        vbox.setStyle("-fx-background-color: #333333;");

        final String BUTTON1_TEXT = "Visualization Sandbox";
        Button button1 = new Button(BUTTON1_TEXT);
        button1.setPrefWidth(vbox.getPrefWidth());
        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        final String BUTTON2_TEXT = "Step 1 Findings";
        Button button2 = new Button(BUTTON2_TEXT);
        button2.setPrefWidth(vbox.getPrefWidth());
        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        final String BUTTON3_TEXT = "Step 2 Findings";
        Button button3 = new Button(BUTTON3_TEXT);
        button3.setPrefWidth(vbox.getPrefWidth());
        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        final String BUTTON4_TEXT = "Steps 3 & 4 Findings";
        Button button4 = new Button(BUTTON4_TEXT);
        button4.setPrefWidth(vbox.getPrefWidth());
        button4.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");

        EventHandler sideMenuHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                switch(((Button)e.getTarget()).getText()){
                    case BUTTON1_TEXT:
                        button1.setStyle("-fx-background-color: #666666; -fx-text-fill: white;");
                        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button4.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        root.setCenter(sandbox);
                        stage.show();
                        break;
                    case BUTTON2_TEXT:
                        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button2.setStyle("-fx-background-color: #666666; -fx-text-fill: white;");
                        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button4.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        BorderPane f = new BorderPane();
                        f.setStyle("-fx-background-color: #444444");
                        root.setCenter(f);
                        stage.show();
                        break;
                    case BUTTON3_TEXT:
                        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");    
                        button3.setStyle("-fx-background-color: #666666; -fx-text-fill: white;");
                        button4.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        BorderPane g = new BorderPane();
                        g.setStyle("-fx-background-color: #333333");
                        root.setCenter(g);
                        stage.show();
                        break;
                    case BUTTON4_TEXT:
                        button1.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button2.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button3.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
                        button4.setStyle("-fx-background-color: #666666; -fx-text-fill: white;");
                        BorderPane h = new BorderPane();
                        h.setStyle("-fx-background-color: #222222");
                        root.setCenter(h);
                        stage.show();
                        break;
                    default:
                }
            }
        };
        button1.setOnAction(sideMenuHandler);
        button2.setOnAction(sideMenuHandler);
        button3.setOnAction(sideMenuHandler);
        button4.setOnAction(sideMenuHandler);
        vbox.getChildren().addAll(button1, button2, button3, button4);
        ///End Side Menu

        vbox.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){

            }
        });

        ///Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu dataMenu = dataMenu(stage, filters);
            
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


        ///add panes to root
        root.setLeft(vbox);
        root.setTop(menuBar);
       
        Scene scene = new Scene(root);
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

    /**
     * Creates a menu that facilitates data import functionality to the program.
     * @param stage
     * @return
     */
    private Menu dataMenu(Stage stage, FlowPane filters){
        Menu dataMenu = new Menu("Data");
        MenuItem addData = new MenuItem("add data");
        addData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                if(dataImport(stage, filters)){
                    int dC = dataCount++;
                    Menu data = new Menu("data" + dC);
                    MenuItem rmData = new MenuItem("remove data");
                    rmData.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent e){
                            dataMenu.getItems().remove(data);
                            dataList.remove(dataCount - removeCount++);
                            refreshColumnList(filters);
                            //System.out.println(dataList.size());
                        }
                    });
                    data.getItems().add(rmData);
                    dataMenu.getItems().add(data);
                    //System.out.println(dataList.size());
                }
            }
        });
        dataMenu.getItems().addAll(addData);
        return dataMenu;
    }

    /**
     * Creates a menu used to import the data from a .csv file into the program.
     * @param stage
     */
    private boolean dataImport(Stage stage, FlowPane filters){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files", "*.csv");
        fileChooser.setTitle("Import Data");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            dataList.add(new Data(file));
            refreshColumnList(filters);
            return true;
            //openFile(file);
        } else {
            return false;
        }
    }
}
