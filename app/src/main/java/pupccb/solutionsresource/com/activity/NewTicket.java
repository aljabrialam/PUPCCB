package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;

/**
 * Created by User on 8/6/2015.
 */
public class NewTicket extends AppCompatActivity {

    private Toolbar toolbar;
    private View view;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private AppCompatActivity appCompatActivity;
    private Communicator communicator;

    public static void launch(AppCompatActivity activity) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent = new Intent(activity, NewTicket.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.activity_new_ticket, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(view);
        communicator = (Communicator) appCompatActivity;
        findViewById(view);
    }

    private void findViewById(View view) {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.send_ticket:

            case R.id.add_attachment:

            case R.id.cancel_action:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public interface Communicator {
        void onNavigationDrawerItemSelected(int position);
    }
}
