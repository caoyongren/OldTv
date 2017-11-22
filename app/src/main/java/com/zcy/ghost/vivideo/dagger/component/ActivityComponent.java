package com.zcy.ghost.vivideo.dagger.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.dagger.module.ActivityModule;
import com.zcy.ghost.vivideo.dagger.scope.ActivityScope;
import com.zcy.ghost.vivideo.view.activitys.CollectionActivity;
import com.zcy.ghost.vivideo.view.activitys.HistoryActivity;
import com.zcy.ghost.vivideo.view.activitys.SearchActivity;
import com.zcy.ghost.vivideo.view.activitys.VideoInfoActivity;
import com.zcy.ghost.vivideo.view.activitys.VideoListActivity;
import com.zcy.ghost.vivideo.view.activitys.WelcomeActivity;
import com.zcy.ghost.vivideo.view.activitys.WelfareActivity;

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

    void inject(VideoInfoActivity videoInfoActivity);

    void inject(WelcomeActivity welcomeActivity);

    void inject(CollectionActivity collectionActivity);

    void inject(HistoryActivity historyActivity);

    void inject(SearchActivity searchActivity);

    void inject(VideoListActivity videoListActivity);

    void inject(WelfareActivity welfareActivity);
}
