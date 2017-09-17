package com.zcy.ghost.vivideo.presenter.contract;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;
import com.zcy.ghost.vivideo.model.bean.VideoType;

import java.util.List;

/**
 * Description: VideoListContract
 * Creator: yxc
 * date: 2016/9/21 14:59
 */
public interface VideoListContract {

    interface View extends BaseView {

        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        void showContent(List<VideoType> list);

        void showMoreContent(List<VideoType> list);
    }

    interface Presenter extends BasePresenter<View> {

        void onRefresh(String catalogId);

        void loadMore();

    }
}
