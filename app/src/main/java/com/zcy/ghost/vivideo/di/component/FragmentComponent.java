package com.zcy.ghost.vivideo.di.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.di.module.FragmentModule;
import com.zcy.ghost.vivideo.di.scope.FragmentScope;
import com.zcy.ghost.vivideo.ui.fragments.CommentFragment;
import com.zcy.ghost.vivideo.ui.fragments.MineFragment;
import com.zcy.ghost.vivideo.ui.fragments.RecommendFragment;
import com.zcy.ghost.vivideo.ui.fragments.ClassificationFragment;
import com.zcy.ghost.vivideo.ui.fragments.DiscoverFragment;
import com.zcy.ghost.vivideo.ui.fragments.VideoIntroFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(DiscoverFragment dailyFragment);

    void inject(ClassificationFragment dailyFragment);

    void inject(RecommendFragment recommendFragment);

    void inject(MineFragment mineFragment);

    void inject(CommentFragment commentFragment);

//    void inject(VideoIntroFragment videoIntroFragment);

}
