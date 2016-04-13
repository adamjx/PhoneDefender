package com.study.gourdboy.phonedefender.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.study.gourdboy.phonedefender.db.MyLockAppDBHelper;
import com.study.gourdboy.phonedefender.provider.MyAppLockProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gourdboy on 2016/4/5.
 */
public class LockAppDao
{
/*    private MyLockAppDBHelper helper;
    private SQLiteDatabase db;*/
    private Context context;

    public LockAppDao(Context ctx)
    {
       /* helper = new MyLockAppDBHelper(ctx,"lockapp.db",null,1);
        db = helper.getReadableDatabase();*/
        context = ctx;
    }
    //加入到该数据库
    public long insertToDB(String pkg)
    {
        if(isLocked(pkg))
        {
            return -2;
        }
        /*ContentValues values = new ContentValues();
        values.put("packagename", pkg);
        return db.insert("lockapp",null,values);*/
        ContentValues values = new ContentValues();
        values.put("packagename", pkg);
        context.getContentResolver().insert(Uri.parse("content://com.study.gourdboy.phonedefender.applock"), values);
        return 0;
    }
    //从数据库中删除
    public int deleteFromDB(String pkg)
    {
        String[] args = {pkg};
//        return db.delete("lockapp", "packagename=?", args);
        return context.getContentResolver().delete(Uri.parse("content://com.study.gourdboy.phonedefender.applock"), null, args);
    }
    public boolean isLocked(String pkg)
    {
        boolean flag = false;
//        Cursor cursor = db.rawQuery("select * from lockapp where packagename=?", new String[]{pkg});
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.study.gourdboy.phonedefender.applock"), null,
                "packagename=?", new String[]{pkg}, null);
        if(cursor.moveToNext())
        {
            flag = true;
        }
        cursor.close();
        return flag;
    }
    public List<String> getAllLockedApp()
    {
        List<String> lockedapplist = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select packagename from lockapp  ",null);
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.study.gourdboy.phonedefender.applock"), new String[]{"packagename"}, null, null,null);
        while(cursor.moveToNext())
        {
            String packagename = cursor.getString(0);
            lockedapplist.add(packagename);
        }
        cursor.close();
        return lockedapplist;
    }
}
