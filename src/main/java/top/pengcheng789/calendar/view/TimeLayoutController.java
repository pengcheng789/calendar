package top.pengcheng789.calendar.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import top.pengcheng789.calendar.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;

/**
 * @author pen
 */
public class TimeLayoutController {

    @FXML private Label titleTimeLabel;
    @FXML private Label beijingTimeLabel;
    @FXML private Label londonTimeLabel;
    @FXML private Label newYorkTimeLabel;
    @FXML private Label sanFranciscoTimeLabel;
    @FXML private Label hawaiiTimeLabel;

    private ExecutorService singleThreadPool;

    /**
     * 设置singleThreadPool变量。
     */
    public void setSingleThreadPool(ExecutorService singleThreadPool) {
        this.singleThreadPool = singleThreadPool;
    }

    /**
     * 更新UI的时间。
     */
    public void updateTime() {
        singleThreadPool.execute(() -> {
            while(true) {
                Platform.runLater(() -> {
                    titleTimeLabel.setText(DateTimeUtil.toStandardString(LocalTime.now()));

                    beijingTimeLabel.setText(
                            DateTimeUtil.toStandardString(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))));
                    londonTimeLabel.setText(
                            DateTimeUtil.toStandardString(LocalDateTime.now(ZoneId.of("Europe/London"))));
                    newYorkTimeLabel.setText(
                            DateTimeUtil.toStandardString(LocalDateTime.now(ZoneId.of("America/New_York"))));
                    sanFranciscoTimeLabel.setText(
                            DateTimeUtil.toStandardString(LocalDateTime.now(ZoneId.of("America/Los_Angeles"))));
                    hawaiiTimeLabel.setText(
                            DateTimeUtil.toStandardString(LocalDateTime.now(ZoneId.of("Pacific/Honolulu"))));
                });
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    System.out.println("时间显示异常！");
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
