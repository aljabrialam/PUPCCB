package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.activity.NewTicket;
import pupccb.solutionsresource.com.adapter.CurrentTicketAdapter;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.communicator.FragmentCommunicator;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 8/5/2015.
 */
public class CurrentTicket extends Fragment implements SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private CurrentTicketAdapter currentTicketAdapter;
    private FloatingActionButton floatingActionButton;
    private List<TicketInfo> ticketInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentCommunicator fragmentCommunicator;
    private Controller.MethodTypes methodTypes;
    private ProgressBar progressBar;
    private TextView emptyText;

    public static CurrentTicket newInstance() {
        return new CurrentTicket();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_current_tickets, container, false);
        findViewById(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getTicketList();
    }

    private void findViewById(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.open, R.color.in_progress, R.color.pending, R.color.myPrimaryColor);
        swipeRefreshLayout.setOnRefreshListener(this);

        fragmentCommunicator = (FragmentCommunicator) getActivity();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        new BaseHelper().stopSpiceManager();
        Log.e("onDetach", "Detaching Fragment");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            newTicket();
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.invalidate();
        getTicketList();
    }

    private void newTicket() {
        startActivity(new Intent(getActivity(), NewTicket.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<TicketInfo> filter(List<TicketInfo> models, String query) {
        query = query.toLowerCase();

        final List<TicketInfo> filteredModelList = new ArrayList<>();
        for (TicketInfo model : models) {
            final String text = model.getSubject().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (recyclerView.getAdapter() != null) {
            final List<TicketInfo> filteredModelList = filter(ticketInfoList, query);
            currentTicketAdapter.animateTo(filteredModelList);
            recyclerView.scrollToPosition(0);
        }
        return true;
    }

    public void onEmpty() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        if (ticketInfoList.size() > 0) {
            emptyText.setVisibility(View.INVISIBLE);
        } else {
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    public void setError(String message, Controller.MethodTypes methodTypes) {
        this.methodTypes = methodTypes;
        new BaseHelper().toastMessage(getActivity(), 3000, ToastMessage.MessageType.DANGER, message);
        onEmpty();
    }

    public void setData(List<TicketInfo> ticketInfoList) {
        this.ticketInfoList = ticketInfoList;
        currentTicketAdapter = new CurrentTicketAdapter(getActivity(), this.ticketInfoList);
        recyclerView.setAdapter(currentTicketAdapter);
        onEmpty();
    }

    public void getTicketList() {
        fragmentCommunicator.getTicketList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == RequestCodes.CANCEL_TICKET && resultCode == Activity.RESULT_OK) {
            onRefresh();
        }
    }
}
