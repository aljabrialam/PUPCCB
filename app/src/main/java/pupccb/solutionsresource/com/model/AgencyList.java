package pupccb.solutionsresource.com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/16/2015.
 */
public class AgencyList {
    private List<Agencies> data;

    public List<Agencies> getData() {
        return data != null ? data : new ArrayList<Agencies>();
    }
    public void setData(List<Agencies> data) {
        this.data = data != null ? data : new ArrayList<Agencies>();
    }
}
