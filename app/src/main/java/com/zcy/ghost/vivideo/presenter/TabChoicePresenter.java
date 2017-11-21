package com.zcy.ghost.vivideo.presenter;

import android.util.Log;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.http.response.VideoHttpResponse;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.presenter.contract.RecommendContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.view.activitys.MainActivity;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Description: TabChoicePresenter
 * Creator: yxc
 * date: 2016/9/21 16:26
 */
public class TabChoicePresenter extends RxPresenter<RecommendContract.View>
                                implements RecommendContract.Presenter {
    private static final String TAG = "MasterChoicePresenter";
    int page = 0;

    @Inject
    public TabChoicePresenter() {}

    @Override
    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
        //主要应用于线程处理；
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getHomePage()  //
                .compose(RxUtil.<VideoHttpResponse<VideoRes>> rxSchedulerHelper())//子线程
                .compose(RxUtil.<VideoRes> handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(final VideoRes res) {
                        if (res != null) {
                            //该方法实现在: {TabChoiceFragment.java} -> showContent(data);
                            mView.showContent(res);
                            if (MainActivity.DEBUG) {
                                Log.i(TAG, "subscribe call" + Thread.currentThread());
                                Log.i(TAG, "pageUrl:" + RetrofitHelper.getVideoApi().getHomePage());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(rxSubscription);
    }
}
