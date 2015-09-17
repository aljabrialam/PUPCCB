package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/27/2015.
 */
public class Agencies {

    String id, name, description, is_member;

    public Agencies(String id, String name, String description, String is_member) {
        setId(id);
        setName(name);
        setDescription(description);
        setId(is_member);
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

    public String getDescription() {
        return description != null ? description.trim() : "";
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : "";
    }

    public String getIs_member() {
        return is_member != null ? is_member.trim() : "";
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member != null ? is_member.trim() : "";
    }
}
