package pupccb.solutionsresource.com.helper.communicator;

import android.app.Activity;

import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;

/**
 * Created by User on 7/16/2015.
 */
public interface OnlineCommunicator {

    void login(Controller controller, Activity activity, Login login, Controller.MethodTypes methodTypes);
    void register(Controller controller, Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes);
}
