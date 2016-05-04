package pupccb.solutionsresource.com.helper.service;


import pupccb.solutionsresource.com.model.TicketResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by User on 7/16/2015.
 */
public interface CommentService {


//    @FormUrlEncoded
//    @POST("/api/v1/add-comment/{ticket_id}")
//    TicketResponse addComment(
//            @Path("ticket_id") String ticket_id,
//            @Field("user_id") String user_id,
//            @Field("ticket_comment") String ticket_comment
//    );

    @Multipart
    @POST("/api/v1/add-comment/{ticket_id}")
    TicketResponse addComment(
            @Path("ticket_id") String ticket_id,
            @Part("user_id") String user_id,
            @Part("ticket_comment") String ticket_comment,
            @Part("attachment") TypedFile file
    );
}

