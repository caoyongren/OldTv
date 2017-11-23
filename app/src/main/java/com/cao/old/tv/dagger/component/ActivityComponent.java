package com.cao.old.tv.dagger.component;

import android.app.Activity;

import com.cao.old.tv.dagger.module.ActivityModule;
import com.cao.old.tv.dagger.scope.ActivityScope;
import com.cao.old.tv.view.activitys.menu.CollectionActivity;
import com.cao.old.tv.view.activitys.HistoryActivity;
import com.cao.old.tv.view.activitys.SearchActivity;
import com.cao.old.tv.view.activitys.VideoInfoActivity;
import com.cao.old.tv.view.activitys.VideoListActivity;
import com.cao.old.tv.view.activitys.WelcomeActivity;
import com.cao.old.tv.view.activitys.menu.WelfareActivity;

import dagger.Component;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity getActivity();

    void inject(VideoInfoActivity videoInfoActivity);//

    void inject(WelcomeActivity welcomeActivity);

    void inject(CollectionActivity collectionActivity);

    void inject(HistoryActivity historyActivity);

    void inject(SearchActivity searchActivity);

    void inject(VideoListActivity videoListActivity);

    void inject(WelfareActivity welfareActivity);
}
