package com.study.gourdboy.phonedefender.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.study.gourdboy.phonedefender.R;

public class AdvanceToolActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advancetool_activity);
    }
    public void querylocation(View view)
    {
        startActivity(new Intent(this,QueryLocationActivity.class));
    }
}
