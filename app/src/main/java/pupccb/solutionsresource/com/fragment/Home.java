package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AnnouncementAdapter;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.communicator.FragmentCommunicator;
import pupccb.solutionsresource.com.model.Announcement;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 7/29/2015.
 */
public class Home extends Fragment implements SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CardGrid.class.getSimpleName();
    private FragmentCommunicator fragmentCommunicator;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Announcement> announcements = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyText;
    private AnnouncementAdapter announcementAdapter;
    private Controller.MethodTypes methodTypes;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public static Home newInstance() {
        return new Home();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_home, container, false);
        collapsingToolbarLayout(view);
        findViewById(view);
        return view;
    }

    private void collapsingToolbarLayout(View view) {
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingtoolbarlayout_main);
        setCollapsingToolbarLayoutTitle(getResources().getString(R.string.ccb_long_name));

//        if (Build.VERSION.SDK_INT >= 21) {
//            ActivityManager.TaskDescription taskDescription = new
//                    ActivityManager.TaskDescription(getResources().getString(R.string.app_name),
//                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
//                getResources().getColor(R.color.myPrimaryColor));
//            setTaskDescription(taskDescription);
//        }
    }

    private void setCollapsingToolbarLayoutTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    private void findViewById(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.open, R.color.in_progress, R.color.pending, R.color.myPrimaryColor);
        swipeRefreshLayout.setOnRefreshListener(this);

        fragmentCommunicator = (FragmentCommunicator) getActivity();

        recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getAnnouncementList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (recyclerView.getAdapter() != null) {
            final List<Announcement> filteredModelList = filter(announcements, query);
            announcementAdapter.animateTo(filteredModelList);
            recyclerView.scrollToPosition(0);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Announcement> filter(List<Announcement> models, String query) {
        query = query.toLowerCase();

        final List<Announcement> filteredModelList = new ArrayList<>();
        for (Announcement model : models) {
            final String text = model.getSubject().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void onEmpty() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        if (announcements.size() > 0) {
            emptyText.setVisibility(View.INVISIBLE);
        } else {
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.invalidate();
        getAnnouncementList();
    }

    public void setError(String message, Controller.MethodTypes methodTypes) {
        this.methodTypes = methodTypes;
        new BaseHelper().toastMessage(getActivity(), 3000, ToastMessage.MessageType.DANGER, message);
        onEmpty();
    }

    public void setData(List<Announcement> announcements) {
        this.announcements = announcements;
        announcementAdapter = new AnnouncementAdapter(getActivity(), this.announcements);
        recyclerView.setAdapter(announcementAdapter);
        onEmpty();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        new BaseHelper().stopSpiceManager();
        Log.e("onDetach", "Detaching Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void getAnnouncementList() {
        fragmentCommunicator.getAnnouncementList();
    }
}
