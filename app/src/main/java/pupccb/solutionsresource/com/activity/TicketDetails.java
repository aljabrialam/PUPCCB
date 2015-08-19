package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pupccb.solutionsresource.com.R;

/**
 * Created by User on 8/17/2015.
 */
public class TicketDetails extends AppCompatActivity {

    public static final String SHARED_VIEW = "shared_view";
    private NavigationDrawerCommunicator communicator;
    private Toolbar toolbar;

    public static void launch(AppCompatActivity activity, View transitionView, String status) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, SHARED_VIEW);
        Intent intent = new Intent(activity, TicketDetails.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ticket_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

        }
        return true;
    }

    @Override
    public void onBackPressed() {
     super.onBackPressed();
    }

    public interface NavigationDrawerCommunicator {
        void onNavigationDrawerItemSelected(int position);
    }
}
