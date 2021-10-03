package com.example.shelflife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.shelflife.DatabaseHelper.KEY_DATEADDED;
import static com.example.shelflife.DatabaseHelper.KEY_DESC;
import static com.example.shelflife.DatabaseHelper.KEY_EXPIRY;
import static com.example.shelflife.DatabaseHelper.KEY_NAME;
import static com.example.shelflife.DatabaseHelper.KEY_TYPE;

public class ItemInfoActivity extends AppCompatActivity implements View.OnClickListener {

    final DatabaseManager dbManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        dbManager.open();
        final long id = getIntent().getLongExtra("id", 0);
        final Cursor cursor = dbManager.getItem(id);
        //Calls method to set Text Views to display the info about the item using a cursor
        setInitialValues(cursor);

        Button editbtn=(Button)findViewById(R.id.edit);
        editbtn.setOnClickListener(this);
        Button deletebtn=(Button)findViewById(R.id.delete);
        deletebtn.setOnClickListener(this);
    }
        //When the button clicks it will either go to edit the item or it will delete it
        //you can also edit the item after it has been
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    //If the user clicks delete they will be asked to make sure they want to delete
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemInfoActivity.this);
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Long itemID = getIntent().getLongExtra("id", 0);
                            dbManager.deleteItem(itemID);
                            dbManager.close();
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
                case R.id.edit:
                    Long itemID2 = getIntent().getLongExtra("id", 0);
                    Intent editIntent = new Intent(ItemInfoActivity.this, EditItemActivity.class);
                    editIntent.putExtra("id", itemID2);
                    startActivity(editIntent);
                    finish();
                    break;
                default:
                    break;
            }
        }
        public void setInitialValues(Cursor cursor)
        {
            TextView name=(TextView) findViewById(R.id.name);
            name.setText("Item name: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            TextView expiry=(TextView) findViewById(R.id.expiry);
            expiry.setText("Expiry Date: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_EXPIRY)));
            TextView desc=(TextView)findViewById(R.id.desc);
            desc.setText("Item Description: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC)));
            TextView type=(TextView)findViewById(R.id.type);
            type.setText("Item Type: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE)));
            TextView dateadded=(TextView)findViewById(R.id.dateadded);
            dateadded.setText("Date Added: " +cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATEADDED)));
        }
}
