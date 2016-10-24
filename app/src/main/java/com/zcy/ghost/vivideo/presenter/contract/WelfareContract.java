package com.zcy.ghost.vivideo.presenter.contract;

import android.content.Context;

import com.zcy.ghost.vivideo.base.BasePresenter;
import com.zcy.ghost.vivideo.base.BaseView;
import com.zcy.ghost.vivideo.model.bean.GankItemBean;

import java.util.List;

/**
 * Description: WelfareContract
 * Creator: yxc
 * date: 2016/10/24 12:34
 */
public interface WelfareContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        Context getContext();

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();

        void loadMore();
    }
}
