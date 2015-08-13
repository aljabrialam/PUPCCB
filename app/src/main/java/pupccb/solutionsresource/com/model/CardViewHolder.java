package pupccb.solutionsresource.com.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pupccb.solutionsresource.com.R;


public class CardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView nameView;
    public TextView likeButton;
    public TextView likeCount;
    public ImageView commentButton;
    public TextView commentCount;
    public ImageView shareButton;
    public TextView shareCount;

    public CardViewHolder(View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.icon);
        nameView = (TextView) itemView.findViewById(R.id.item_name);
        likeButton = (TextView) itemView.findViewById(R.id.like);
        likeCount = (TextView) itemView.findViewById(R.id.likeCount);
        commentButton = (ImageView) itemView.findViewById(R.id.comment);
        commentCount = (TextView) itemView.findViewById(R.id.commentCount);
        shareButton = (ImageView) itemView.findViewById(R.id.share);
        shareCount = (TextView) itemView.findViewById(R.id.shareCount);
    }
}
