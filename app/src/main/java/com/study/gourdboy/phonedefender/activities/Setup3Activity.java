package com.study.gourdboy.phonedefender.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;

public class Setup3Activity extends SetupBaseActivity
{
    private EditText et_setup3_safenum;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et_setup3_safenum = (EditText) findViewById(R.id.et_setup3_safenum);
    }
/*    //方法一：
    public void selectcontact(View view)
    {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, 100);
    }*/
    public void selectcontact(View view)
    {
        startActivityForResult(new Intent(this,ContactActivity.class),200);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==100)
            {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri,projection,null,null,null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                et_setup3_safenum.setText(number);
            }
        }
        else if(resultCode==1000)
        {
            if(requestCode==200)
            {
                String number = data.getStringExtra("number");
                et_setup3_safenum.setText(number);
            }
        }
    }

    @Override
    public void next(View view)
    {
        String number = et_setup3_safenum.getText().toString();
        if(!number.isEmpty())
        {
            MyApplication.setConfigValues("safenum",number);
            startActivity(new Intent(this, Setup4Activity.class));
            overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
        }
        else
        {
            Toast.makeText(Setup3Activity.this, "安全号码不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void previous(View view)
    {
        startActivity(new Intent(this,Setup2Activity.class));
        overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
    }
}
