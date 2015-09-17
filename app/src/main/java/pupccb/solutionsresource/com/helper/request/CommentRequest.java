package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.CommentService;
import pupccb.solutionsresource.com.model.TicketResponse;
import retrofit.mime.TypedFile;

/**
 * Created by User on 9/14/2015.
 */
public class CommentRequest extends RetrofitSpiceRequest<TicketResponse, CommentService> {

    private String ticket_id, user_id, ticket_comment;
    private TypedFile file;

    public CommentRequest(String ticket_id, String user_id, String ticket_comment, TypedFile file) {
        super(TicketResponse.class, CommentService.class);
        this.ticket_id = ticket_id;
        this.user_id = user_id;
        this.ticket_comment = ticket_comment;
        this.file = file;
    }

    @Override
    public TicketResponse loadDataFromNetwork() throws Exception {
        return getService().addComment(ticket_id, user_id, ticket_comment, file);
    }

    public String createCacheKey() {
        return "CommentRequest " + ticket_comment;
    }

}
