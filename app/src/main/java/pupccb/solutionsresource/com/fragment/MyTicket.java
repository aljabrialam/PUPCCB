package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.MyTicketTabPagerAdapter;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.model.ViewPagerTab;
import pupccb.solutionsresource.com.util.PrettyTime;

/**
 * Created by User on 7/29/2015.
 */
public class MyTicket extends Fragment {

    private View view;
    private ArrayList<ViewPagerTab> tabsList;
    private MyTicketTabPagerAdapter myTicketTabPagerAdapter;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

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

        tabsList = new ArrayList<>();
        tabsList.add(new ViewPagerTab("Current Tickets", 0));
        tabsList.add(new ViewPagerTab("Past Tickets", 0));

        myTicketTabPagerAdapter = new MyTicketTabPagerAdapter(getChildFragmentManager(), tabsList, getActivity());
        pager.setAdapter(myTicketTabPagerAdapter);
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
    }

    private void notifyTabStripChanged(int position, String notificationsCount) {
        LinearLayout tabHost = (LinearLayout) tabs.getChildAt(0);
        RelativeLayout tabLayout = (RelativeLayout) tabHost.getChildAt(position);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
        if (!notificationsCount.matches("")) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(notificationsCount);
        } else {
            badge.setVisibility(View.GONE);
        }
    }

    public void setTicketListResult(List<TicketInfo> ticketInfoList) {
        myTicketTabPagerAdapter.setCurrentTicketData(ticketInfoList);
        myTicketTabPagerAdapter.setPastTicketData(ticketInfoList);
        setBadge(ticketInfoList);
    }

    public void setCurrentTicketError(String message, Controller.MethodTypes methodTypes) {
        myTicketTabPagerAdapter.setCurrentTicketError(message, methodTypes);
    }

    public void setBadge(List<TicketInfo> ticketInfoList) {
        List<TicketInfo> result = new ArrayList<TicketInfo>();
        for (TicketInfo ticketInfo : ticketInfoList) {
            if (checkMinutes(PrettyTime.toDuration(System.currentTimeMillis() - (new BaseHelper().convertDateTimeToMillis(ticketInfo.getUpdated_at()))))) {
                if (!ticketInfo.getStatus().matches("4") || !ticketInfo.getStatus().matches("5")){
                    result.add(ticketInfo);
                }
            }
        }
        notifyTabStripChanged(0, result.size() > 0 ? result.size() + "" : "");
    }

    public boolean checkMinutes(String time) {
        String numberOnly = time.replaceAll("\\D+", "");
        int timeCount = Integer.parseInt(numberOnly);
        if (time.contains("seconds")) {
            if (timeCount <= 59) {
                return true;
            } else {
                return false;
            }
        } else if (time.contains("minutes") || time.contains("minute")) {
            if (timeCount <= 2) {
                return true;
            }
        }
        return false;
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
}
