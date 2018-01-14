package com.master.old.tv.widget.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

public class UnScrollPagerView extends ViewPager {

    private boolean isScrollable = false;
    private Context mContext;

    public UnScrollPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollPagerView(Context context) {
        super(context);
        this.mContext = context;
        Scroller scroller = new Scroller(mContext);
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isScrollable)
            return super.onTouchEvent(arg0);
        boolean b = super.onTouchEvent(arg0);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isScrollable)
            return super.onInterceptTouchEvent(arg0);
        return false;
    }
}