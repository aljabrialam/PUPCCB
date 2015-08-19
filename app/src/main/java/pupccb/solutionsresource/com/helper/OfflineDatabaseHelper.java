package pupccb.solutionsresource.com.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.model.FileAttachment;
import retrofit.mime.TypedFile;

/**
 * Created by User on 8/17/2015.
 */
public class OfflineDatabaseHelper extends SQLiteOpenHelper {

    private int version;

    private static final String tag = "OfflineHelper";

    public static final String ticket_table = "ticket";
    public static final String ticket_documents_table = "ticket_documents_table";


    //common field
    private static final String id = "_id";
    private static final String user_id = "user_id";
    private static final String ticket_id = "ticket_id";
    private static final String created_date = "created_date";
    private static final String modified_date = "modified_date";
    //consultation documents table
    private static final String file_name = "file_name";
    private static final String file_type = "file_type";
    private static final String file_size = "file_size";
    private static final String file_attachment_path = "file_attachment_path";
    private static final String sync = "sync";

    public OfflineDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //file attachment
        sqLiteDatabase.execSQL("CREATE TABLE " + ticket_documents_table + " (" +
                        id + " integer primary key, " +
                        ticket_id + " integer, " +
                        user_id + " integer, " +
                        file_name + " text, " +
                        file_type + " text, " +
                        file_size + " text, " +
                        file_attachment_path + " text, " +
                        sync + " text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ticket_table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ticket_documents_table);
        onCreate(sqLiteDatabase);
    }

    public long insertDocument(FileAttachment fileAttachment, String ticket_id, String user_id) throws Exception {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.insert(ticket_documents_table, null, setDocumentsValues(fileAttachment, ticket_id, user_id));
    }

    private ContentValues setDocumentsValues(FileAttachment fileAttachment, String ticket_id, String user_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfflineDatabaseHelper.ticket_id, ticket_id);
        contentValues.put(OfflineDatabaseHelper.user_id, user_id);
        contentValues.put(file_name, fileAttachment.getFile_name());
        contentValues.put(file_type, fileAttachment.getFile_type());
        contentValues.put(file_size, fileAttachment.getFile_size());
        contentValues.put(file_attachment_path, fileAttachment.getFile_attachment_path());
        contentValues.put(sync, fileAttachment.getSync());
        return contentValues;
    }

    public List<FileAttachment> getAllDocuments(String ticket_id, String user_id) throws Exception {
        List<FileAttachment> list = new ArrayList<FileAttachment>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ticket_documents_table +
                " WHERE " + OfflineDatabaseHelper.ticket_id + " = '" + ticket_id + "'" +
                " AND " + OfflineDatabaseHelper.user_id + " = '" + user_id + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(newFileAttachment(cursor));
            cursor.moveToNext();
        }
        return list;
    }

    private FileAttachment newFileAttachment(Cursor cursor) {
        return new FileAttachment(
                String.valueOf(cursor.getInt(cursor.getColumnIndex(id))),
                cursor.getString(cursor.getColumnIndex(file_name)),
                cursor.getString(cursor.getColumnIndex(file_type)),
                cursor.getString(cursor.getColumnIndex(file_size)),
                cursor.getString(cursor.getColumnIndex(file_attachment_path)),
                cursor.getString(cursor.getColumnIndex(sync)),
                new TypedFile(cursor.getString(cursor.getColumnIndex(file_type)),
                        new File(cursor.getString(cursor.getColumnIndex(file_attachment_path)))));
    }

    public void dropTables() {
        onUpgrade(this.getWritableDatabase(), version, ++version);
    }
}
