package com.zcy.ghost.ghost.app.taskcontract;


import android.content.Context;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.ghost.app.BasePresenter;
import com.zcy.ghost.ghost.app.BaseView;
import com.zcy.ghost.ghost.adapter.VideoListAdapter;

public interface VideoListContract {

    interface View extends BaseView<Presenter> {

        VideoListAdapter getAdapter();

        void showTitle(String title);

        Context getContexts();

        RecyclerArrayAdapter.OnLoadMoreListener getLoadMoreListener();

        boolean isActive();

        void clearFooter();
    }

    interface Presenter extends BasePresenter {

        void onRefresh();

        void loadMore();

        void onItemClickView(int position);
    }
}
