package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.FileAttachment;

/**
 * Created by User on 8/25/2015.
 */
public class FileAttachmentAdapter extends RecyclerView.Adapter<FileAttachmentAdapter.FileAttachmentViewHolder> {

    private int layoutResourcceId;
    private ArrayList<FileAttachment> fileAttachmentsArrayList;


    public FileAttachmentAdapter(Activity activity, ArrayList<FileAttachment> fileAttachmentsArrayList) {
        fileAttachmentsArrayList = new ArrayList<FileAttachment>();
        fileAttachmentsArrayList.addAll(fileAttachmentsArrayList);
    }

    @Override
    public FileAttachmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_attachment, viewGroup, false);
        final FileAttachmentViewHolder fileAttachmentViewHolder = new FileAttachmentViewHolder(view);

        return fileAttachmentViewHolder;
    }

    @Override
    public void onBindViewHolder(FileAttachmentViewHolder fileAttachmentViewHolder, final int i) {

        FileAttachment selectedItem = fileAttachmentsArrayList.get(i);
        fileAttachmentViewHolder.file.setText(selectedItem.getFile_name());
        fileAttachmentViewHolder.size.setText(selectedItem.getFile_size());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return fileAttachmentsArrayList != null ? fileAttachmentsArrayList.size() : 0;
    }

    public List<FileAttachment> getItems() {
        return fileAttachmentsArrayList;
    }

    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public class FileAttachmentViewHolder extends RecyclerView.ViewHolder {
        TextView file, size;
        CardView cardView;
        ImageView imageView;

        FileAttachmentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_attachment);
            imageView = (ImageView)itemView.findViewById(R.id.attachmentImage);
            file = (TextView) itemView.findViewById(R.id.filename);
            size = (TextView) itemView.findViewById(R.id.filesize);
        }
    }

}
