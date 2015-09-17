package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.helper.request.AgenciesRequest;
import pupccb.solutionsresource.com.helper.request.CreateTicketRequest;
import pupccb.solutionsresource.com.helper.request.RegistrationRequest;
import pupccb.solutionsresource.com.helper.request.SessionRequest;
import pupccb.solutionsresource.com.helper.request.UserInfoRequest;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.AgencyList;
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

    @Override
    public void userInfo(Controller controller, Activity activity, UserInfo userInfo, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performUserInfoRequest(activity, userInfo, methodTypes);
    }

    @Override
    public void agencies(Controller controller, Activity activity, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performAgenciesRequest(activity, methodTypes);
    }

    private void performAgenciesRequest(Activity activity, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        AgenciesRequest agenciesRequest = new AgenciesRequest();
        spiceManager.execute(agenciesRequest, agenciesRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new agenciesListener(methodTypes));
    }

    private void performUserInfoRequest(Activity activity, UserInfo userInfo, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        UserInfoRequest userInfoRequest = new UserInfoRequest(userInfo);
        spiceManager.execute(userInfoRequest, userInfoRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new userInfoListener(userInfo, methodTypes));
    }

    @Override
    public void register(Controller controller, Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performRegistrationRequest(activity, registrationDetails, methodTypes);
    }

    private void performRegistrationRequest(Activity activity, RegistrationDetails registrationDetails, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        this.activity = activity;
        RegistrationRequest registrationRequest = new RegistrationRequest(registrationDetails);
        spiceManager.execute(registrationRequest, registrationRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new registrationListener(registrationDetails, methodTypes));
    }

    //@Override
    public void createTicket(Controller controller, Activity activity, Ticket ticket, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performNewTicketRequest(activity, ticket, methodTypes);
    }

    private void performNewTicketRequest(Activity activity, Ticket ticket, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        this.activity = activity;
        CreateTicketRequest createTicketRequest = new CreateTicketRequest(ticket);
        spiceManager.execute(createTicketRequest, createTicketRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new newTicketListener(ticket, methodTypes));
    }

    public class agenciesListener implements RequestListener<AgencyList> {
        private Controller.MethodTypes methodTypes;
        private ArrayList<Agencies> list;

        public agenciesListener(Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(AgencyList agencyList) {
            list = (ArrayList<Agencies>) agencyList.getData();
            stopSpiceManager();
            controller.agenciesResult(list);
        }
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

    public class userInfoListener implements RequestListener<UserInfoResponse> {
        private Controller.MethodTypes methodTypes;
        private UserInfo userInfo;

        public userInfoListener(UserInfo userInfo, Controller.MethodTypes methodTypes) {
            this.userInfo = userInfo;
            this.methodTypes = methodTypes;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(UserInfoResponse userInfoResponse) {
            stopSpiceManager();
            controller.UserInfoResult(userInfoResponse, userInfo);
        }
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
        public void onRequestSuccess(RegistrationResponse registrationResponse) {
            stopSpiceManager();
            controller.registerResult(registrationResponse, registrationDetails);
        }
    }

    public class newTicketListener implements RequestListener<TicketResponse> {
        private Ticket ticket;
        private Controller.MethodTypes methodTypes;

        public newTicketListener(Ticket ticket, Controller.MethodTypes methodTypes) {
            this.ticket = ticket;
            this.methodTypes = methodTypes;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(TicketResponse ticketResponse) {
            stopSpiceManager();
            controller.createTicketResult(ticketResponse, ticket);
        }
    }

}
