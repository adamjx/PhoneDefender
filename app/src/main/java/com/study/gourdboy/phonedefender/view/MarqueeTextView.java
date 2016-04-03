package com.study.gourdboy.phonedefender.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * Created by gourdboy on 2016/3/29.
 */
public class MarqueeTextView extends TextView
{
    public MarqueeTextView(Context context)
    {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    @Override
    public boolean isFocused()
    {
        return true;
//        return super.isFocused();
    }
}
