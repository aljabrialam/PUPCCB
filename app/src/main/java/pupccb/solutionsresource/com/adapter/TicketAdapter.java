package pupccb.solutionsresource.com.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Ticket;

/**
 * Created by User on 8/5/2015.
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {


    private List<Ticket> tickets;


    public TicketAdapter(List<Ticket> tickets){
        this.tickets = tickets;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_info_card_view, viewGroup, false);
        TicketViewHolder ticketViewHolder = new TicketViewHolder(v);
        return ticketViewHolder;
    }

    @Override
    public void onBindViewHolder(TicketViewHolder ticketViewHolder, int i) {
        if(tickets.get(i).status.equalsIgnoreCase("Open")){
            ticketViewHolder.status.setBackgroundResource(R.color.open);
        }else if(tickets.get(i).status.equalsIgnoreCase("Resolved")){
            ticketViewHolder.status.setBackgroundResource(R.color.resolved);
        }else if(tickets.get(i).status.equalsIgnoreCase("Ongoing")){
            ticketViewHolder.status.setBackgroundResource(R.color.ongoing);
        }
        ticketViewHolder.status.setText(tickets.get(i).status);
        ticketViewHolder.title.setText(tickets.get(i).title);
        ticketViewHolder.assignTo.setText(tickets.get(i).assignTo);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView assignTo;
        TextView status;


        TicketViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.ticket_info_card);
            title = (TextView)itemView.findViewById(R.id.ticketSubjectTextView);
            assignTo = (TextView)itemView.findViewById(R.id.assignedToTextView);
            status = (TextView)itemView.findViewById(R.id.ticketDetailStatusTextView);
        }
    }

}
