package top.pengcheng789.calendar.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author pen
 */
public class DateTimeUtil {

    /**
     * 将LocalDateTime类型转换成String类型，格式为“m月d日 hh:mm:ss”。
     */
    public static String toStandardString(LocalDateTime localDateTime) {
        return (localDateTime.getMonthValue() < 10 ? "0" : "") + localDateTime.getMonthValue() + "月"
                + (localDateTime.getDayOfMonth() < 10 ? "0" : "") + localDateTime.getDayOfMonth() + "日 "
                + (localDateTime.getHour() < 10 ? "0" : "") + localDateTime.getHour() + ":"
                + (localDateTime.getMinute() < 10 ? "0" : "") + localDateTime.getMinute() + ":"
                + (localDateTime.getSecond() < 10 ? "0" : "") + localDateTime.getSecond();
    }

    /**
     * 将LocalTime类型转换成String类型，格式为“hh:mm:ss”(24小时制)。
     */
    public static String toStandardString(LocalTime localTime) {
        return (localTime.getHour() < 10 ? "0" : "") + localTime.getHour() + ":"
                + (localTime.getMinute() < 10 ? "0" : "") + localTime.getMinute() + ":"
                + (localTime.getSecond() < 10 ? "0" : "") + localTime.getSecond();
    }

    /**
     * 将LocalDateTime类型转换成String类型，格式为“hh:mm”(24小时制)。
     */
    public static String toShortString(LocalDateTime localDateTime) {
        return (localDateTime.getHour() < 10 ? "0" : "") + localDateTime.getHour() + ":"
                + (localDateTime.getMinute() < 10 ? "0" : "") + localDateTime.getMinute();
    }
}
