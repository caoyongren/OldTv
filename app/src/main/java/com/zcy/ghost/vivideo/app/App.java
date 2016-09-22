package com.zcy.ghost.vivideo.app;


import android.app.Activity;
import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;
import com.zcy.ghost.vivideo.widget.AppBlockCanaryContext;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Activity> allActivities;

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

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}