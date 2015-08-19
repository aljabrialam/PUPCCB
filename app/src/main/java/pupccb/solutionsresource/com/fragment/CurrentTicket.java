package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.activity.NewTicket;
import pupccb.solutionsresource.com.activity.TicketDetails;
import pupccb.solutionsresource.com.adapter.TicketAdapter;
import pupccb.solutionsresource.com.model.TicketInfo;

/**
 * Created by User on 8/5/2015.
 */
public class CurrentTicket extends Fragment implements TicketAdapter.Communicator, SwipeRefreshLayout.OnRefreshListener {


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
        ticketInfos.add(new TicketInfo("Open", "Lorem Ipsum", "BIR"));
        ticketInfos.add(new TicketInfo("Resolved", "Lorem Ipsum", "PhilHealth"));
        ticketInfos.add(new TicketInfo("Resolved", "Lorem Ipsum", "DOH"));
        ticketInfos.add(new TicketInfo("Ongoing", "Lorem Ipsum", "PhilHealth"));
        ticketInfos.add(new TicketInfo("Open", "Lorem Ipsum", "BIR"));
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
