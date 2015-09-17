package pupccb.solutionsresource.com.model;

/**
 * Created by User on 9/15/2015.
 */
public class UserInfoResponse {

    private String id;

    UserInfoResponse(String id){
        setId(id);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }
}
