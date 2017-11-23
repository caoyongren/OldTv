package com.cao.old.tv.presenter.contract;


import com.cao.old.tv.base.BasePresenter;
import com.cao.old.tv.base.BaseView;
import com.cao.old.tv.model.bean.VideoRes;

/**
 * Description: RecommendContract
 * Creator: yxc
 * date: 2016/9/21 15:53
 */
public interface RecommendContract {

    interface View extends BaseView {

        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();
    }
}
