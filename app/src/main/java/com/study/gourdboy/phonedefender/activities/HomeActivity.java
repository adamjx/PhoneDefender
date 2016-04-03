package com.study.gourdboy.phonedefender.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;
import com.study.gourdboy.phonedefender.utils.MD5Utils;

public class HomeActivity extends ActionBarActivity
{
    GridView gv_home_content;
    private int[] iconarray ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app
            ,    R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};

    private  String[] titles={"手机防盗","通讯卫士","软件管理",
            "进程管理","流量统计","手机杀毒",
            "缓存清理","高级工具","设置中心"};
    private final int  CONTENT_NUM = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        gv_home_content = (GridView) findViewById(R.id.gv_home_content);
        gv_home_content.setAdapter(new GridAdapter());
        gv_home_content.setOnItemClickListener(new MyItemOnClickListener());
    }
    class GridAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return CONTENT_NUM;
        }
        @Override
        public Object getItem(int position)
        {
            return null;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = View.inflate(HomeActivity.this,R.layout.item_gridview,null);
            ImageView  iv_gv_icon = (ImageView) v.findViewById(R.id.iv_gv_icon);
            TextView tv_gv_name = (TextView) v.findViewById(R.id.tv_gv_name);
            iv_gv_icon.setImageResource(iconarray[position]);
            tv_gv_name.setText(titles[position]);
            return v;
        }
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    }
    class MyItemOnClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                    //设置一个密码，输入密码正确之后才可以进入设置
                    //第一次进入的时候让他设置密码，保存到sp里。之后第二次以后才验证密码
                    String phonesafe_pwd = MyApplication.configsp.getString("phonesafe_pwd","");
                    if(phonesafe_pwd.isEmpty())
                    {
                        showSteppwdDialog();
                    }
                    else
                    {
                        showInputpwdDialog();
                    }
                    break;
                case 7:
                    startActivity(new Intent(HomeActivity.this,AdvanceToolActivity.class));
                    break;
            }
        }
    }

    private void showSteppwdDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = View.inflate(this,R.layout.steppwd_dialog,null);
        final EditText et_dialog_pwd = (EditText) v.findViewById(R.id.et_dialog_pwd);
        final EditText et_dialog_pwdcon = (EditText) v.findViewById(R.id.et_dialog_pwdcon);
        Button bt_setpwddialog_confirm = (Button) v.findViewById(R.id.bt_setpwddialog_confirm);
        Button bt_setpwddialog_cancel = (Button) v.findViewById(R.id.bt_setpwddialog_cancel);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        bt_setpwddialog_confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String pwd = et_dialog_pwd.getText().toString();
                String pwdcon = et_dialog_pwdcon.getText().toString();
                if (!pwd.isEmpty() && !pwdcon.isEmpty())
                {
                    if (pwd.equals(pwdcon))
                    {
                        String pwdMD5 = MD5Utils.getMD5Digest(pwd);
                        MyApplication.setConfigValues("phonesafe_pwd", pwdMD5);
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this,PhoneSafeActitvity.class));
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this, "密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_setpwddialog_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }
    private void showInputpwdDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = View.inflate(this,R.layout.inputpwd_dialog,null);
        final EditText et_condialog_pwd = (EditText) v.findViewById(R.id.et_condialog_pwd);
        Button bt_conpwddialog_confirm = (Button) v.findViewById(R.id.bt_conpwddialog_confirm);
        Button bt_conpwddialog_cancle = (Button) v.findViewById(R.id.bt_conpwddialog_cancle);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        bt_conpwddialog_confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String pwd = et_condialog_pwd.getText().toString();
                if (!pwd.isEmpty())
                {
                    String pwdMD5 = MD5Utils.getMD5Digest(pwd);
                    String savepwd = MyApplication.configsp.getString("phonesafe_pwd", "");
                    if (!savepwd.isEmpty())
                    {
                        if (savepwd.equals(pwdMD5))
                        {
                            startActivity(new Intent(HomeActivity.this,PhoneSafeActitvity.class));
                            dialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        bt_conpwddialog_cancle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }
}
