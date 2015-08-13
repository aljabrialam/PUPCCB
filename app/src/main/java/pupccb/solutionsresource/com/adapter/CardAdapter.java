package pupccb.solutionsresource.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.model.CardViewHolder;
import pupccb.solutionsresource.com.model.RecyclerItem;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private final Context context;
    private final List<RecyclerItem> items;
//    private final List<RecyclerItem> recyclerItems;
    private final RecyclerCardCallback recyclerCardCallback;

    public CardAdapter(Context context, RecyclerCardCallback recyclerCardCallback) {//, List<RecyclerItem> recyclerItems
        this.context = context;
        this.items = getDefaultItems();
//        this.recyclerItems =  new ArrayList<>(recyclerItems);
        this.recyclerCardCallback = recyclerCardCallback;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card, viewGroup, false);

        final CardViewHolder viewholder = new CardViewHolder(v);

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCardCallback.onItemImageClick(viewholder.getAdapterPosition());
            }
        });

        viewholder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCardCallback.onItemLikeButtonClick(viewholder.getAdapterPosition());
            }
        });

        viewholder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCardCallback.onItemCommentButtonClick(viewholder.getAdapterPosition());
            }
        });

        viewholder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCardCallback.onItemShareButtonClick(viewholder.getAdapterPosition());
            }
        });

        return viewholder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, final int i) {
        RecyclerItem selectedItem = items.get(i);
        selectedItem.setImageView(viewHolder.imageView);
        viewHolder.likeCount.setText(selectedItem.getLikeCount());
        viewHolder.commentCount.setText(selectedItem.getCommentCount());
        viewHolder.shareCount.setText(selectedItem.getShareCount());
        Picasso.with(context).load(selectedItem.getUrl()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<RecyclerItem> getItems() {
        return items;
    }

    /* Creates dummy data for cards */
    private List<RecyclerItem> getDefaultItems() {
        List<RecyclerItem> items = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            RecyclerItem recyclerItem = new RecyclerItem("http://lorempixel.com/800/600/sports/" + String.valueOf(i + 1), String.valueOf(random.nextInt(800 - 0)),
                    String.valueOf(random.nextInt(300 - 0)), String.valueOf(random.nextInt(100 - 0)));
            recyclerItem.setName(" Item " + i);
            items.add(recyclerItem);
        }

        return items;
    }

    public interface RecyclerCardCallback {
        void onItemImageClick(int position);

        void onItemLikeButtonClick(int position);

        void onItemCommentButtonClick(int position);

        void onItemShareButtonClick(int position);
    }
}
