package com.zcy.ghost.vivideo.app;


import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;
import com.zcy.ghost.vivideo.widget.AppBlockCanaryContext;

/******************************************
 * 类名称：App
 * 类描述：
 *
 * @version: 2.3.1
 * @author: caopeng
 * @time: 2016/9/13 10:53
 ******************************************/
public class App extends Application {
    private static App instance;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //蒲公英crash上报
        PgyCrashManager.register(this);
        //初始化内存泄漏检测
        LeakCanary.install(this);
        //初始化过度绘制检测
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}