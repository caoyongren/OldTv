package com.zcy.ghost.vivideo.presenter.contract;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;

import java.util.List;

/**
 * Description: VideoListContract
 * Creator: zjg
 * date: 2016/10/11 14:59
 */
public interface SearchVideoListContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        void showContent(List<VideoType> list);

        void showMoreContent(List<VideoType> list);

        void showRecommend(List<VideoInfo> list);
    }

    interface Presenter extends BasePresenter {

        void onRefresh();

        void loadMore();

        void setSearchKey(String strSearchKey);

    }
}
