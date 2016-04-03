package com.study.gourdboy.phonedefender.utils;

import android.os.Message;

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
            for(byte b:digest)
            {
                int ret = b&0xff;
                String hexString = Integer.toHexString(ret);
                if(hexString.length()==1)
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
}
