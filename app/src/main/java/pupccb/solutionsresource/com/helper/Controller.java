package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.activity.NewTicket;
import pupccb.solutionsresource.com.activity.Registration;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.helper.communicator.AttachmentCommunicator;
import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.util.ErrorHandler;

/**
 * Created by User on 7/16/2015.
 */
public class Controller {

    protected OnlineCommunicator onlineCommunicator;
    protected AttachmentCommunicator attachmentCommunicator;
    private Activity activity;
    private Login login;
    private RegistrationDetails registrationDetails;


    public Controller(OnlineCommunicator onlineCommunicator) {
        this.onlineCommunicator = onlineCommunicator;
    }
    public Controller(AttachmentCommunicator attachmentCommunicator) {
        this.attachmentCommunicator = attachmentCommunicator;
    }

    public void setError(ErrorHandler.Error error, MethodTypes methodTypes) {
        if (error.getErrorMessage().contains("java.io.EOFException")) {

        } else if (error.getErrorType().equals(ErrorHandler.ErrorType.LOGOUT)) {

        } else {
            if (activity instanceof Main) {
                ((Main) activity).setError(error, methodTypes);
            } else if (activity instanceof Registration) {
                ((Registration) activity).setError(error, methodTypes);
            }
        }
    }

    public void login(Activity activity, Login login) {
        this.activity = activity;
        this.login = login;
        onlineCommunicator.login(this, activity, login, MethodTypes.LOGIN);
    }

    public void loginResult(Session session, Login login) {
        if (activity instanceof Main) {
            ((Main) activity).loginResult(session, login);
        }
    }

    public void register(Activity activity, RegistrationDetails registrationDetails) {
        this.activity = activity;
        this.registrationDetails = registrationDetails;
        onlineCommunicator.register(this, activity, registrationDetails, MethodTypes.REGISTER);
    }

    public void registerResult(RegistrationResponse registrationResponse, RegistrationDetails registrationDetails) {
        if (activity instanceof Registration) {
            ((Registration) activity).registerResult(registrationResponse, registrationDetails);
        }
    }

    public void postFileAttachment(Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment) {
        this.activity = activity;
        attachmentCommunicator.postFileAttachment(this, activity, ticket_id, user_id, fileAttachment, MethodTypes.POST_FILE_ATTACHMENT);
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        if (activity instanceof NewTicket) {
           //((NewTicket) activity).postFileAttachmentResult(value, fileAttachmentAdapter);
        }
    }

    public enum MethodTypes {
        LOGIN, REGISTER, POST_FILE_ATTACHMENT
    }
}


