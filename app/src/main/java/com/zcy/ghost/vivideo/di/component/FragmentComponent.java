package com.zcy.ghost.vivideo.di.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.di.module.FragmentModule;
import com.zcy.ghost.vivideo.di.scope.FragmentScope;
import com.zcy.ghost.vivideo.view.fragments.CommentFragment;
import com.zcy.ghost.vivideo.view.fragments.TabFindFragment;
import com.zcy.ghost.vivideo.view.fragments.TabMySelfFragment;
import com.zcy.ghost.vivideo.view.fragments.TabChoiceFragment;
import com.zcy.ghost.vivideo.view.fragments.TabTopicFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(TabFindFragment dailyFragment);

    void inject(TabTopicFragment dailyFragment);

    void inject(TabChoiceFragment tabChoiceFragment);

    void inject(TabMySelfFragment tabMySelfFragment);

    void inject(CommentFragment commentFragment);

//    void inject(VideoIntroFragment videoIntroFragment);

}
