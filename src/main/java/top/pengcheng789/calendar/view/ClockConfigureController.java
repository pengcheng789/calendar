package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import top.pengcheng789.calendar.model.Clock;
import top.pengcheng789.calendar.util.ClockUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author pen
 */
public class ClockConfigureController {

    private static final int GRID_PANEL_ROWS = 10;
    private final static String CLOCK_DATA_FILE_PATH = "/clock/clock.xml";

    private static final String LABEL_CLOCK_STYLE = "-fx-font-size: 25px;"
            + "-fx-padding: 15px;";

    @FXML private Label hourLabel;
    @FXML private Label minuteLabel;
    @FXML private Label countLabel;
    @FXML private GridPane gridPane;

    private volatile List<Clock> clockList;
    private Stage clockConfigureViewStage;

    public void setClockList(List<Clock> clockList) {
        this.clockList = clockList;
    }

    public void setClockConfigureViewStage(Stage clockConfigureViewStage) {
        this.clockConfigureViewStage = clockConfigureViewStage;
    }

    /**
     * 显示闹钟列表
     */
    public void updateClockView() {
        removeClockNode();

        // 加1是因为clockList存放了一个占位用的无效的闹钟。
        int validNum = clockList.size() + 1 > GRID_PANEL_ROWS ? GRID_PANEL_ROWS : clockList.size();

        countLabel.setText(validNum - 1 + "/10");

        for (int i = 1; i < validNum; i++) {
            Clock clock = clockList.get(i);

            Label clockLabel = new Label(clockList.get(i).toString());
            clockLabel.setStyle(LABEL_CLOCK_STYLE);

            Label deleteLabel = new Label("✖");
            deleteLabel.setOnMouseClicked(e -> {
                clockList.remove(clock);
                updateClockView();
                try {
                    ClockUtil.saveClockDataToFile(
                            new File(getClass().getResource(CLOCK_DATA_FILE_PATH).toURI()), clockList);
                } catch (Exception exception) {
                    System.out.println("保存闹钟列表失败！");
                    throw new RuntimeException(exception);
                }
            });
            deleteLabel.setStyle(LABEL_CLOCK_STYLE);

            HBox hBox = new HBox();
            hBox.getChildren().add(clockLabel);
            hBox.getChildren().add(deleteLabel);
            gridPane.add(hBox, 0, i-1);
        }
    }

    /**
     * 处理点击时钟的动作。
     */
    @FXML
    private void handleClickHourAction() {
        int selectedHour = showHourSelectorDialog();
        hourLabel.setText((selectedHour < 10 ? "0" : "" ) + selectedHour);
    }

    /**
     * 处理点击分钟的动作。
     */
    @FXML
    private void handleClickMinuteAction() {
        int selectedMinute = showMinuteSelectorDialog();
        minuteLabel.setText((selectedMinute < 10 ? "0" : "" ) + selectedMinute);
    }

    /**
     * 处理添加闹钟的动作。
     */
    @FXML
    private void handleAddAction() {
        Clock clock = new Clock(Integer.valueOf(hourLabel.getText()),
                Integer.valueOf(minuteLabel.getText()));

        boolean flag = false;
        for (Clock c : clockList) {
            if (c.equals(clock)) {
                flag = true;
                break;
            }
        }

        // 加1是因为clockList存放了一个占位用的无效的闹钟。
        if (!flag && clockList.size() + 1 < GRID_PANEL_ROWS) {
            clockList.add(clock);
        }

        updateClockView();
        try {
            ClockUtil.saveClockDataToFile(new File(getClass().getResource(CLOCK_DATA_FILE_PATH).toURI()), clockList);
        } catch (URISyntaxException e) {
            System.out.println("保存闹钟列表失败！");
            throw new RuntimeException(e);
        }
    }

    /**
     * 清除GridPane的子节点（使用空字符串的Label取代）。
     */
    private void removeClockNode() {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
    }

    /**
     * 渲染“闹钟选择时钟”视图
     */
    private int showHourSelectorDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/HourSelectorView.fxml"));

        BorderPane borderPane;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            System.out.println("加载HourSelectorView.fxml失败");
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("选择时钟");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(clockConfigureViewStage);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setResizable(false);

        HourSelectorController controller = loader.getController();
        controller.setHourSelectorStage(stage);

        stage.showAndWait();

        return controller.getSelectedHour();
    }

    /**
     * 渲染“闹钟选择分钟”视图
     */
    private int showMinuteSelectorDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/MinuteSelectorView.fxml"));

        BorderPane borderPane;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            System.out.println("加载MinuteSelectorView.fxml失败");
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("选择分钟");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(clockConfigureViewStage);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setResizable(false);

        MinuteSelectorController controller = loader.getController();
        controller.setMinuteSelectorStage(stage);

        stage.showAndWait();

        return controller.getSelectedMinute();
    }
}
