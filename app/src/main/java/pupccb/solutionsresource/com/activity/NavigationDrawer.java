package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;
import pupccb.solutionsresource.com.drawer.NavigationDrawerFragment;
import pupccb.solutionsresource.com.fragment.Home;
import pupccb.solutionsresource.com.fragment.MyTicket;
import pupccb.solutionsresource.com.helper.BaseHelper;


public class NavigationDrawer extends AppCompatActivity implements NavigationDrawerAdapter.NavigationDrawerCommunicator {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private BaseHelper baseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        baseHelper = new BaseHelper();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragmentToReplace = null;
        switch (position) {
            case 0:
                newTicket();
                break;
            case 1:
                mTitle = "Home";
                setTitle(mTitle);
                fragmentToReplace = new Home();
                break;
            case 2:
                mTitle = "My Ticket";
                setTitle(mTitle);
                fragmentToReplace = new MyTicket();
                break;
            case 3:
                baseHelper.logout(NavigationDrawer.this, false);
                break;
            default:
                mTitle = "Home";
                setTitle(mTitle);
                fragmentToReplace = new Home();
                break;
        }

        if (fragmentToReplace != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragmentToReplace)
                    .commit();
        }
    }

    private void newTicket(){
        Intent intent = new Intent(this, NewTicket.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }
    }

}

