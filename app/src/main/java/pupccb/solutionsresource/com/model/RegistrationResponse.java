package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/14/2015.
 */
public class RegistrationResponse {

    private String msg;

    public String getMsg() {
        return msg != null ? msg.trim() : "";
    }

    public void setMsg(String msg) {
        this.msg = msg != null ? msg.trim() : "";
    }

}
