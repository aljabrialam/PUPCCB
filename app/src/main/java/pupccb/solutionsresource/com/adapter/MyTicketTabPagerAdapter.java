package pupccb.solutionsresource.com.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.fragment.CurrentTicket;
import pupccb.solutionsresource.com.fragment.PastTicket;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.model.ViewPagerTab;

/**
 * Created by User on 7/31/2015.
 */
public class MyTicketTabPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

    private ArrayList<ViewPagerTab> viewPagerTabs;
    private TextView tabTitle, badge;
    private RelativeLayout tabLayout;
    private ViewPagerTab tab;
    private CurrentTicket currentTicket;
    private PastTicket pastTicket;

    private CurrentTicketAdapter currentTicketAdapter;
    private Activity activity;

    public MyTicketTabPagerAdapter(FragmentManager fragmentManager, ArrayList<ViewPagerTab> viewPagerTabs, Activity activity) {
        super(fragmentManager);
        this.viewPagerTabs = viewPagerTabs;
        this.activity = activity;
        currentTicket = CurrentTicket.newInstance();
        pastTicket = PastTicket.newInstance();
    }

    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        tabLayout = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_layout, null);
        tabTitle = (TextView) tabLayout.findViewById(R.id.tab_title);
        badge = (TextView) tabLayout.findViewById(R.id.badge);
        tab = viewPagerTabs.get(i);
        tabTitle.setText(tab.title.toUpperCase());

        if (tab.notifications > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(tab.notifications));
        } else {
            badge.setVisibility(View.GONE);
        }

        return tabLayout;
    }

    @Override
    public void tabSelected(View view) {
        final RelativeLayout tabLayout = (RelativeLayout) view;
        badge = (TextView) tabLayout.findViewById(R.id.badge);
        if (tabLayout.callOnClick()) {
            badge.setVisibility(View.GONE);
        }
    }


    @Override
    public void tabUnselected(View view) {

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return currentTicket;
            case 1:
                return pastTicket;
        }
        return CurrentTicket.newInstance();
    }

    @Override
    public int getCount() {
        return viewPagerTabs.size();
    }

    public void setCurrentTicketError(String message, Controller.MethodTypes methodTypes) {
        currentTicket.setError(message, methodTypes);
        pastTicket.setError(message, methodTypes);
    }

    public void setCurrentTicketData(List<TicketInfo> ticketInfoList) {
        List<TicketInfo> result = new ArrayList<TicketInfo>();
        for (TicketInfo ticketInfo : ticketInfoList) {
            if (!ticketInfo.getStatus().matches("4") && !ticketInfo.getStatus().matches("5")) {
                result.add(ticketInfo);
            }
        }
        currentTicket.setData(result);
    }

    public void setPastTicketData(List<TicketInfo> ticketInfoList) {
        List<TicketInfo> result = new ArrayList<TicketInfo>();
        for (TicketInfo ticketInfo : ticketInfoList) {
            if (ticketInfo.getStatus().matches("4") || ticketInfo.getStatus().matches("5")) {
                result.add(ticketInfo);
            }
        }
        pastTicket.setData(result);
    }
}
