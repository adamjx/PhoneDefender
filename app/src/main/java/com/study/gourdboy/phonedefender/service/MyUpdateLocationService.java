package com.study.gourdboy.phonedefender.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.study.gourdboy.phonedefender.application.MyApplication;

import javax.security.auth.login.LoginException;

/**
 * Created by gourdboy on 2016/4/1.
 */
public class MyUpdateLocationService extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates("gps", 0, 0, new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                MyApplication.setConfigValues("longitude",longitude+"");
                MyApplication.setConfigValues("latitude",latitude+"");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }
        });
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }
}
