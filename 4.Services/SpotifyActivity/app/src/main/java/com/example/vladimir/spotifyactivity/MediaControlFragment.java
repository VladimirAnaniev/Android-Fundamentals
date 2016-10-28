package com.example.vladimir.spotifyactivity;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MediaControlFragment extends Fragment implements View.OnClickListener {

    private TextView mSongName;
    private TextView mAuthorName;
    private Button mFastForward;
    private Button mRewind;
    private Button mStartBtn;
    private Button mPauseBtn;
    private Context ctx;
    private Intent playerServiceIntent;
    private Integer mSong;
    private Boolean isPlaying;
    private PlayerService.PlayerServiceBinder mPlayerService;


    public Boolean getPlaying() {
        return isPlaying;
    }

    public void chnageSong(Song s){
        mSongName.setText(s.getName());
        mAuthorName.setText(s.getAuthor());
        mSong = s.getSong();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control_fragment_layout,container,false);

        mSongName = (TextView) view.findViewById(R.id.song_title);
        mAuthorName = (TextView) view.findViewById(R.id.song_author);
        mFastForward = (Button) view.findViewById(R.id.btn_forward);
        mRewind = (Button) view.findViewById(R.id.btn_backward);
        mStartBtn = (Button) view.findViewById(R.id.btnPlay);
        mPauseBtn = (Button) view.findViewById(R.id.btnStop);

        mFastForward.setOnClickListener(this);
        mRewind.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);

        isPlaying = false;

        return view;
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mPlayerService = (PlayerService.PlayerServiceBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("DEBUG","SERVICE DISCONNECTED!!");
        }
    };

    @Override
    public void onClick(View v) {
        if(mSong!=null){
            if(v.equals(mStartBtn)){
                resume();
            }
            if(v.equals(mPauseBtn)){
                pause();
            }
            if(v.equals(mFastForward)){
                fastForward();
            }
            if(v.equals(mRewind)){
                rewind();
            }
        }
        else{
            Toast.makeText(ctx,"Please choose a song first",Toast.LENGTH_SHORT).show();
        }
    }

    private void rewind() {
        if(isPlaying){
            mPlayerService.getService().rewind();
        }
    }

    private void fastForward() {
        if(isPlaying){
            mPlayerService.getService().fastForward();
        }
    }

    private void pause() {
        mPauseBtn.setVisibility(View.INVISIBLE);
        mStartBtn.setVisibility(View.VISIBLE);
        mPlayerService.getService().pausePlayer();
    }

    private void resume() {
        mStartBtn.setVisibility(View.INVISIBLE);
        mPauseBtn.setVisibility(View.VISIBLE);
        mPlayerService.getService().resumePlayer();
    }

    public void start() {
        mStartBtn.setVisibility(View.INVISIBLE);
        mPauseBtn.setVisibility(View.VISIBLE);

        playerServiceIntent = new Intent(ctx,PlayerService.class);
        playerServiceIntent.putExtra("Song",mSong);

        ctx.startService(playerServiceIntent);
        ctx.bindService(playerServiceIntent,conn, Context.BIND_AUTO_CREATE);

        isPlaying = true;
    }

    public void stop() {
        if(playerServiceIntent == null)
            playerServiceIntent = new Intent(ctx,PlayerService.class);

        ctx.stopService(playerServiceIntent);
        ctx.unbindService(conn);

        mPauseBtn.setVisibility(View.INVISIBLE);
        mStartBtn.setVisibility(View.VISIBLE);

        isPlaying = false;
    }
}
