package pupccb.solutionsresource.com.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.astuetz.PagerSlidingTabStrip;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.MyTicketTabPagerAdapter;

/**
 * Created by User on 7/29/2015.
 */
public class MyTicket extends Fragment implements View.OnClickListener {


    private View view;
    private MyTicketTabPagerAdapter myTicketTabPagerAdapter;
    private ImageButton floatingActionButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_my_ticket_tab, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewById(view);
    }

    private void findViewById(View view) {

//        floatingActionButton = (ImageButton)view.findViewById(R.id.floatingActionButton);
//        floatingActionButton.setOnClickListener(this);

        myTicketTabPagerAdapter = new MyTicketTabPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(myTicketTabPagerAdapter);
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.pagerSlidingTabStrip);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.white));
        pagerSlidingTabStrip.setTextColor(getResources().getColor(R.color.white));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
//            case R.id.floatingActionButton:
//                Toast.makeText(getActivity(), getString(R.string.floating_button), Toast.LENGTH_SHORT).show();
//                break;
        }
    }


}
