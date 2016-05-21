package news.demosisto.demosistonews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by len on 21/05/16.
 */
public class MyDBAdapter {
    private static String databaseName = "globalData";
    private static int databaseVer = 1;

    private Context context;
    private SQLiteOpenHelper myDBHelper;

    public MyDBAdapter (Context context){
        this.context = context;
        this.myDBHelper = new mySQLiteHelper(this.context, databaseName, null, databaseVer);
    }

    public long insert(String key, String dataX){
        ContentValues contentValues = new ContentValues();
        contentValues.put("key",key);
        contentValues.put("dataX",dataX);

        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        long numRows = sqlDB.insert("globalData",null,contentValues);

        sqlDB.close();
        return numRows;
    }

    public String getDataX(String key){
        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        String result ="";
        Cursor cursor = sqlDB.query(
                "globalData",
                new String[]{"pk_id","dataX"},
                "pk_id = 'dataX'",
                new String[]{key},
                null,
                null,
                null);
        result = cursor.getString(1);

        sqlDB.close();
        return result;
    }

}
