package pupccb.solutionsresource.com.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pupccb.solutionsresource.com.R;

/**
 * Created by User on 8/4/2015.
 */
public class CallCCB extends AppCompatActivity {

    private Button callBtn;
    private Button dialBtn;
    private EditText number;
    private Toolbar toolbar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_call_ccb, null);
        setContentView(view);
        toolBar();
        callBtn = (Button) findViewById(R.id.btnCall);

        // add PhoneStateListener for monitoring
        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        callBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // set the data
                    String uri = "tel:" + "16565";
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                    startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Your call has failed...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void toolBar() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(CallCCB.this, incomingNumber + " calls you", Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(CallCCB.this, "on call...", Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // in initialization of the class and at the end of phone call
                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        try {
                            // stop listening
                            TelephonyManager mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            mTM.listen(this, PhoneStateListener.LISTEN_NONE);
                        } catch (Exception e) {
                            Log.e("callMonitor: ", e.toString());
                        }
                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }
    }
}
