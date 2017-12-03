package top.pengcheng789.calendar;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import top.pengcheng789.calendar.model.Clock;
import top.pengcheng789.calendar.thread.ClockThread;
import top.pengcheng789.calendar.util.ClockUtil;
import top.pengcheng789.calendar.view.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author pen
 */
public class MainApp extends Application {

    private final static String CLOCK_DATA_FILE_PATH = "/clock/clock.xml";

    private Stage primaryStage;
    private BorderPane rootLayout;
    private volatile List<Clock> clockList;

    private ThreadFactory threadFactory;
    private ExecutorService singleThreadPool;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("万年历");
        primaryStage.setResizable(false);

        // 读取闹钟列表
        List<Clock> clockList;
        try {
            clockList = ClockUtil.loadClockDataFromFile(
                    new File(getClass().getResource(CLOCK_DATA_FILE_PATH).toURI()));
        } catch (Exception e) {
            System.out.println("加载闹钟列表异常！");
            throw new RuntimeException(e);
        }
        this.clockList = clockList == null ? new ArrayList<>() : clockList;

        // 线程池配置
        threadFactory = new ThreadFactoryBuilder().setNameFormat("calendar-pool-%d").build();
        singleThreadPool = new ThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

        // 启动闹钟线程进行时间监听。
        singleThreadPool.execute(new ClockThread(this.clockList));

        initRootLayout();
        initDateLayout();
        initTimeLayout();
    }

    @Override
    public void stop() {
        singleThreadPool.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 渲染“年份选择”视图
     */
    public int showYearSelectorDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/YearSelectorView.fxml"));

        BorderPane borderPane;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            System.out.println("加载YearSelectorView.fxml失败");
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("选择年份");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setResizable(false);

        YearSelectorController controller = loader.getController();
        controller.setYearSelectorStage(stage);

        stage.showAndWait();

        return controller.getSelectedYear();
    }

    /**
     * 渲染“月份选择”视图
     */
    public int showMonthSelectorDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/MonthSelectorView.fxml"));

        BorderPane borderPane;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            System.out.println("加载MonthSelectorView.fxml失败");
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("选择月份");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setResizable(false);

        MonthSelectorController controller = loader.getController();
        controller.setMonthSelectorStage(stage);

        stage.showAndWait();

        return controller.getSelectedMonth();
    }

    /**
     * 渲染“闹钟功能”视图
     */
    public void showClockFeatureDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/ClockConfigureView.fxml"));

        BorderPane borderPane;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            System.out.println("加载ClockConfigureView.fxml失败");
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("闹钟设置");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setResizable(false);

        ClockConfigureController controller = loader.getController();
        controller.setClockList(clockList);
        controller.setClockConfigureViewStage(stage);
        controller.updateClockView();

        stage.showAndWait();
    }

    /**
     * 初始化根界面
     */
    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/RootLayout.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.out.println("加载RootLayout.fxml失败！");
            throw new RuntimeException(e);
        }

        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 初始化日期界面
     */
    private void initDateLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/DateLayout.fxml"));

        BorderPane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println("加载DateLayout.fxml失败！");
            throw new RuntimeException(e);
        }

        DateLayoutController controller = loader.getController();
        controller.setMainApp(this);
        rootLayout.setCenter(pane);
    }

    /**
     * 初始化时间界面
     */
    private void initTimeLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/asset/fxml/TimeLayout.fxml"));

        AnchorPane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println("加载TimeLayout.fxml失败！");
            throw new RuntimeException(e);
        }

        TimeLayoutController controller = loader.getController();
        controller.setSingleThreadPool(singleThreadPool);
        controller.updateTime();

        rootLayout.setRight(pane);
    }
}
