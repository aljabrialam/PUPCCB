package pupccb.solutionsresource.com.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.octo.android.robospice.SpiceManager;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.helper.service.RetrofitSpiceService;
import pupccb.solutionsresource.com.util.ErrorHandler;


/**
 * Created by User on 7/16/2015.
 */
public class BaseHelper {

    // < -- BASE URL -- >
    protected static final String BaseUrl = "http://";
    // < -- SHARED PREFERENCE -- >
    protected static final String SharedPreference = "pup_ccb_preference";
    // < -- ROBO SPICE -- >
    protected SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);

    public static String getBaseUrl() {
        return BaseUrl;
    }

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(SharedPreference, Activity.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditSharedPreference(Context context) {
        return getSharedPreference(context).edit();
    }

    // < -- ERROR HANDLER -- >
    protected ErrorHandler errorHandler = new ErrorHandler();
    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }


    // < -- OFFLINE DATABASE HELPER -- >
    protected OfflineDatabaseHelper offlineDatabaseHelper;
    protected void openDatabase(Context context) {
        offlineDatabaseHelper = new OfflineDatabaseHelper(context, "offlineCCB", null, 1);
    }
    protected void closeDatabase() {
        offlineDatabaseHelper.close();
    }
    public OfflineDatabaseHelper getOfflineDatabaseHelper(Context context) {
        openDatabase(context);
        return offlineDatabaseHelper;
    }

    // < -- INTERNET CHECKER -- >
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // < -- LOGOUT -- >
    public void logout(Activity activity, boolean sessionTimeout) {
        SharedPreferences.Editor sharedPreference = getEditSharedPreference(activity);
        sharedPreference.putBoolean("logged_in", false);
        sharedPreference.apply();

        Intent intent = new Intent(activity, Main.class);
        intent.putExtra("sessionTimeout", sessionTimeout);

        activity.finish();
        activity.startActivity(intent);
    }

    public void startSpiceManager(Context context) {
        if (!spiceManager.isStarted()) {
            spiceManager.start(context);
        }
    }

    public void stopSpiceManager() {
        if (spiceManager.isStarted() && spiceManager.getPendingRequestCount() == 0) {
            spiceManager.shouldStop();
        }
    }
}
