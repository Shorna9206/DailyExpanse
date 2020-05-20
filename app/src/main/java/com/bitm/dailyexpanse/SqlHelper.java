package com.bitm.dailyexpanse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "expence.db";
    public static String TABLE_NAME = "StoreData";
    public static String COL_ID = "Id";
    public static String COL_EXPANSE_TYPE = "Expanse_type";
    public static String COL_AMOUNT = "Amount";
    public static String COL_DATE = "Date";
    public static String COL_TIME = "Time";
    public static String COL_DOCUMENT = "Image";

    private static int VERSION = 1;
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(Id INTEGER PRIMARY KEY AUTOINCREMENT,Expanse_type TEXT,Amount TEXT,Date TEXT,Time TEXT,image BLOB)";


    public SqlHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Long insertValues(String type, String amount, String date, String time, byte[] image) {


        ContentValues values = new ContentValues();
        values.put(COL_EXPANSE_TYPE, type);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_DOCUMENT, image);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;


    }

    public Long updateValues(int Id, String type, String amount, String date, String time, byte[] image) {


        ContentValues values = new ContentValues();
        values.put(COL_EXPANSE_TYPE, type);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_DOCUMENT, image);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id = sqLiteDatabase.update(TABLE_NAME, values, "Id = ?", new String[]{String.valueOf(Id)});
        sqLiteDatabase.close();
        return id;


    }


    public Cursor showData(String sql) {


        //String show_all="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;


    }

    public void deleteRow(String sql) {


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);


    }
}
