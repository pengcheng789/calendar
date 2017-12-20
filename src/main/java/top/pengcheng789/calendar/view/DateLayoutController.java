package top.pengcheng789.calendar.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import top.pengcheng789.calendar.MainApp;
import top.pengcheng789.calendar.model.Date;
import top.pengcheng789.calendar.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * @author pen
 */
public class DateLayoutController {

    private final static int GRID_PANE_ROWS = 7;
    private final static int GRID_PANE_COLUMNS = 7;

    private final static String LABEL_LUNAR_STYLE = "-fx-font-size: 15px;";
    private final static String LABEL_FIX_MONTH_STYLE = "-fx-text-fill: #606060;";
    private final static String BORDER_PANE_BACKGROUND_COLOR = "-fx-background-color: #202020;";

    @FXML private Label yearLabel;
    @FXML private Label monthLabel;
    @FXML private GridPane gridPane;

    private MainApp mainApp;
    private LocalDate localDate;

    /**
     * 设置mainApp成员变量。
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        handleResetAction();
    }

    /**
     * 在界面上显示某年月的信息。
     */
    private void showMonth(LocalDate localDate) {
        this.localDate = localDate;
        showYearAndMonth(localDate);

        List<Date> dateList = DateUtil.getFixDatesOfMonth(localDate);
        addDateListToPane(dateList);
    }

    /**
     * 在界面上显示当前月份。
     */
    @FXML
    private void handleResetAction() {
        showMonth(LocalDate.now());
    }

    /**
     * 在界面上显示上一个月份。
     */
    @FXML
    private void handleLastMonthAction() {
        if (localDate.getMonth().equals(Month.JANUARY)) {
            localDate = LocalDate.of(localDate.getYear() - 1, Month.DECEMBER, 1);
        } else {
            localDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue() - 1, 1);
        }

        showYearAndMonth(localDate);
        List<Date> dateList = DateUtil.getFixDatesOfMonth(localDate);
        addDateListToPane(dateList);
    }

    /**
     * 在界面上显示下一个月份。
     */
    @FXML
    private void handleNextMonthAction() {
        if (localDate.getMonth().equals(Month.DECEMBER)) {
            localDate = LocalDate.of(localDate.getYear() + 1, Month.JANUARY, 1);
        } else {
            localDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + 1, 1);
        }

        showYearAndMonth(localDate);
        List<Date> dateList = DateUtil.getFixDatesOfMonth(localDate);
        addDateListToPane(dateList);
    }

    /**
     * 处理点击年份动作，弹出年份选择界面。
     */
    @FXML
    private void handleClickedYearAction() {
        int selectedYear = mainApp.showYearSelectorDialog();
        showMonth(LocalDate.of(selectedYear, localDate.getMonth(), localDate.getDayOfMonth()));
    }

    /**
     * 处理点击月份动作，弹出年份选择界面。
     */
    @FXML
    private void handleClickedMonthAction() {
        int selectedMonth = mainApp.showMonthSelectorDialog();
        showMonth(LocalDate.of(localDate.getYear(), selectedMonth, localDate.getDayOfMonth()));
    }

    /**
     * 显示界面中的年份和月份
     */
    private void showYearAndMonth(LocalDate localDate) {
        yearLabel.setText(String.valueOf(localDate.getYear()));
        monthLabel.setText((localDate.getMonthValue() > 10 ? "" : "0")
                + String.valueOf(localDate.getMonthValue()));
    }

    /**
     * 将日期添加进界面中。
     */
    private void addDateListToPane(List<Date> dateList) {
        removeDateNodes();
        int n = 0;

        for (int i = 1; i < GRID_PANE_ROWS; i++) {
            for (int j = 0; j < GRID_PANE_COLUMNS && n < dateList.size(); j++) {
                gridPane.add(getDateBorderPane(dateList.get(n++)), j, i);
            }
        }
    }

    /**
     * 清除界面的日期（使用空字符Label进行替代）。
     */
    private void removeDateNodes() {
        gridPane.getChildren().remove(7, gridPane.getChildren().size());
    }

    /**
     * 根据日期生成阳历和农历的布局。
     */
    private BorderPane getDateBorderPane(Date date) {
        BorderPane borderPane = new BorderPane();

        Label dateLabel = new Label(String.valueOf(date.getDate()));
        dateLabel.setAlignment(Pos.CENTER);
        borderPane.setCenter(dateLabel);

        Label lunarDateLabel = new Label(date.getLunarDate());
        lunarDateLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        lunarDateLabel.setMaxHeight(Double.POSITIVE_INFINITY);
        lunarDateLabel.setAlignment(Pos.CENTER);
        lunarDateLabel.setStyle(LABEL_LUNAR_STYLE);
        BorderPane.setMargin(lunarDateLabel, new Insets(0, 0, 10, 0));
        borderPane.setBottom(lunarDateLabel);

        if (!date.getMonth().equals(localDate.getMonth())) {
            dateLabel.setStyle(LABEL_FIX_MONTH_STYLE);
            lunarDateLabel.setStyle(lunarDateLabel.getStyle() + LABEL_FIX_MONTH_STYLE);
        } else if (date.getLocalDate().equals(LocalDate.now())) {
            borderPane.setStyle(BORDER_PANE_BACKGROUND_COLOR);
        }

        return borderPane;
    }
}
