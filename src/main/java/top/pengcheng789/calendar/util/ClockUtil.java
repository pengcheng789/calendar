package top.pengcheng789.calendar.util;

import top.pengcheng789.calendar.model.Clock;
import top.pengcheng789.calendar.model.ClockListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * @author pen
 */
public class ClockUtil {

    /**
     * 从指定的xml文件中获取闹钟列表。
     */
    public static List<Clock> loadClockDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ClockListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            ClockListWrapper wrapper = (ClockListWrapper) um.unmarshal(file);
            return wrapper.getClocks();
        } catch (Exception e) {
            System.out.println("加载闹钟列表异常!不能从" + file.getPath() + "读取数据！");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将闹钟列表存储进指定的xml文件中。
     */
    public static void saveClockDataToFile(File file, List<Clock> clocks) {
        try {
            JAXBContext context = JAXBContext.newInstance(ClockListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ClockListWrapper wrapper = new ClockListWrapper();
            wrapper.setClocks(clocks);
            m.marshal(wrapper, file);
        } catch (Exception e) {
            AlertUtil.showErrorAlert("保存闹钟列表异常", "不能将闹钟列表保存到" + file.getPath(), e);
        }
    }
}
