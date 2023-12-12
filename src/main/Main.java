package main;

import data.*;
import prediction.*;
import charts.*;
import utility.*;

import javafx.scene.control.TitledPane;
import java.io.File;
import java.io.IOException;
import javafx.scene.chart.*;
import javafx.scene.Node;
import java.util.stream.Collectors;
import javafx.geometry.Side;
import java.util.*;
import javax.xml.stream.EventFilter;
import javafx.scene.control.Label;

import AdditionalPlotClasses.*;
import AdditionalPlotClasses.CorrelationClass;

import java.awt.Desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    //private final Desktop desktop = Desktop.getDesktop();

    public static int dataCount = 0; //used to count Data objects
    public static int removeCount = 1; //number of removed Data objects offset by 1
    public static ArrayList<Data> dataList = new ArrayList<Data>();
    public static ObservableList<String> fileList;
    public static ObservableList<String> columnList;


    ///a flag for scatter plot that indicates whether to draw regression line or not
    public static boolean line_bool = false;

    



    // method for drawing Pie Chart with cum-laude students. returns vbox with all required elements
    private VBox PieChart(Stage stage) {
        VBox vBox = new VBox();

       

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.setItems(FXCollections.observableArrayList("GPA greater than boundary value", "No grades lower than boundary value"));
        filterComboBox.getSelectionModel().selectFirst();


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades"));
        dataSelector.setValue("GraduateGrades"); 


        ComboBox<String> filterBoundary = new ComboBox<>();
        filterBoundary.setItems(FXCollections.observableArrayList("7", "7.5", "8.0"));
        filterBoundary.setValue("7.5");
        
        Label label = new Label("Boundary value:");

        HBox hbox = new HBox();

        hbox.getChildren().addAll(label, filterBoundary);
        hbox.setSpacing(10);
        // Creating a Pie chart

        PieChart pieChart = new PieChart();


        filterComboBox.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue(), filterBoundary.getValue()));
        dataSelector.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue(), filterBoundary.getValue()));
        filterBoundary.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue(), filterBoundary.getValue()));





        updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue(), filterBoundary.getValue());
        
        vBox.getChildren().addAll(dataSelector, filterComboBox, hbox, pieChart);
        return vBox;


    }
    /// method for updating pie chart with cum-laude students when buttons are pressed
    private void updatePieChart(String selectedData, PieChart PieChart, String selectedFilter, String selectedBoundary) {
        PieChart.getData().clear(); // Очистка предыдущих данных
        Double boundary = Double.parseDouble(selectedBoundary);
        ArrayList<Integer> cum_students = DataFunc.cum(selectedData, selectedFilter, boundary);
        int all_students_length = DataFunc.data_size(selectedData);
        PieChart.Data slice1 = new PieChart.Data("Cum-Laude", cum_students.size());
        PieChart.Data slice2 = new PieChart.Data("Others", all_students_length-cum_students.size());
        PieChart.getData().add(slice1);
        PieChart.getData().add(slice2);
        PieChart.setLabelsVisible(true);
    }
    // method for drawing histogram or count plot (based on the data type of variable)
    private VBox Histogram(Stage stage) {
        VBox vBox = new VBox();


        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.setItems(FXCollections.observableArrayList(MainFunc.all_courses));
        filterComboBox.getSelectionModel().selectFirst();
        filterComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filterComboBox.getSelectionModel().selectFirst(); // Выбор первого элемента по умолчанию


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades", "StudentInfo"));
        dataSelector.setValue("GraduateGrades");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        

        
        filterComboBox.setOnAction(e -> updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue()));
        dataSelector.setOnAction(e -> {
            
            if(dataSelector.getValue().equals("StudentInfo")) {

                List<String> newItems = Arrays.asList("Suruna Value", "Hurni Level", "Volta ", "Lal Count");
            
                filterComboBox.setItems(FXCollections.observableArrayList(newItems));
                filterComboBox.getSelectionModel().selectFirst();
            } else {
                filterComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
                filterComboBox.getSelectionModel().selectFirst();
            }
            updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue());
        });
        
    
        updateHistogram(dataSelector.getValue(), barChart, filterComboBox.getValue());

        vBox.getChildren().addAll(dataSelector, filterComboBox, barChart);
        
    
        return vBox;
    }
    /// method to update the histogram when buttons are pressed
    private void updateHistogram(String selectedData, BarChart<String, Number> barChart, String selectedFilter) {
        barChart.getData().clear();
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.getCategories().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(selectedData);




        //Getting data to draw hist
        Map<String, Integer> freqMap = DataFunc.raw_data_hist(selectedData, selectedFilter);
        ArrayList<String> all_categories= new ArrayList<>();
        if (selectedData.equals("GraduateGrades")) {
            List<String> dataToAdd = Arrays.asList("6", "7", "8", "9", "10");
            all_categories.addAll(dataToAdd);
            xAxis.setCategories(FXCollections.observableArrayList(dataToAdd));

        } else if (selectedData.equals("CurrentGrades")) {
            List<String> dataToAdd1 = Arrays.asList("-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            all_categories.addAll(dataToAdd1);
            List<String> dataToAddtoShow = Arrays.asList("Missing values", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            xAxis.setCategories(FXCollections.observableArrayList(dataToAddtoShow));
        } else if(selectedData.equals("StudentInfo"))
         {
            if(selectedFilter.equals("Lal Count")) {
                List<String> dataToAdd3 = new ArrayList<>(DataFunc.LalSorted(selectedData));
                all_categories.addAll(dataToAdd3);
                xAxis.setCategories(FXCollections.observableArrayList(dataToAdd3));
            } else {
            List<String> dataToAdd2 = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
                dataToAdd2.add(get_value_names(entry.getKey(), selectedFilter));
                all_categories.add(entry.getKey());
            }
            xAxis.setCategories(FXCollections.observableArrayList(dataToAdd2));
         } }
        for (String category: all_categories) {
            Integer value = freqMap.get(category);
            if(selectedFilter.equals("Hurni Level") || selectedFilter.equals("Suruna Value") || selectedFilter.equals("Volta ")) {
                String categoryString = get_value_names(category, selectedFilter);
                series.getData().add(new XYChart.Data<>(categoryString, (value != null) ? value : 0));
            }
            if(category.equals("-1")) 
                series.getData().add(new XYChart.Data<>("Missing values", (value != null) ? value : 0));
            else
                series.getData().add(new XYChart.Data<>(category, (value != null) ? value : 0));
        
    }


        barChart.getData().add(series);
        barChart.layout();
        

}
// decode our double values of categorical variables (which we encoded in the first place in order to put all the data in double[][])
public static String get_value_names(String value, String course_name) {
    switch (course_name) {
        case "Suruna Value":
            switch (value) {
                case "0":
                    return "nulp";
                case "1":
                    return "doot";
                case "2":
                    return "lobi"; 
                default:
                    return "unknown";

            }
        case "Hurni Level":
            switch (value) {
                case "0":
                    return "nothing";
                case "1":
                    return "low";
                case "2":
                    return "medium";
                case "3":
                    return "high";
                case "4":
                    return "full";
                default:
                    return "unknown";

            
            }
            
        case "Volta ":
            switch (value) {
                case "1":
                    return "1 star";
                case "2":
                    return "2 stars";
                case "3":
                    return "3 stars";
                case "4":
                    return "4 stars";
                case "5":
                    return "5 stars";
                default:
                    return "unknown"; }
            
        default:
            return "unknown";   
         }}

// method for drawing count plot about course difficulty. average grade per course is used as a measure of difficulty
private VBox Course_difficulty() {
    VBox vBox = new VBox();
    
    ComboBox<String> dataSelector = new ComboBox<>();
    dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades"));
    dataSelector.setValue("GraduateGrades");

    ComboBox<String> filterComboBox = new ComboBox<>();
    filterComboBox.setItems(FXCollections.observableArrayList("Descending order", "Ascending order"));
    filterComboBox.setValue("Ascending order");

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        
    dataSelector.setOnAction(e -> {
        
        updateDifficulty(dataSelector.getValue(), barChart, filterComboBox.getValue());
    });
    filterComboBox.setOnAction(e -> {
        
        updateDifficulty(dataSelector.getValue(), barChart, filterComboBox.getValue());
    });
    

        updateDifficulty(dataSelector.getValue(), barChart, filterComboBox.getValue());

        vBox.getChildren().addAll(dataSelector, filterComboBox,  barChart);
        
    
        return vBox;

} 
// update count plot about course difficulty when buttons are pressed
private void updateDifficulty(String data_name,BarChart<String, Number> barChart, String filter_name ){
        barChart.getData().clear();
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.getCategories().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(data_name);
        LinkedHashMap<String, Double> freqMap = new LinkedHashMap<>();
        if(filter_name.equals("Ascending order")) {
            freqMap = DataFunc.courses_diffculty(data_name, true);
        } else {
            freqMap = DataFunc.courses_diffculty(data_name, false);
        }
        

        List<String> dataToAdd1 = new ArrayList<String>();
        

        for (Map.Entry<String, Double> entry : freqMap.entrySet()) {
        Double value = entry.getValue();
        series.getData().add(new XYChart.Data<>(entry.getKey(), (value != null) ? value : 0));
        dataToAdd1.add(entry.getKey());
        
    }
    xAxis.setCategories(FXCollections.observableArrayList(dataToAdd1));


        barChart.getData().add(series);
        barChart.layout();
}
// method for drawing scatter plot
private VBox Scatter(Stage stage){

        VBox vBox = new VBox();

        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> filter2ComboBox= new ComboBox<>();
        filter2ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter2ComboBox.getSelectionModel().selectLast(); // selecting default value

        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("GraduateGrades", "CurrentGrades"));
        dataSelector.getSelectionModel().selectFirst(); // selecting default value


        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(filter1ComboBox.getValue());
        yAxis.setLabel(filter2ComboBox.getValue());
        
        CustomScatterChart scatterChart = new CustomScatterChart(xAxis, yAxis);
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(dataSelector.getValue())) {
                    right_data = data;
            }
            }
        int course_col_num1 = 0;
        int course_col_num2  = 0;
              
        for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(filter2ComboBox.getValue())) {
                course_col_num2= k;
            }
            if(right_data.columnNames[k].equals(filter1ComboBox.getValue())) {
                course_col_num1 = k;
            }
        }

        Button line = new Button();
        line.setText("Add regression line");
        line.setOnAction(e -> {
            updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), true);
            line_bool = true;
        });

        updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), line_bool);
        scatterChart.legendVisibleProperty().setValue(false);

        filter1ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), line_bool));
        filter2ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(),scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), line_bool));
        dataSelector.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), line_bool));
        
        vBox.getChildren().addAll(dataSelector, filter1ComboBox,filter2ComboBox,line, scatterChart);
        
        return vBox;
    } 
    @Override
    public void start(Stage stage) {

        Image icon = new Image("media/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Data visualization");

        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press ESC when you get bored");
        stage.setWidth(1440);
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
        root.setCenter(startScreen);

        ///Sandbox
        BorderPane sandbox = new BorderPane();
        sandbox.setStyle("-fx-background-color: #fff5ee");
        //sandbox.setAlignment(graph, Pos.CENTER);
 
        //Sandbox Filter Bar FlowPane
        FlowPane filters = new FlowPane(10, 10);
        filters.setStyle("-fx-background-color: #997744");

        //X-Axis Data Selection
        VBox xAxisBox = new VBox();
        Text xCol = new Text("X-Axis Column:");
        ComboBox<String> xAxisCol = new ComboBox<String>();
        xAxisCol.setValue("----------");
        Text xFile = new Text("X-Axis File:");
        ComboBox<String> xAxisFile = new ComboBox<String>(fileList);
        xAxisFile.setValue("----------");
        xAxisFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                for(Data d: dataList)
                    if(d.name.equals(xAxisFile.getValue())){
                        xAxisCol.getItems().clear();
                        xAxisCol.getItems().addAll(d.columnNames);
                    }
            }
        });
        xAxisBox.getChildren().addAll(xFile, xAxisFile, xCol, xAxisCol);
        filters.getChildren().add(xAxisBox);
        //Y-Axis Data Selection
        VBox yAxisBox = new VBox();
        Text yCol = new Text("Y-Axis Column:");
        ComboBox<String> yAxisCol = new ComboBox<String>();
        yAxisCol.setValue("----------");
        Text yFile = new Text("Y-Axis File:");
        ComboBox<String> yAxisFile = new ComboBox<String>(fileList);
        yAxisFile.setValue("----------");
        yAxisFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                for(Data d: dataList)
                    if(d.name.equals(yAxisFile.getValue())){
                        yAxisCol.getItems().clear();
                        yAxisCol.getItems().addAll(d.columnNames);
                    }
            }
        });
        yAxisBox.getChildren().addAll(yFile, yAxisFile, yCol, yAxisCol);
        filters.getChildren().add(yAxisBox);
        //refreshColumnList(filters);

        sandbox.setTop(filters);
        ///END SandBox


        ///Side Menu
        VBox sideMenu = new VBox();
        sideMenu.setPrefWidth(260);
        sideMenu.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");

        VBox content1 = new VBox();
        VBox content2 = new VBox();
        VBox content3 = new VBox();
        VBox content4 = new VBox();

        int BUTTON_COUNT = 14;
        String[] BUTTON_TEXTS = {"Histograms and count plots", "Scatter plots", "Joint plots (with bar chart)","Joint plots (with line chart)",  "Swarm plots", "Cum-laude", "Courses difficulty", "Courses similarity", "Grade Average for Groups of Students by Student Property", "Number of Missing Grades per Student","Ungraded Students Count per Course", "Sorted Graded Students Count per Course",  "Swarm plot", "Prediction accuracy"};
        

        TitledPane titledPane1 = new TitledPane("Raw data", content1);
        TitledPane titledPane2 = new TitledPane("Step 1", content2);
        TitledPane titledPane3 = new TitledPane("Step 2", content3);
        TitledPane titledPane4 = new TitledPane("Step 3 and 4", content4);
        
        Button[] buttons = new Button[BUTTON_COUNT];
        

        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(BUTTON_TEXTS[i]);
            if(i<5) {
                content1.getChildren().addAll(buttons[i]);
            } else if(i<9) {
                content2.getChildren().addAll(buttons[i]);
            }else if(i<12) {
                content3.getChildren().addAll(buttons[i]);
            } else {
                content4.getChildren().addAll(buttons[i]);
            }
        
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
                        VBox vBox0 = Histogram(stage);
                        root.setCenter(vBox0);
                        stage.show();   
                        break;
                    case 1:
                        VBox vBox2 = Scatter(stage);
                        root.setCenter(vBox2);
                        stage.show();
                        break;
                    case 2:
                        VBox VBox3 = JointPlot_bar();
                        root.setCenter(VBox3);
                        stage.show();
                        break;
                    case 3:
                        VBox vBox4 = JointPlot_line();
                        root.setCenter(vBox4);
                        stage.show();
                        break;
                    case 4:
                        VBox vBox5 = SwarmPlot();
                        root.setCenter(vBox5);
                        stage.show();
                        break;
                    case 5:
                        VBox vBox6 = PieChart(stage);
                        root.setCenter(vBox6);
                        stage.show();
                        break;
                    case 6:
                        VBox vBox7 = Course_difficulty();
                        root.setCenter(vBox7);
                        stage.show();
                        break;
                    case 7:
                        VBox vBox8 = Table_draw();
                        root.setCenter(vBox8);
                        stage.show();
                        break;
                    case 8:
                        root.setCenter(Findings.gradeAveragePerPropertyValue(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv")));
                        stage.show();
                        break;
                    case 9: 
                        root.setCenter(Findings.NumberOfNGsCourses(dataList.get(0)));
                        stage.show();
                        break;
                    case 10: 
                        root.setCenter(Findings.NumberOfNGsStudents(dataList.get(0)));
                        stage.show();
                        break;
                    case 11: 
                        root.setCenter(Findings.NumberOfNGsCoursesSorted());
                        stage.show();
                        break;
                    case 12:
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    case 13:
                        VBox vBox13 = SwarmPlot();
                        root.setCenter(vBox13);
                        stage.show();
                        break;
                    case 14: 
                        VBox vBox14 = TablePred();
                        root.setCenter(vBox14);
                        stage.show();
                        break;
                    default: 
                        root.setCenter(startScreen);
                        stage.show();
                        break;

                }
                for(int j = 0; j < buttons.length; j++)
                    if(j!=i) buttons[j].setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
            }
        };
                
        for(Button b : buttons) b.setOnAction(sideMenuHandler);
        sideMenu.getChildren().addAll(titledPane1, titledPane2, titledPane3, titledPane4);
        //add Side Menu to root
        root.setLeft(sideMenu);
        ///End Side Menu
        
        
        Scene scene = new Scene(root, 1080, 1480);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
        
    
    


    private void openHistogramWindow(Stage stage, Scene scene) {
        HistogramChart histogram = new HistogramChart(scene);
        histogram.start(stage);
    }
    /* private void openPieChartWindow(Stage stage, Scene scene) {
        PieChartPlot pieChart = new PieChartPlot(scene);
        pieChart.start(stage); 
    } */
    private void openScatterPlotWindow(Stage stage, Scene scene) {
        ScatterPlotChart scatterPlot = new ScatterPlotChart(scene);
        scatterPlot.start(stage);
    }

    // method to draw joint plot where LineChart is used to show the distribution of categorocal variable
    private VBox JointPlot_line() {

    VBox VBox = new VBox();

        GridPane gridPane = new GridPane();





        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> filter2ComboBox= new ComboBox<>();
        filter2ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter2ComboBox.getSelectionModel().selectLast(); // selecting default value

        ComboBox<String> filter3ComboBox = new ComboBox<>();
        filter3ComboBox.setItems(FXCollections.observableArrayList("Suruna Value", "Hurni Level", "Volta "));
        filter3ComboBox.getSelectionModel().selectFirst(); // selecting default value

        /// working with scatter
        NumberAxis xAxis1 = new NumberAxis();
        NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel(filter1ComboBox.getValue());
        yAxis1.setLabel(filter2ComboBox.getValue());


        CustomScatterChart scatterChart = new CustomScatterChart(xAxis1, yAxis1);
        scatterChart.setPrefSize(700, 700);
        scatterChart.setMaxSize(800, 800);
        //scatterChart.setMinHeight(700);

        updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
        



        ///working with x-axis histogram
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
    
     
        LineChart<String, Number> barChart1 = new LineChart<>(xAxis2, yAxis2);
        
        
        barChart1.setPrefSize(850, 700);
        barChart1.setLegendVisible(true);
        barChart1.setLegendSide(Side.RIGHT);
    
        xAxis2.setGapStartAndEnd(false);
        yAxis2.setOpacity(0);
     
     
        GridPane.setMargin(barChart1, new Insets(0, 0, 0, 12));

       

        
        Button btn = new Button();
        btn.setText("Switch axes");
        btn.setOnAction(e -> {
            String value1 =filter1ComboBox.getValue();
            String value2 = filter2ComboBox.getValue();
            filter1ComboBox.setValue(value2);
            filter2ComboBox.setValue(value1);
        });
        filter1ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
            updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
    
        });
        filter2ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
            updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
        });
        filter3ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
        });

        updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
        gridPane.add(barChart1, 1, 0);
        gridPane.add(scatterChart, 1, 1);  
        VBox.getChildren().addAll(filter1ComboBox, filter2ComboBox, filter3ComboBox,btn, gridPane);
        return VBox;


        

    }
    //  method to draw joint plot where BarChart is used to show the distribution of categorocal variable
    private VBox JointPlot_bar() {
        VBox VBox = new VBox();

        GridPane gridPane = new GridPane();

        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> filter2ComboBox= new ComboBox<>();
        filter2ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter2ComboBox.getSelectionModel().selectLast(); // selecting default value

        ComboBox<String> filter3ComboBox = new ComboBox<>();
        filter3ComboBox.setItems(FXCollections.observableArrayList("Suruna Value", "Hurni Level", "Volta "));
        filter3ComboBox.getSelectionModel().selectFirst(); // selecting default value

        /// working with scatter
        NumberAxis xAxis1 = new NumberAxis();
        NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel(filter1ComboBox.getValue());
        yAxis1.setLabel(filter2ComboBox.getValue());


        CustomScatterChart scatterChart = new CustomScatterChart(xAxis1, yAxis1);
        scatterChart.setPrefSize(700, 700);
        scatterChart.setMaxSize(800, 800);
        //scatterChart.setMinHeight(700);

        updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
        



        ///working with x-axis histogram
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
    
     
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis2, yAxis2);
        
        
        barChart1.setPrefSize(850, 700);
        barChart1.setLegendVisible(true);
        barChart1.setLegendSide(Side.RIGHT);
    
        xAxis2.setGapStartAndEnd(false);
        yAxis2.setOpacity(0);
     
     
        GridPane.setMargin(barChart1, new Insets(0, 0, 0, 12));

       

        
        Button btn = new Button();
        btn.setText("Switch axes");
        btn.setOnAction(e -> {
            String value1 =filter1ComboBox.getValue();
            String value2 = filter2ComboBox.getValue();
            filter1ComboBox.setValue(value2);
            filter2ComboBox.setValue(value1);
        });
        filter1ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
            updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
    
        });
        filter2ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
            updateScatter("Current+StudentInfo", scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), false);
        });
        filter3ComboBox.setOnAction(e -> {
            updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
        });

        updateJoint("Current+StudentInfo",scatterChart, barChart1, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter3ComboBox.getValue());
        gridPane.add(barChart1, 1, 0);
        gridPane.add(scatterChart, 1, 1);  
        VBox.getChildren().addAll(filter1ComboBox, filter2ComboBox, filter3ComboBox,btn, gridPane);
        return VBox;


        

    }

    /// method for updating joint plot for join plot with line chart 
    private void updateJoint(String selectedData, CustomScatterChart scatterChart, LineChart<String, Number> barChart1,  String selectedFilter1, String selectedFilter2, String selectedFilter3 ) {
    double upper_b_x = ((NumberAxis) scatterChart.getXAxis()).getUpperBound();
        double lower_b_x = ((NumberAxis) scatterChart.getXAxis()).getLowerBound();
        double upper_b_y = ((NumberAxis) scatterChart.getYAxis()).getUpperBound();
        double lower_b_y = ((NumberAxis) scatterChart.getYAxis()).getLowerBound();

        
        ArrayList<ArrayList<Series<String,Number>>> all_axis_array = DataFunc.getJoint(selectedFilter1, selectedFilter2, selectedFilter3, selectedData);

        ArrayList<String> dataToAdd_x  = new ArrayList<>();
        ArrayList<String> dataToAdd_y  = new ArrayList<>();


        for(int i =(int) lower_b_x; i<=(int) upper_b_x; i++) {
            dataToAdd_x.add(String.valueOf(i));
        }
         for(int i =(int) lower_b_y; i<=(int) upper_b_y; i++) {
            dataToAdd_y.add(String.valueOf(i));
        }
        ((CategoryAxis) barChart1.getXAxis()).setCategories(FXCollections.observableArrayList(dataToAdd_x));
        

        
        

        barChart1.getData().clear();
        
        for(Series<String, Number> seris_one_iter: all_axis_array.get(0)) {
            barChart1.getData().add(seris_one_iter);
        }
        
        
        for (int i = 0; i < barChart1.getData().size(); i++) {
            XYChart.Series<String, Number> series = barChart1.getData().get(i);
            for (XYChart.Data<String, Number> data : series.getData()) {
                // Применение стиля к каждому столбцу в зависимости от какого-либо условия
                Node node = data.getNode();
                switch (i) {
                    case 0:
                        node.getStyleClass().add("bar-color-1");
                        break;
                    case 1:
                        node.getStyleClass().add("bar-color-2");
                        break;
                    case 2:
                        node.getStyleClass().add("bar-color-3");
                        break;
                    case 3:
                        node.getStyleClass().add("bar-color-4");
                        break;
                    case 4:
                        node.getStyleClass().add("bar-color-5");
                        break;
                    
                    // и так далее для других серий или условий
                }
            }
        }
        

        barChart1.layout();


    }

    
    /// method for updating joint plot for join plot with bar chart 
    private void updateJoint(String selectedData, CustomScatterChart scatterChart, BarChart<String, Number> barChart1,  String selectedFilter1, String selectedFilter2, String selectedFilter3 ) {
        double upper_b_x = ((NumberAxis) scatterChart.getXAxis()).getUpperBound();
        double lower_b_x = ((NumberAxis) scatterChart.getXAxis()).getLowerBound();
        double upper_b_y = ((NumberAxis) scatterChart.getYAxis()).getUpperBound();
        double lower_b_y = ((NumberAxis) scatterChart.getYAxis()).getLowerBound();

        
        ArrayList<ArrayList<Series<String,Number>>> all_axis_array = DataFunc.getJoint(selectedFilter1, selectedFilter2, selectedFilter3, selectedData);

        ArrayList<String> dataToAdd_x  = new ArrayList<>();
        ArrayList<String> dataToAdd_y  = new ArrayList<>();


        for(int i =(int) lower_b_x; i<=(int) upper_b_x; i++) {
            dataToAdd_x.add(String.valueOf(i));
        }
         for(int i =(int) lower_b_y; i<=(int) upper_b_y; i++) {
            dataToAdd_y.add(String.valueOf(i));
        }
        ((CategoryAxis) barChart1.getXAxis()).setCategories(FXCollections.observableArrayList(dataToAdd_x));
        

        
        

        barChart1.getData().clear();
        
        for(Series<String, Number> seris_one_iter: all_axis_array.get(0)) {
            barChart1.getData().add(seris_one_iter);
        }
        
        
        for (int i = 0; i < barChart1.getData().size(); i++) {
            XYChart.Series<String, Number> series = barChart1.getData().get(i);
            for (XYChart.Data<String, Number> data : series.getData()) {
                // Применение стиля к каждому столбцу в зависимости от какого-либо условия
                Node node = data.getNode();
                switch (i) {
                    case 0:
                        node.getStyleClass().add("bar-color-1");
                        break;
                    case 1:
                        node.getStyleClass().add("bar-color-2");
                        break;
                    case 2:
                        node.getStyleClass().add("bar-color-3");
                        break;
                    case 3:
                        node.getStyleClass().add("bar-color-4");
                        break;
                    case 4:
                        node.getStyleClass().add("bar-color-5");
                        break;
                    
                    // и так далее для других серий или условий
                }
            }
        }
        

        barChart1.layout();


    }
    // method for updating scatter plot
    private void updateScatter(String selectedData, CustomScatterChart scatterChart, String selectedFilter1, String selectedFilter2, boolean line_bool) {
        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(selectedData)) {
                    right_data = data;
            }
            }

        int course_col_num1 = 0;
        int course_col_num2  = 0;
              
        for(int k =0; k<right_data.columnNames.length; k++) {
            if(right_data.columnNames[k].equals(selectedFilter2)) {
                course_col_num2= k;
            }
            if(right_data.columnNames[k].equals(selectedFilter1)) {
                course_col_num1 = k;
            }
        }
        scatterChart.getData().clear();
        NumberAxis XAxis = (NumberAxis) scatterChart.getXAxis();
        NumberAxis YAxis = (NumberAxis) scatterChart.getYAxis();
        XAxis.autoRangingProperty().set(true);
        YAxis.autoRangingProperty().set(true);
        XAxis.setLabel(selectedFilter1);
        YAxis.setLabel(selectedFilter2);
    
        scatterChart.setAnimated(true);

        
            
        XYChart.Series<Number, Number> series =  DataFunc.getScatter(selectedFilter1, selectedFilter2,  selectedData);
        
        if(selectedData.equals("GraduateGrades")){
            XAxis.autoRangingProperty().set(false);
            XAxis.setUpperBound(11);
            XAxis.setLowerBound(5);
            XAxis.setTickUnit(1);

            YAxis.autoRangingProperty().set(false);
            YAxis.setUpperBound(11);
            YAxis.setLowerBound(5);
            YAxis.setTickUnit(1); 
        } else {
        XAxis.autoRangingProperty().set(false);
        XAxis.setUpperBound(11);
        XAxis.setLowerBound(4);
        XAxis.setTickUnit(1);

        YAxis.autoRangingProperty().set(false);
        YAxis.setUpperBound(11);
        YAxis.setLowerBound(4);
        YAxis.setTickUnit(1); 
        }
        
        double[] series_for_regress_line = Utility.linearRegressionLine(right_data, course_col_num1 , course_col_num2);
        if (line_bool) {
             scatterChart.addLine(series_for_regress_line);
        }
     
        scatterChart.getData().add(series);

        scatterChart.layout(); 

    
}


/// method to graw tables with correlation, cosine similarity and euclidean distance
    public VBox Table_draw() {
        VBox vBox = new VBox();

        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList("Correlation", "Cosine similarity", "Euclidean distance"));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("GraduateGrades", "CurrentGrades"));
        dataSelector.getSelectionModel().selectFirst(); // selecting default value

        TableView tbv = new TableView();
        tbv.setEditable(true);

        updateTable(dataSelector.getValue(), tbv, filter1ComboBox.getValue());
        filter1ComboBox.setOnAction(e -> updateTable(dataSelector.getValue(), tbv, filter1ComboBox.getValue()));
        dataSelector.setOnAction(e -> updateTable(dataSelector.getValue(), tbv, filter1ComboBox.getValue()));



        vBox.getChildren().addAll(filter1ComboBox, dataSelector, tbv);
        return vBox;
      
       
        


    }
    /// method for updating tables with correlation, cosine similarity and euclidean distance
    public void updateTable(String selectedData, TableView tbv, String selectedFilter) {
        tbv.getItems().clear();
        tbv.getColumns().clear();
        tbv.setStyle("-fx-font-size: 10px;"); // Уменьшение до 10px
        tbv.setMinSize(1080, 1080); // Минимальная ширина 200px, Минимальная высота 100px

        Data right_data = new Data();
            for (Data data : dataList) {
                if (data.name.equals(selectedData)) {
                    right_data = data;
            }
            }

    ArrayList<String> all_courses_corr = new ArrayList<>(Arrays.asList("JTE_234", "ATE_003", "TGL_013", "PPL_239", "WDM_974", "GHL_823", "HLU_200", "MON_014", "FEA_907", "LPG_307", "TSO_010", "LDE_009", "JJP_001", "MTE_004", "LUU_003", "LOE_103", "PLO_132", "BKO_800", "SLE_332", "BKO_801", "DSE_003", "DSE_005", "ATE_014", "JTW_004", "ATE_008", "DSE_007", "ATE_214", "JHF_101", "KMO_007", "WOT_104"));
        
        TableColumn<String, CorrelationClass> cl1 = new TableColumn<>("Feauture name"); 
            cl1.setCellValueFactory(new PropertyValueFactory<>("course_name"));
            cl1.setPrefWidth(50);
            tbv.getColumns().add(cl1);
        for(String course: all_courses_corr) {
            TableColumn<CorrelationClass, Double> cl = new TableColumn<>(course); 
            cl.setCellValueFactory(new PropertyValueFactory<>(course));
            if (selectedFilter.equals("Correlation")) {
                cl.setCellFactory(column -> new ColorChangingCell_Corr());
            } else if(selectedFilter.equals("Cosine similarity")) {
                cl.setCellFactory(column -> new ColorChangingCell_COS());
            } else {
                cl.setCellFactory(column -> new ColorChangingCellEuclid());
            }
            
            cl.setPrefWidth(48.5);
            tbv.getColumns().add(cl);
        }
        

        
         
        ArrayList<CorrelationClass> all_corrs = new ArrayList<>();
        
        for(String course: all_courses_corr) {
            CorrelationClass feature_corr = new CorrelationClass(selectedData, course, selectedFilter);
            all_corrs.add(feature_corr);
        }
        
        for (CorrelationClass corr_solo : all_corrs){
            tbv.getItems().addAll(corr_solo);
        }
        
    }
    /// method to draw swarm plot 
    public VBox SwarmPlot() {
        VBox vBox = new VBox();

        ComboBox<String> filter1ComboBox = new ComboBox<>();
        filter1ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter1ComboBox.getSelectionModel().selectFirst(); // selecting default value


        ComboBox<String> filter2ComboBox= new ComboBox<>();
        filter2ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
        filter2ComboBox.getSelectionModel().selectLast(); // selecting default value

        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("GraduateGrades", "CurrentGrades", "Current+StudentInfo"));
        dataSelector.getSelectionModel().selectFirst(); // selecting default value

        Button best_stump = new Button();
        best_stump.setText("Best stump for this course");
        


        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(filter1ComboBox.getValue());
        yAxis.setLabel(filter2ComboBox.getValue());
        
        ScatterChart<Number, Number> swarmChart = new CustomScatterChart(xAxis, yAxis);

        best_stump.setOnAction(event -> {
            updateSwarm(dataSelector.getValue(), swarmChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter2ComboBox, true);
        });
        
        updateSwarm(dataSelector.getValue(), swarmChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter2ComboBox, false);


        filter1ComboBox.setOnAction(e -> updateSwarm(dataSelector.getValue(), swarmChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter2ComboBox, false));
        filter2ComboBox.setOnAction(e -> updateSwarm(dataSelector.getValue(),swarmChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter2ComboBox, false));
        dataSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateFilter2(filter2ComboBox, newValue);
            updateSwarm(dataSelector.getValue(), swarmChart, filter1ComboBox.getValue(), filter2ComboBox.getValue(), filter2ComboBox, false);
        });
        
            
            
        vBox.getChildren().addAll(dataSelector, filter1ComboBox,filter2ComboBox, best_stump, swarmChart);
        
        return vBox;
    }
    // method to draw our table with predictions from step 4
    public VBox TablePred() {
        VBox vBox = new VBox();


        TableView tbv = new TableView();
        tbv.setEditable(true);

     
    

        TableColumn<String, PredictTable> cl1 = new TableColumn<>("Weighted euclid distance"); 
        cl1.setCellValueFactory(new PropertyValueFactory<>("name"));
        cl1.setPrefWidth(200);
        TableColumn<String, PredictTable> cl2 = new TableColumn<>("ATE-003"); 
        cl2.setCellValueFactory(new PropertyValueFactory<>("ATE_003"));
        cl2.setPrefWidth(200);
        TableColumn<String, PredictTable> cl3 = new TableColumn<>("MON-014"); 
        cl3.setCellValueFactory(new PropertyValueFactory<>("MON_014"));
        cl3.setPrefWidth(200);
        TableColumn<String, PredictTable> cl4 = new TableColumn<>("BKO-800"); 
        cl4.setCellValueFactory(new PropertyValueFactory<>("BKO_800"));
        cl4.setPrefWidth(200);

        tbv.getColumns().addAll(cl1, cl2, cl3, cl4);
        
        ArrayList<PredictTable> all_pred_in_table = new ArrayList<>();
        all_pred_in_table.add(new PredictTable(0.039976, 0.046447, 0.0307771, "10 best splits"));
        all_pred_in_table.add(new PredictTable(0.040746, 0.047967, 0.030994, "3 best splits"));
        all_pred_in_table.add(new PredictTable(0.028359, 0.031919, 0.023208, "1 best splits"));
        

        for(PredictTable pred_solo : all_pred_in_table) {
            tbv.getItems().addAll(pred_solo);
        }
        

        vBox.getChildren().addAll(tbv);
        return vBox;
    
       
        


    

    }

    //method for updating filter  in a warm plot (change possible variables when diffrent data is selected)
    public void updateFilter2(ComboBox filter2ComboBox, String selectedData) {
        if(selectedData.equals("Current+StudentInfo")) {
            List<String> newItems = Arrays.asList("Suruna Value", "Hurni Level", "Volta ", "Lal Count");
            filter2ComboBox.setItems(FXCollections.observableArrayList(newItems));
            filter2ComboBox.getSelectionModel().selectFirst();
        } else {
            filter2ComboBox.setItems(FXCollections.observableArrayList(DataFunc.all_courses));
            filter2ComboBox.getSelectionModel().selectFirst();
        }
    }

    // method for updating swarm plot when button is pressed
    public void updateSwarm(String selectedData, ScatterChart<Number, Number> scatterChart, String selectedFilter1, String selectedFilter2, ComboBox filter2ComboBox, boolean best) {
        
        Data right_data = new Data();
                for (Data data : dataList) {
                    if (data.name.equals(selectedData)) {
                        right_data = data;
                }
                }

            int course_col_num1 = 0;
            int course_col_num2  = 0;
                    
            for(int k =0; k<right_data.columnNames.length; k++) {
                if(right_data.columnNames[k].equals(selectedFilter2)) {
                    course_col_num2= k;
                }
                if(right_data.columnNames[k].equals(selectedFilter1)) {
                    course_col_num1 = k;
                }
            }
            
            scatterChart.getData().clear();
            NumberAxis XAxis = (NumberAxis) scatterChart.getXAxis();
            NumberAxis YAxis = (NumberAxis) scatterChart.getYAxis();
            XAxis.autoRangingProperty().set(true);
            YAxis.autoRangingProperty().set(true);
            XAxis.setLabel(selectedFilter2);
            YAxis.setLabel(selectedFilter1);
            scatterChart.setAnimated(true);
            XYChart.Series<Number, Number> series =  new XYChart.Series<>();
            DS ds;
            if(!best) {
            ds = DecisionStumpFactory.createDecisionStumpBasic(right_data.data, selectedFilter1, selectedFilter2, right_data.columnNames);
            series =  DataFunc.getSwarm(ds.get_users_for_plots(), ds.get_property_name());
            } else {
                ArrayList<String> forbidd_courses = new ArrayList<>();
                ds = DecisionStumpFactory.find_best_stump(right_data.data, selectedFilter1, right_data.columnNames, forbidd_courses);
                series =  DataFunc.getSwarm(ds.get_users_for_plots(), ds.get_property_name());
                XAxis.setLabel(ds.get_property_name());
            }



        
            YAxis.autoRangingProperty().set(false);
            YAxis.setUpperBound(11);
            YAxis.setLowerBound(4);
            YAxis.setTickUnit(1); 
            

            XAxis.autoRangingProperty().set(false);
            XAxis.setUpperBound(ds.get_users_for_plots().size()+2);
            XAxis.setLowerBound(1);
            XAxis.setTickUnit(1);
            
            if(ds instanceof DSCat) {
                XAxis.setTickLabelFormatter(new CatTickLabelFormatter(XAxis, right_data.columnNames, course_col_num2, ds));
            } else {
                XAxis.setTickLabelFormatter(new NumTickLabelFormatter(XAxis, right_data.columnNames, course_col_num1, ds));
            }
           
            
            
    
            
            scatterChart.getData().add(series);

            scatterChart.layout(); 


        }


    // main method
    public static void main(String[] args) {
        dataInit();
        launch(args);
    }


    /// initialize our basic datasets to use them in the programm
    private static void dataInit(){
    
        ///Statically import data
        Data data_current = new Data("data/CurrentGrades.csv");
        data_current.name = "CurrentGrades";
        dataList.add(data_current);

        Data data_graduate = new Data("data/bugData.csv");
        data_graduate.name = "GraduateGrades";
        dataList.add(data_graduate);

        
        Data data_student_info = new Data("data/StudentInfo.csv");
        data_student_info.name = "Student Info";
        dataList.add(data_student_info);

        Data combinedData = new Data(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv"));
        combinedData.name = "Current+StudentInfo";
        dataList.add(combinedData);
    }
}
