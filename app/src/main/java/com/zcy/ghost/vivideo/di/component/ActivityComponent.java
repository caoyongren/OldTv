package com.zcy.ghost.vivideo.di.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.di.module.ActivityModule;
import com.zcy.ghost.vivideo.di.scope.ActivityScope;
import com.zcy.ghost.vivideo.ui.activitys.CollectionActivity;
import com.zcy.ghost.vivideo.ui.activitys.HistoryActivity;
import com.zcy.ghost.vivideo.ui.activitys.SearchActivity;
import com.zcy.ghost.vivideo.ui.activitys.VideoInfoActivity;
import com.zcy.ghost.vivideo.ui.activitys.VideoListActivity;
import com.zcy.ghost.vivideo.ui.activitys.WelcomeActivity;
import com.zcy.ghost.vivideo.ui.activitys.WelfareActivity;

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
