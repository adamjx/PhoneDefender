package com.study.gourdboy.phonedefender.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;

/**
 * Created by gourdboy on 2016/3/31.
 */
public class SettingItem extends RelativeLayout implements View.OnClickListener
{
    private static final String TAG = "SETTINGITEM";
    private String itemtitle;
    private String onstring;
    private String offstring;
    private String sp_keyname;
    private CheckBox cb_setting_update;
    private TextView tv_setting_autoupdate;
    private TextView tv_setting_updatestatus;
    private SharedPreferences.Editor editor;
    private MyOnclickListener mylistener;
    public SettingItem(Context context)
    {
        super(context);
        init(null);
    }
    public SettingItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }
    private void init(AttributeSet attrs)
    {
        editor = MyApplication.editor;
        View v = View.inflate(getContext(), R.layout.update_item,null);
        tv_setting_autoupdate = (TextView) v.findViewById(R.id.tv_setting_autoupdate);
        cb_setting_update = (CheckBox) v.findViewById(R.id.cb_setting_update);
        tv_setting_updatestatus = (TextView) v.findViewById(R.id.tv_setting_updatestatus);
        tv_setting_autoupdate.setText(itemtitle);
        if(attrs!=null)
        {
            itemtitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "itemtitle");
            onstring = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "onstring");
            offstring = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "offstring");
            sp_keyname = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "sp_keyname");
            tv_setting_autoupdate.setText(itemtitle);
            if (sp_keyname != null)
            {
                if (MyApplication.configsp.getBoolean(sp_keyname, true))
                {
                    tv_setting_updatestatus.setText(onstring);
                    cb_setting_update.setChecked(true);
                }
                else
                {
                    tv_setting_updatestatus.setText(offstring);
                    cb_setting_update.setChecked(false);
                }
            }
        }
        addView(v);
        setOnClickListener(null);
    }
    @Override
    public void setOnClickListener(OnClickListener l)
    {
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        boolean checked = cb_setting_update.isChecked();
        Log.i(TAG,checked+"");
        if(checked)
        {
            Log.i(TAG,checked+"取消");
            cb_setting_update.setChecked(false);
            tv_setting_updatestatus.setText(offstring);
            editor.putBoolean(sp_keyname,false);
            editor.commit();
            if(mylistener!=null)
            {
                mylistener.myCancelOnclick();
            }
        }
        else
        {
            Log.i(TAG,checked+"确认");
            cb_setting_update.setChecked(true);
            tv_setting_updatestatus.setText(onstring);
            editor.putBoolean(sp_keyname,true);
            editor.commit();
            if(mylistener!=null)
            {
                mylistener.myCheckOnclick();
            }
        }
    }
    public void setCheckBox(boolean flag)
    {
        if(flag)
        {
            cb_setting_update.setChecked(true);
            tv_setting_updatestatus.setText(onstring);
        }
        else
        {
            tv_setting_updatestatus.setText(offstring);
            cb_setting_update.setChecked(false);
        }
    }
    public interface MyOnclickListener
    {
        void myCheckOnclick();
        void myCancelOnclick();
    }
    public void setMyOnClickListener(MyOnclickListener l)
    {
        mylistener = l;
    }
}
