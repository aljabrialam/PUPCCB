package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.TicketResponse;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by User on 7/16/2015.
 */
public interface TicketService {

    @Multipart
    @POST("/api/v1/new-ticket")
    TicketResponse createTicket(
            @Part("date_time") String date_time,
            @Part("subject") String subject,
            @Part("agency") String agency,
            @Part("complainee") String complainee,
            @Part("incident_details") String incident_details,
            @Part("user_id") String user_id,
            @Part("file") TypedFile file
    );
}

