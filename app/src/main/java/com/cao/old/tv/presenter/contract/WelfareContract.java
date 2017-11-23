package com.cao.old.tv.presenter.contract;

import com.cao.old.tv.base.BasePresenter;
import com.cao.old.tv.base.BaseView;
import com.cao.old.tv.model.bean.GankItemBean;

import java.util.List;

/**
 * Description: WelfareContract
 * Creator: yxc
 * date: 2016/10/24 12:34
 */
public interface WelfareContract {
    interface View extends BaseView {


        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();

        void loadMore();
    }
}
