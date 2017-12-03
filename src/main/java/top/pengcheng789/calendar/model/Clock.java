package top.pengcheng789.calendar.model;

/**
 * @author pen
 */
public class Clock {

    private int hour;
    private int minute;

    public Clock() {}

    public Clock(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * 进行比较，如果小时和分钟一致则返回true。
     */
    public boolean equals(Clock that) {
        return this.getHour() == that.getHour()
                && this.getMinute() == that.getMinute();
    }

    /**
     * 重写toString方法，输出格式为“HH:mm”。
     */
    @Override
    public String toString() {
        return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
    }
}
