package pupccb.solutionsresource.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pupccb.solutionsresource.com.fragment.Home;
import pupccb.solutionsresource.com.fragment.CurrentTicket;
import pupccb.solutionsresource.com.fragment.PastTicket;

/**
 * Created by User on 7/31/2015.
 */
public class MyTicketTabPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] titles = {"Current Ticket", "Past Ticket"};
    public static final int NUM_ITEMS = 2;
    public static final int CURRENT_TICKET = 0;
    public static final int PAST_TICKET = 1;

    public MyTicketTabPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CURRENT_TICKET:
                return CurrentTicket.newInstance();
            case PAST_TICKET:
                return PastTicket.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
