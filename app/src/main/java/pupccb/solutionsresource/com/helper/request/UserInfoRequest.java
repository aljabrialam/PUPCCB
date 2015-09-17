package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.UserInfoService;
import pupccb.solutionsresource.com.model.UserInfo;
import pupccb.solutionsresource.com.model.UserInfoResponse;

/**
 * Created by User on 9/14/2015.
 */
public class UserInfoRequest extends RetrofitSpiceRequest<UserInfoResponse, UserInfoService> {

    private UserInfo userInfo;
    public UserInfoRequest(UserInfo userInfo) {
        super(UserInfoResponse.class, UserInfoService.class);
        this.userInfo = userInfo;
    }

    @Override
    public UserInfoResponse loadDataFromNetwork() throws Exception {
        return getService().userInfo(userInfo.getToken());
    }

    public String createCacheKey() {
        return "UserInfoRequest" + userInfo.getToken();
    }

}
