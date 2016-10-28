package com.example.vladimir.batterymanager;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IServiceCommunication {

    private Button mServiceBtn;
    private TextView mStartedOn;
    private TextView mStartPercentage;
    private TextView mLatestUpdate;
    private TextView mLatestPercentage;
    private TextView mSummary;
    private Intent mBatteryServiceIntent;
    private BatteryService.BatteryServiceBinder mBatteryService;
    private Boolean isServiceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceBtn = (Button) findViewById(R.id.start_stop_btn);
        mStartedOn = (TextView) findViewById(R.id.txt_started_on);
        mStartPercentage = (TextView) findViewById(R.id.pct_on_start);
        mLatestUpdate = (TextView) findViewById(R.id.txt_latest_update);
        mLatestPercentage = (TextView) findViewById(R.id.pct_latest_update);
        mSummary = (TextView) findViewById(R.id.summary);

        mServiceBtn.setOnClickListener(this);

        if(isMyServiceRunning(BatteryService.class)){
            //I can't make this work for some reason
            updateUI();
            isServiceRunning = true;
            mServiceBtn.setText("End Service");
        }


    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBatteryService = (BatteryService.BatteryServiceBinder)service;
            mBatteryService.getService().setServiceCallback(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBatteryService = null;
        }
    };

    @Override
    public void onClick(View v) {
        if(v.equals(mServiceBtn)){
            if(isServiceRunning){
                //It's running, shut it down
                unbindService(conn);
                stopService(mBatteryServiceIntent);
                mServiceBtn.setText("Start service");
                isServiceRunning = false;
            }
            else{
                //It isn't running, start it
                mBatteryServiceIntent = new Intent(this, BatteryService.class);
                bindService(mBatteryServiceIntent, conn, BIND_AUTO_CREATE);
                startService(mBatteryServiceIntent);
                mServiceBtn.setText("End Service");
                isServiceRunning = true;
            }
        }
    }

    @Override
    public void updateUI() {
        BatteryInformation startInfo = mBatteryService.getService().getStartInfo();
        BatteryInformation currentInfo = mBatteryService.getService().getCurrentInfo();
        mStartedOn.setText("Started on: "+startInfo.getDate());
        mStartPercentage.setText(startInfo.getBatteryPct()+"%");
        mLatestUpdate.setText("Latest update: "+currentInfo.getDate());
        mLatestPercentage.setText(currentInfo.getBatteryPct()+"%");
        Integer diff = Integer.parseInt(startInfo.getBatteryPct())-Integer.parseInt(currentInfo.getBatteryPct());
        if(diff.equals(0)){
            mSummary.setText("Your battery state hasn't changed since you started the service.");
        }
        else if(diff>0){
            mSummary.setText("You have lost "+diff+"% of your battery.");
        }
        else{
            mSummary.setText("You have charged "+(-diff)+"%");
        }
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
