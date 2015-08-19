package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.CardAdapter;
import pupccb.solutionsresource.com.model.RecyclerItem;
import pupccb.solutionsresource.com.util.ScreenSize;


public class CardGrid extends Fragment implements CardAdapter.RecyclerCardCallback, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = CardGrid.class.getSimpleName();

    private AppCompatActivity appCompatActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<RecyclerItem> recyclerItems;
    private View view;

    public static CardGrid newInstance() {
        return new CardGrid();
    }

    @Override
    public void onAttach(Activity activity) {
        appCompatActivity = (AppCompatActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_recycler_card, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewById(view);
    }

    public void findViewById(View view) {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        cardAdapter = new CardAdapter(getAppCompatActivity(), this);
        gridLayoutManager = new GridLayoutManager(getAppCompatActivity(), ScreenSize.getMaxColumnsForScreen(getAppCompatActivity(), 300));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(cardAdapter);
    }

    @Override
    public void onRefresh() {
        getSwipeRefreshLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 2000);
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }


    @Override
    public void onItemImageClick(int position) {
    }

    @Override
    public void onItemLikeButtonClick(int position) {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.like_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemCommentButtonClick(int position) {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.comment_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemShareButtonClick(int position) {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.share_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
