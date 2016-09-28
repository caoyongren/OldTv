package com.zcy.ghost.vivideo.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.app.App;
import com.zcy.ghost.vivideo.utils.PreUtils;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.utils.SystemUtils;
import com.zcy.ghost.vivideo.widget.theme.ColorRelativeLayout;
import com.zcy.ghost.vivideo.widget.theme.Theme;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import rx.Subscription;

/**
 * Description: BaseActivity
 * Creator: yxc
 * date: 2016/9/7 11:45
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements EmptyView {

    protected boolean isConnection = false;
    protected Subscription subscription;
    protected static long lastClickTime;
    protected T mPresenter;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(true);
        onPreCreate();
        isConnection = SystemUtils.checkNet(this);
        regReceiver();
        App.getInstance().registerActivity(this);
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitleHeight(getRootView(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().unregisterActivity(this);
        if (netListener != null)
            unregisterReceiver(netListener);
        if (mPresenter != null)
            mPresenter.detachView();
        if (unbinder != null)
            unbinder.unbind();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    /**
     * 注册广播，监听网络状态
     */
    private void regReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(netListener, filter);
    }

    private BroadcastReceiver netListener = new BroadcastReceiver() {

        String wifiAction = "android.net.wifi.WIFI_STATE_CHANGED";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(wifiAction)) {
//                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//                isConnection = wifiManager.isWifiEnabled();
                isConnection = SystemUtils.checkNet(context);
            }
        }
    };

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private void setTitleHeight(View view) {
        if (view != null) {
            ColorRelativeLayout title = (ColorRelativeLayout) view.findViewById(R.id.title);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                if (title != null) {
                    ViewGroup.LayoutParams lp = title.getLayoutParams();
                    lp.height = ScreenUtil.dip2px(this, 48);
                    title.setLayoutParams(lp);
                    title.setPadding(0, 0, 0, 0);
                }
            }
        }
    }

    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
