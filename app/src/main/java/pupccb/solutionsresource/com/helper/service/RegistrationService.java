package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.Session;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by User on 7/16/2015.
 */
public interface RegistrationService {

    @FormUrlEncoded
    @POST("/api/v1/register")
    RegistrationResponse register(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("contact_number") String contact_number,
            @Field("email") String email,
            @Field("password") String password
    );
}
