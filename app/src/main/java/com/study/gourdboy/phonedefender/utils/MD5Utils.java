package com.study.gourdboy.phonedefender.utils;

import android.os.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gourdboy on 2016/3/29.
 */
public class MD5Utils
{
    public static String getMD5Digest(String password)
    {
        String afterEncrypt = "";
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer result = new StringBuffer();
            for (byte b : digest)
            {
                int ret = b & 0xff;
                String hexString = Integer.toHexString(ret);
                if (hexString.length() == 1)
                {
                    result.append("0");
                }
                result.append(hexString);
            }
            afterEncrypt = result.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return afterEncrypt;
    }

    public static String getAppMD5Digest(String apkLocation)
    {
        String afterEncrypt = "";
        byte[] digest = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            File file = new File(apkLocation);
            if (file.exists())
            {
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int length = -1;
                while((length=fis.read(bytes))!=-1)
                {
                    md.update(bytes,0,length);//计算整个文件的MD5值
                }
                fis.close();
                digest = md.digest();
            }
            //将MD5值变成字符串
            if(digest!=null)
            {
                StringBuffer result = new StringBuffer();
                for (byte b : digest)
                {
                    int ret = b & 0xff;
                    String hexString = Integer.toHexString(ret);
                    if (hexString.length() == 1)
                    {
                        result.append("0");
                    }
                    result.append(hexString);
                }
                afterEncrypt = result.toString();
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return afterEncrypt;
    }
}