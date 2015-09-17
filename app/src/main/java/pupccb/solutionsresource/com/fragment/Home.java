package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.CardAdapter;
import pupccb.solutionsresource.com.adapter.HomeAdapter;
import pupccb.solutionsresource.com.model.Note;
import pupccb.solutionsresource.com.model.RecyclerItem;

/**
 * Created by User on 7/29/2015.
 */
public class Home extends Fragment implements SearchView.OnQueryTextListener, HomeAdapter.RecyclerCardCallback, SwipeRefreshLayout.OnRefreshListener{


    public static final String TAG = CardGrid.class.getSimpleName();



    private AppCompatActivity appCompatActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Note> mModels;
    private CardAdapter cardAdapter;

    private static final String[] LIST_TITLES = {"shopping", "to bring", "on sale", "look for",
            "buy", "get rid of"};


    @Override
    public void onAttach(Activity activity)
    {
        appCompatActivity = (AppCompatActivity)activity;
        super.onAttach(activity);
    }

    public static Home newInstance() {
        return new Home();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (swipeRefreshLayout == null)
        {
            swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setColorSchemeResources( R.color.open,R.color.resolved,R.color.ongoing,R.color.myPrimaryColor);
            swipeRefreshLayout.setOnRefreshListener( this );

        }

        recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mModels = new ArrayList<>();

        for (String search : LIST_TITLES  ) {
            mModels.add(new Note(search,search,search,0,0));
        }

        homeAdapter = new HomeAdapter(getAppCompatActivity(),10, mModels);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Note> filteredModelList = filter(mModels, query);
        homeAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Note> filter(List<Note> models, String query) {
        query = query.toLowerCase();

        final List<Note> filteredModelList = new ArrayList<>();
        for (Note model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



    @Override
    public void onRefresh()
    {
        getSwipeRefreshLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 2000);
    }

    public AppCompatActivity getAppCompatActivity()
    {
        return appCompatActivity;
    }
    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return swipeRefreshLayout;
    }


    @Override
    public void onItemImageClick(int position)
    {
        RecyclerItem selectedItem = cardAdapter.getItems().get(position);
    }

    @Override
    public void onItemLikeButtonClick(int position)
    {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.like_label), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemCommentButtonClick(int position)
    {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.comment_label), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemShareButtonClick(int position)
    {
        Toast.makeText(getAppCompatActivity(), getAppCompatActivity().getString(R.string.share_label), Toast.LENGTH_SHORT).show();
    }

}
