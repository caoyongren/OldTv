package com.cao.old.tv.dagger.component;


import com.cao.old.tv.app.App;
import com.cao.old.tv.dagger.module.AppModule;
import com.cao.old.tv.dagger.module.HttpModule;
import com.cao.old.tv.model.DataManager;
import com.cao.old.tv.model.db.RealmHelper;
import com.cao.old.tv.model.http.RetrofitHelper1;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper1 retrofitHelper();  //提供http的帮助类

    RealmHelper realmHelper();    //提供数据库帮助类

}
