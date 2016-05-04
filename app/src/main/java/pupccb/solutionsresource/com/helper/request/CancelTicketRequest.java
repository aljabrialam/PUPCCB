package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.CancelTicketService;
import pupccb.solutionsresource.com.helper.service.TicketService;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.TicketResponse;

/**
 * Created by User on 7/16/2015.
 */
public class CancelTicketRequest extends RetrofitSpiceRequest<TicketResponse, CancelTicketService> {

    private String id, user_id;

    public CancelTicketRequest(String id, String user_id) {
        super(TicketResponse.class, CancelTicketService.class);
        this.id = id;
        this.user_id = user_id;
    }

    @Override
    public TicketResponse loadDataFromNetwork() throws Exception {
            return getService().cancelTicket(this.id, this.user_id);
    }

    public String createCacheKey() {
        return "TicketRequest" + this.user_id;
    }
}
