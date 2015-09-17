package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.RegistrationService;
import pupccb.solutionsresource.com.helper.service.TicketService;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
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
                    ticket.getDate_time(),
                    ticket.getSubject(),
                    ticket.getAgency(),
                    ticket.getIncident_details(),
                    ticket.getComplainee(),
                    ticket.getUser_id()
            );
    }

    public String createCacheKey() {
        return "TicketRequest" + ticket.getUser_id();
    }
}
