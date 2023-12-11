import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class ColorChangingCell_COS extends TableCell<CorrelationClass, Double> {
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
            if (item>0.99999) {
                setTextFill(Color.BLACK); // Цвет текста
                setStyle("-fx-font-size: 14px; -fx-background-color: #D3D3D3"); // Цвет фона
            
            } else if(item>=0.99) {
                setTextFill(Color.BLACK); // Цвет текста
                setStyle("-fx-font-size: 14px; -fx-background-color: orange"); // Цвет фона
            }
            else {
                setTextFill(Color.BLACK);
                setStyle("-fx-font-size: 14px; -fx-background-color: white");
            }
            setItem(item);
        }
    }
}
}
    

