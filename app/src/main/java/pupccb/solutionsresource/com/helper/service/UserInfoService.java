package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.UserInfoResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by User on 9/14/2015.
 */
public interface UserInfoService {

    @GET("/api/v1/userinfo")
    UserInfoResponse userInfo(@Query("token") String token);
}
