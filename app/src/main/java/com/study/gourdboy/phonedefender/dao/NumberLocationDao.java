package com.study.gourdboy.phonedefender.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by gourdboy on 2016/4/2.
 */
public class NumberLocationDao
{
    public static String getNumberLocation(Context ctx,String number)
    {
        String result = "";
        if(number.matches("1[3578]\\d{9}"))
        {
            String subnum = number.substring(0, 7);
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(ctx.getFilesDir() + "/location.db", null);
            Cursor cursor = db.rawQuery("  select city , cardtype from address_tb where _id = ( select  outkey  from numinfo where mobileprefix =" + subnum + ")", null);
            while (cursor.moveToNext())
            {
                int city = cursor.getColumnIndex("city");
                int cardtype = cursor.getColumnIndex("cardtype");
                String citystr = cursor.getString(city);
                String cardtypestr = cursor.getString(cardtype);
                result = citystr + cardtypestr;
            }
            if (result.isEmpty())
            {
                return "查询不到！";
            }
            return result;
        }
        result = "您的号码格式不正确，请重新输入";
        return result;
    }
}
