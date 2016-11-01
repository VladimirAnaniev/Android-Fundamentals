package com.naskogeorgiev.simpleshoppinglist;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.naskogeorgiev.simpleshoppinglist.services.APIService;


public class ShoppingApplication extends Application {

    private APIService mService;
    private Boolean mBound;

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(new Intent(ShoppingApplication.this,APIService.class),mConnection, Context.BIND_AUTO_CREATE);
    }

    public APIService getAPIService(){
        return this.mService;
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            APIService.LocalBinder binder = (APIService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



    @Override
    public void onTerminate() {
        super.onTerminate();
        if(mBound){
            unbindService(mConnection);
            mBound=false;
        }
    }
}
