package com.zcy.ghost.ghost.app;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcy.ghost.ghost.app.activitys.MainActivity;
import com.zcy.ghost.ghost.app.theme.ColorUiUtil;
import com.zcy.ghost.ghost.utils.LogUtils;
import com.zcy.ghost.ghost.utils.SystemUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.ButterKnife;
import rx.Subscription;


/**
 * Description: BaseFragment
 * Creator: yxc
 * date: 2016/9/7 11:40
 */
public abstract class BaseFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected boolean isConnection = false; // 判断网络状态是否连接 默认为false;
    protected Subscription subscription;
    protected static long lastClickTime;
    protected View rootView;

    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        if (mContext != null) {
            this.mContext = mContext;
        } else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnection = SystemUtils.checkNet(mContext);
        LogUtils.v(TAG, "onCreate");
        regReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.v(TAG, "onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView(inflater);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    public String getName() {
        return BaseFragment.class.getName();
    }

    protected abstract int getLayoutResource();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.v(TAG, "onActivityCreated");
        initEvent();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.v(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.v(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.v(TAG, "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.v(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.v(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().register(this);
        super.onDestroyView();
        LogUtils.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (netListener != null)
            mContext.unregisterReceiver(netListener);
        LogUtils.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        LogUtils.v(TAG, "onDetach");
        super.onDetach();
    }

    protected void initView(LayoutInflater inflater) {
    }

    protected void initEvent() {
    }

    private void regReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        mContext.registerReceiver(netListener, filter);
    }

    private BroadcastReceiver netListener = new BroadcastReceiver() {

        String wifiAction = "android.net.wifi.WIFI_STATE_CHANGED";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(wifiAction)) {
//                WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//                isConnection = wifiManager.isWifiEnabled();
                isConnection = SystemUtils.checkNet(context);
            }
        }
    };

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 防止重复点击
     *
     * @return 是否重复点击
     */
    @SuppressWarnings("unused")
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Subscriber(tag = MainActivity.Set_Theme_Color)
    public void setTheme(String arg) {
        final View rootView = getActivity().getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getContext().getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }
}
