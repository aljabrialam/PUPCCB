package pupccb.solutionsresource.com.model;

/**
 * Created by User on 7/16/2015.
 */
public class Session {

    private String access_token, token_type, expires_in, refresh_token;


    public void setAccess_token(String access_token) {
        this.access_token = access_token != null ? access_token.trim() : "";
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type != null ? token_type.trim() : "";
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in != null ? expires_in.trim() : "";
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token != null ? refresh_token.trim() : "";
    }

    public String getAccess_token() {
        return access_token != null ? access_token.trim() : "";
    }

    public String getToken_type() {
        return token_type != null ? token_type.trim() : "";
    }

    public String getExpires_in() {
        return expires_in != null ? expires_in.trim() : "";
    }

    public String getRefresh_token() {
        return refresh_token  != null ? refresh_token.trim() : "";
    }

}
