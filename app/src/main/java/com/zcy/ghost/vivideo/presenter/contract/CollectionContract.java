package com.zcy.ghost.vivideo.presenter.contract;


import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;
import com.zcy.ghost.vivideo.model.bean.VideoType;

import java.util.List;

/**
 * Description: CollectionContract
 * Creator: yxc
 * date: 2016/9/29 12:06
 */
public interface CollectionContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showContent(List<VideoType> list);

    }

    interface Presenter extends BasePresenter {
        void getCollectData();

        void delAllDatas();

        void getRecordData();

        int getType();
    }
}
