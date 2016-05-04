package pupccb.solutionsresource.com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/21/2015.
 */
public class TicketList {

    private List<TicketInfo> data;

    public List<TicketInfo> getData() {
        return data != null ? data : new ArrayList<TicketInfo>();

    }

    public void setData(List<TicketInfo> data) {
        this.data = data != null ? data : new ArrayList<TicketInfo>();
    }
}
