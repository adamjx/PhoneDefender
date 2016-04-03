package com.study.gourdboy.phonedefender.activities;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.study.gourdboy.phonedefender.R;
public class Setup1Activity extends SetupBaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }
    @Override
    public void next(View view)
    {
        startActivity(new Intent(this,Setup2Activity.class));
        overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
    }
    @Override
    public void previous(View view)
    {

    }
}
