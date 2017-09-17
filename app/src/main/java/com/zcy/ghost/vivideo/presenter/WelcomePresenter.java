package com.zcy.ghost.vivideo.presenter;

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
public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private static final int COUNT_DOWN_TIME = 2200;

    @Inject
    public WelcomePresenter() {
    }

    @Override
    public void getWelcomeData() {
//        Subscription rxSubscription = RetrofitHelper1.getVideoApi().getHomePage()
//                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
//                .compose(RxUtil.<VideoRes>handleResult())
//                .subscribe(new Action1<VideoRes>() {
//                    @Override
//                    public void call(final VideoRes res) {
//                        if (res != null) {
//                            if (mView.isActive()) {
//                                mView.showContent(res);
//                            }
//                        }
//                        startCountDown();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        startCountDown();
//                    }
//                });
//        addSubscribe(rxSubscription);
        mView.showContent(getImgData());
        startCountDown();
    }

    private void startCountDown() {
        Subscription rxSubscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
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
//        imgs.add("file:///android_asset/d.jpg");
        imgs.add("file:///android_asset/e.jpg");

        imgs.add("file:///android_asset/f.jpg");
        imgs.add("file:///android_asset/g.jpg");
        return imgs;
    }

}
