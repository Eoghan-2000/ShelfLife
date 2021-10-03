package com.example.shelflife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import androidx.activity.OnBackPressedCallback;

import static com.example.shelflife.DatabaseHelper.DATABASE_TABLE;
import static com.example.shelflife.DatabaseHelper.KEY_EXPIRY;
import static com.example.shelflife.DatabaseHelper.KEY_DATEADDED;
import static com.example.shelflife.DatabaseHelper.KEY_NAME;
import static com.example.shelflife.DatabaseHelper.KEY_DESC;
import static com.example.shelflife.DatabaseHelper.KEY_ROWID;
import static com.example.shelflife.DatabaseHelper.KEY_TYPE;

public class DatabaseManager {
    Context context;
    private DatabaseHelper myDatabaseHelper;
    private SQLiteDatabase myDatabase;

    //This code was taken from the lab example on databases

    public DatabaseManager(Context context)
    {
        this.context = context;

    }
    //getting a connection to the database
    public DatabaseManager open() throws SQLException {
        myDatabaseHelper = new DatabaseHelper(context);
        myDatabase = myDatabaseHelper.getWritableDatabase();
        return this;
    }

    //---closes the database--- any activity that uses the dB will need to do this
    public void close()
    {
        myDatabaseHelper.close();
    }

    //---insert an item into the database---
    public long insertItem(Item item)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, item.get_itemName());
        initialValues.put(KEY_DATEADDED, item.get_itemDateAdded());
        initialValues.put(KEY_DESC, item.get_itemDesc());
        initialValues.put(KEY_EXPIRY, item.get_itemExpiry());
        initialValues.put(KEY_TYPE, item.get_itemType());
        return myDatabase.insert(DATABASE_TABLE, null, initialValues);//insert is in the api to make it easier to insert
    }

    //---deletes a specific item---
    public boolean deleteItem(long rowId)
    {
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return myDatabase.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //Drops table
    public void droptable(String tablename)
    {
        myDatabase.execSQL("Drop table if exists " + tablename);
    }

    //retrieves all the rows
    public Cursor getAllItems()
    {
        return myDatabase.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_NAME,
                        KEY_DATEADDED,
                        KEY_DESC,
                        KEY_TYPE,
                        KEY_EXPIRY},
                null,
                null,
                null,
                null,
                null);
    }
    //get items of a specific type
    public Cursor getitemsByType(String type){
        Cursor mCursor =
                myDatabase.query(true, DATABASE_TABLE, new String[] {
                                KEY_ROWID,
                                KEY_NAME,
                                KEY_DATEADDED,
                                KEY_DESC,
                                KEY_TYPE,
                                KEY_EXPIRY},
                        KEY_TYPE + "=" + "\'" + type + "\'",
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //gets a particular item
    public Cursor getItem(long rowId) throws SQLException
    {
        Cursor mCursor =
                myDatabase.query(true, DATABASE_TABLE, new String[] {
                                KEY_ROWID,
                                KEY_NAME,
                                KEY_DATEADDED,
                                KEY_DESC,
                                KEY_TYPE,
                                KEY_EXPIRY},
                        KEY_ROWID + "=" + rowId,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates an item in the database---
    public boolean editItem(long rowId, String name,
                            String expiry, String dateAdded, String desc, String type)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_DATEADDED, dateAdded);
        args.put(KEY_DESC, desc);
        args.put(KEY_TYPE, type);
        args.put(KEY_EXPIRY, expiry);
        return myDatabase.update(DATABASE_TABLE, args,
                KEY_ROWID + "=" + rowId, null) > 0;
    }

}
