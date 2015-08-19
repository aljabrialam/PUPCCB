package pupccb.solutionsresource.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Search;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Search> mModels;

    public ExampleAdapter(Context context, List<Search> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new ExampleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final Search model = mModels.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<Search> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Search> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final Search model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Search> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Search model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Search> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Search model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Search removeItem(int position) {
        final Search model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Search model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Search model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvText;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }

        public void bind(Search model) {
            tvText.setText(model.getSearch());
        }
    }
}
