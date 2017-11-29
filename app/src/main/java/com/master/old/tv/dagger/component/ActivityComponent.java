package com.master.old.tv.dagger.component;

import android.app.Activity;

import com.master.old.tv.dagger.module.ActivityModule;
import com.master.old.tv.dagger.scope.ActivityScope;
import com.master.old.tv.view.activitys.HistoryListActivity;
import com.master.old.tv.view.activitys.drawer.CollectionListActivity;
import com.master.old.tv.view.activitys.SearchListActivity;
import com.master.old.tv.view.activitys.VideoInfoActivity;
import com.master.old.tv.view.activitys.VideoListActivity;
import com.master.old.tv.view.activitys.WelcomeActivity;
import com.master.old.tv.view.activitys.drawer.WelfareActivity;

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

    void inject(CollectionListActivity collectionListActivity);

    void inject(HistoryListActivity historyListActivity);

    void inject(SearchListActivity searchListActivity);

    void inject(VideoListActivity videoListActivity);

    void inject(WelfareActivity welfareActivity);
}
