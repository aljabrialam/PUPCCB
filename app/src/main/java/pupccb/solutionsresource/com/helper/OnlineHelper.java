package pupccb.solutionsresource.com.helper;

import android.app.Activity;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.adapter.AnnouncementAdapter;
import pupccb.solutionsresource.com.adapter.CurrentTicketAdapter;
import pupccb.solutionsresource.com.helper.communicator.OnlineCommunicator;
import pupccb.solutionsresource.com.helper.request.AgenciesRequest;
import pupccb.solutionsresource.com.helper.request.AnnouncementListRequest;
import pupccb.solutionsresource.com.helper.request.CancelTicketRequest;
import pupccb.solutionsresource.com.helper.request.CommentListRequest;
import pupccb.solutionsresource.com.helper.request.CommentRequest;
import pupccb.solutionsresource.com.helper.request.CreateTicketRequest;
import pupccb.solutionsresource.com.helper.request.RegistrationRequest;
import pupccb.solutionsresource.com.helper.request.SessionRequest;
import pupccb.solutionsresource.com.helper.request.TicketListRequest;
import pupccb.solutionsresource.com.helper.request.UserInfoRequest;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.AgencyList;
import pupccb.solutionsresource.com.model.Announcement;
import pupccb.solutionsresource.com.model.AnnouncementList;
import pupccb.solutionsresource.com.model.Comment;
import pupccb.solutionsresource.com.model.CommentList;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.model.TicketList;
import pupccb.solutionsresource.com.model.TicketResponse;
import pupccb.solutionsresource.com.model.UserInfo;
import pupccb.solutionsresource.com.model.UserInfoResponse;
import pupccb.solutionsresource.com.util.Connectivity;
import pupccb.solutionsresource.com.util.ErrorHandler;
import retrofit.mime.TypedFile;


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

    @Override
    public void getTickets(Controller controller, Activity activity, String id, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performTicketListRequest(activity, id, methodTypes);
    }

    @Override
    public void getComments(Controller controller, Activity activity, String ticket_id, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performCommentListRequest(activity, ticket_id, methodTypes);
    }

    @Override
    public void getAnnouncements(Controller controller, Activity activity, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performAnnouncementRequest(activity, methodTypes);
    }

    @Override
    public void cancelTicket(Controller controller, Activity activity, String id, String user_id, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performCancelTicketRequest(activity, id, user_id, methodTypes);
    }

    @Override
    public void addComment(Controller controller, Activity activity, String ticket_id, String user_id, String ticket_comment, TypedFile file, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        performCommentRequest(activity, ticket_id, user_id, ticket_comment, file, methodTypes);
    }

    private void performLoginRequest(Activity activity, Login login, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        SessionRequest sessionRequest = new SessionRequest(login);
        spiceManager.execute(sessionRequest, sessionRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new sessionListener(login, methodTypes));
    }

    private void performCommentRequest(Activity activity, String ticket_id, String user_id, String ticket_comment, TypedFile file, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        CommentRequest commentRequest = new CommentRequest(ticket_id, user_id, ticket_comment, file);
        spiceManager.execute(commentRequest, commentRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new commentTicketListener(activity, ticket_id, user_id, ticket_comment, methodTypes));
    }

    private void performCancelTicketRequest(Activity activity, String id, String user_id, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        CancelTicketRequest cancelTicketRequest = new CancelTicketRequest(id, user_id);
        spiceManager.execute(cancelTicketRequest, cancelTicketRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new cancelTicketListener(activity, id, user_id, methodTypes));
    }

    private void performAnnouncementRequest(Activity activity, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        AnnouncementListRequest announcementListRequest = new AnnouncementListRequest();
        spiceManager.execute(announcementListRequest, announcementListRequest.createCacheKey(), BaseHelper.isOnline(activity) && Connectivity.isConnectedFast(activity
        ) ? DurationInMillis.ALWAYS_EXPIRED : DurationInMillis.ONE_WEEK, new announcementListListener(activity, methodTypes));
    }

    private void performCommentListRequest(Activity activity, String ticket_id, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        CommentListRequest commentListRequest = new CommentListRequest(ticket_id);
        spiceManager.execute(commentListRequest, commentListRequest.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new commentListListener(activity, ticket_id, methodTypes));
    }

    private void performTicketListRequest(Activity activity, String id, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        TicketListRequest ticketListRequest = new TicketListRequest(id);
        spiceManager.execute(ticketListRequest, ticketListRequest.createCacheKey(), BaseHelper.isOnline(activity) && Connectivity.isConnectedFast(activity
        ) ? DurationInMillis.ALWAYS_EXPIRED : DurationInMillis.ONE_WEEK, new ticketListListener(activity, id, methodTypes));
    }

    private void performAgenciesRequest(Activity activity, Controller.MethodTypes methodTypes) {
        startSpiceManager(activity);
        AgenciesRequest agenciesRequest = new AgenciesRequest();
        spiceManager.execute(agenciesRequest, agenciesRequest.createCacheKey(), BaseHelper.isOnline(activity) ? DurationInMillis.ALWAYS_EXPIRED : DurationInMillis.ONE_WEEK, new agenciesListener(methodTypes));
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

    @Override
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

    public class cancelTicketListener implements RequestListener<TicketResponse> {
        private Controller.MethodTypes methodTypes;
        private List<TicketInfo> list;
        private CurrentTicketAdapter currentTicketAdapter;
        private Activity activity;
        private String id, user_id;

        public cancelTicketListener(Activity activity, String id, String user_id, Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
            this.activity = activity;
            this.id = id;
            this.user_id = user_id;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(TicketResponse ticketResponse) {
            stopSpiceManager();
            controller.cancelTicketResult(ticketResponse);
        }
    }

    public class commentTicketListener implements RequestListener<TicketResponse> {
        private Controller.MethodTypes methodTypes;
        private Activity activity;
        private String ticket_id, user_id, ticket_comment;

        public commentTicketListener(Activity activity, String ticket_id, String user_id, String ticket_comment, Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
            this.activity = activity;
            this.ticket_id = ticket_id;
            this.user_id = user_id;
            this.ticket_comment = ticket_comment;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(TicketResponse ticketResponse) {
            stopSpiceManager();
            controller.commentTicketResult(ticketResponse);
        }
    }

    public class commentListListener implements RequestListener<CommentList> {
        private Controller.MethodTypes methodTypes;
        private List<Comment> list;
        private CurrentTicketAdapter currentTicketAdapter;
        private Activity activity;
        private String ticket_id;

        public commentListListener(Activity activity, String ticket_id, Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
            this.activity = activity;
            this.ticket_id = ticket_id;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(CommentList commentList) {
            list = (ArrayList<Comment>) commentList.getData();
            stopSpiceManager();
            controller.commentListResult(list);
        }
    }

    public class announcementListListener implements RequestListener<AnnouncementList> {
        private Controller.MethodTypes methodTypes;
        private List<Announcement> list;
        private AnnouncementAdapter announcementAdapter;
        private Activity activity;

        public announcementListListener(Activity activity, Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
            this.activity = activity;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(AnnouncementList announcementList) {
            stopSpiceManager();
            list = (ArrayList<Announcement>) announcementList.getData();
            controller.announcementListResult(list);
        }
    }

    public class ticketListListener implements RequestListener<TicketList> {
        private Controller.MethodTypes methodTypes;
        private List<TicketInfo> list;
        private CurrentTicketAdapter currentTicketAdapter;
        private Activity activity;
        private String id;

        public ticketListListener(Activity activity, String id, Controller.MethodTypes methodTypes) {
            this.methodTypes = methodTypes;
            this.activity = activity;
            this.id = id;

        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            stopSpiceManager();
            controller.setError(new ErrorHandler().onRequestFailure(spiceException), methodTypes);
        }

        @Override
        public void onRequestSuccess(TicketList ticketList) {
            stopSpiceManager();
            list = (ArrayList<TicketInfo>) ticketList.getData();
            controller.ticketListResult(list);
        }
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
