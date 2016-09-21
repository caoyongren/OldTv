package com.zcy.ghost.vivideo.widget;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class SwipeViewPager extends ViewPager {

    private int mTouchSlop;

    public SwipeViewPager(Context context) {
        super(context);
        init();
    }

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final Context context = getContext();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    float mLastX;
    float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem() == 0) {
            float x = ev.getX();
            float y = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float xDiff = Math.abs(x - mLastY);
                    float yDiff = Math.abs(y - mLastY);
                    //在第一页，判断到是向左边滑动，即想滑动第二页
                    if (xDiff > 0 && x - mLastX < 0 && xDiff * 0.5f > yDiff) {
                        //告诉父容器不要拦截事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else if (yDiff > mTouchSlop && xDiff < mTouchSlop) {
                        //竖直滑动时，告诉父容器拦截事件，用于在ScrollView中可以竖直滑动
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            mLastX = x;
            mLastY = y;
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}