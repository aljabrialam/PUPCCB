package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;
import pupccb.solutionsresource.com.drawer.NavigationDrawerFragment;
import pupccb.solutionsresource.com.fragment.Home;
import pupccb.solutionsresource.com.fragment.MyTicket;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.helper.communicator.FragmentCommunicator;
import pupccb.solutionsresource.com.model.Announcement;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.RequestCodes;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;


public class NavigationDrawer extends AppCompatActivity implements FragmentCommunicator, NavigationDrawerAdapter.NavigationDrawerCommunicator {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private Controller onlineController;
    private SharedPreferences sharedPreferences;
    private MyTicket myTicket;
    private Controller.MethodTypes methodTypes;
    private Home home;
    private LoadToast loadToast;
    private Toolbar toolbar;
    private View view;
    private FragmentCommunicator fragmentCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_navigation_drawer, null);
        setContentView(view);
        toolBarNavigationDrawer(view);
        start();
    }

    private void toolBarNavigationDrawer(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) view.findViewById(R.id.drawer), toolbar);
    }

    public void start() {
        onlineController = new Controller(new OnlineHelper());
        sharedPreferences = BaseHelper.getSharedPreference(this);
        loadToast = new LoadToast(this);
    }

    private void loadToast(String message) {
        loadToast.setProgressColor(R.color.selected_selected_blue);
        loadToast.setProgressColor(R.color.half_black);
        loadToast.setTranslationY(200);
        loadToast.setText(message);
        loadToast.show();
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        this.methodTypes = methodTypes;
        if (Controller.MethodTypes.ANNOUNCEMENT_LIST == methodTypes) {
            home.setError(error.getErrorMessage(), methodTypes);
        } else if (Controller.MethodTypes.TICKET_LIST == methodTypes) {
            myTicket.setCurrentTicketError(error.getErrorMessage(), methodTypes);
        }
        loadToast.error();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                newTicket();
                break;
            case 1:
                mTitle = "Home";
                setTitle(mTitle);
                home = new Home();
                setFragment(home);
                break;
            case 2:
                mTitle = "My Tickets";
                setTitle(mTitle);
                myTicket = new MyTicket();
                setFragment(myTicket);
                break;
            /*case 3:
                callCCB();
                MaterialShowcaseView.resetSingleUse(this, RequestCodes.SHOWCASE_ID);
                break;*/
            case 3:
                new BaseHelper().logout(NavigationDrawer.this, false);
                break;
            default:
                mTitle = "Home";
                setTitle(mTitle);
                home = new Home();
                setFragment(home);
                break;
        }

    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void newTicket() {
        startActivity(new Intent(this, NewTicket.class));
    }

    private void callCCB() {
        startActivity(new Intent(this, CallCCB.class));
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.CANCEL_TICKET) {
            getTicketList();
        }
    }

    @Override
    public void getTicketList() {
        onlineController.ticketList(this, sharedPreferences.getString("user_id", null));
    }

    public void ticketListResult(boolean value, List<TicketInfo> ticketInfoList) {
        if (myTicket != null) {
            myTicket.setTicketListResult(ticketInfoList);
        }
    }

    public void getAnnouncementList() {
        onlineController.getAnnouncementList(this);
    }

    public void getAnnouncementListResult(boolean value, List<Announcement> announcements) {
        if (home != null) {
            home.setData(announcements);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

