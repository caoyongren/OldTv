package com.zcy.ghost.vivideo.ui.fragments.classification;


import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.newbase.BasePresenter;
import com.zcy.ghost.vivideo.newbase.BaseView;

/**
 * Description: ClassificationContract
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public interface ClassificationContract {

    interface View extends BaseView {

        boolean isActive();

        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();
    }
}
