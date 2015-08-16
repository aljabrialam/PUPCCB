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
    @POST("/")
    RegistrationResponse register(
            @Field("firsname") String firstname,
            @Field("middlename") String middlename,
            @Field("lastname") String lastname,
            @Field("contactnumber") String contactnumber,
            @Field("emailaddress") String emailaddress,
            @Field("password") String passsword
    );
}
