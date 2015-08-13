package pupccb.solutionsresource.com.model;

/**
 * Created by User on 7/16/2015.
 */
public class Session {

    private String access_token, token_type, expires_in;

    public String getAccess_token() {
        return access_token != null ? access_token.trim() : "";
    }

    public String getToken_type() {
        return token_type != null ? token_type.trim() : "";
    }

    public String getExpires_in() {
        return expires_in != null ? expires_in.trim() : "";
    }
}
