package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.CommentList;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by User on 9/14/2015.
 */
public interface CommentListService {

    @GET("/api/v1/comments/{ticket_id}")
    CommentList getComments(@Path("ticket_id") String ticket_id);

}
