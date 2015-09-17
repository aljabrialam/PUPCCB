package pupccb.solutionsresource.com.helper.communicator;

import android.app.Activity;

import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.UserInfo;

/**
 * Created by User on 7/16/2015.
 */
public interface OnlineCommunicator {

    void login(Controller controller, Activity activity, Login login, Controller.MethodTypes methodTypes);
    void register(Controller controller, Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes);
    void createTicket(Controller controller, Activity activity, Ticket ticket, Controller.MethodTypes methodTypes);
    void userInfo(Controller controller, Activity activity,UserInfo userInfo, Controller.MethodTypes methodTypes);
    void agencies(Controller controller, Activity activity, Controller.MethodTypes methodTypes);
}
