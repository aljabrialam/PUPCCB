package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;
import pupccb.solutionsresource.com.drawer.NavigationDrawerFragment;
import pupccb.solutionsresource.com.fragment.Home;
import pupccb.solutionsresource.com.fragment.MyTicket;


public class NavigationDrawer extends AppCompatActivity implements NavigationDrawerAdapter.NavigationDrawerCallbacks, AddTicket.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragmentToReplace = null;
        switch (position) {
            case 0:
                AddTicket.launch(this);
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
                startActivity(new Intent(this, Main.class));
                finish();
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

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
