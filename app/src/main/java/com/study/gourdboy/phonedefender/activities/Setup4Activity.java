package com.study.gourdboy.phonedefender.activities;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.receiver.MyAdminReceiver;
import com.study.gourdboy.phonedefender.view.SettingItem;

import org.w3c.dom.Comment;

public class Setup4Activity extends SetupBaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }
    @Override
    public void next(View view)
    {
        startActivity(new Intent(this, PhoneSafeActitvity.class));
        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    @Override
    public void previous(View view)
    {
        startActivity(new Intent(this,Setup3Activity.class));
        overridePendingTransition(R.anim.slideinleft,R.anim.slideoutright);
    }
    public void active(View view)
    {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName mDeviceAdminSample = new ComponentName(this, MyAdminReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"hello,kitty");
        startActivityForResult(intent,100);
    }
}
