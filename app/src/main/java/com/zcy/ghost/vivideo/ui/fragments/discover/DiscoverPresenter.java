package com.zcy.ghost.vivideo.ui.fragments.discover;

import com.zcy.ghost.vivideo.model.DataManager;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.http.response.VideoHttpResponse;
import com.zcy.ghost.vivideo.newbase.RxPresenter;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.utils.SystemUtils;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: DiscoverPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class DiscoverPresenter extends RxPresenter<DiscoverContract.View> implements DiscoverContract.Presenter {
    final String catalogId = "402834815584e463015584e53843000b";

    int max = 90;
    int min = 1;

    private DataManager mDataManager;

    @Inject
    public DiscoverPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getData() {
        getNextVideos();
    }

    private void getNextVideos() {
        Subscription rxSubscription = mDataManager.fetchVideoList(catalogId, getNextPage() + "")
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
