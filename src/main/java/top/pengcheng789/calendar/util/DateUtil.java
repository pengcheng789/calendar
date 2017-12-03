package top.pengcheng789.calendar.util;

import top.pengcheng789.calendar.model.Date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pen
 */
public class DateUtil {

    /**
     * 获取指定日期当月的日期列表。
     */
    public static List<Date> getDatesOfMonth(LocalDate localDate) {
        List<Date> dateList = new ArrayList<>();
        boolean leapYear = localDate.isLeapYear();

        for (int i = 1; i <= localDate.getMonth().length(leapYear); i++) {
            dateList.add(new Date(LocalDate.of(localDate.getYear(), localDate.getMonth(), i)));
        }

        return dateList;
    }

    /**
     * 获取指定日期当月的日期列表，并填充前后各一个月的部分日期，使得列表的第一个日期总是周一，最后一个日期总是周日。
     */
    public static List<Date> getFixDatesOfMonth(LocalDate localDate) {
        List<Date> dateList = getDatesOfMonth(localDate);
        DayOfWeek firstWeekDayOfMonth = dateList.get(0).getDayOfWeek();
        DayOfWeek lastWeekDayOfMonth = dateList.get(dateList.size() - 1).getDayOfWeek();

        if (!firstWeekDayOfMonth.equals(DayOfWeek.MONDAY)) {
            List<Date> lastDatesOfPreMonth = getLastDaysOfPreviousMonth(localDate, firstWeekDayOfMonth.getValue() - 1);

            for (int i = lastDatesOfPreMonth.size() - 1; i >= 0; i--) {
                dateList.add(0, lastDatesOfPreMonth.get(i));
            }
        }

        if (!lastWeekDayOfMonth.equals(DayOfWeek.SUNDAY)) {
            List<Date> firstDatesOfNextMonth = getFirstDaysOfNextMonth(localDate, 7 - lastWeekDayOfMonth.getValue());
            for (Date date : firstDatesOfNextMonth) {
                dateList.add(dateList.size(), date);
            }
        }

        return dateList;
    }

    /**
     * 获取指定日期的上一个月的日期列表，并填充前后各一个月的部分日期，使得列表的第一个日期总是周一，最后一个日期总是周日。
     */
    public static List<Date> getFixDatesOfPreMonth(LocalDate localDate) {
        List<Date> dateList;

        if (localDate.getMonth().equals(Month.JANUARY)) {
            dateList = getFixDatesOfMonth(LocalDate.of(localDate.getYear() - 1, Month.DECEMBER, 1));
        } else {
            dateList = getFixDatesOfMonth(LocalDate.of(localDate.getYear(),
                    localDate.getMonthValue() - 1, 1));
        }

        return dateList;
    }

    /**
     * 获取指定日期的下一个月的日期列表，并填充前后各一个月的部分日期，使得列表的第一个日期总是周一，最后一个日期总是周日。
     */
    public static List<Date> getFixDatesOfNextMonth(LocalDate localDate) {
        List<Date> dateList;

        if (localDate.getMonth().equals(Month.DECEMBER)) {
            dateList = getFixDatesOfMonth(LocalDate.of(localDate.getYear() + 1, Month.JANUARY, 1));
        } else {
            dateList = getFixDatesOfMonth(LocalDate.of(localDate.getYear(),
                    localDate.getMonthValue() + 1, 1));
        }

        return dateList;
    }

    /**
     * 获取指定日期上一个月的最后几个日期组成列表。
     */
    private static List<Date> getLastDaysOfPreviousMonth(LocalDate localDate, int n) {
        List<Date> dateList;

        if (localDate.getMonth().equals(Month.JANUARY)) {
            dateList = getDatesOfMonth(LocalDate.of(localDate.getYear() - 1, Month.DECEMBER, 1));
        } else {
            dateList = getDatesOfMonth(LocalDate.of(localDate.getYear(),
                    localDate.getMonthValue() - 1, 1));
        }

        return dateList.subList(dateList.size() - n, dateList.size());
    }

    /**
     * 获取指定日期下一个月的最前几个日期组成列表。
     */
    private static List<Date> getFirstDaysOfNextMonth(LocalDate localDate, int n) {
        List<Date> dateList;

        if (localDate.getMonth().equals(Month.DECEMBER)) {
            dateList = getDatesOfMonth(LocalDate.of(localDate.getYear() + 1, Month.JANUARY, 1));
        } else {
            dateList = getDatesOfMonth(LocalDate.of(localDate.getYear(),
                    localDate.getMonthValue() + 1, 1));
        }

        return dateList.subList(0, n);
    }
}
