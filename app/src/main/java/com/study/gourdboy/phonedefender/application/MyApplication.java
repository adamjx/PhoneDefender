package com.study.gourdboy.phonedefender.application;

import android.app.ActivityManager;
import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.bean.ProcessInfo;
import com.study.gourdboy.phonedefender.service.MyNumberLocationService;
import com.study.gourdboy.phonedefender.utils.ProcessUtil;
import com.study.gourdboy.phonedefender.widget.MyWidgetProvider;

import java.util.List;

/**
 * Created by gourdboy on 2016/3/28.
 */
public class MyApplication extends Application
{
    public static String SERVER_URL;
    public static SharedPreferences configsp;
    public static SharedPreferences.Editor editor;
    private MyReceiver myReceiver;
    @Override
    public void onCreate()
    {
        super.onCreate();
        IntentFilter filter = new IntentFilter("com.study.gourdboy.phonedefender.widgetupdate");
        myReceiver = new MyReceiver();
        getApplicationContext().registerReceiver(myReceiver,filter);
        SERVER_URL = "http://192.168.3.108/PhoneDefender";
        configsp = getSharedPreferences("config", MODE_PRIVATE);
        editor = configsp.edit();
        if(configsp.getBoolean("showloaction",false))
        {
            startService(new Intent(this, MyNumberLocationService.class));
        }
    }
    public static void setConfigValues(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }
    public static void setConfigIntValues(String key,int value)
    {
        editor.putInt(key,value);
        editor.commit();
    }
    @Override
    public void onTerminate()
    {
        getApplicationContext().unregisterReceiver(myReceiver);
        super.onTerminate();
    }
    class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //第一步，kill后台进程
            List<ProcessInfo> processInfoList = ProcessUtil.getAllProcessInfo(context);
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            for(ProcessInfo processInfo:processInfoList)
            {
                if(processInfo.getPackagename().equals(getPackageName()))
                {
                    continue;
                }
                activityManager.killBackgroundProcesses(processInfo.getPackagename());//需要权限
            }
            //更新widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context,MyWidgetProvider.class);
            RemoteViews remoteViews = new RemoteViews("com.study.gourdboy.phonedefender", R.layout.processmanager_appwidget);
            String availableRAM = "可用内存:"+ProcessUtil.getAvailableRAM(context);
            String totalProcess = "总进程数:"+ProcessUtil.getRunningProcessCount(context);
            remoteViews.setTextViewText(R.id.tv_processwidget_memory, availableRAM);
            remoteViews.setTextViewText(R.id.tv_processwidget_count,totalProcess);
            appWidgetManager.updateAppWidget(name,remoteViews);
        }
    }
}
