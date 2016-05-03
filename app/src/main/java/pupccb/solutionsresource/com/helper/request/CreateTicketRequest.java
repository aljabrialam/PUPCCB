package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.TicketService;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.TicketResponse;

/**
 * Created by User on 7/16/2015.
 */
public class CreateTicketRequest extends RetrofitSpiceRequest<TicketResponse, TicketService> {

    private Ticket ticket;

    public CreateTicketRequest(Ticket ticket) {
        super(TicketResponse.class, TicketService.class);
        this.ticket = ticket;
    }

    @Override
    public TicketResponse loadDataFromNetwork() throws Exception {
        return getService().createTicket(
                ticket.getIncident_date_time(),
                ticket.getSubject(),
                ticket.getAgency(),
                ticket.getComplainee(),
                ticket.getIncident_details(),
                ticket.getUser_id(),
                ticket.getFile(),
                ticket.getAnonymous()
        );
    }

    public String createCacheKey() {
        return "TicketRequest" + ticket.getUser_id();
    }
}
