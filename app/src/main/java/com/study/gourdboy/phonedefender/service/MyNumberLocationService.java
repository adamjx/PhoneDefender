package com.study.gourdboy.phonedefender.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;
import com.study.gourdboy.phonedefender.dao.NumberLocationDao;

public class MyNumberLocationService extends Service
{
    private WindowManager mWM;
    private View view;
    public MyNumberLocationService()
    {
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }
    class MyPhoneStateListener extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            super.onCallStateChanged(state, incomingNumber);
            switch (state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    hideLocationView();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String location = NumberLocationDao.getNumberLocation(MyNumberLocationService.this,incomingNumber);
                    showLocationView(location);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }
    private void showLocationView(String location)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.mynumberlocation,null);
        TextView message = (TextView) view.findViewById(R.id.message);
        message.setText(location);
        mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;//半透明
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.gravity = Gravity.LEFT|Gravity.TOP;
        params.x = MyApplication.configsp.getInt("toastx",200);
        params.y = MyApplication.configsp.getInt("toasty",300);
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mWM.addView(view,params);
    }
    private void hideLocationView()
    {
        if(mWM!=null)
        {
            mWM.removeView(view);
        }
    }
}
