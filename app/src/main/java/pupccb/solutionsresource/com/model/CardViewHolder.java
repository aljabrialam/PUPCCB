package pupccb.solutionsresource.com.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pupccb.solutionsresource.com.R;


public class CardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView filename;
    public TextView filesize;

    public CardViewHolder(View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.attachmentImage);
        filename = (TextView) itemView.findViewById(R.id.filename);
        filesize = (TextView) itemView.findViewById(R.id.filesize);
    }
}
