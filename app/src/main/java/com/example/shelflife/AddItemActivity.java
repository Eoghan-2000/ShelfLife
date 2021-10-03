package com.example.shelflife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddItemActivity extends AppCompatActivity{
    final DatabaseManager dbManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //Create EditTexts for adding item
        final EditText addname=(EditText) findViewById(R.id.itemName);
        final EditText expiry=(EditText) findViewById(R.id.expiryDate);
        final EditText desc=(EditText)findViewById(R.id.desc);
        final Spinner type=(Spinner)findViewById(R.id.type);
        //Setting up spinner to hold the 3 different categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        Button saveBtn=(Button)  findViewById(R.id.save);

        //https://developer.android.com/guide/topics/ui/dialogs created with the help of this official android documentation
        saveBtn.setOnClickListener(new View.OnClickListener() {
           //when save button is clicked
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
                builder.setMessage("Do you want to add this item to Calender");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    //When the save button is pressed and a dialog will pop up to ask do they want to add this item to the calender
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.open();
                        // creating the current date and formatting it to add to database
                        String date = new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                        //Splitting the date in the database into 3 parts to allow for adding to a callendar
                        String[] split = expiry.getText().toString().split("/");
                        Calendar startDate = Calendar.getInstance();
                        startDate.set(new Integer(split[2]),new Integer(split[1]),new Integer(split[0]));
                        //start date set by the expiry entered
                        dbManager.insertItem(new Item(addname.getText().toString(),date, expiry.getText().toString(), desc.getText().toString(), type.getSelectedItem().toString()));
                        //creating an intent to insert an event into calender
                        //https://developer.android.com/guide/topics/providers/calendar-provider#sync-adapter
                        Intent addtoCall = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, addname.getText().toString() + " expires today")
                                .putExtra(CalendarContract.Events.DESCRIPTION, "The Item " + addname.getText().toString() + " will Expire today")
                                .putExtra(CalendarContract.Events.DTSTART, startDate.getTimeInMillis())
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                                .putExtra(CalendarContract.Events.ALL_DAY, "false");
                        if(addtoCall.resolveActivity(getPackageManager()) != null){
                            startActivity(addtoCall);
                        }else{
                            Toast.makeText(AddItemActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                        }
                        dbManager.close();
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    //if they don't want to add to calender it will just add the item to the database
                    public void onClick(DialogInterface dialog, int which) {
                        //If the user does not want to add the item to calendar the item will be added to database
                        dbManager.open();
                        String date = new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                        dbManager.insertItem(new Item(addname.getText().toString(),date, expiry.getText().toString(), desc.getText().toString(), type.getSelectedItem().toString()));
                        dbManager.close();
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}