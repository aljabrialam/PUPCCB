package pupccb.solutionsresource.com.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;

import com.octo.android.robospice.SpiceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import pupccb.solutionsresource.com.activity.Main;
import pupccb.solutionsresource.com.helper.service.RetrofitSpiceService;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.ToastMessage;


/**
 * Created by User on 7/16/2015.
 */
public class BaseHelper {

    // < -- BASE URL -- >
    protected static final String BaseUrl = "http://ccb.apps.solutionsresource.com";
    // < -- SHARED PREFERENCE -- >
    protected static final String SharedPreference = "pup_ccb_preference";
    private static AtomicInteger nextId = new AtomicInteger(0);
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

    public static String getSharedPreference() {
        return SharedPreference;
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

    // < -- DATE YEAR MONTH -- >
    public static String getDateYearMonth() {
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

        return year + mm;
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

        String timeSet;
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            timeSet = "PM";
        } else if (hourOfDay == 0) {
            hourOfDay += 12;
            timeSet = "AM";
        } else if (hourOfDay == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes;
        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);

        // Append in a StringBuilder
        return new StringBuilder().append(hourOfDay).append(':').append(minutes).append(" ").append(timeSet).toString();
        //return new StringBuilder().append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(':').append(minute < 10 ? "0" + minute : minute).append(":00").toString();
    }

    public String formatDateTime( String dateTime) {
        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.ENGLISH);
            Date date = originalFormat.parse(dateTime);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String formatTime(String time) {
        String formattedTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
            SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            Date d = sdf.parse(time);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public String formatDate(String date) {
        String formattedDate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date tempDate = simpleDateFormat.parse(date);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formattedDate = outputDateFormat.format(tempDate);
            System.out.println("Output date is = " + outputDateFormat.format(tempDate));
        } catch (ParseException ex) {
            System.out.println("Parse Exception");
        }
        return formattedDate;
    }

    public long convertDateTimeToMillis(String dateTime) {
        long dateTimeInMillis = 0L;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(dateTime);
            dateTimeInMillis = date.getTime();
            System.out.println("Date in milli :: " + dateTimeInMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTimeInMillis;
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

    public void toastMessage(Context activity, int duration, ToastMessage.MessageType messageType, String message) {
        ToastMessage toastMessage = new ToastMessage();
        try {
            if (activity != null) {
                toastMessage.init(activity)
                        .timer(duration)
                        .gravity(Gravity.BOTTOM)
                        .message(message)
                        .messageType(messageType)
                        .build()
                        .show();
            }
        } catch (Exception e) {
            Log.e("ToastMessage", e.getMessage());
        }

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
