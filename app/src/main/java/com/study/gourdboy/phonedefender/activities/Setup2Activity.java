package com.study.gourdboy.phonedefender.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;
import com.study.gourdboy.phonedefender.view.SettingItem;

public class Setup2Activity extends SetupBaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        SettingItem si_setup2_bindsim = (SettingItem) findViewById(R.id.si_setup2_bindsim);
        si_setup2_bindsim.setMyOnClickListener(new SettingItem.MyOnclickListener()
        {
            @Override
            public void myCheckOnclick()
            {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String imsi = telephonyManager.getSimSerialNumber();
                MyApplication.setConfigValues("imsi",imsi);
            }
            @Override
            public void myCancelOnclick()
            {
                MyApplication.setConfigValues("imsi","");
            }
        });
    }

    @Override
    public void next(View view)
    {
        String imsi = MyApplication.configsp.getString("imsi","");
        if(imsi.isEmpty())
        {
            Toast.makeText(Setup2Activity.this, "请绑定SIM卡，否则无法使用本功能！", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(this, Setup3Activity.class));
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
        }
    }
    @Override
    public void previous(View view)
    {
        startActivity(new Intent(this,Setup1Activity.class));
        overridePendingTransition(R.anim.slideinleft,R.anim.slideoutright);
    }
}
