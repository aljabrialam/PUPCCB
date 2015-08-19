package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.CardAdapter;

/**
 * Created by User on 8/17/2015.
 */
public class TicketDetails extends AppCompatActivity {

    public static final String SHARED_VIEW = "shared_view";
    private NavigationDrawerCommunicator communicator;
    private Toolbar toolbar;
    private View view;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;

    public static void launch(AppCompatActivity activity, View transitionView, String status) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, SHARED_VIEW);
        Intent intent = new Intent(activity, TicketDetails.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_ticket_detail_with_attachment, null);
        setContentView(view);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setAttachmentCard(view);
    }
    public void setAttachmentCard(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAttachment);
        cardAdapter = new CardAdapter(this, null);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
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
