package com.example.shelflife;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.shelflife.DatabaseHelper.KEY_EXPIRY;
import static com.example.shelflife.DatabaseHelper.KEY_NAME;

public class ListItemsActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        //retrieve the type clicked on main screen
        String mySelection = getIntent().getStringExtra("String");
        DatabaseManager dbManager = new DatabaseManager(this);
        dbManager.open();
        try {
            //Gets all items of that type and displays it
            Cursor cursor = dbManager.getitemsByType(mySelection);
            itemAdapter myAdapter = new itemAdapter(this, cursor);
            setListAdapter(myAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbManager.close();
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
        //on list item click it will go to edit screen and pass the row it wants to edit
        Cursor selection = (Cursor) l.getItemAtPosition(position);
        Long itemID = selection.getLong(selection.getColumnIndex("_id"));
        Intent intent2 = new Intent(ListItemsActivity.this, ItemInfoActivity.class);
        intent2.putExtra("id", itemID);
        finish();
        startActivity(intent2);

    }
    //custom adapter to display what the user will see from that row
    public class itemAdapter extends CursorAdapter {
        private LayoutInflater mLayoutInflater;
        private Context mContext;
        public itemAdapter(Context context, Cursor cursor)
        {
            super(context,cursor);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view= mLayoutInflater.inflate(R.layout.item_and_info, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            //Gets data for each row to display in list view
            String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
            String expiry = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EXPIRY));

            TextView text_name = (TextView) view.findViewById(R.id.itemName);
            text_name.setText(name);

            TextView text_exp = (TextView) view.findViewById(R.id.expiry);
            text_exp.setText(expiry);

            ImageView icon = (ImageView) view.findViewById(R.id.image);
            icon.setImageResource(R.drawable.info);

        }
    }
}
