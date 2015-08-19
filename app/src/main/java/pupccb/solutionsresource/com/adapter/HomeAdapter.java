package pupccb.solutionsresource.com.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.Note;
import pupccb.solutionsresource.com.model.NotesViewHolder;

/**
 * Created by User on 7/29/2015.
 */
public class HomeAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private final Context context;
    private Communicator communicator;

    private Note[] notes;
    private final List<Note> mModels;

    public HomeAdapter(Context context, int numNotes, List<Note> models, Communicator communicator) {
        this.context = context;
        notes = generateNotes(context, numNotes);
        mModels = new ArrayList<>(models);
        this.communicator = communicator;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);

        final NotesViewHolder notesViewHolder = new NotesViewHolder(view);
        notesViewHolder.noteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.adapterSelectedNote(view, notesViewHolder.getAdapterPosition());
            }
        });

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Note noteModel = notes[position];
        String title = noteModel.getTitle();
        String note = noteModel.getNote();
        String info = noteModel.getInfo();
        int infoImage = noteModel.getInfoImage();
        int color = noteModel.getColor();

        // Set text
        holder.titleTextView.setText(title);
        holder.noteTextView.setText(note);
        holder.infoTextView.setText(info);

        // Set image
        holder.infoImageView.setImageResource(infoImage);

        // Set visibilities
        holder.titleTextView.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        holder.noteTextView.setVisibility(TextUtils.isEmpty(note) ? View.GONE : View.VISIBLE);
        holder.infoLayout.setVisibility(TextUtils.isEmpty(info) ? View.GONE : View.VISIBLE);

        // Set padding
        int paddingTop = (holder.titleTextView.getVisibility() != View.VISIBLE) ? 0
                : holder.itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.note_content_spacing);
        holder.noteTextView.setPadding(holder.noteTextView.getPaddingLeft(), paddingTop,
                holder.noteTextView.getPaddingRight(), holder.noteTextView.getPaddingBottom());

        // Set background color
        ((CardView) holder.itemView).setCardBackgroundColor(color);
    }


    @Override
    public int getItemCount() {
        return notes.length;
    }

    public Note[] generateNotes(Context context, int numNotes) {
        Note[] notes = new Note[numNotes];
        for (int i = 0; i < notes.length; i++) {
            notes[i] = Note.randomNote(context);
        }
        return notes;
    }

    public void animateTo(List<Note> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Note> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final Note model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Note> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Note model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Note> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Note model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Note removeItem(int position) {
        final Note model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Note model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Note model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface Communicator {
        void adapterSelectedNote(View view, int position);
    }

}
