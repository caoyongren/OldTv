package com.master.old.tv.dagger.component;

import android.app.Activity;

import com.master.old.tv.dagger.module.ActivityModule;
import com.master.old.tv.dagger.scope.ActivityScope;
import com.master.old.tv.view.activitys.menu.CollectionActivity;
import com.master.old.tv.view.activitys.HistoryActivity;
import com.master.old.tv.view.activitys.SearchActivity;
import com.master.old.tv.view.activitys.VideoInfoActivity;
import com.master.old.tv.view.activitys.VideoListActivity;
import com.master.old.tv.view.activitys.WelcomeActivity;
import com.master.old.tv.view.activitys.menu.WelfareActivity;

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
