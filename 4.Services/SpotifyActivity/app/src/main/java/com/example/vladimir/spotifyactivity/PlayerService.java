package com.example.vladimir.spotifyactivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.io.IOException;



public class PlayerService extends Service {

    IBinder binder = new PlayerServiceBinder();
    MediaPlayer player = new MediaPlayer();
    Integer resumePosition;



    public class PlayerServiceBinder extends Binder
    {
        PlayerService getService()
        {
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String songUri = "android.resource://com.example.vladimir.spotifyactivity/raw/"+intent.getIntExtra("Song",R.raw.lose_yourself);
        startPlayer(songUri);
        return super.onStartCommand(intent, flags, startId);
    }

    public void pausePlayer() {
        resumePosition = player.getCurrentPosition();
        player.pause();
    }

    public void resumePlayer() {
        if(resumePosition==null){
            resumePosition=0;
        }
        player.seekTo(resumePosition);
        player.start();
    }

    public void startPlayer(String uri){
        try{
            player.setDataSource(getApplicationContext(),Uri.parse(uri));
            player.prepare();
            player.setLooping(true);
            player.start();
        } catch(IOException ex) {
        }
    }

    public void rewind(){
        Integer position = player.getCurrentPosition()-3000;
        player.seekTo(position);
    }

    public void fastForward(){
        Integer position = player.getCurrentPosition()+3000;
        player.seekTo(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.stop();
            player.reset();
            player.release();
        }
    }
}
