package com.study.gourdboy.phonedefender;
import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.Toast;

import com.study.gourdboy.phonedefender.dao.NumberLocationDao;

public class AndroidTest extends AndroidTestCase
{
    public void testLoog()
    {
        Log.i("tag", "123");
    }
    public void queryNum()
    {
        String location = NumberLocationDao.getNumberLocation(getContext(),"13888888888");
        Toast.makeText(getContext(), location, Toast.LENGTH_SHORT).show();
    }
}