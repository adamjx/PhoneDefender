package com.study.gourdboy.phonedefender.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.study.gourdboy.phonedefender.application.MyApplication;
/**
 * Created by gourdboy on 2016/4/1.
 */
public class MyBootCompleteReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        boolean anti_thief = MyApplication.configsp.getBoolean("anti_thief",true);
        if(anti_thief)
        {
            String imsi_saved = MyApplication.configsp.getString("imsi","");
            TelephonyManager mTelmanager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            String imsi_current = mTelmanager.getSimSerialNumber();
            if(!imsi_current.equals(imsi_saved))
            {
                SmsManager smsManager = SmsManager.getDefault();
                String safenum = MyApplication.configsp.getString("safenum","");
                smsManager.sendTextMessage(safenum,null,"您的手机被换SIM卡了！——手机卫士",null,null);
            }
        }
    }
}
