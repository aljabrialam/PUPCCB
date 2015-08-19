package pupccb.solutionsresource.com.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;

import com.octo.android.robospice.SpiceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.helper.service.RetrofitSpiceService;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.ToastMessage;


/**
 * Created by User on 7/16/2015.
 */
public class BaseHelper {

    // < -- BASE URL -- >
    protected static final String BaseUrl = "http://ccb.solutionsresource.com";
    // < -- SHARED PREFERENCE -- >
    protected static final String SharedPreference = "pup_ccb_preference";
    // < -- ROBO SPICE -- >
    protected SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);
    // < -- ERROR HANDLER -- >
    protected ErrorHandler errorHandler = new ErrorHandler();
    // < -- OFFLINE DATABASE HELPER -- >
    protected OfflineDatabaseHelper offlineDatabaseHelper;

    public static String getBaseUrl() {
        return BaseUrl;
    }

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(SharedPreference, Activity.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditSharedPreference(Context context) {
        return getSharedPreference(context).edit();
    }

    // < -- INTERNET CHECKER -- >
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // < -- DATE NOW -- >
    public static String getDateNow() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String mm = String.valueOf((month + 1));
        String dd = String.valueOf(day);
        if (month <= 8) {
            mm = "0" + mm;
        }
        if (day < 10) {
            dd = "0" + dd;
        }

        return mm + "/" + dd + "/" + year;
    }

    public static String getLongDateNow() {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E, dd MMMM yyyy");
        return DATE_FORMAT.format(calendar.getTime());
    }

    // < -- TIME NOW -- >
    @SuppressWarnings("StringBufferReplaceableByString")
    public static String getTimeNow() {
        final Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new StringBuilder().append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(':').append(minute < 10 ? "0" + minute : minute).append(":00").toString();
    }

    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }

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

    public void toastMessage(Activity activity, int duration, ToastMessage.MessageType messageType, String message) {
        ToastMessage toastMessage = new ToastMessage();
        toastMessage.init(activity)
                .timer(duration)
                .gravity(Gravity.BOTTOM)
                .message(message)
                .messageType(messageType)
                .build()
                .show();
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
