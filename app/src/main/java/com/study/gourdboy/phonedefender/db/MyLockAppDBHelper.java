package com.study.gourdboy.phonedefender.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gourdboy on 2016/4/5.
 */
public class MyLockAppDBHelper extends SQLiteOpenHelper
{
    public MyLockAppDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table lockapp( _id integer primary key autoincrement, packagename varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
