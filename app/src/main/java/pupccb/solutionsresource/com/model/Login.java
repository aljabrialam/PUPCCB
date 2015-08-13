package pupccb.solutionsresource.com.model;

/**
 * Created by User on 7/16/2015.
 */
public class Login {

    private String username, password, client_id, client_secret, scope, grant_type;

    public Login(String username, String password, String client_id,
                 String client_secret, String scope, String grant_type) {
        setUsername(username);
        setPassword(password);
        setClient_id(client_id);
        setClient_secret(client_secret);
        setScope(scope);
        setGrant_type(grant_type);
    }

    public String getUsername() {
        return username != null ? username.trim() : "";
    }

    public String getPassword() {
        return password != null ? password.trim() : "";
    }

    public String getClient_id() {
        return client_id != null ? client_id.trim() : "";
    }

    public String getClient_secret() {
        return client_secret != null ? client_secret.trim() : "";
    }

    public String getScope() {
        return scope != null ? scope.trim() : "";
    }

    public String getGrant_type() {
        return grant_type != null ? grant_type.trim() : "";
    }

    public void setUsername(String username) {
        this.username = username != null ? username.trim() : "";
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : "";
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id != null ? client_id.trim() : "";
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret != null ? client_secret.trim() : "";
    }

    public void setScope(String scope) {
        this.scope = scope != null ? scope.trim() : "";
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type != null ? grant_type.trim() : "";
    }

}
