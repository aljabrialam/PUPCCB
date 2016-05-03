package pupccb.solutionsresource.com.helper.communicator;

import android.app.Activity;

import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.UserInfo;
import retrofit.mime.TypedFile;

/**
 * Created by User on 7/16/2015.
 */
public interface OnlineCommunicator {

    void login(Controller controller, Activity activity, Login login, Controller.MethodTypes methodTypes);
    void register(Controller controller, Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes);
    void createTicket(Controller controller, Activity activity, Ticket ticket, Controller.MethodTypes methodTypes);
    void userInfo(Controller controller, Activity activity,UserInfo userInfo, Controller.MethodTypes methodTypes);
    void agencies(Controller controller, Activity activity, Controller.MethodTypes methodTypes);
    void getTickets(Controller controller, Activity activity, String id, Controller.MethodTypes methodTypes);
    void getComments(Controller controller, Activity activity, String ticket_id, Controller.MethodTypes methodTypes);
    void getAnnouncements(Controller controller, Activity activity, Controller.MethodTypes methodTypes);
    void cancelTicket(Controller controller, Activity activity, String id, String user_id, Controller.MethodTypes methodTypes);
    void addComment(Controller controller, Activity activity, String ticket_id, String user_id, String ticket_comment, TypedFile file, Controller.MethodTypes methodTypes);
    void createAgency(Controller controller, Activity activity, Agencies agency, Controller.MethodTypes methodTypes);
}
