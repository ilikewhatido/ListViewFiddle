package com.example.song.listviewfiddle.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Song on 2015/12/7.
 */
public class DbAdapter {

    private static final String DB_NAME = "list.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mSQLiteDatabase;

    public static final String TABLE_RESTAURANT = "Restaurant";
    public static final String RESTAURANT_UID = "_id";
    public static final String RESTAURANT_NAME = "Name";
    public static final String RESTAURANT_NUMBER = "Number";

    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + " (" + RESTAURANT_UID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RESTAURANT_NAME + " VARCHAR(255), " + RESTAURANT_NUMBER +
            " VARCHAR(255));";
    private static final String DROP_TABLE_RESTAURANT = "DROP TABLE " + TABLE_RESTAURANT + " IF EXISTS;";

    public DbAdapter(Context context) {
        mSQLiteDatabase = new DbHelper(context, DB_NAME, null, DB_VERSION).getWritableDatabase();
    }

    public long addRestaurant(String name, String number) {
        Log.e(getClass().getName(), "executing addRestaurant...");
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESTAURANT_NAME, name);
        contentValues.put(RESTAURANT_NUMBER, number);
        return mSQLiteDatabase.insert(TABLE_RESTAURANT, null, contentValues);
    }

    public Cursor getRestaurants() {
        Log.e(getClass().getName(), "executing getRestaurants...");
        String[] columns = {RESTAURANT_UID, RESTAURANT_NAME, RESTAURANT_NUMBER};
        return mSQLiteDatabase.query(TABLE_RESTAURANT, columns, null, null, null, null, null);
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.e(getClass().getName(), "Creating database...");
                db.execSQL(CREATE_TABLE_RESTAURANT);
            } catch (SQLException e) {
                Log.e(getClass().getName(), e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.e(getClass().getName(), "Upgrading database...");
                db.execSQL(DROP_TABLE_RESTAURANT);
                onCreate(db);
            } catch (SQLException e) {
                Log.e(getClass().getName(), e.getMessage());
            }
        }
    }

}
