package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.Session;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by User on 7/16/2015.
 */
public interface SessionService {

    @FormUrlEncoded
    @POST("/oauth/access_token")
    Session getAccessToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("scope") String scope);
}
