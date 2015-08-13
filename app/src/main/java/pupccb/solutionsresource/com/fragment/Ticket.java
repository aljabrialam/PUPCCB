package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.TicketAdapter;

/**
 * Created by User on 8/5/2015.
 */
public class Ticket extends Fragment {




    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private List<pupccb.solutionsresource.com.model.Ticket> tickets;
    private View view;

    public static Ticket newInstance(){
        return new Ticket();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newsfeed,container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);

        initializeData();
        initializeAdapter();

        return  view;
    }

    private void initializeAdapter(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ticketAdapter = new TicketAdapter(tickets);
        recyclerView.setAdapter(ticketAdapter);
    }

    private void initializeData(){
        tickets = new ArrayList<>();
        tickets.add(new pupccb.solutionsresource.com.model.Ticket("Open", "Lorem Ipsum", "BIR"));
        tickets.add(new pupccb.solutionsresource.com.model.Ticket("Resolved", "Lorem Ipsum", "PhilHealth"));
        tickets.add(new pupccb.solutionsresource.com.model.Ticket("Resolved", "Lorem Ipsum","DOH"));
        tickets.add(new pupccb.solutionsresource.com.model.Ticket("Resolved", "Lorem Ipsum", "PhilHealth"));
        tickets.add(new pupccb.solutionsresource.com.model.Ticket("Open", "Lorem Ipsum", "BIR"));
    }
}
