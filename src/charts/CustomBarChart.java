package charts;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class CustomBarChart extends BarChart<String, Number> {

    public CustomBarChart(CategoryAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (getLegend() != null) {
            // Положение легенды по умолчанию - снизу. Перемещаем её вправо.
            getLegend().setVisible(false); // Скрываем встроенную легенду

            // Если у вас есть контейнер, в который вы хотите поместить легенду, можно сделать это здесь.
            // Например, если у вас есть StackPane для легенды, добавьте легенду в этот StackPane.
            StackPane legendContainer = new StackPane(getLegend());
            legendContainer.setPrefWidth(USE_COMPUTED_SIZE);
            legendContainer.setLayoutX(getWidth() - legendContainer.prefWidth(-1)); // Позиционирование справа
            legendContainer.setLayoutY(5); // Отступ сверху

            getChildren().add(legendContainer);
        }
    }
}
