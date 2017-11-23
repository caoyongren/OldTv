package com.master.old.tv.presenter.contract;


import com.master.old.tv.base.BasePresenter;
import com.master.old.tv.base.BaseView;
import com.master.old.tv.model.bean.VideoType;

import java.util.List;

/**
 * Description: CollectionContract
 * Creator: yxc
 * date: 2016/9/29 12:06
 */
public interface CollectionContract {

    interface View extends BaseView {


        void showContent(List<VideoType> list);

    }

    interface Presenter extends BasePresenter<View> {
        void getData(int type);

        void getCollectData();

        void delAllDatas();

        void getRecordData();

        int getType();
    }
}
