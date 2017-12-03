package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * @author pen
 */
public class MonthSelectorController {

    private final static String STANDARD_LABEL_STYLE = "-fx-font-size: 20px;" + "-fx-padding: 25px;";
    private final static String MOUSE_HOVER_LABEL_STYLE = "-fx-text-fill: #303030;" + "-fx-background-color: #E0E0E0;";
    private final static String MOUSE_EXIT_LABEL_STYLE = "-fx-text-fill: #606060;" + "-fx-background-color: #303030;";

    private final static int GRID_PANE_COLUMNS = 4;
    private final static int GRID_PANE_ROWS = 3;

    @FXML private GridPane pane;

    private Stage monthSelectorStage;
    private int selectedMonth = LocalDate.now().getMonthValue();

    /**
     * 设置yearSelectorStage成员变量。
     */
    public void setMonthSelectorStage(Stage stage) {
        this.monthSelectorStage = stage;
    }

    /**
     * 返回选择的年份。
     */
    public int getSelectedMonth() {
        return selectedMonth;
    }

    @FXML
    private void initialize() {
        int i = 1;

        for (int j = 0; j < GRID_PANE_ROWS; j++) {
            for (int k = 0; k < GRID_PANE_COLUMNS; k++) {
                Label label = new Label(String.valueOf(i++));
                label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE);
                label.setOnMouseEntered(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_HOVER_LABEL_STYLE));
                label.setOnMouseExited(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE));
                label.setOnMouseClicked(e -> {
                    selectedMonth = Integer.valueOf(((Label) e.getSource()).getText());
                    monthSelectorStage.close();
                });

                pane.add(label, k, j);
            }
        }
    }
}
