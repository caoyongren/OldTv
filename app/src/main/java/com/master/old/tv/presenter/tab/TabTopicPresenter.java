package com.master.old.tv.presenter.tab;

import com.master.old.tv.base.RxPresenter;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.model.exception.ApiException;
import com.master.old.tv.model.net.HttpMethods;
import com.master.old.tv.model.net.MyObserver;
import com.master.old.tv.presenter.contract.tab.TabTopicContract;

import javax.inject.Inject;

/**
 * Description: TabTopicPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class TabTopicPresenter extends RxPresenter<TabTopicContract.View> implements TabTopicContract.Presenter {
    int page = 0;

    @Inject
    public TabTopicPresenter() {
    }

    @Override
    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
        HttpMethods.getInstance().queryClassification()
                .subscribe(new MyObserver<VideoRes>() {
                    @Override
                    protected void onError(ApiException ex) {
                        mView.refreshFaild(ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onNext(VideoRes res) {
                        if (res != null) {
                            mView.showContent(res);
                        }
                    }
                });
    }
}
