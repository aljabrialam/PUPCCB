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
import pupccb.solutionsresource.com.drawer.NavigationDrawerAdapter;

/**
 * Created by User on 8/6/2015.
 */
public class AddTicket extends AppCompatActivity {

    private Toolbar toolbar;
    private View view;
    private NavigationDrawerAdapter navigationDrawerAdapter;


    private  NavigationDrawerCallbacks navigationDrawerCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        view = getLayoutInflater().inflate(R.layout.activity_new_ticket, null);
        setContentView(view);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(view);

    }

    private void findViewById(View view) {


    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.send_ticket)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NavigationDrawer.class));
        this.finish();
       super.onBackPressed();
    }
    public  static void launch(AppCompatActivity activity)
    {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent = new Intent(activity, AddTicket.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
