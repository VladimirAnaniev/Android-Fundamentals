package com.example.vladimir.batterymanager;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Vladimir on 21-Sep-16.
 */
public class BatteryService extends Service {

    private IBinder binder = new BatteryServiceBinder();
    private ArrayList<BatteryInformation> mInfo;
    private IServiceCommunication callback;



    public class BatteryServiceBinder extends Binder
    {
        BatteryService getService()
        {
            return BatteryService.this;
        }
    }

    public void setServiceCallback(IServiceCommunication callback) {
        this.callback = callback;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("android.intent.action.BATTERY_CHANGED")){

                String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                mInfo.add(new BatteryInformation(date,String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1))));

                if(callback != null)
                {
                    callback.updateUI();
                }
            }

            if(action.equals("android.intent.action.BATTERY_LOW")){
                showLowBatteryNotification();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        filter.addAction("android.intent.action.BATTERY_LOW");

        registerReceiver(receiver, filter);

        mInfo = new ArrayList<>();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInfo = new ArrayList<>();
        stopSelf();
        unregisterReceiver(receiver);
    }

    public void showLowBatteryNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Battery low")
                        .setContentText("Your battery is low, please charge.");

        // Sets an ID for the notification
        int mNotificationId = 1;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public BatteryInformation getStartInfo(){
        return mInfo.get(0);
    }

    public BatteryInformation getCurrentInfo(){
        return mInfo.get(mInfo.size()-1);
    }

}
