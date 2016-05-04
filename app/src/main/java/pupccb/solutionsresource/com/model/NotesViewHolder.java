package pupccb.solutionsresource.com.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pupccb.solutionsresource.com.R;

/**
 * Created by User on 7/30/2015.
 */
public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView,noteTextView, infoTextView;
    public LinearLayout infoLayout;
    public ImageView infoImageView;
    public CardView noteCardView;

    public NotesViewHolder(View itemView) {
        super(itemView);
        noteCardView = (CardView) itemView.findViewById(R.id.noteCardView);
        titleTextView = (TextView) itemView.findViewById(R.id.note_title);
        noteTextView = (TextView) itemView.findViewById(R.id.note_text);
        infoLayout = (LinearLayout) itemView.findViewById(R.id.note_info_layout);
        infoTextView = (TextView) itemView.findViewById(R.id.note_info);
        infoImageView = (ImageView) itemView.findViewById(R.id.note_info_image);
    }

}
