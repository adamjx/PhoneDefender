package com.study.gourdboy.phonedefender.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.study.gourdboy.phonedefender.service.MyNumberLocationService;

/**
 * Created by gourdboy on 2016/3/28.
 */
public class MyApplication extends Application
{
    public static String SERVER_URL;
    public static SharedPreferences configsp;
    public static SharedPreferences.Editor editor;
    @Override
    public void onCreate()
    {
        super.onCreate();
        SERVER_URL = "http://192.168.3.108/PhoneDefender";
        configsp = getSharedPreferences("config",MODE_PRIVATE);
        editor = configsp.edit();
        startService(new Intent(this, MyNumberLocationService.class));
    }
    public static void setConfigValues(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }

    @Override
    public void onTerminate()
    {
        stopService(new Intent(this,MyNumberLocationService.class));
        super.onTerminate();
    }
}
