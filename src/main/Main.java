package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import java.util.stream.Collectors;

import javax.xml.stream.EventFilter;

import charts.CustomScatterChart;
import charts.HistogramChart;
import charts.PieChartPlot;
import charts.ScatterPlotChart;
import data.AggregateData;
import data.Data;
import data.DataFunc;
import data.DataImport;

import java.awt.Desktop;
import java.awt.Label;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
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
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.Utility;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import java.util.Arrays;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Random;
import charts.PieChartPlot;

public class Main extends Application {

    //private final Desktop desktop = Desktop.getDesktop();

    public static int dataCount = 0; //used to count Data objects
    public static int removeCount = 1; //number of removed Data objects offset by 1
    public static ArrayList<Data> dataList = new ArrayList<Data>();
    public static ObservableList<String> fileList;
    public static ObservableList<String> columnList;

    final static int BUTTON_COUNT = 10;
    final static String[] BUTTON_TEXTS = {"Visualization Sandbox", "Step 1 Findings", "Bar Chart", "Histogram", "Pie Chart", "Scatter Plot", "Swarm Plot", "Step 2 Findings", "Steps 3 & 4 Findings", "PieChartsCum"};
    
    private VBox PieChart(Stage stage) {
        VBox vBox = new VBox();

       

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.setItems(FXCollections.observableArrayList("GPA greater than 7.5", "No grades lower than 7"));
        filterComboBox.getSelectionModel().selectFirst();


        ComboBox<String> dataSelector = new ComboBox<>();
        dataSelector.setItems(FXCollections.observableArrayList("CurrentGrades", "GraduateGrades"));
        dataSelector.setValue("GraduateGrades"); 
        // Creating a Pie chart

        PieChart pieChart = new PieChart();


        filterComboBox.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue()));
        dataSelector.setOnAction(e -> updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue()));





        updatePieChart(dataSelector.getValue(), pieChart, filterComboBox.getValue());
        
        vBox.getChildren().addAll(dataSelector, filterComboBox, pieChart);
        return vBox;


    }
    private void updatePieChart(String selectedData, PieChart PieChart, String selectedFilter) {
        PieChart.getData().clear(); // Очистка предыдущих данных
        ArrayList<Integer> cum_students = DataFunc.cum(selectedData, selectedFilter);
        int all_students_length = DataFunc.data_size(selectedData);
        PieChart.Data slice1 = new PieChart.Data("Cum-Laude", cum_students.size());
        PieChart.Data slice2 = new PieChart.Data("Others", all_students_length-cum_students.size());
        PieChart.getData().add(slice1);
        PieChart.getData().add(slice2);
        PieChart.setLabelsVisible(true);
    }
        
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
        BorderPane borderPane;

        
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
            return "unknown";    }
}



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
        
        updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue());


        filter1ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));
        filter2ComboBox.setOnAction(e -> updateScatter(dataSelector.getValue(),scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));
        dataSelector.setOnAction(e -> updateScatter(dataSelector.getValue(), scatterChart, filter1ComboBox.getValue(), filter2ComboBox.getValue()));
        
        vBox.getChildren().addAll(dataSelector, filter1ComboBox,filter2ComboBox, scatterChart);
        
        return vBox;
    } 
    @Override
    public void start(Stage stage) {

        Image icon = new Image("media/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Data visualization");

        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press ESC when you get bored");
        stage.setWidth(1200);
        stage.setHeight(800); 
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
                        root.setCenter(Findings.NumberOfNGsCourses(dataList.get(0)));
                        stage.show();
                        break;
                    case 2:
                        root.setCenter(Findings.NumberOfNGsStudents(dataList.get(0)));
                        stage.show();
                        break;
                    case 3:
                        VBox vBox2 = Histogram(stage);
                        root.setCenter(vBox2);
                        stage.show();
                        break;
                    case 4:
                        root.setCenter(Findings.NumberOfNGsCoursesSorted());
                        stage.show();
                        break;
                    case 5:
                        root.setCenter(Findings.gradeAveragePerPropertyValue(new AggregateData("data/CurrentGrades.csv", "data/StudentInfo.csv")));
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
                        VBox vBox1 = PieChart(stage);
                        root.setCenter(vBox1);
                        stage.show();
                        break;
                    case 10: 
                        root.setCenter(startScreen);
                        stage.show();
                        break;
                    default:
                }
                for(int j = 0; j < buttons.length; j++)
                    if(j!=i) buttons[j].setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
            }
        };
        for(Button b : buttons) b.setOnAction(sideMenuHandler);
        sideMenu.getChildren().addAll(buttons);
        //add Side Menu to root
        root.setLeft(sideMenu);
        ///End Side Menu
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void openPieChartWindow(Stage stage, Scene scene) {
        PieChartPlot pieChart = new PieChartPlot(scene);
        pieChart.start(stage); 
    }
    private void updateScatter(String selectedData, CustomScatterChart scatterChart, String selectedFilter1, String selectedFilter2) {
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
    
        scatterChart.setAnimated(false);

        
            
        
    
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
        scatterChart.addLine(series_for_regress_line);
        
        scatterChart.getData().add(series);

        scatterChart.layout(); 

    
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
