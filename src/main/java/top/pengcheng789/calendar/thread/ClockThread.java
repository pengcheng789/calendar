package top.pengcheng789.calendar.thread;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.Notifications;
import top.pengcheng789.calendar.model.Clock;

import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.List;

/**
 * @author pen
 */
public class ClockThread extends Thread {

    private static final String CLOCK_WAV_FILE = "/mp3/clock.mp3";

    private volatile List<Clock> clockList;

    public ClockThread(List<Clock> clockList) {
        this.clockList = clockList;
    }

    @Override
    public void run() {
        while (true) {

            Clock now = new Clock(LocalTime.now().getHour(), LocalTime.now().getMinute());
            synchronized (clockList) {
                for (Clock clock : clockList) {
                    if (clock.equals(now)) {
                        Platform.runLater(() -> {
                            Notifications.create().title("闹钟提醒")
                                    .text("已经到了" + clock.getHour() + "时" + clock.getMinute() + "分了！")
                                    .show();
                            try {
                                Media hit = new Media(getClass().getResource(CLOCK_WAV_FILE).toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(hit);
                                mediaPlayer.play();
                            } catch (URISyntaxException e) {
                                System.out.println("播放闹钟音乐异常！");
                                throw new RuntimeException(e);
                            }
                        });

                        try {
                            Thread.sleep(60 * 1_000);
                        } catch (InterruptedException e) {
                            System.out.println("闹钟线程异常！");
                            throw new RuntimeException(e);
                        }

                        break;
                    }
                }
            }

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                System.out.println("闹钟线程停止运行！");
                break;
                // throw new RuntimeException(e);
            }
        }
    }
}
