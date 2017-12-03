package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.stream.IntStream;

/**
 * @author pen
 */
public class MinuteSelectorController {

    private final static String STANDARD_LABEL_STYLE = "-fx-font-size: 20px;" + "-fx-pref-width: 66px;"
            + "-fx-padding: 8px;" + "-fx-alignment: center;";
    private final static String MOUSE_HOVER_LABEL_STYLE = "-fx-text-fill: #303030;" + "-fx-background-color: #E0E0E0;";
    private final static String MOUSE_EXIT_LABEL_STYLE = "-fx-text-fill: #606060;" + "-fx-background-color: #303030;";

    @FXML private FlowPane pane;

    private int selectedMinute;
    private Stage minuteSelectorStage;

    public int getSelectedMinute() {
        return selectedMinute;
    }

    public void setMinuteSelectorStage(Stage minuteSelectorStage) {
        this.minuteSelectorStage = minuteSelectorStage;
    }

    @FXML
    private void initialize() {
        IntStream.rangeClosed(0, 59).forEach(i -> {
            Label label = new Label((i < 10 ? "0" : "" )+ i);
            label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE);
            label.setOnMouseEntered(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_HOVER_LABEL_STYLE));
            label.setOnMouseExited(e -> label.setStyle(STANDARD_LABEL_STYLE + MOUSE_EXIT_LABEL_STYLE));
            label.setOnMouseClicked(e -> {
                selectedMinute = Integer.valueOf(((Label)e.getSource()).getText());
                minuteSelectorStage.close();
            });

            pane.getChildren().add(label);
        });
    }
}
