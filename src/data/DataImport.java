package data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;

/**
 * Data Import Utilities for the user interface
 */
public class DataImport {

    /**
     * Reads the data at the relative paths and adds them to the list.
     * @param dataMenu
     */
    public static void dataInit(Menu dataMenu){
        ArrayList<String> fileNames = new ArrayList<String>();
        
        ///Statically import data
        Data data_current = new Data("data/CurrentGrades.csv");
        data_current.name = "Current Grades";
        Main.dataList.add(data_current);
        fileNames.add(data_current.name);

        Data data_graduate = new Data("data/GraduateGrades.csv");
        data_graduate.name = "Graduate Grades";
        Main.dataList.add(data_graduate);
        fileNames.add(data_graduate.name);

        Data data_aggregate = new Data("data/bugData.csv");
        data_aggregate.name = "Aggregate Grades";
        Main.dataList.add(data_aggregate);
        fileNames.add(data_aggregate.name);
        
        Data data_student_info = new Data("data/StudentInfo.csv");
        data_student_info.name = "Student Info";
        Main.dataList.add(data_student_info);
        fileNames.add(data_student_info.name);

        Main.fileList = FXCollections.observableArrayList(fileNames);
        
        ///Add data to Data Menu
        MenuItem cDataMenu = new MenuItem("Current Grades");
        MenuItem gDataMenu = new MenuItem("Graduate Grades");
        MenuItem aDataMenu = new MenuItem("Aggregate Grades");
        MenuItem iDataMenu = new MenuItem("Student Info");

        dataMenu.getItems().addAll(cDataMenu, gDataMenu, aDataMenu, iDataMenu);


    }

    /**
     * Creates a menu that facilitates data import functionality to the program.
     * To do: Work out adding new data.
     * @param stage
     * @return
     */
    public static Menu dataMenu(Stage stage){
        Menu dataMenu = new Menu("Data");
        //MenuItem addData = new MenuItem("add data");
        dataInit(dataMenu);
        
        //dataMenu.getItems().addAll(addData);
        return dataMenu;
    }

    /**
     * Creates a menu used to import the data from a .csv file into the program.
     * @param stage
     */
    public static boolean dataImport(Stage stage){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files", "*.csv");
        fileChooser.setTitle("Import Data");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Main.dataList.add(new Data(file));
            return true;
            //openFile(file);
        } else {
            return false;
        }
    }

    /**
     * Doesn't work yet. Removes last item every time.
     * @param stage
     * @param dataMenu
     * @param addData
     */
    public static void addData(Stage stage, Menu dataMenu, MenuItem addData){
        addData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                if(dataImport(stage)){
                    int dC = Main.dataCount++;
                    Menu data = new Menu("data" + dC);
                    MenuItem rmData = new MenuItem("remove data");
                    rmData.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent e){
                            dataMenu.getItems().remove(data);
                            Main.dataList.remove(Main.dataCount - Main.removeCount++);
                            //System.out.println(dataList.size());
                        }
                    });
                    data.getItems().add(rmData);
                    dataMenu.getItems().add(data);
                    //System.out.println(dataList.size());
                }
            }
        });
    }
}
