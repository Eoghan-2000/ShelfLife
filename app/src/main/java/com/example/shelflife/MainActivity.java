package com.example.shelflife;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shelflife.DatabaseHelper.DATABASE_TABLE;

public class MainActivity extends ListActivity{
    ArrayList<String> itemTypes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemTypes.add("Fridge");
        itemTypes.add("Pantry");
        itemTypes.add("Other");
        Button addBtn= (Button) findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
        myAdapter myAdapter= new myAdapter(this, R.layout.opening,itemTypes);
        setListAdapter(myAdapter);

    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
        //when a type is selected it will bring the user to a new screen to see the items they have in that type
        String selection = l.getItemAtPosition(position).toString();
        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
        intent.putExtra("String", selection);
        startActivity(intent);

    }



    public class myAdapter extends ArrayAdapter{
        //custom adapter to show the type and an arrow to indicate they can click to the next screen
        public myAdapter(Context context, int rowLayoutId, ArrayList myArrayData)
        {
            super(context,rowLayoutId,myArrayData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;

            if(row==null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.opening, parent, false);
                TextView label = (TextView) row.findViewById(R.id.textView);
                label.setText(itemTypes.get(position));
                ImageView icon = (ImageView) row.findViewById(R.id.image);
                    icon.setImageResource(R.drawable.arrow);

                return row;
            }
            return row;
        }
    }
}