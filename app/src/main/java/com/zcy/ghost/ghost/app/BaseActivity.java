package com.zcy.ghost.ghost.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zcy.ghost.ghost.utils.SystemUtils;

import rx.Subscription;

/**
 * Description: BaseActivity
 * Creator: yxc
 * date: 2016/9/7 11:45
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isConnection = false;
    protected Subscription subscription;
    protected static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isConnection = SystemUtils.checkNet(this);
        regReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netListener != null)
            unregisterReceiver(netListener);
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
     * 跳转
     */
    public void jump(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected String getInputString(EditText editText) {
        return editText.getText().toString().trim();
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
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                isConnection = wifiManager.isWifiEnabled();
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
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    protected void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
