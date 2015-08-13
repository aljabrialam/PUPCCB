package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.Session;

/**
 * Created by User on 7/16/2015.
 */
public class Controller {

    protected OnlineCommunicator onlineCommunicator;
    public Controller(OnlineCommunicator onlineCommunicator){
        this.onlineCommunicator = onlineCommunicator;
    }
    public enum MethodTypes{
        LOGIN
    }

    private Activity activity;
    private Login login;

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
