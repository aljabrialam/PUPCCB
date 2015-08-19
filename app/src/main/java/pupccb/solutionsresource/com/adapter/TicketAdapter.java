package pupccb.solutionsresource.com.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.TicketInfo;

/**
 * Created by User on 8/5/2015.
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder>{


    private List<TicketInfo> ticketInfos;
    private Communicator communicator;
    private AppCompatActivity appCompatActivity;

    public TicketAdapter(AppCompatActivity appCompatActivity, List<TicketInfo> ticketInfos, Communicator communicator){
        this.appCompatActivity = appCompatActivity;
        this.ticketInfos = ticketInfos;
        this.communicator = communicator;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tickets, viewGroup, false);
        final TicketViewHolder ticketViewHolder = new TicketViewHolder(v);

        ticketViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.adapterSelectedTicket(view, ticketViewHolder.getAdapterPosition());
            }
        });

        return ticketViewHolder;
    }

    @Override
    public void onBindViewHolder(TicketViewHolder ticketViewHolder, final int i) {

        TicketInfo selectedItem = ticketInfos.get(i);
        if(ticketInfos.get(i).status.equalsIgnoreCase("Open")){
            ticketViewHolder.status.setBackgroundResource(R.color.open);
        }else if(ticketInfos.get(i).status.equalsIgnoreCase("Resolved")){
            ticketViewHolder.status.setBackgroundResource(R.color.resolved);
        }else if(ticketInfos.get(i).status.equalsIgnoreCase("Ongoing")){
            ticketViewHolder.status.setBackgroundResource(R.color.ongoing);
        }
        ticketViewHolder.status.setText(selectedItem.getStatus());
        ticketViewHolder.title.setText(selectedItem.getTitle());
        ticketViewHolder.assignTo.setText(selectedItem.getAssignTo());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return ticketInfos != null ? ticketInfos.size() : 0;
    }

    public List<TicketInfo> getItems() {
        return ticketInfos;
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title;
        TextView assignTo;
        TextView status;

        TicketViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.ticket_info_card);
            title = (TextView)itemView.findViewById(R.id.ticketSubjectTextView);
            assignTo = (TextView)itemView.findViewById(R.id.assignedToTextView);
            status = (TextView)itemView.findViewById(R.id.ticketDetailStatusTextView);
        }

    }


    public interface Communicator {
        void adapterSelectedTicket (View view, int position);
    }
}
