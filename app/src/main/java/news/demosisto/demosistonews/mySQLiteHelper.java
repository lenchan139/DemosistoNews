package news.demosisto.demosistonews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mySQLiteHelper extends SQLiteOpenHelper {
    public mySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE globalData(pk_id TEXT PRIMARY KEY, dataX Text)";
        db.execSQL(sql);
    }

    public void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "DROP TABLE IF EXISTS globalData";
        db.execSQL(sql);
        onCreate(db);
    }
}