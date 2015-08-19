package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Agency;

/**
 * Created by User on 8/27/2015.
 */
public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.AgencyViewHolder> {


    private final LayoutInflater layoutInflater;
    private final List<Agency> agencyList;

    public AgencyAdapter(Activity activity, List<Agency> agencyList) {
        layoutInflater = LayoutInflater.from(activity);
        this.agencyList = new ArrayList<>(agencyList);

    }

    @Override
    public AgencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.agency_item, parent, false);
        return new AgencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgencyViewHolder holder, int position) {
        final Agency agency = agencyList.get(position);
        holder.bind(agency);
    }

    public void animateTo(List<Agency> agencyList) {
        applyAndAnimateRemovals(agencyList);
        applyAndAnimateAdditions(agencyList);
        applyAndAnimateMovedItems(agencyList);
    }

    private void applyAndAnimateRemovals(List<Agency> newModels) {
        for (int i = agencyList.size() - 1; i >= 0; i--) {
            final Agency model = agencyList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Agency> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Agency model = newModels.get(i);
            if (!agencyList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Agency> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Agency model = newModels.get(toPosition);
            final int fromPosition = agencyList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Agency removeItem(int position) {
        final Agency model = agencyList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Agency model) {
        agencyList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Agency model = agencyList.remove(fromPosition);
        agencyList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return agencyList.size();
    }

    public class AgencyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAgency;

        public AgencyViewHolder(View itemView) {
            super(itemView);
            textViewAgency = (TextView) itemView.findViewById(R.id.textViewAgency);
        }

        public void bind(Agency agency) {
            textViewAgency.setText(agency.getAgencyname());
        }
    }

}
