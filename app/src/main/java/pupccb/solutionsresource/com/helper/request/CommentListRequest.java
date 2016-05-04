package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.CommentListService;
import pupccb.solutionsresource.com.helper.service.TicketListService;
import pupccb.solutionsresource.com.model.CommentList;
import pupccb.solutionsresource.com.model.TicketList;

/**
 * Created by User on 9/14/2015.
 */
public class CommentListRequest extends RetrofitSpiceRequest<CommentList, CommentListService> {

    String ticket_id;

    public CommentListRequest(String ticket_id) {
        super(CommentList.class, CommentListService.class);
        this.ticket_id = ticket_id;
    }

    @Override
    public CommentList loadDataFromNetwork() throws Exception {
        return getService().getComments(ticket_id);
    }

    public String createCacheKey() {
        return "CommentListRequest";
    }

}
