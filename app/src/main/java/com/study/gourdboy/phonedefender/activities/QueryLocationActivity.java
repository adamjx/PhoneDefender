package com.study.gourdboy.phonedefender.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.dao.NumberLocationDao;

public class QueryLocationActivity extends ActionBarActivity
{
    EditText et_querylocation_num;
    TextView tv_querylocation_num;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_location);
        et_querylocation_num = (EditText) findViewById(R.id.et_querylocation_num);
        tv_querylocation_num = (TextView) findViewById(R.id.tv_querylocation_num);
        et_querylocation_num.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String location = NumberLocationDao.getNumberLocation(QueryLocationActivity.this,s.toString());
                tv_querylocation_num.setText(location);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    public void query(View view)
    {
        String num = et_querylocation_num.getText().toString();
        String location = NumberLocationDao.getNumberLocation(this,num);
        tv_querylocation_num.setText(location);
    }
}
