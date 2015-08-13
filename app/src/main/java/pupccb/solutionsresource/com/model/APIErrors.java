package pupccb.solutionsresource.com.model;

/**
 * Created by User on 7/16/2015.
 */
public class APIErrors {

    private String field;
    private String message;

    public String getField() {
        return field != null ? field.trim() : "";
    }

    public String getMessage() {
        return message != null ? message.trim() : "";
    }
}
