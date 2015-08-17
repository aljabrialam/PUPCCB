package pupccb.solutionsresource.com.activity;

import android.app.Activity;
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
import android.widget.ImageButton;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;
import pupccb.solutionsresource.com.drawer.NavigationDrawerFragment;
import pupccb.solutionsresource.com.fragment.*;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationDrawerAdapter.NavigationDrawerCallbacks, AddTicket.NavigationDrawerCallbacks, View.OnClickListener {


    private ImageButton floatingActionButton;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

//        floatingActionButton = (ImageButton)findViewById(R.id.floatingActionButton);
//        floatingActionButton.setOnClickListener(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragmentToReplace = PlaceholderFragment.newInstance(position + 1);
        switch (position) {
            case 0:
//                mTitle = "Add Ticket";
//                setTitle(mTitle);
                AddTicket.launch(this);
                break;
            case 1:
                mTitle = "Home";
                setTitle(mTitle);
                fragmentToReplace = new Home().newInstance();
                break;
            case 2:
                mTitle = "My Ticket";
                setTitle(mTitle);
                fragmentToReplace = new MyTicket();
                break;
//            case 3:
//                mTitle = "Call 16565";
//                setTitle(mTitle);
//                fragmentToReplace = new Newsfeed();
//                break;
            case 3:
                startActivity(new Intent(this, Main.class));
                this.finish();
                break;

            default:
                mTitle = "Home";
                setTitle(mTitle);
                fragmentToReplace = new Home();
                break;

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentToReplace)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Add Ticket";
                setTitle(mTitle);
                return;
            case 2:
                mTitle = "Home";
                setTitle(mTitle);
                return;
            case 3:
                mTitle = "My Ticket";
                setTitle(mTitle);
                return;
            case 4:
                mTitle = "Call CCB";
                setTitle(mTitle);
                return;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
//            case R.id.floatingActionButton:
//                Toast.makeText(this, getString(R.string.floating_button), Toast.LENGTH_SHORT).show();
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            startActivity(new Intent(this, Main.class));
            this.finish();
            super.onBackPressed();
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
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavigationDrawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }
    }


}
