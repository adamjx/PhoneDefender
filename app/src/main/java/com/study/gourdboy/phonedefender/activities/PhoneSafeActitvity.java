package com.study.gourdboy.phonedefender.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;

public class PhoneSafeActitvity extends ActionBarActivity
{
    TextView tv_phonesafe_safenum;
    ImageView iv_phonesafe_enalbe;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_safe_actitvity);
        tv_phonesafe_safenum = (TextView) findViewById(R.id.tv_phonesafe_safenum);
        iv_phonesafe_enalbe = (ImageView) findViewById(R.id.iv_phonesafe_enalbe);
        String safenum = MyApplication.configsp.getString("safenum","");
        if(safenum.isEmpty())
        {
            startActivity(new Intent(this,Setup1Activity.class));
        }
        else
        {
            tv_phonesafe_safenum.setText(safenum);
            boolean flag = MyApplication.configsp.getBoolean("anti_thief",true);
            if(flag)
            {
                iv_phonesafe_enalbe.setImageResource(R.drawable.lock);
            }
            else
            {
                iv_phonesafe_enalbe.setImageResource(R.drawable.unlock);
            }
        }
    }
    public void resetup(View view)
    {
        startActivity(new Intent(this,Setup1Activity.class));
    }
}
