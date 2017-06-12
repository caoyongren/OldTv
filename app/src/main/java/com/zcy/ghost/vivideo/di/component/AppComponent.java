package com.zcy.ghost.vivideo.di.component;


import com.zcy.ghost.vivideo.app.App;
import com.zcy.ghost.vivideo.di.module.AppModule;
import com.zcy.ghost.vivideo.di.module.HttpModule;
import com.zcy.ghost.vivideo.model.DataManager;
import com.zcy.ghost.vivideo.model.db.RealmHelper;
import com.zcy.ghost.vivideo.model.http.RetrofitHelper1;

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
