package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.stream.IntStream;

/**
 * @author pen
 */
public class YearSelectorController {

    private final static String STANDARD_LABEL_STYLE = "-fx-font-size: 20px;" + "-fx-padding: 12px;";
    private final static String MOUSE_HOVER_LABEL_STYLE = "-fx-text-fill: #303030;" + "-fx-background-color: #E0E0E0;";
    private final static String MOUSE_EXIT_LABEL_STYLE = "-fx-text-fill: #606060;" + "-fx-background-color: #303030;";

    @FXML private FlowPane flowPane;

    private Stage yearSelectorStage;
    private int selectedYear = LocalDate.now().getYear();

    /**
     * 设置yearSelectorStage成员变量。
     */
    public void setYearSelectorStage(Stage stage) {
        this.yearSelectorStage = stage;
    }

    /**
     * 返回选择的年份。
     */
    public int getSelectedYear() {
        return selectedYear;
    }

    @FXML
    private void initialize() {
        IntStream.rangeClosed(1900, 2500).forEach(i -> {
            Label label = new Label(String.valueOf(i));
            label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE);
            label.setOnMouseEntered(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_HOVER_LABEL_STYLE));
            label.setOnMouseExited(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE));
            label.setOnMouseClicked(e -> {
                selectedYear = Integer.valueOf(((Label)e.getSource()).getText());
                yearSelectorStage.close();
            });

            flowPane.getChildren().add(label);
        });
    }
}
