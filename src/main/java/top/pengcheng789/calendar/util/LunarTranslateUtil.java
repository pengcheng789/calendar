package top.pengcheng789.calendar.util;

/**
 * @author pen
 */
public class LunarTranslateUtil {

    /**
     * 将数字日期转化成汉字日期，年份则转换成天干地支表示方法。“初一”则转换成月份，
     * 如：“2月1日”则转换成“二月”。
     */
    public static String getYearTranslator(int lunarYear) {
        return LunarUtil.lunarYearToGanZhi(lunarYear);
    }

    /**
     * 将数字日期转化成汉字日期，年份则转换成天干地支表示方法。“初一”则转换成月份，
     * 如：“1月1日”则转换成“正月”。
     */
    public static String getMonthTranslate(int lunarMonth) {
        String string;

        if (lunarMonth > 12 || lunarMonth <= 0) {
            string = "混沌";
        } else {
            string = NumberTranslateUtil.twoBitNumToChinese(lunarMonth);
        }

        if (string.equals("一")) {
            string = "正";
        }

        return string + "月";
    }

    /**
     * 将数字日期转化成汉字日期，年份则转换成天干地支表示方法。“初一”则转换成月份，
     * 如：“1月1日”则转换成“正月”。
     */
    public static String getDateTranslate(int lunarMonth, int lunarDay) {
        if (lunarDay <= 0) {
            return  "混沌";
        } else if (lunarDay == 1) {
            return getMonthTranslate(lunarMonth);
        } else if (lunarDay <= 10) {
            return "初" + NumberTranslateUtil.twoBitNumToChineseLunarStyle(lunarDay);
        } else {
            return NumberTranslateUtil.twoBitNumToChineseLunarStyle(lunarDay);
        }
    }
}
