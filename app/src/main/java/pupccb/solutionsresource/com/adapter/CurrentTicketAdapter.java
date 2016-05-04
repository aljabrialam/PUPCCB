package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.activity.TicketDetails;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.util.PrettyTime;
import pupccb.solutionsresource.com.util.RequestCodes;

/**
 * Created by User on 8/5/2015.
 */
public class CurrentTicketAdapter extends RecyclerView.Adapter<CurrentTicketAdapter.TicketViewHolder> {

    private final LayoutInflater mInflater;
    private final List<TicketInfo> ticketInfoList;
    private final Activity activity;
    private TextView assignTo;

    public CurrentTicketAdapter(Activity activity, List<TicketInfo> ticketInfoList) {
        this.activity = activity;
        mInflater = LayoutInflater.from(activity);
        this.ticketInfoList = new ArrayList<>(ticketInfoList);
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = mInflater.inflate(R.layout.card_tickets, viewGroup, false);
        assignTo = (TextView) view.findViewById(R.id.assignedToLabelTextView);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder ticketViewHolder, int i) {
        final TicketInfo selectedItem = ticketInfoList.get(i);
        if (!selectedItem.getStatus().equalsIgnoreCase("4")) {

        }

        if (ticketInfoList.get(i).status.equalsIgnoreCase("1")) {
            ticketViewHolder.status.setBackgroundResource(R.color.open);
            ticketViewHolder.status.setText("New");
        } else if (ticketInfoList.get(i).status.equalsIgnoreCase("2")) {
            ticketViewHolder.status.setBackgroundResource(R.color.in_progress);
            ticketViewHolder.status.setText("In Progress");
        } else if (ticketInfoList.get(i).status.equalsIgnoreCase("3")) {
            ticketViewHolder.status.setBackgroundResource(R.color.pending);
            ticketViewHolder.status.setText("Pending");
        } else if (ticketInfoList.get(i).status.equalsIgnoreCase("4")) {
            ticketViewHolder.status.setBackgroundResource(R.color.cancelled);
            ticketViewHolder.status.setText("Cancelled");
        } else if (ticketInfoList.get(i).status.equalsIgnoreCase("5")) {
            ticketViewHolder.status.setBackgroundResource(R.color.closed);
            ticketViewHolder.status.setText("Closed");
        }

        if (selectedItem.getAssignee().equals("")) {
            assignTo.setVisibility(View.INVISIBLE);
        } else {
            assignTo.setVisibility(View.VISIBLE);
        }

        ticketViewHolder.subject.setText(selectedItem.getSubject());
        ticketViewHolder.assignee.setText(selectedItem.getAssignee());
        ticketViewHolder.date.setText(checkDateTime(selectedItem.getUpdated_at()));
        ticketViewHolder.ticketNumber.setText(selectedItem.getTicket_id());

        if (checkMinutes(PrettyTime.toDuration(System.currentTimeMillis() - (new BaseHelper().convertDateTimeToMillis(selectedItem.getUpdated_at()))))){
            ticketViewHolder.status.setTypeface(null, Typeface.BOLD);
            ticketViewHolder.subject.setTypeface(null, Typeface.BOLD);
            ticketViewHolder.assignee.setTypeface(null, Typeface.BOLD);
            ticketViewHolder.date.setTypeface(null, Typeface.BOLD);
        }
    }

    public String checkDateTime(String dateTime) {
        String formattedDateTime;

        String dt = PrettyTime.toDuration(System.currentTimeMillis() - (new BaseHelper().convertDateTimeToMillis(dateTime)));
        if (dt.contains("day") || dt.contains("hour") || dt.contains("month")) {
            formattedDateTime = new BaseHelper().formatDateTime(dateTime);
        } else {
            formattedDateTime = dt;
        }
        return formattedDateTime;
    }

    public boolean checkMinutes(String time) {
        String numberOnly = time.replaceAll("\\D+", "");
        Log.e("checkMinutes", numberOnly);
        int timeCount = Integer.parseInt(numberOnly);
        if (time.contains("seconds")) {
            if (timeCount <= 59) {
                return true;
            } else {
                return false;
            }
        } else if (time.contains("minutes") || time.contains("minute")) {
            if (timeCount <= 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return ticketInfoList != null ? ticketInfoList.size() : 0;
    }

    public List<TicketInfo> getItems() {
        return ticketInfoList;
    }

    public void animateTo(List<TicketInfo> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<TicketInfo> newModels) {
        for (int i = ticketInfoList.size() - 1; i >= 0; i--) {
            final TicketInfo model = ticketInfoList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<TicketInfo> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final TicketInfo model = newModels.get(i);
            if (!ticketInfoList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<TicketInfo> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final TicketInfo model = newModels.get(toPosition);
            final int fromPosition = ticketInfoList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void addItem(int position, TicketInfo model) {
        ticketInfoList.add(position, model);
        notifyItemInserted(position);
    }

    public TicketInfo removeItem(int position) {
        final TicketInfo model = ticketInfoList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void moveItem(int fromPosition, int toPosition) {
        final TicketInfo model = ticketInfoList.remove(fromPosition);
        ticketInfoList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void ticketDetails(int position) {
        Intent intent = new Intent(activity, TicketDetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketInfoList", ticketInfoList.get(position));
        intent.putExtras(bundle);
        ((Activity) activity).startActivityForResult(intent, RequestCodes.CANCEL_TICKET);
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView subject, assignee, status, date, ticketNumber;
        CardView cardView;

        TicketViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.ticket_info_card);
            subject = (TextView) itemView.findViewById(R.id.ticketSubjectTextView);
            assignee = (TextView) itemView.findViewById(R.id.assignedToTextView);
            status = (TextView) itemView.findViewById(R.id.ticketDetailStatusTextView);
            date = (TextView) itemView.findViewById(R.id.dateTimeHolder);
            ticketNumber = (TextView) itemView.findViewById(R.id.ticketNumberTextView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ticketDetails(getAdapterPosition());
            status.setTypeface(null, Typeface.NORMAL);
            subject.setTypeface(null, Typeface.NORMAL);
            assignee.setTypeface(null, Typeface.NORMAL);
            date.setTypeface(null, Typeface.NORMAL);
        }
    }
}
