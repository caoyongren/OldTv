package com.zcy.ghost.vivideo.presenter.contract;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;

import java.util.List;

/**
 * Description: WelcomeContract
 * Creator: yxc
 * date: 2016/9/22 13:16
 */
public interface WelcomeContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showContent(List<String> list);

        void jumpToMain();
    }

    interface Presenter extends BasePresenter {
        void getWelcomeData();
    }
}
