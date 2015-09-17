package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.TicketList;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by User on 9/14/2015.
 */
public interface TicketListService {

    @GET("/api/v1/ticket-list")
    TicketList getTickets(@Query("id") String id);
}
