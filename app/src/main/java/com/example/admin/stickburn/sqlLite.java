package com.example.admin.stickburn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 26/11/2560.
 */

public class sqlLite extends SQLiteOpenHelper {



    public sqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS stickburn(" +
                "id TEXT PRIMARY KEY, " +

                "cal_day REAL)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void insertID(String id, String cal_day /*, float gpax*/) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO stickburn(id,cal_day) VALUES(?, ?, ?)";
    //    String[] args = {id, name, String.valueOf(gpax)};
        String[] args = {id,cal_day};
        db.execSQL(sql, args);
    }

    public void update(String id){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE stickburn SET cal_day = ? WHERE id = ?";
        //    String[] args = {id, name, String.valueOf(gpax)};
        String[] args = {String.valueOf(0.0),id};
        db.execSQL(sql, args);

    }

  /*  public Cursor selectAllStudent() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM student";
        return db.rawQuery(sql, null);
    }
*/


}
