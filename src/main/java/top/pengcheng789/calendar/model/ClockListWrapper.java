package top.pengcheng789.calendar.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author pen
 */
@XmlRootElement(name = "clocks")
public class ClockListWrapper {

    private List<Clock> clocks;

    @XmlElement(name = "clock")
    public List<Clock> getClocks() {
        return clocks;
    }

    public void setClocks(List<Clock> clocks) {
        this.clocks = clocks;
    }
}
