package com.zcy.ghost.vivideo.di.component;

import android.app.Activity;

import com.zcy.ghost.vivideo.di.module.FragmentModule;
import com.zcy.ghost.vivideo.di.scope.FragmentScope;
import com.zcy.ghost.vivideo.ui.fragments.classification.ClassificationFragment;
import com.zcy.ghost.vivideo.ui.fragments.discover.DiscoverFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(DiscoverFragment dailyFragment);

    void inject(ClassificationFragment dailyFragment);

}
