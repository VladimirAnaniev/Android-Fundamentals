package com.example.vladimir.spotifyactivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mContext=this;

        ArrayList<Song> data = new ArrayList<>();
        data.add(new Song("Bohemian Rhapsody","Queen","A Night At The Opera"));
        data.add(new Song("Stairway To Heaven","Led Zeppelin","Led Zeppelin IV"));
        data.add(new Song("Imagine","John Lennon","Imagine"));
        data.add(new Song("Smells Like Teen Spirit","Nirvana","Nevermind"));
        data.add(new Song("Hotel California","Eagles","Hotel California"));
        data.add(new Song("Sweet Child O' Mine","Guns N' Roses","Appetite for Destruction"));
        data.add(new Song("One","Metallica","...And Justice for All"));
        data.add(new Song("Comfortably Numb","Pink Floyd","The Wall"));
        data.add(new Song("Like a Rolling Stone","Bob Dylan","Highway 61 Revisited"));
        data.add(new Song("Lose Yourself","Eminem","8 Mile"));


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
