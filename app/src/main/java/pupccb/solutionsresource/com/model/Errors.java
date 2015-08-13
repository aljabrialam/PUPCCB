package pupccb.solutionsresource.com.model;

import java.util.ArrayList;

/**
 * Created by User on 7/16/2015.
 */
public class Errors {

    //OAUTH
    private String error;
    private String error_description;
    //API
    private String code;
    private String message;
    private String description;
    private ArrayList<APIErrors> errors;
    //OTHERS
    private String status;
    private String error_message;


    public String getError() {
        return error != null ? error.trim() : "";
    }

    public String getError_description() {
        return error_description != null ? error_description.trim() : "";
    }

    public String getCode() {
        return code != null ? code.trim() : "";
    }

    public String getMessage() {
        return message != null ? message.trim() : "";
    }

    public String getDescription() {
        return description != null ? description.trim() : "";
    }

    public ArrayList<APIErrors> getErrors() {
        return errors != null ? errors : new ArrayList<APIErrors>();
    }

    public String getStatus() {
        return status != null ? status.trim() : "";
    }

    public String getError_message() {
        return error_message != null ? error_message.trim() : "";
    }
}
