package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import top.pengcheng789.calendar.MainApp;

/**
 * @author pen
 */
public class RootLayoutController {

    private MainApp mainApp;

    @FXML private MenuItem clockMenuItem;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 处理闹钟功能的操作，即显示闹钟功能界面。
     */
    @FXML
    private void handleClockMenuItemClicked() {
        mainApp.showClockFeatureDialog();
    }
}
