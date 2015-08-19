package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.helper.request.RegistrationRequest;
import pupccb.solutionsresource.com.helper.request.SessionRequest;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.util.ErrorHandler;

/**
 * Created by User on 7/16/2015.
 */
public class OnlineHelper extends BaseHelper implements OnlineCommunicator {

    private Controller controller;
    private Activity activity;

    @Override
    public void login(Controller controller, Activity activity, Login login, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performLoginRequest(activity, login, methodTypes);
    }

    private void performLoginRequest(Activity activity, Login login, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        SessionRequest sessionRequest = new SessionRequest(login);
        spiceManager.execute(sessionRequest, sessionRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new sessionListener(login, methodTypes));
    }

    public class sessionListener implements RequestListener<Session> {
        private Login login;
        private Controller.MethodTypes methodTypes;

        public sessionListener(Login login, Controller.MethodTypes methodTypes) {
            this.login = login;
            this.methodTypes = methodTypes;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(Session session) {
            stopSpiceManager();
            controller.loginResult(session, login);
        }
    }


    @Override
    public void register(Controller controller, Activity activity,RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performRegistrationRequest(activity, registrationDetails, methodTypes);
    }

    private void performRegistrationRequest(Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        this.activity = activity;
        RegistrationRequest registrationRequest =  new RegistrationRequest(registrationDetails);
        spiceManager.execute(registrationRequest, registrationRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new registrationListener(registrationDetails, methodTypes));
    }

    public class registrationListener implements RequestListener<RegistrationResponse> {
        private RegistrationDetails registrationDetails;
        private Controller.MethodTypes methodTypes;

        public registrationListener(RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes) {
            this.registrationDetails = registrationDetails;
            this.methodTypes = methodTypes;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(RegistrationResponse registrationResponse)  {
            stopSpiceManager();
            controller.registerResult(registrationResponse,registrationDetails);
        }
    }
}
