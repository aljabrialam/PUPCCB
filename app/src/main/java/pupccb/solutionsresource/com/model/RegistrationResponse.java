package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/14/2015.
 */
public class RegistrationResponse {

    private String message, description;

    public String getMessage() {
        return message != null ? message.trim() : "";
    }
    public String getDescription() {
        return description != null ? description.trim() : "";
    }
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : "";;
    }

}
