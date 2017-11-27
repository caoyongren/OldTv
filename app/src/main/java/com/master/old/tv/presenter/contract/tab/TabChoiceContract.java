package com.master.old.tv.presenter.contract.tab;


import com.master.old.tv.base.BasePresenter;
import com.master.old.tv.base.BaseView;
import com.master.old.tv.model.bean.VideoRes;

/**
 * Description: TabChoiceContract
 * Creator: yxc
 * date: 2016/9/21 15:53
 */
public interface TabChoiceContract {

    interface View extends BaseView {

        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();
    }
}
