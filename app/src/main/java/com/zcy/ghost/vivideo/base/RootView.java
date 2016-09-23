package com.zcy.ghost.vivideo.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.Unbinder;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class RootView extends LinearLayout {
    /**
     * 是否被销毁
     */
    protected boolean mActive;
    protected Context mContext;
    protected Unbinder unbinder;

    public RootView(Context context) {
        super(context);
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
        unbinder.unbind();
        mContext = null;
    }
}
