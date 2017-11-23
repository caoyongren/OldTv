package com.cao.old.tv.presenter.contract;


import com.cao.old.tv.base.BasePresenter;
import com.cao.old.tv.base.BaseView;
import com.cao.old.tv.model.bean.VideoRes;

public interface VideoInfoContract {

    interface View extends BaseView {

        void showContent(VideoRes videoRes);

        void hidLoading();

        void collected();

        void disCollect();
    }

    interface Presenter extends BasePresenter<View> {
        void getDetailData(String dataId);

        void collect();

        void insertRecord();

    }
}
