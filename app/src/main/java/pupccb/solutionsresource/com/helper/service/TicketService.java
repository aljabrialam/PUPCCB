package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.model.TicketResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by User on 7/16/2015.
 */
public interface TicketService {

    @FormUrlEncoded
    @POST("/api/v1/new-ticket")
    TicketResponse createTicket(
            @Field("date_time") String date_time,
            @Field("subject") String subject,
            @Field("agency") String agency,
            @Field("complainee") String complainee,
            @Field("incident_details") String incident_details,
            @Field("user_id") String user_id
    );
}

