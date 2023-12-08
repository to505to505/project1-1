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

    private static int dataCount = 0; //used to count Data objects
    private static int removeCount = 1; //number of removed Data objects offset by 1
    private static ArrayList<Data> dataList = new ArrayList<Data>();
    private static ObservableList<String> columnList;

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

        VBox startScreen = new VBox();
        startScreen.setStyle("-fx-background-color: #fff5ee");

         ///Sandbox Pane
        BorderPane sandbox = new BorderPane();
        sandbox.setStyle("-fx-background-color: #fff5ee");
        //sandbox.setAlignment(graph, Pos.CENTER);
        //Sandbox Filter Bar FlowPane
        FlowPane filters = new FlowPane(10, 10);
        filters.setStyle("-fx-background-color: #554222");
        //Label xAxisLab = new Label("X Axis: ");
        ComboBox<String> xAxiss = new ComboBox<String>();
        xAxiss.setValue("----------");
        //Label yAxisLab = new Label("X Axis: ");
        ComboBox<String> yAxiss = new ComboBox<String>();
        yAxiss.setValue("----------");
        filters.getChildren().addAll(xAxiss, yAxiss);
        filters.getChildren().remove(0);
        filters.getChildren().add(0, xAxiss);
        sandbox.setTop(filters);

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
        ///End Side Menu

        sideMenu.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
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
        root.setLeft(sideMenu);
        root.setTop(menuBar);
        
        
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        

        histogramItem.setOnAction(e -> openHistogramWindow(stage, scene));
        piechartItem.setOnAction(e -> openPieChartWindow(stage, scene));
        piechartItem1.setOnAction(e -> openScatterPlotWindow(stage, scene));
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
        dataInit();
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

    private static void dataInit(){
        Data data_current = new Data("src/data/CurrentGrades.csv");
        data_current.name = "CurrentGrades";
        dataList.add(data_current);
        Data data_graduate = new Data("src/data/bugData.csv");
        data_graduate.name = "GraduateGrades";
        dataList.add(data_graduate);
        Data data_student_info = new Data("src/data/StudentInfo.csv");
        data_student_info.name = "StudentInfo";
        dataList.add(data_student_info);
    }
}
