package com.example.shelflife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.shelflife.DatabaseHelper.KEY_DATEADDED;
import static com.example.shelflife.DatabaseHelper.KEY_DESC;
import static com.example.shelflife.DatabaseHelper.KEY_EXPIRY;
import static com.example.shelflife.DatabaseHelper.KEY_NAME;

public class EditItemActivity extends AppCompatActivity implements View.OnClickListener{

    final DatabaseManager dbManager = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);
        dbManager.open();
        final long id = getIntent().getLongExtra("id", 0);
        final Cursor cursor = dbManager.getItem(id);

        //setting all fields to the selected item
        setInitialValues(cursor);

        //Linking buttons to xml and setting onclickListeners
        Button deleteBtn=(Button)  findViewById(R.id.delete);
        deleteBtn.setOnClickListener(this);
        Button saveBtn=(Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                //If the user clicks delete they will be asked to make sure they want to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(EditItemActivity.this);
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
            case R.id.save:
                //If the user presses the save button the changes to the item will be used to edit item using dbManager
                EditText editName = (EditText) findViewById(R.id.itemName);
                EditText editExpiry = (EditText) findViewById(R.id.expiryDate);
                EditText desc = (EditText) findViewById(R.id.desc);
                Spinner type = (Spinner) findViewById(R.id.type);
                Long itemID2 = getIntent().getLongExtra("id", 0);
                String date = new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                dbManager.editItem(itemID2, editName.getText().toString(), editExpiry.getText().toString(), date, desc.getText().toString(), type.getSelectedItem().toString());
                dbManager.close();
                finish();
                break;
            default:
                break;
        }
    }

    public void setInitialValues(Cursor cursor)
    {
        //This sets the initial values of the edit text to hold the current values of the item
        EditText editName=(EditText) findViewById(R.id.itemName);
        editName.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
        EditText editExpiry=(EditText) findViewById(R.id.expiryDate);
        editExpiry.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EXPIRY)));
        EditText desc=(EditText)findViewById(R.id.desc);
        desc.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC)));
        Spinner type=(Spinner)findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        //When the user presses the back button it will ask does the user want to exit without saving changes
        AlertDialog.Builder builder = new AlertDialog.Builder(EditItemActivity.this);
        builder.setMessage("Are you sure you want to go back without saving?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}