package com.study.gourdboy.phonedefender.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by gourdboy on 2016/4/3.
 */
public class RunningServiceUtil
{
    public static boolean serviceIsRunning(Context ctx,String servicename)//全类名
    {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);//100代表获取当前service最多数目值
        for (ActivityManager.RunningServiceInfo serviceInfo:runningServices)
        {
            if(servicename.equals(serviceInfo.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
}
