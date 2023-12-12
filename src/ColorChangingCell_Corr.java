import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ColorChangingCell_Corr extends TableCell<CorrelationClass, Double> {
    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setStyle("");

        // Установка текста и стиля только для непустых ячеек
        if (!empty && item != null) {
            // Устанавливаем текст ячейки
            
            setText(item.toString());

        if (empty || item == null) {
            setText(null);
            setStyle("");
        } else {
            // Условие для изменения цвета ячейки
            if (item>=0.99) {
                setTextFill(Color.BLACK); // Цвет текста
                setStyle("-fx-font-size: 14px; -fx-background-color: #D3D3D3"); // Цвет фона
                
            }
            else if (item>=0.8) {
                setTextFill(Color.BLACK); // Цвет текста
                setStyle("-fx-font-size: 14px; -fx-background-color: red"); // Цвет фона
            } else if (item>=0.7) {
                setStyle("-fx-font-size: 14px; -fx-background-color: orange");
            } else if (item>=0.5) {
                setStyle("-fx-font-size: 14px; -fx-background-color: yellow"); 
            } else if (item<=0.2) {
                setStyle("-fx-font-size: 14px; -fx-background-color: #99EEFF");
            } else {
                setTextFill(Color.BLACK);
                setStyle("-fx-font-size: 14px; -fx-background-color: white");
            }
            setItem(item);
        }
    }
}
}
