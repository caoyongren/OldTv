package com.zcy.ghost.vivideo.presenter;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;
import com.zcy.ghost.vivideo.presenter.contract.DiscoverContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.utils.SystemUtils;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: DiscoverPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class DiscoverPresenter extends RxPresenter implements DiscoverContract.Presenter {
    DiscoverContract.View mView;
    final String catalogId = "402834815584e463015584e53843000b";

    int max = 90;
    int min = 1;


    public DiscoverPresenter(@NonNull DiscoverContract.View threeView) {
        mView = Preconditions.checkNotNull(threeView);
        mView.setPresenter(this);
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
                                       if (mView.isActive()) {
                                           mView.showContent(res);
                                       }
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
                                   if (mView.isActive())
                                       mView.hidLoading();
                               }
                           }
                );

        addSubscrebe(rxSubscription);
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
