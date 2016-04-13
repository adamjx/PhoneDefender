package com.study.gourdboy.phonedefender.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.application.MyApplication;

public class SetToastLocationActivity extends ActionBarActivity
{
    private LinearLayout ll_setlocation_toast;
    private int windowWidth;
    private int windowHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_toast_location);
        ll_setlocation_toast = (LinearLayout) findViewById(R.id.ll_setlocation_toast);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_setlocation_toast.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        windowHeight = dm.heightPixels;
        windowWidth = dm.widthPixels;
        ll_setlocation_toast.setOnTouchListener(new View.OnTouchListener()
        {
            float startx;
            float starty;
            float endx;
            float endy;
            int finalx;
            int finaly;
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        startx = event.getRawX();
                        starty = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endx = event.getRawX();
                        endy = event.getRawY();
                        float dx = endx - startx;
                        float dy = endy - starty;
                        //获取的是相对父控件的坐标，所以之后绘制的时候需要加上一个bar的高度
                        float oldx = ll_setlocation_toast.getX();
                        float oldy = ll_setlocation_toast.getY();
                        float oldrightx = oldx+ll_setlocation_toast.getWidth();
                        float oldbottomy = oldy+ll_setlocation_toast.getHeight();
                        if(oldx+dx<0||oldy+dy<50||oldrightx+dx>windowWidth||oldbottomy+dy>windowHeight)
                        {
                            break;
                        }
                        ll_setlocation_toast.layout((int)(oldx+dx),(int)(oldy+dy),(int)(oldrightx+dx),(int)(oldbottomy+dy));
                        finalx = (int)(oldx+dx);
                        finaly = (int)(oldy+dy);
                        //以当前move事件的终点作为下一次移动的起点
                        startx=endx;
                        starty=endy;
                        break;
                    case MotionEvent.ACTION_UP:
                        MyApplication.setConfigIntValues("toastx", finalx);
                        MyApplication.setConfigIntValues("toasty", finaly);
                        break;
                }
                return false;
            }
        });
        //增加双击事件
        ll_setlocation_toast.setOnClickListener(new View.OnClickListener()
        {
            boolean firstClick = true;
            long firstClickTime;
            @Override
            public void onClick(View v)
            {
                if(firstClick)
                {
                    firstClickTime = System.currentTimeMillis();
                    firstClick = false;
                }
                else
                {
                    long secondeClickTime = System.currentTimeMillis();
                    if(secondeClickTime-firstClickTime<500)
                    {
                        ll_setlocation_toast.layout(200, 300, 200 + ll_setlocation_toast.getWidth(), 300 + ll_setlocation_toast.getHeight());
                        MyApplication.setConfigIntValues("toastx", 200);
                        MyApplication.setConfigIntValues("toasty", 300);
                        firstClick = true;
                    }
                    else
                    {
                        firstClick = true;
                    }
                }
            }
        });
    }
}
