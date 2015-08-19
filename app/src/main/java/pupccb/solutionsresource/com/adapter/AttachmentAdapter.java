package pupccb.solutionsresource.com.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Attachment;

/**

 */
public class AttachmentAdapter extends ArrayAdapter<Attachment> {

    private int layoutResourceId;
    private PackageManager packageManager;

    private static class ViewHolder {
        ImageView imageViewIcon;
        TextView textViewApplication;
    }

    public AttachmentAdapter(Context context, int layoutResourceId, ArrayList<Attachment> arrayList) {
        super(context, layoutResourceId, arrayList);
        this.layoutResourceId = layoutResourceId;
        this.packageManager = context.getPackageManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attachment child = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResourceId, null);
            viewHolder.imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            viewHolder.textViewApplication = (TextView) convertView.findViewById(R.id.textViewApplication);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageViewIcon.setImageDrawable(child.getResolveInfo().loadIcon(packageManager));
        viewHolder.textViewApplication.setText(child.getResolveInfo().loadLabel(packageManager));

        return convertView;
    }
}
