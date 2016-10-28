package com.example.vladimir.homework;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Vladimir on 29-Sep-16.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private ArrayList<Item> data;
    private DbManager mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_activity);

        if(getIntent()!=null){
            int elements = getIntent().getIntExtra("elements",-1);
            if(elements<=0){
                Toast.makeText(this,"No items to show.",Toast.LENGTH_LONG).show();
            }
            else{

                data = new ArrayList<>();
                mDbManager = DbManager.getInstance(this);

                Cursor items = mDbManager.getAllElements();

                if(items!=null){
                    while(items.moveToNext()){
                        Item item = new Item();
                        item.setMainText(items.getString(0));
                        item.setSubText(items.getString(1));
                        data.add(item);
                    }
                }

                RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                Adapter mAdapter = new Adapter(data);
                mRecyclerView.setAdapter(mAdapter);
            }
        }



    }


}
