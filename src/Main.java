import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
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

    public static int dataCount = 0; //used to count Data objects
    public static int removeCount = 1; //number of removed Data objects offset by 1
    public static ArrayList<Data> dataList = new ArrayList<Data>();
    public static ObservableList<String> columnList;

    final static int BUTTON_COUNT = 10;
    final static String[] BUTTON_TEXTS = {"Visualization Sandbox", "Step 1 Findings", "Bar Chart", "Histogram", "Pie Chart", "Scatter Plot", "Swarm Plot", "Step 2 Findings", "Steps 3 & 4 Findings", "PieChartsCum"};
    


    /* Methods for visualizition */


    //create frequency map for histogram
    public static Map<String, Integer> raw_data_hist(String data_name, String course) {
        Data right_data = new Data();
        for (Data data : dataList) {
            if (data.name.equals(data_name)) {
                right_data = data;
            }
        }
        int course_col_num = 0;
        for (int i = 0; i < right_data.columnNames.length; i++) {
            if (right_data.columnNames[i].equals(course)) {
                course_col_num = i;
            }
        }
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for(int i =0; i<right_data.data.length; i++) {
            for(int j = 0; j<right_data.data[i].length; j++) {
                if(j==course_col_num)
                    frequencyMap.put(String.valueOf(Math.round(right_data.data[i][j])), frequencyMap.getOrDefault(String.valueOf(Math.round(right_data.data[i][j])), 0) + 1);  
            }
        }
        return frequencyMap;
    
    }

    // sorting LAL
    public static List<String> LalSorted(Data right_data) {
        int Lal_count_col_n = 0;
        List<Integer> originalList = new ArrayList<>();
        for(int k=0; k<right_data.data.length; k++) {
            if(right_data.data[k].equals("Lal Count")) {
                Lal_count_col_n = k;
            }
            }
        
        for(int i =0; i < right_data.data.length; i++) {
            for(int j =0; j <right_data.data[i].length; j++) {
                if(j==Lal_count_col_n) {
                    originalList.add((int)right_data.data[i][j]);
                }
                    
            }
        }
        Set<Integer> uniqueSet = new HashSet<>(originalList);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);
        List<Integer> sortedIntList = uniqueList.stream().sorted().collect(Collectors.toList());
        List<String> stringList = new ArrayList<>();    
        stringList = sortedIntList.stream()
                                               .map(Object::toString)
                                               .collect(Collectors.toList());
        return stringList;
    }

    private VBox Scatter(Stage stage){

        VBox vBox = new VBox();

        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> filter2ComboBox= new ComboBox<>();
        filter2ComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
        filter2ComboBox.getSelectionModel().selectLast(); // selecting default value

        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("GraduateGrades", "CurrentGrades"));
        dataSelector.getSelectionModel().selectFirst(); // selecting default value


        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(filter1ComboBox.getValue());
        yAxis.setLabel(filter2ComboBox.getValue());
        
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        
        
        
        updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue() );


        filter1ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));
        filter2ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(),scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));

        vBox.getChildren().addAll(dataSelector, filter1ComboBox,filter2ComboBox, scatterChart);

        return vBox;
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

        ///Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu dataMenu = DataImport.dataMenu(stage);
            
        Menu preferencesMenu = new Menu("Preferences");
        Menu fontSMenu = new Menu("Font");
            MenuItem font1 = new MenuItem("font1");
        fontSMenu.getItems().addAll(font1);
        Menu sizeSMenu = new Menu("Size");
        Menu styleSMeun = new Menu("Style");
        preferencesMenu.getItems().addAll(fontSMenu, sizeSMenu, styleSMeun);

        menuBar.getMenus().addAll(dataMenu, preferencesMenu);
        //add menubar to root
        root.setTop(menuBar);
        ///END Menu Bar

        VBox startScreen = new VBox();
        startScreen.setStyle("-fx-background-color: #fff5ee");

        ///Sandbox
        BorderPane sandbox = new BorderPane();
        sandbox.setStyle("-fx-background-color: #fff5ee");
        //sandbox.setAlignment(graph, Pos.CENTER);


        //Sandbox Filter Bar FlowPane
        FlowPane filters = new FlowPane(10, 10);
        filters.setStyle("-fx-background-color: #554222");
        //Label xAxisLab = new Label("X Axis: ");

        VBox xAxisBox = new VBox();
        ComboBox<String> xAxisFile = new ComboBox<String>();
        ComboBox<String> xAxiss = new ComboBox<String>();
        xAxiss.setValue("----------");
        //Label yAxisLab = new Label("X Axis: ");
        ComboBox<String> yAxiss = new ComboBox<String>();
        yAxiss.setValue("----------");
        filters.getChildren().addAll(xAxiss, yAxiss);
        refreshColumnList(filters);

        sandbox.setTop(filters);
        ///END SandBox


        ///Side Menu
        VBox sideMenu = new VBox();
        sideMenu.setPrefWidth(200);
        sideMenu.setStyle("-fx-background-color: #333333;");

        Button[] buttons = new Button[BUTTON_COUNT];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(BUTTON_TEXTS[i]);
            buttons[i].setPrefWidth(sideMenu.getPrefWidth());
            buttons[i].setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }

        EventHandler<ActionEvent> sideMenuHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                String but = ((Button)e.getTarget()).getText();
                int i = 0;
                for(;i < BUTTON_TEXTS.length; i++)
                    if(but.equals(BUTTON_TEXTS[i]))
                        break;
                buttons[i].setStyle("-fx-background-color: #666666; -fx-text-fill: white;");
                switch(i){
                    case 0:
                        root.setCenter(sandbox);
                        stage.show();   
                        break;
                    case 1:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 2:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 3:
                        VBox vBox = Scatter(stage);
                        root.setCenter(vBox);
                        stage.show();
                        break;
                    case 4:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 5:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 6:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 7:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 8:
                        BorderPane g = new BorderPane();
                        g.setStyle("-fx-background-color: #333333");
                        root.setCenter(g);
                        stage.show();
                        break;
                    case 9:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 10: 
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    default:
                }
                for(int j = 0; i < buttons.length; j++)
                    if(j!=i) buttons[j].setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
            }
        };
        for(Button b: buttons) b.setOnAction(sideMenuHandler);
        sideMenu.getChildren().addAll(buttons);
        //add Side Menu to root
        root.setLeft(sideMenu);
        ///End Side Menu
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void openHistogramWindow(Stage stage, Scene scene) {
        HistogramChart histogram = new HistogramChart(scene);
        histogram.start(stage);
    }
    private void openPieChartWindow(Stage stage, Scene scene) {
        PieChartPlot pieChart = new PieChartPlot(scene);
        pieChart.start(stage); 
    }
    private void openScatterPlotWindow(Stage stage, Scene scene) {
        ScatterPlotChart scatterPlot = new ScatterPlotChart(scene);
        scatterPlot.start(stage);
    }
    private void updateScatter(String selectedData, ScatterChart<Number, Number> ScatterChart, String selectedFilter1, String selectedFilter2) {
        ScatterChart.getData().clear();
        
        XYChart.Series<Number, Number> series =  MainFunc.getScatter(selectedFilter1, selectedFilter2,  selectedData);
        ScatterChart.getData().add(series);
        ScatterChart.layout();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void swarmPlot(){
        VBox swormBox = new VBox();
        FlowPane filters = new FlowPane();
        Group swarmPlot = new Group();
        swormBox.getChildren().addAll(filters, swarmPlot);
    }

    private static void refreshColumnList(FlowPane filters){ //has a problem when the same column names appear more than once - considers them as different.
        ArrayList<String> temp = new ArrayList<String>();
        for(Data data : Main.dataList)
            for(String s : data.columnNames)
                temp.add(s);
        Main.columnList = FXCollections.observableArrayList(temp);
        System.out.println(Main.columnList);
        ChoiceBox<String> xAxis = new ChoiceBox<String>(Main.columnList);
        xAxis.setValue("------------");
        ChoiceBox<String> yAxis = new ChoiceBox<String>(Main.columnList);
        yAxis.setValue("------------");
        filters.getChildren().remove(0);
        filters.getChildren().add(0, xAxis);
        filters.getChildren().remove(1);
        filters.getChildren().add(1, yAxis);
    }
}
