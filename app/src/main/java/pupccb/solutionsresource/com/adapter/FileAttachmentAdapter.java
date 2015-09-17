package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.model.RecyclerItem;

/**
 * Created by User on 8/25/2015.
 */
public class FileAttachmentAdapter extends RecyclerView.Adapter<FileAttachmentAdapter.FileAttachmentViewHolder> {

    private ArrayList<FileAttachment> fileAttachments = new ArrayList<>();
    private Activity activity;
    private Communicator communicator;

    public FileAttachmentAdapter(Activity activity, ArrayList<FileAttachment> fileAttachments) {
        this.activity = activity;
        this.fileAttachments = fileAttachments;
    }

    public void updateList(ArrayList<FileAttachment> fileAttachments){
        this.fileAttachments = fileAttachments;
        notifyDataSetChanged();
    }

    public void addItem(int position, FileAttachment fileAttachment) {
        fileAttachments.add(fileAttachment);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        fileAttachments.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FileAttachmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_attachment, viewGroup, false);
        final FileAttachmentViewHolder fileAttachmentViewHolder = new FileAttachmentViewHolder(view);

        communicator = (Communicator) activity;

        fileAttachmentViewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.adapterSelectedTicket(view, fileAttachmentViewHolder.getAdapterPosition());
            }
        });

        return fileAttachmentViewHolder;
    }

    @Override
    public void onBindViewHolder(FileAttachmentViewHolder fileAttachmentViewHolder, final int i) {

        FileAttachment selectedItem = fileAttachments.get(i);
        fileAttachmentViewHolder.file.setText(selectedItem.getFile_name());
        fileAttachmentViewHolder.size.setText(humanReadableByteCount(Long.parseLong(selectedItem.getFile_size()), true));
        Uri uri = Uri.fromFile(new File(selectedItem.getFile_attachment_path()));
        Picasso.with(activity).load(uri).into(fileAttachmentViewHolder.imageView);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return fileAttachments != null ? fileAttachments.size() : 0;
    }

    public List<FileAttachment> getItems() {
        return fileAttachments;
    }

    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public interface Communicator {
        void adapterSelectedTicket(View view, int position);
    }

    public class FileAttachmentViewHolder extends RecyclerView.ViewHolder {
        TextView file, size;
        CardView cardView;
        ImageView imageView, imageViewDelete;

        FileAttachmentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_attachment);
            imageView = (ImageView) itemView.findViewById(R.id.attachmentImage);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDelete);
            file = (TextView) itemView.findViewById(R.id.filename);
            size = (TextView) itemView.findViewById(R.id.filesize);
        }
    }

}
