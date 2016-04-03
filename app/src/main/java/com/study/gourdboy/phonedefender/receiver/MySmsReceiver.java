package com.study.gourdboy.phonedefender.receiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;
import com.study.gourdboy.phonedefender.service.MyUpdateLocationService;
/**
 * Created by gourdboy on 2016/4/1.
 */
public class MySmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Object[] objs = (Object[])intent.getExtras().get("pdus");
        for(Object obj:objs)
        {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();
            String sender = smsMessage.getOriginatingAddress();
            if(body.equals("*#alarm#*"))
            {
                playAlarm(context);
            }
            else if(body.equals("*#location#*"))
            {
                getlocation(context);
            }
            else if(body.equals("*#wipedata#*"))
            {
                wipedata(context);
            }
            else if(body.equals("*#lockscreen#*"))
            {
                lockscreen(context);
            }
        }
    }
    private void lockscreen(Context context)
    {
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDPM.lockNow();
        mDPM.resetPassword("123",0);
    }
    private void wipedata(Context context)
    {
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDPM.wipeData(0);
    }
    private void getlocation(Context context)
    {
        context.startService(new Intent(context, MyUpdateLocationService.class));
        String longitude = MyApplication.configsp.getString("longitude", "");
        String latitude = MyApplication.configsp.getString("latitude","");
        SmsManager smsManager = SmsManager.getDefault();
        String safenum = MyApplication.configsp.getString("safenum","");
        smsManager.sendTextMessage(safenum, null, "longitude: "+longitude+"---latitude: "+latitude+"  ——手机卫士", null, null);
        Log.i("longitudelatitude",longitude+" : "+latitude);
    }
    private void playAlarm(Context ctx)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(ctx, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
