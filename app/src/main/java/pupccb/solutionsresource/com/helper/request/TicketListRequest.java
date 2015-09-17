package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.TicketListService;
import pupccb.solutionsresource.com.model.TicketList;

/**
 * Created by User on 9/14/2015.
 */
public class TicketListRequest extends RetrofitSpiceRequest<TicketList, TicketListService> {

    String id;

    public TicketListRequest(String id) {
        super(TicketList.class, TicketListService.class);
        this.id = id;
    }

    @Override
    public TicketList loadDataFromNetwork() throws Exception {
        return getService().getTickets(id);
    }

    public String createCacheKey() {
        return "TicketListRequest";
    }

}
