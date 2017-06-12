package com.zcy.ghost.vivideo.ui.fragments.discover;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.newbase.BaseView;

/**
 * Description: RecommendContract
 * Creator: yxc
 * date: 2016/9/21 15:53
 */
public interface DiscoverContract {

    interface View extends BaseView {


        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);

        void hidLoading();

        int getLastPage();

        void setLastPage(int page);
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
    }
}
