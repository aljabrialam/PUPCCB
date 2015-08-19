package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.activity.NewTicket;
import pupccb.solutionsresource.com.activity.TicketDetails;
import pupccb.solutionsresource.com.adapter.TicketAdapter;
import pupccb.solutionsresource.com.model.Note;
import pupccb.solutionsresource.com.model.TicketInfo;

/**
 * Created by User on 8/5/2015.
 */
public class CurrentTicket extends Fragment implements SearchView.OnQueryTextListener, TicketAdapter.Communicator, SwipeRefreshLayout.OnRefreshListener {


    public TicketAdapter ticketAdapter;
    private AppCompatActivity appCompatActivity;
    private RecyclerView recyclerView;
    private List<TicketInfo> ticketInfos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    public static CurrentTicket newInstance() {
        return new CurrentTicket();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_tickets, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        if (swipeRefreshLayout == null) {
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setColorSchemeResources(R.color.open, R.color.resolved, R.color.ongoing, R.color.myPrimaryColor);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        initializeData();
        initializeAdapter();
        return view;
    }

    private void initializeAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ticketAdapter = new TicketAdapter(appCompatActivity, ticketInfos, this);
        recyclerView.setAdapter(ticketAdapter);


        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTicket.launch(getAppCompatActivity());
            }
        });
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

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    private void initializeData() {
        ticketInfos = new ArrayList<>();
        ticketInfos.add(new TicketInfo("Open", "Lorem Ipsum", "BIR", "2 hours ago"));
        ticketInfos.add(new TicketInfo("Resolved", "Lorem Ipsum", "PhilHealth", "August 21, 2015"));
        ticketInfos.add(new TicketInfo("Resolved", "Lorem Ipsum", "DOH", "August 28, 2015"));
        ticketInfos.add(new TicketInfo("Ongoing", "Lorem Ipsum", "PhilHealth", "August 23, 2015"));
        ticketInfos.add(new TicketInfo("Open", "Lorem Ipsum", "BIR", "August 13, 2015"));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
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
//        final List<Note> filteredModelList = filter(mModels, query);
//        homeAdapter.animateTo(filteredModelList);
//        recyclerView.scrollToPosition(0);
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
    public void adapterSelectedTicket(View view, int position) {
        TicketInfo selectedItem = ticketAdapter.getItems().get(position);
        TicketDetails.launch(getAppCompatActivity(), view, selectedItem.getStatus());
    }

    @Override
    public void onAttach(Activity activity) {
        appCompatActivity = (AppCompatActivity) activity;
        super.onAttach(activity);
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

}
