
package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pupccb.solutionsresource.com.R;

public class DetailActivity extends AppCompatActivity
{
    public static final String SHARED_VIEW = "shared_view";

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ImageView image = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(image, SHARED_VIEW);
        Picasso.with(this).load(getIntent().getStringExtra(SHARED_VIEW)).into(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_search)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Launches an activity with a transition from the shared view
     * @param activity
     * @param transitionView
     * @param url
     */
    public static void launch(AppCompatActivity activity, View transitionView, String url)
    {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, SHARED_VIEW);
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(SHARED_VIEW, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
