package pupccb.solutionsresource.com.model;

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

    public TextView titleTextView;
    public TextView noteTextView;
    public LinearLayout infoLayout;
    public TextView infoTextView;
    public ImageView infoImageView;

    public NotesViewHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView.findViewById(R.id.note_title);
        noteTextView = (TextView) itemView.findViewById(R.id.note_text);
        infoLayout = (LinearLayout) itemView.findViewById(R.id.note_info_layout);
        infoTextView = (TextView) itemView.findViewById(R.id.note_info);
        infoImageView = (ImageView) itemView.findViewById(R.id.note_info_image);
    }

    public void bind(Search model) {
        titleTextView.setText(model.getSearch());
    }
}
