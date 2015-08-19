package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.MyTicketTabPagerAdapter;
import pupccb.solutionsresource.com.model.ViewPagerTab;

/**
 * Created by User on 7/29/2015.
 */
public class MyTicket extends Fragment {

    private View view;
    private MyTicketTabPagerAdapter myTicketTabPagerAdapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private FragmentStatePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_ticket_tab, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewById(view);
    }

    private void findViewById(View view) {

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.pagerSlidingTabStrip);
        pager = (ViewPager) view.findViewById(R.id.viewPager);

        ArrayList<ViewPagerTab> tabsList = new ArrayList<>();
        tabsList.add(new ViewPagerTab("Current Ticket", 0));
        tabsList.add(new ViewPagerTab("Past Ticket", 0));

        adapter = new MyTicketTabPagerAdapter(getChildFragmentManager(), tabsList);

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        pager.setOffscreenPageLimit(2);
        notifyTabStripChanged(0, 1);
//        myTicketTabPagerAdapter = new MyTicketTabPagerAdapter(getChildFragmentManager());
//        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(myTicketTabPagerAdapter);
//        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.pagerSlidingTabStrip);
//        pagerSlidingTabStrip.setViewPager(viewPager);
//        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.white));
//        pagerSlidingTabStrip.setTextColor(getResources().getColor(R.color.white));
    }

    private void notifyTabStripChanged(int position, int notificationsCount) {
        LinearLayout tabHost = (LinearLayout) tabs.getChildAt(0);
        RelativeLayout tabLayout = (RelativeLayout) tabHost.getChildAt(position);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
        if (notificationsCount > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(notificationsCount));
        } else {
            badge.setVisibility(View.GONE);
        }
    }

}
