package com.zcy.ghost.vivideo.dagger.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.dagger.module.FragmentModule;
import com.zcy.ghost.vivideo.dagger.scope.FragmentScope;
import com.zcy.ghost.vivideo.view.fragments.CommentFragment;
import com.zcy.ghost.vivideo.view.fragments.tab.TabFindFragment;
import com.zcy.ghost.vivideo.view.fragments.tab.TabMySelfFragment;
import com.zcy.ghost.vivideo.view.fragments.tab.TabChoiceFragment;
import com.zcy.ghost.vivideo.view.fragments.tab.TabTopicFragment;

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
