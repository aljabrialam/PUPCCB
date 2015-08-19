package pupccb.solutionsresource.com.adapter;

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

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.fragment.CurrentTicket;
import pupccb.solutionsresource.com.fragment.PastTicket;
import pupccb.solutionsresource.com.model.ViewPagerTab;

/**
 * Created by User on 7/31/2015.
 */
public class MyTicketTabPagerAdapter extends FragmentStatePagerAdapter  implements PagerSlidingTabStrip.CustomTabProvider {

    ArrayList<ViewPagerTab> viewPagerTabs;

    public MyTicketTabPagerAdapter(FragmentManager fragmentManager, ArrayList<ViewPagerTab> viewPagerTabs) {
        super(fragmentManager);
        this.viewPagerTabs = viewPagerTabs;
    }

    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        RelativeLayout tabLayout = (RelativeLayout)
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_layout, null);

        TextView tabTitle = (TextView) tabLayout.findViewById(R.id.tab_title);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);

        ViewPagerTab tab = viewPagerTabs.get(i);

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
//        RelativeLayout tabLayout = (RelativeLayout) view;
//        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
//        badge.setVisibility(View.GONE);
    }

    @Override
    public void tabUnselected(View view) {

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CurrentTicket.newInstance();
            case 1:
                return PastTicket.newInstance();
        }
        return CurrentTicket.newInstance();
    }

    @Override
    public int getCount() {
        return viewPagerTabs.size();
    }

}
