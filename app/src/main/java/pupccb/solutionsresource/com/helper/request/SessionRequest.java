package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.SessionService;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.Session;

/**
 * Created by User on 7/16/2015.
 */
public class SessionRequest extends RetrofitSpiceRequest<Session, SessionService> {

    private Login login;

    public SessionRequest(Login login) {
        super(Session.class, SessionService.class);
        this.login = login;
    }

    @Override
    public Session loadDataFromNetwork() throws Exception {
        return getService().getAccessToken(login.getUsername(),
                login.getPassword());
    }

    public String createCacheKey() {
        return "SessionRequest" + login.getUsername();
    }
}
