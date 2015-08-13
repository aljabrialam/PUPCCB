package pupccb.solutionsresource.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pupccb.solutionsresource.com.R;


public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private AppCompatActivity appCompatActivity;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Activity activity)
    {
        appCompatActivity = (AppCompatActivity)activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (swipeRefreshLayout == null)
        {
            swipeRefreshLayout = (SwipeRefreshLayout)inflater.inflate(R.layout.fragment_base, container, false);
            swipeRefreshLayout.setColorSchemeResources( R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorPrimary,R.color.colorPrimaryDark);
            swipeRefreshLayout.setOnRefreshListener( this );

        }
        return swipeRefreshLayout;
    }

    @Override
    public void onRefresh()
    {
        getSwipeRefreshLayout().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 2000);
    }

    public AppCompatActivity getAppCompatActivity()
    {
        return appCompatActivity;
    }
    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return swipeRefreshLayout;
    }
}
