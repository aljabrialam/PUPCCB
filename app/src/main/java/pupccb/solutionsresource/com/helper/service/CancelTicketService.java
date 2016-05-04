package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.TicketResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by User on 7/16/2015.
 */
public interface CancelTicketService {

    @GET("/api/v1/cancel-ticket/{id}")
    TicketResponse cancelTicket(@Path("id") String id, @Query("user_id") String user_id);

}

