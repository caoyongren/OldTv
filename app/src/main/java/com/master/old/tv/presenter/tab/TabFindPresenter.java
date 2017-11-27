package com.master.old.tv.presenter.tab;

import com.master.old.tv.base.RxPresenter;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.model.http.response.VideoHttpResponse;
import com.master.old.tv.model.net.RetrofitHelper;
import com.master.old.tv.presenter.contract.tab.TabFinderContract;
import com.master.old.tv.utils.RxUtil;
import com.master.old.tv.utils.StringUtils;
import com.master.old.tv.utils.system.SystemUtils;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: TabFindPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class TabFindPresenter extends RxPresenter<TabFinderContract.View> implements TabFinderContract.Presenter {
    final String catalogId = "402834815584e463015584e539330016";

    int max = 108;
    int min = 1;

    @Inject
    public TabFindPresenter() {
    }

    @Override
    public void getData() {
        getNextVideos();
    }

    private void getNextVideos() {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoList(catalogId, getNextPage() + "")
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                               @Override
                               public void call(final VideoRes res) {
                                   if (res != null) {
                                       mView.showContent(res);
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {

                                   mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                               }
                           }, new Action0() {
                               @Override
                               public void call() {
                                   mView.hidLoading();
                               }
                           }
                );

        addSubscribe(rxSubscription);
    }


    private int getNextPage() {
        int page = mView.getLastPage();
        if (SystemUtils.isNetworkConnected()) {
            page = StringUtils.getRandomNumber(min, max);
            mView.setLastPage(page);
        }
        return page;
    }
}
