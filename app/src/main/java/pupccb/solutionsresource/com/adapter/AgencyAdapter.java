package pupccb.solutionsresource.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Agencies;

/**
 * Created by User on 9/2/2015.
 */
public class AgencyAdapter extends ArrayAdapter<Agencies> {

    private Communicator communicator;
    private int layoutResourceId;
    private ArrayList<Agencies> agencies;
    private ArrayList<Agencies> originalList;
    private MethodTypes methodTypes;
    private ItemFilter filter;

    public AgencyAdapter(Context context, int layoutResourceId, ArrayList<Agencies> onlineList, ArrayList<Agencies> offlineList, MethodTypes methodTypes) {
        super(context, layoutResourceId, onlineList != null ? onlineList : offlineList);
        this.communicator = (Communicator) context;
        this.layoutResourceId = layoutResourceId;
        this.agencies = new ArrayList<Agencies>();
        this.originalList = new ArrayList<Agencies>();
        this.methodTypes = methodTypes;
        if (MethodTypes.READ == methodTypes && onlineList != null) {
            for (Agencies offline : offlineList) {
                for (Agencies online : onlineList) {
                    if (offline.getId().equals(online.getId())) {
                        onlineList.remove(online);
                        break;
                    }
                }
            }
            this.agencies.addAll(onlineList);
            this.originalList.addAll(onlineList);
        } else {
            this.agencies.addAll(offlineList);
            this.originalList.addAll(offlineList);
        }
    }

    public void updateOriginalDataSet(List<Agencies> list) {
        this.originalList.clear();
        this.originalList.addAll(list);
    }

    public void removeFromOriginalList(Agencies agency) {
        this.agencies.remove(agency);
        this.originalList.remove(agency);
    }

    public void removeAllItem() {
        this.agencies.clear();
        this.originalList.clear();
    }

    public Agencies getAgency(String agency_id) {
        for (Agencies item : originalList) {
            if (item.getId().equals(agency_id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Agencies child = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResourceId, null);
            viewHolder.textAgencyName = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.image1 = (ImageView) convertView.findViewById(R.id.image1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textAgencyName.setText(child.getName());

        if (MethodTypes.READ == methodTypes) {
            viewHolder.image1.setVisibility(View.VISIBLE);
            viewHolder.image1.setBackground(getContext().getResources().getDrawable(R.drawable.ic_help_outline_black_36dp));
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    communicator.adapterSelectedAgency(child);
                }
            });
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ItemFilter();
        }
        return filter;
    }

    public enum MethodTypes {
        READ
    }

    public interface Communicator {
        void adapterSelectedAgency(Agencies agency);
    }

    private static class ViewHolder {
        TextView textAgencyName;
        ImageView image1;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<Agencies> filteredItems = new ArrayList<Agencies>();
                for (Agencies item : originalList) {
                    if (item.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        filteredItems.add(item);
                    }
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            agencies = (ArrayList<Agencies>) results.values;
            notifyDataSetChanged();
            clear();
            for (Agencies item : agencies) {
                add(item);
            }
            notifyDataSetInvalidated();
        }
    }
}
