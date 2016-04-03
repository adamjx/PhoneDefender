package com.study.gourdboy.phonedefender.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity
{
    private static final String TAG = "splashActivity";
    private TextView tv_splash_version;
    private ProgressBar pb_splash_download;
    private String current_version;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        pb_splash_download = (ProgressBar) findViewById(R.id.pb_splash_download);
        current_version = getVersion();
        tv_splash_version.setText("version:"+current_version);
        if(MyApplication.configsp.getBoolean("autoupdate",true))
        {
            getNewVersion();
        }
        else
        {
            waitWhile();
        }
        copydb();
    }

    private void waitWhile()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                enterHome();
            }
        }).start();
    }

    private void copydb()
    {
        File file = new File(getFilesDir(),"location.db");
        if(file.exists())
        {
            return;
        }
        AssetManager assetManager = getAssets();
        try
        {
            InputStream is = assetManager.open("naddress.db");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length = -1;
            while((length=is.read(bytes))!=-1)
            {
                fos.write(bytes,0,length);
            }
            fos.close();
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private String getVersion()
    {
        PackageManager manager = getPackageManager();
        String versionName = "";
        try
        {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(),0);
            versionName = packageInfo.versionName;
//            Log.i(TAG, versionName);//版本信息1.0
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }
    private void getNewVersion()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MyApplication.SERVER_URL + "/version.json", new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if (statusCode == 200)
                {
                    String versionInformation = new String(responseBody);
                    try
                    {
                        JSONObject obj = new JSONObject(versionInformation);
                        String version = obj.getString("version");
                        String downloadURL = obj.getString("download_url");
                        String versionDescription = obj.getString("description");
                        float mnewversion = Float.parseFloat(version);
                        float mversion = Float.parseFloat(current_version);
                        if (mversion < mnewversion)
                        {
                            update(downloadURL, versionDescription);
                        }
                        else
                        {
                            enterHome();
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    enterHome();
                    Toast.makeText(SplashActivity.this, "获取更新信息失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                enterHome();
                Toast.makeText(SplashActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update(String url,String message)
    {
        final String apkurl = url;
        new AlertDialog.Builder(this).setTitle("发现新版本")
                .setMessage(message)
                .setPositiveButton("更新", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(apkurl, new AsyncHttpResponseHandler()
                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                            {
                                try
                                {
                                    pb_splash_download.setVisibility(View.VISIBLE);
                                    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/update.apk");
                                    FileOutputStream fos = new FileOutputStream(file);
                                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                                    bos.write(responseBody);
                                    bos.close();
                                    fos.close();
                                    install(file);
                                }
                                catch (FileNotFoundException e)
                                {
                                    e.printStackTrace();
                                    enterHome();
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                    enterHome();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                            {
                                Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onProgress(int bytesWritten, int totalSize)
                            {
                                super.onProgress(bytesWritten, totalSize);
                                pb_splash_download.setMax(totalSize);
                                pb_splash_download.setProgress(bytesWritten);
                            }
                            private void install(File file)
                            {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                startActivityForResult(intent,100);
                            }
                        });
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_CANCELED){
            //进入主页面
            enterHome();
        }
    }

    private void enterHome()
    {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
