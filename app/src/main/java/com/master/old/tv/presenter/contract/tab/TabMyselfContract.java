package com.master.old.tv.presenter.contract.tab;


import com.master.old.tv.base.BasePresenter;
import com.master.old.tv.base.BaseView;
import com.master.old.tv.model.bean.VideoType;

import java.util.List;

/**
 * Description: CollectionContract
 * Creator: cp
 * date: 2016/9/29 12:19
 */
public interface TabMyselfContract {

    interface View extends BaseView {

        void showContent(List<VideoType> list);

    }

    interface Presenter extends BasePresenter<View> {
        void getHistoryData();

        void delAllHistory();
    }
}
