package pupccb.solutionsresource.com.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pupccb.solutionsresource.com.R;

/**
 * Created by User on 8/6/2015.
 */
public class Registration extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> arraylist;
    private Spinner spinner;
    private View view;
    private TextView txtHaveAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_registration, null);
        setContentView(view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        findViewById(view);

    }

    private void findViewById(View view) {

        txtHaveAccount = (TextView) view.findViewById(R.id.btnHaveAccount);
        txtHaveAccount.setOnClickListener(buttonListener);

    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == txtHaveAccount){
                startActivity(new Intent(getApplicationContext(), Main.class));
                finish();
            }
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Main.class));
        this.finish();
        super.onBackPressed();
    }

}
