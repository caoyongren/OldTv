package com.master.old.tv.base;

import android.os.Bundle;

import com.master.old.tv.app.App;
import com.master.old.tv.dagger.component.ActivityComponent;
import com.master.old.tv.dagger.component.DaggerActivityComponent;
import com.master.old.tv.dagger.module.ActivityModule;

import javax.inject.Inject;

/*****************************
 * Create by MasterMan
 * Description:
 *   抽象类:
 *     mvpActivity的基础类
 *     initInject()方法－－> Dagger２的初始化；
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017年11月10日
 *****************************/

public abstract class BaseMvpActivity<T extends BasePresenter>
        extends BaseActivity implements BaseView {

    /**
     * <T extends BasePresenter></>
     *   interface BasePresenter
     *     attachView()/detachView()
     *
     * */
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
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

    /**
     * 在子类中实现Dagger的注入
     *  getActivityComponent().inject(this);
     * */
    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
