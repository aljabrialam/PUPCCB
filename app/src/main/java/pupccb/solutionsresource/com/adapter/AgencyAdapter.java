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
import pupccb.solutionsresource.com.model.Agency;

/**
 * Created by User on 9/2/2015.
 */
public class AgencyAdapter extends ArrayAdapter<Agency> {

    private Communicator communicator;
    private int layoutResourceId;
    private ArrayList<Agency> agencies;
    private ArrayList<Agency> originalList;
    private MethodTypes methodTypes;
    private ItemFilter filter;

    public void updateOriginalDataSet(List<Agency> list) {
        this.originalList.clear();
        this.originalList.addAll(list);
    }

    public void removeFromOriginalList(Agency agency) {
        this.agencies.remove(agency);
        this.originalList.remove(agency);
    }

    public void removeAllItem() {
        this.agencies.clear();
        this.originalList.clear();
    }

    public Agency getAgency(String agency_id) {
        for (Agency item : originalList) {
            if (item.getId().equals(agency_id)) {
                return item;
            }
        }
        return null;
    }

    public enum MethodTypes {
        READ
    }

    private static class ViewHolder {
        TextView textAgencyName;
        ImageView image1;
    }

    public AgencyAdapter(Context context, int layoutResourceId, ArrayList<Agency> onlineList, ArrayList<Agency> offlineList, MethodTypes methodTypes) {
        super(context, layoutResourceId, onlineList != null ? onlineList : offlineList);
        this.communicator = (Communicator) context;
        this.layoutResourceId = layoutResourceId;
        this.agencies = new ArrayList<Agency>();
        this.originalList = new ArrayList<Agency>();
        this.methodTypes = methodTypes;
        if (MethodTypes.READ == methodTypes && onlineList != null) {
            for (Agency offline : offlineList) {
                for (Agency online : onlineList) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Agency child = getItem(position);
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

        viewHolder.textAgencyName.setText(child.getAgencyname());

        if(MethodTypes.READ == methodTypes) {
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

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<Agency> filteredItems = new ArrayList<Agency>();
                for (Agency item : originalList) {
                    if (item.getAgencyname().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
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
            agencies = (ArrayList<Agency>) results.values;
            notifyDataSetChanged();
            clear();
            for (Agency item : agencies) {
                add(item);
            }
            notifyDataSetInvalidated();
        }
    }

    public interface Communicator {
        void adapterSelectedAgency(Agency agency);
    }
}
