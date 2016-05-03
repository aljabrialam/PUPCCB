package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/27/2015.
 */
public class Agencies {

    String id, name, is_member, region, province, location;

    public Agencies(String id, String name, String region, String province, String location, String is_member) {
        setId(id);
        setName(name);
        setId(is_member);
        setRegion(region);
        setProvince(province);
    }

    public Agencies(String name, String location) {
        setName(name);
        setLocation(location);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getName() {
        return name != null ? name.trim() : "";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public String getIs_member() {
        return is_member != null ? is_member.trim() : "";
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member != null ? is_member.trim() : "";
    }

    public String getRegion() {
        return region != null ? region.trim() : "";
    }

    public void setRegion(String region) {
        this.region = region != null ? region.trim() : "";
    }

    public String getProvince() {
        return province != null ? province.trim() : "";
    }

    public void setProvince(String province) {
        this.province = province != null ? province.trim() : "";
    }

    public String getLocation() { return location != null ? location.trim() : ""; }

    public void setLocation(String location) { this.location = location != null ? location.trim() : ""; }
}
