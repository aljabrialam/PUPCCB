package pupccb.solutionsresource.com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/21/2015.
 */
public class AnnouncementList {

    private List<Announcement> data;

    public List<Announcement> getData() {
        return data != null ? data : new ArrayList<Announcement>();
    }

    public void setData(List<Announcement> data) {
        this.data = data != null ? data : new ArrayList<Announcement>();
    }
}
