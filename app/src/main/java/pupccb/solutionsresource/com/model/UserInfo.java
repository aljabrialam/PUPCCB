package pupccb.solutionsresource.com.model;

/**
 * Created by User on 9/14/2015.
 */
public class UserInfo {

    String token;

    public UserInfo(String token) {
       setToken(token);
    }

    public String getToken() {
        return token != null ? token.trim() : "";
    }

    public void setToken(String token) {
        this.token = token != null ? token.trim() : "";
    }
}
