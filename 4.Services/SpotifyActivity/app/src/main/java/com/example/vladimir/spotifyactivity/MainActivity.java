package com.example.vladimir.spotifyactivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.IRecyclerViewHandler{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MediaControlFragment mMediaControl;
    private ArrayList<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mContext=this;

        mMediaControl = (MediaControlFragment) getFragmentManager().findFragmentById(R.id.control_fragment);

        mSongs = new ArrayList<>();
        mSongs.add(new Song("Bohemian Rhapsody","Queen","A Night At The Opera",R.raw.bohemian_rhapsody));
        mSongs.add(new Song("Stairway To Heaven","Led Zeppelin","Led Zeppelin IV",R.raw.stairway_to_heaven));
        mSongs.add(new Song("Imagine","John Lennon","Imagine",R.raw.imagine));
        mSongs.add(new Song("Smells Like Teen Spirit","Nirvana","Nevermind",R.raw.smells_like_teen_spirit));
        mSongs.add(new Song("Hotel California","Eagles","Hotel California",R.raw.hotel_california));
        mSongs.add(new Song("Sweet Child O' Mine","Guns N' Roses","Appetite for Destruction",R.raw.sweet_child_o_mine));
        mSongs.add(new Song("One","Metallica","...And Justice for All",R.raw.one));
        mSongs.add(new Song("Comfortably Numb","Pink Floyd","The Wall",R.raw.comfortably_numb));
        mSongs.add(new Song("Like a Rolling Stone","Bob Dylan","Highway 61 Revisited",R.raw.like_a_rolling_stone));
        mSongs.add(new Song("Lose Yourself","Eminem","8 Mile",R.raw.lose_yourself));


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mSongs, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSongClicked(Integer index) {
        if(mMediaControl.getPlaying()){
            mMediaControl.stop();
        }
        mMediaControl.chnageSong(mSongs.get(index));
        mMediaControl.start();
    }

    @Override
    protected void onDestroy() {
        if(mMediaControl.getPlaying()){
            mMediaControl.stop();
        }
        super.onDestroy();
    }
}
