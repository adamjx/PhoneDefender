package com.study.gourdboy.phonedefender.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gourdboy on 2016/3/31.
 */
public abstract class SetupBaseActivity extends ActionBarActivity
{
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        gestureDetector = new GestureDetector(this, new MyGestureListener());
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            float startx = e1.getX();
            float starty = e1.getY();
            float endx = e2.getX();
            float endy = e2.getY();
            if(endx-startx>200)
            {
                previous(null);
            }
            if(startx-endx>200)
            {
                next(null);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    public abstract void next(View view);
    public abstract void previous(View view);
}
