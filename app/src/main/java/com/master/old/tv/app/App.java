package com.master.old.tv.app;


import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.master.old.tv.dagger.component.AppComponent;
import com.master.old.tv.dagger.component.DaggerAppComponent;
import com.master.old.tv.dagger.module.AppModule;
import com.master.old.tv.dagger.module.HttpModule;
import com.master.old.tv.model.db.RealmHelper;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/*****************************
 * Create by MasterMan
 * Description:
 *   application的应用。
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: ２０１７年１１月２７日
 *****************************/

/**
 * Application类
 *   1. Application和Activity,Service一样是Android框架的一个系统组件，当Android程序启动时系统会创建一个Application对象，用来存储系统的一些信息。
 *   2. Android系统自动会为每个程序运行时创建一个Application类的对象且只创建一个，所以Application可以说是单例（singleton）模式的一个类.
 *   3. 我们在Application创建的时候初始化全局变量，同一个应用的所有Activity都可以取到这些全局变量的值，换句话说，
 *      我们在某一个Activity中改变了这些全局变量的值，那么在同一个应用的其他Activity中值就会改变。
 *   4. 建一个类继承Application并在AndroidManifest.xml文件中的application标签中进行注册（只需要给application标签增加name属性，并添加自己的 Application的名字即可）。
 *   5. Application对象的生命周期是整个程序中最长的，它的生命周期就等于这个程序的生命周期。
 *      因为它是全局的单例的，所以在不同的Activity,Service中获得的对象都是同一个对象
 *   6. 应用场景:
 *     1. 应用程序的全局变量
 *     2. 清单文件:
 *       <application
             android:name="CustomApplication">
         </application>
       3. (CustomApplication) getApplication(); // 获得CustomApplication对象.
 *
 * */
public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static App getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        /**
         * 初始化Realm
         * init(getApplicationContext())放这里程序的入口很合适。
         * */
        initRealm();
        Realm.init(getApplicationContext());
    }

    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出app
     * android.os.Process.killProcess(android.os.Process.myPid());//结束进程
     * */
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

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static AppComponent appComponent;

    /**
     * 注解中: 连接器使用；
     * @return : component
     * */
    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }
}