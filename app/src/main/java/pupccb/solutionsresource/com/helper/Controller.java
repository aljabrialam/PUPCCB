package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import java.util.ArrayList;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.activity.NewTicket;
import pupccb.solutionsresource.com.activity.Registration;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.helper.communicator.AttachmentCommunicator;
import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.AgencyList;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.TicketResponse;
import pupccb.solutionsresource.com.model.UserInfo;
import pupccb.solutionsresource.com.model.UserInfoResponse;
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
    private Ticket ticket;

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
            } else if (activity instanceof NewTicket) {
                ((NewTicket) activity).setError(error, methodTypes);
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

    public void userInfo(Activity activity, UserInfo userInfo) {
        this.activity = activity;
        onlineCommunicator.userInfo(this, activity, userInfo, MethodTypes.USER_INFO);
    }

    public void UserInfoResult(UserInfoResponse userInfoResponse, UserInfo userInfo) {
        if (activity instanceof Main) {
            ((Main) activity).userInfoResult(userInfoResponse, userInfo);
        }
    }

    public void agencies(Activity activity) {
        this.activity = activity;
        onlineCommunicator.agencies(this, activity, MethodTypes.AGENCIES);
    }

    public void agenciesResult(ArrayList<Agencies> agencyList) {
        if (activity instanceof NewTicket) {
            ((NewTicket) activity).agenciesResult(false, agencyList);
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

    public void createTicket(Activity activity, Ticket ticket) {
        this.activity = activity;
        this.ticket = ticket;
        onlineCommunicator.createTicket(this, activity, ticket, MethodTypes.CREATE_TICKET);
    }

    public void createTicketResult(TicketResponse ticketResponse, Ticket ticket) {
        if (activity instanceof NewTicket) {
            ((NewTicket) activity).createTicketResult(ticketResponse, ticket);
        }
    }

    public void postFileAttachment(Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment) {
        this.activity = activity;
        attachmentCommunicator.postFileAttachment(this, activity, ticket_id, user_id, fileAttachment, MethodTypes.POST_FILE_ATTACHMENT);
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        if (activity instanceof NewTicket) {
            ((NewTicket) activity).postFileAttachmentResult(value, fileAttachmentAdapter);
        }
    }

    //< -- FILE ATTACHMENT -->
    public void getFileAttachmentList(Activity activity, String consultation_id, String patient_id) {
        this.activity = activity;
        attachmentCommunicator.getFileAttachmentList(this, activity, consultation_id, patient_id, MethodTypes.GET_FILE_ATTACHMENT_LIST);
    }

    public void getFileAttachmentListResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        if (activity instanceof NewTicket) {
            ((NewTicket) activity).getFileAttachmentListResult(value, fileAttachmentAdapter);
        }
    }

    public void deleteFile(Activity activity, FileAttachment fileAttachment) {
        this.activity = activity;
        attachmentCommunicator.deleteFile(this, activity, fileAttachment, MethodTypes.DELETE_FILE_ATTACHMENT);
    }

    public void deleteFileResult() {
        if (activity instanceof NewTicket) {
            ((NewTicket) activity).deleteFileResult();
        }
    }

    public enum MethodTypes {
        LOGIN, REGISTER, CREATE_TICKET, POST_FILE_ATTACHMENT, GET_FILE_ATTACHMENT_LIST, DELETE_FILE_ATTACHMENT, USER_INFO, AGENCIES
    }
}


