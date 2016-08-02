// Assignment 4 by Ted Callow
// 1. In this exercise, you’ll create a database that you can use with the Tip Calculator app.
//Add a database class
//2. Start Android Studio and open the project named HM_ch13_ex4_TipCalculator.
//3. Review the code. Note that it includes a Tip class that you can use to store the data for a tip.
//4. Add a database class that creates a table with these column names and data types:
// _id             INTEGER
// bill_date       INTEGER
//bill_amount      REAL
//tip_percent      REAL
//5. When the database class creates the database, it should also insert two rows of test data.
// (You can use 0 for the bill date values,
// but make up some bill amount and tip percent values such as 40.60 and .15.)
//6. Add a public getTips method that
// returns an ArrayList<Tip> object that contains all columns and rows from the database table.
//7. Switch to the activity class. Then, add code to its onResume method that calls the getTips method and
// loops through all saved tips. For each saved tip, this code should use LogCat logging to send
// the bill date milliseconds, the bill amount, and the tip percent to the LogCat view.




package com.murach.tipcalculator;

/**
 * Created by tedcu76 on 7/31/16.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TipDB {

    // database constants
    public static final String DB_NAME = "tip.db";
    public static final int    DB_VERSION = 1;

    // tip table constants
    public static final String TIP_TABLE = "tip";//changed from "list"

    public static final String TIP_ID = "id";
    public static final int    TIP_ID_COL = 0;

    public static final String BILL_DATE = "bill_date";
    public static final int    BILL_DATE_COL = 1;

    public static final String BILL_AMOUNT = "bill_amount";
    public static final int    BILL_AMOUNT_COL = 2;

    public static final String TIP_PERCENT = "tip_percent";
    public static final int    TIP_PERCENT_COL = 3;




    // CREATE and DROP TABLE statements
    public static final String CREATE_TIP_TABLE =
            "CREATE TABLE " + TIP_TABLE + " (" +
                    TIP_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BILL_DATE + " INTEGER, " +
                    BILL_AMOUNT +" REAL, " +
                    TIP_PERCENT  +"REAL);";



    public static final String DROP_TIP_TABLE =
            "DROP TABLE IF EXISTS " + TIP_TABLE;

//    public static final String DROP_TASK_TABLE =
//            "DROP TABLE IF EXISTS " + TASK_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_TIP_TABLE);
            //db.execSQL(CREATE_TASK_TABLE);

            // insert default lists
            db.execSQL("INSERT INTO tip VALUES (1, 0, 25.50, .15)");
            db.execSQL("INSERT INTO tip VALUES (2, 0, 42.50, .25)");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Tip list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(TipDB.DROP_TIP_TABLE);
            //db.execSQL(TaskListDB.DROP_TASK_TABLE);
            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public TipDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    // public methods


    public ArrayList<Tip> getTips() {
        String where =
                TIP_ID + "= ?";
       int i=1;
        int TipID = (int)getTip(i).getId();
            String[] whereArgs = { Integer.toString(TipID) };
            this.openReadableDB();
            Cursor cursor = db.query(TIP_TABLE, null,
                    null, null,
                    null, null, null);
            ArrayList<Tip> tips = new ArrayList<Tip>();
            while (cursor.moveToNext()) {
                tips.add(getTipFromCursor(cursor));
            }
            if (cursor != null)
                cursor.close();
            this.closeDB();
            return tips;
        }


    public Tip getTip(int id) {
        String where = TIP_ID + "= ?";
        String[] whereArgs = { Integer.toString(id) };

        this.openReadableDB();
        Cursor cursor = db.query(TIP_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Tip tip = getTipFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return tip;
    }

    private static Tip getTipFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Tip tip = new Tip(
                        cursor.getInt(TIP_ID_COL),
                        cursor.getInt(BILL_DATE_COL),
                        cursor.getFloat(BILL_AMOUNT_COL),
                        cursor.getFloat(TIP_PERCENT_COL));

                return tip;
            }
            catch(Exception e) {
                return null;
            }
        }
    }
}
