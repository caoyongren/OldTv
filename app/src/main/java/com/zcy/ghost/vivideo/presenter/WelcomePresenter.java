package com.zcy.ghost.vivideo.presenter;

import android.util.Log;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.presenter.contract.WelcomeContract;
import com.zcy.ghost.vivideo.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Description: WelcomePresenter
 * Creator: yxc
 * date: 2016/9/22 13:17
 */
public class WelcomePresenter extends RxPresenter<WelcomeContract.View>
                                      implements WelcomeContract.Presenter {
    //RxPresenter extends BasePresenter;

    private static final int COUNT_DOWN_TIME = 2200;
    private static final String TAG = "WPresenter";

    @Inject
    public WelcomePresenter() {
    }

    @Override
    public void getWelcomeData() {
        mView.showContent(getImgData());
        startCountDown();
    }

    private void startCountDown() {
        //利用rx的观察者模式
        Subscription rxSubscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("logo" + TAG, "thread" + Thread.currentThread());
                        mView.jumpToMain();
                    }
                });
        addSubscribe(rxSubscription);
    }

    private List<String> getImgData() {
        List<String> imgs = new ArrayList<>();
        imgs.add("file:///android_asset/a.jpg");
        imgs.add("file:///android_asset/b.jpg");
        imgs.add("file:///android_asset/c.jpg");
        imgs.add("file:///android_asset/e.jpg");
        imgs.add("file:///android_asset/f.jpg");
        imgs.add("file:///android_asset/g.jpg");
        return imgs;
    }

}
