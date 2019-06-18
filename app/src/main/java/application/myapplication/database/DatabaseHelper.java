package application.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_ACCOUNT_TABLE = "create table account(" +
            "id integer primary key autoincrement," +
            "account text," +
            "password text)";
    public static final String CRCREATE_CHAT_RECORD_TABLE = "create table chatRecord(" +
            "id integer primary key autoincrement," +
            "name text," +
            "type int," +
            "content text," +
            "time int)";


    private Context mContext;


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CRCREATE_CHAT_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
