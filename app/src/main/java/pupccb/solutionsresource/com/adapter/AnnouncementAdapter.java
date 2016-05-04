package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.fragment.MaterialDialog;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.model.Announcement;
import pupccb.solutionsresource.com.util.CreateMaterialDialog;
import pupccb.solutionsresource.com.util.Dialog;
import pupccb.solutionsresource.com.util.PrettyTime;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 8/5/2015.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.NotesViewHolder> {

    private final List<Announcement> announcements;
    private Activity activity;
    private Announcement announcement;

    public AnnouncementAdapter(Activity activity, List<Announcement> announcements) {
        this.activity = activity;
        this.announcements = new ArrayList<>(announcements);
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_card, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder notesViewHolder, final int i) {
        final Announcement selectedItem = announcements.get(i);
        notesViewHolder.titleTextView.setText(Html.fromHtml(selectedItem.getSubject()));
        notesViewHolder.noteTextView.setText(Html.fromHtml(selectedItem.getMessage()));
        notesViewHolder.infoTextView.setText(checkDateTime(selectedItem.getUpdated_at()));
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public List<Announcement> getItems() {
        return announcements;
    }

    public void animateTo(List<Announcement> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Announcement> newModels) {
        for (int i = announcements.size() - 1; i >= 0; i--) {
            final Announcement model = announcements.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Announcement> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Announcement model = newModels.get(i);
            if (!announcements.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Announcement> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Announcement model = newModels.get(toPosition);
            final int fromPosition = announcements.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Announcement removeItem(int position) {
        final Announcement model = announcements.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Announcement model) {
        announcements.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Announcement model = announcements.remove(fromPosition);
        announcements.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void announcementDetailDialog(int position){
        announcement = announcements.get(position);
        CreateMaterialDialog(announcement.getSubject()
                , "Close"
                , ""
                , new ViewHolder(R.layout.dialog_home_content)
                , Gravity.CENTER
                , false
                , true);

    }

    public void CreateMaterialDialog(String title, String cancel, String confirm,
                                     Holder holder, int gravity, boolean expanded, boolean cancelable) {
        TextView textViewTitle, textViewClose, textViewConfirm;
        final DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setHeader(R.layout.header)
                .setCancelable(cancelable)
                .setGravity(gravity)
                .setExpanded(expanded)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(newCommentDialogListener)
                .create();
        textViewTitle = (TextView) holder.getHeader().findViewById(R.id.title);
        textViewClose = (TextView) holder.getHeader().findViewById(R.id.close);
        WebView webView = (WebView) holder.getInflatedView().findViewById(R.id.webView);
        webView.loadData("<p align =\"justify\">" + announcement.getMessage() + "</p>", "text/html", null);
        textViewTitle.setText(title);
        textViewClose.setVisibility(View.VISIBLE);
        dialog.show();
    }

    OnClickListener newCommentDialogListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            switch (view.getId()) {
                case R.id.close:
                    dialog.dismiss();
                    break;
            }
            dialog.dismiss();
        }
    };

    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTextView, noteTextView, infoTextView;
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
            infoImageView.setBackgroundResource(R.drawable.ic_nav2);
            noteTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            announcementDetailDialog(getAdapterPosition());
        }
    }
}
