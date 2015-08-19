package pupccb.solutionsresource.com.model;

/**
 * Created by User on 7/16/2015.
 */
public class Login {

    private String username, password;

    public Login(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username != null ? username.trim() : "";
    }

    public void setUsername(String username) {
        this.username = username != null ? username.trim() : "";
    }

    public String getPassword() {
        return password != null ? password.trim() : "";
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : "";
    }

}
