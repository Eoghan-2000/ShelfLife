package com.example.shelflife;

import android.content.Context;
import android.database.sqlite.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    // This class was taken an modified from lab example
    // These are the names of the columns the table will contain. Could make these private
    // and use getters so that other classes can access them, but, as they are "final", this removes
    // the security risk that encapsulation (privacy/ getters/ setters) protects against
    public static final String KEY_ROWID = "_id";
    public static final String KEY_DATEADDED = "item_date_added";
    public static final String KEY_NAME = "item_name";
    public static final String KEY_EXPIRY = "item_expiry";
    public static final String KEY_DESC = "item_desc";
    public static final String KEY_TYPE = "item_type";
    public static final String DATABASE_NAME = "ShelfLife";
    public static final String DATABASE_TABLE = "Item";
    public static final int DATABASE_VERSION = 1;

    // This is the string containing the SQL database create statement
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE  +
                    " (" + KEY_ROWID + " integer primary key autoincrement,"
                    + KEY_DATEADDED + " date not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_EXPIRY + " date not null,"
                    + KEY_DESC + " text not null,"
                    + KEY_TYPE + " text not null);"

            ;

    // constructor for your dB helper class. This code is standard. You’ve set up the parameter values for the constructor already…database name,etc
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {

        // The “Database_create” string below needs to contain the SQL statement needed to create the db
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        onCreate(db);
    }
}


