package pupccb.solutionsresource.com.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 8/17/2015.
 */
public class OfflineDatabaseHelper extends SQLiteOpenHelper {

    private int version;
    public OfflineDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    private static final String tag = "OfflineHelper";

    public static final String ticket_table = "ticket";



    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ticket_table);
        onCreate(sqLiteDatabase);
    }

    public void dropTables(){
        onUpgrade(this.getWritableDatabase(), version, ++version);
    }
}
