package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/27/2015.
 */
public class Agency {

    String id, agencyname;

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getAgencyname() {
        return agencyname != null ? agencyname.trim() : "";
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname != null ? agencyname.trim() : "";
    }
}
