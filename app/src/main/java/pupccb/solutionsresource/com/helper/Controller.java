package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.activity.Registration;
import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
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
    public Controller(OnlineCommunicator onlineCommunicator){
        this.onlineCommunicator = onlineCommunicator;
    }
    public enum MethodTypes{
        LOGIN, REGISTER

    public void setError(ErrorHandler.Error error, MethodTypes methodTypes) {
        if (error.getErrorMessage().contains("java.io.EOFException")) {

        } else if (error.getErrorType().equals(ErrorHandler.ErrorType.LOGOUT)) {

        } else {
            if (activity instanceof Main) {
                ((Main) activity).setError(error, methodTypes);
            }
        }
    }

    public enum MethodTypes {
        LOGIN
    }

    private Activity activity;
    private Login login;
    private RegistrationDetails registrationDetails;

    public void login(Activity activity,Login login){
        this.activity = activity;
        this.login = login;
        onlineCommunicator.login(this,activity,login, MethodTypes.LOGIN);
    }

    public void loginResult(Session session, Login login){
        if(activity instanceof Main){
            ((Main) activity).loginResult(session, login);
        }
    }
}
