package com.master.old.tv.base;

import android.os.Bundle;

import com.master.old.tv.app.App;
import com.master.old.tv.dagger.component.ActivityComponent;
import com.master.old.tv.dagger.component.DaggerActivityComponent;
import com.master.old.tv.dagger.module.ActivityModule;

import javax.inject.Inject;

/**
 * Description: MVP Activity基类
 * Creator: yxc
 * date: 17/9/14
 */

public abstract class BaseMvpActivity<T extends BasePresenter>
        extends BaseActivity implements BaseView {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initInject();

}
