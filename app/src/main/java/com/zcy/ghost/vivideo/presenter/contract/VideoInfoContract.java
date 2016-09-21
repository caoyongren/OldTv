package com.zcy.ghost.vivideo.presenter.contract;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;
import com.zcy.ghost.vivideo.model.bean.VideoRes;

public interface VideoInfoContract {

    interface View extends BaseView<Presenter> {

        void showContent(VideoRes videoRes);

        boolean isActive();

        void hidLoading();
    }

    interface Presenter extends BasePresenter {
        void getDetailData(String dataId);
    }
}
