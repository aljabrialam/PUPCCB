package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.CardAdapter;
import pupccb.solutionsresource.com.model.RecyclerItem;


public class CardGrid extends Fragment implements CardAdapter.Communicator {
    public static final String TAG = CardGrid.class.getSimpleName();

    private AppCompatActivity appCompatActivity;
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        cardAdapter = new CardAdapter(getAppCompatActivity(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cardAdapter);
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    @Override
    public void onItemImageClick(int position) {

    }

}
