package com.study.gourdboy.phonedefender.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.service.MyNumberLocationService;
import com.study.gourdboy.phonedefender.utils.RunningServiceUtil;
import com.study.gourdboy.phonedefender.view.SettingItem;

public class SettingActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initLocationService();
    }
    private void initLocationService()
    {
        SettingItem si_setting_showlocation = (SettingItem) findViewById(R.id.si_setting_showlocation);
        si_setting_showlocation.setMyOnClickListener(new SettingItem.MyOnclickListener()
        {
            @Override
            public void myCheckOnclick()
            {
                startService(new Intent(SettingActivity.this,MyNumberLocationService.class));
            }

            @Override
            public void myCancelOnclick()
            {
                stopService(new Intent(SettingActivity.this, MyNumberLocationService.class));
            }
        });
        if(RunningServiceUtil.serviceIsRunning(this,"com.study.gourdboy.phonedefender.service.MyNumberLocationService"))
        {
            si_setting_showlocation.setCheckBox(true);
        }
        else
        {
            si_setting_showlocation.setCheckBox(false);
        }
    }
    public void settoastlocation(View view)
    {
        startActivity(new Intent(this,SetToastLocationActivity.class));
    }
}
