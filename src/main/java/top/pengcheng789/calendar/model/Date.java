package top.pengcheng789.calendar.model;

import top.pengcheng789.calendar.util.LunarTranslateUtil;
import top.pengcheng789.calendar.util.LunarUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author pen
 */
public class Date {

    private LocalDate localDate;
    private String lunarYear;
    private String lunarMonth;
    private String lunarDate;

    public Date(LocalDate localDate) {
        this.localDate = localDate;
        if (localDate.getYear() > 2100 || localDate.getYear() < 1900) {
            lunarYear = "混沌";
            lunarMonth = "混沌";
            lunarDate = "混沌";
        } else {
            Lunar lunar = LunarUtil.SolarToLunar(
                    new Solar(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()));
            lunarYear = LunarTranslateUtil.getYearTranslator(lunar.getYear());
            lunarMonth = LunarTranslateUtil.getMonthTranslate(lunar.getMonth());
            lunarDate = LunarTranslateUtil.getDateTranslate(lunar.getMonth(), lunar.getDay());
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public int getYear() {
        return localDate.getYear();
    }

    public Month getMonth() {
        return localDate.getMonth();
    }

    public int getDate() {
        return localDate.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }
}
