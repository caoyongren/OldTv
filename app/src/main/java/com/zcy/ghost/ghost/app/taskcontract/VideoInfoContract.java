package com.zcy.ghost.ghost.app.taskcontract;


import android.content.Context;

import com.zcy.ghost.ghost.app.BasePresenter;
import com.zcy.ghost.ghost.app.BaseView;
import com.zcy.ghost.ghost.bean.VideoRes;

public interface VideoInfoContract {

    interface View extends BaseView<Presenter> {

        void setViewData(VideoRes videoRes);

        void setTitleName(String title);

        Context getContexts();

        boolean isActive();

        void hidLoading();
    }

    interface Presenter extends BasePresenter {
        void getVideoInformation();
    }
}
