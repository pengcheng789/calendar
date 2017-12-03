package top.pengcheng789.calendar.util;

/**
 * @author pen
 */
public class NumberTranslateUtil {

    /**
     * 将一个两位数以下（包括两位数）的数字转换成中文表示，并按照农历风格将“二十一”等
     * 翻译成“廿一”。
     */
    public static String twoBitNumToChineseLunarStyle(int num) {
        if (num > 20 && num < 30) {
            return "廿" + oneBitToChinese(num % 10);
        } else {
            return twoBitNumToChinese(num);
        }
    }

    /**
     * 将一个两位数以下（包括两位数）的数字转换成中文表示。
     */
    public static String twoBitNumToChinese(int num) {
        String string;

        if (num > 99 || num < 0) {
            string = "无效";
        } else if (num >= 20 && num % 10 != 0) {
            String tenBit = oneBitToChinese(num / 10);
            String oneBit = oneBitToChinese(num % 10);
            string = tenBit + "十" + oneBit;
        } else if (num > 10 && num < 20) {
            string = "十" + oneBitToChinese(num % 10);
        } else if (num > 10 && num % 10 == 0) {
            String tenBit = oneBitToChinese(num / 10);
            string = tenBit + "十";
        } else if (num == 10) {
            string = "十";
        } else {
            string = oneBitToChinese(num % 10);
        }

        return string;
    }

    /**
     * 将一个一位数的数字转换成中文表示。
     */
    public static String oneBitToChinese(int num) {
        if (num > 9 || num < 0) {
            return  "无效";
        } else {
            switch (num) {
                case 0: return "零";
                case 1: return "一";
                case 2: return "二";
                case 3: return "三";
                case 4: return "四";
                case 5: return "五";
                case 6: return "六";
                case 7: return "七";
                case 8: return "八";
                case 9: return "九";
                default: return "无效";
            }
        }
    }
}
