package com.master.old.tv.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.master.old.tv.app.App;
import com.master.old.tv.dagger.component.DaggerFragmentComponent;
import com.master.old.tv.dagger.component.FragmentComponent;
import com.master.old.tv.dagger.module.FragmentModule;

import javax.inject.Inject;

/**
 * Create by MasterMan
 * Description:
 *   BaseMvpFragment.java基础类
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date:
 */

public abstract class BaseMvpFragment<T extends BasePresenter>
                      extends BaseFragment implements BaseView {

    /**
     * mPresenter是需要注入的；
     *
     * 在getFragmentComponent()的调用的地方进行实现注入连接；
     * TabChoiceFragment实现注入连接；　extends {@BaseMvpFragment}
     * 则完成： Inject 注入　和　注入连接;
     * */
    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        mPresenter = null;
        super.onDestroy();
    }

    protected FragmentComponent getFragmentComponent() {
        //注入注解
        //DaggerFragmentComponent是在编译的时候生成的。
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void initInject();
}
