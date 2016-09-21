package com.zcy.ghost.vivideo.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;
import com.zcy.ghost.vivideo.presenter.contract.ThreeContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import java.util.Random;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: ThreePresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class ThreePresenter extends RxPresenter implements ThreeContract.Presenter {
    ThreeContract.View mView;
    final String catalogId = "402834815584e463015584e53843000b";

    int max = 90;
    int min = 1;
    Handler handler = new Handler();


    public ThreePresenter(@NonNull ThreeContract.View threeView) {
        mView = StringUtils.checkNotNull(threeView);
        mView.setPresenter(this);
        getData();
    }

    @Override
    public void getData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                getNextVideos();
            }
        }, 1000);
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

                                   mView.refreshFaild(throwable.getMessage());
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
        return new Random().nextInt(90) % (max - min + 1) + min;
    }
}
