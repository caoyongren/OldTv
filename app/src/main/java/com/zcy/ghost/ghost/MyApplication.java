package com.zcy.ghost.ghost;


import android.app.Application;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.zcy.ghost.ghost.views.AppBlockCanaryContext;

/******************************************
 * 类名称：MyApplication
 * 类描述：
 *
 * @version: 2.3.1
 * @author: caopeng
 * @time: 2016/9/13 10:53
 ******************************************/
public class MyApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        //初始化内存泄漏检测
        LeakCanary.install(this);
        //初始化过度绘制检测
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}