package com.master.old.tv.presenter.contract.tab;


import com.master.old.tv.base.BasePresenter;
import com.master.old.tv.base.BaseView;
import com.master.old.tv.model.bean.VideoRes;

/**
 * Description: TabTopicContract
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public interface TabTopicContract {

    interface View extends BaseView {

        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();
    }
}
