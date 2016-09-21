package com.zcy.ghost.vivideo.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;
import com.zcy.ghost.vivideo.presenter.contract.VideoInfoContract;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import org.simple.eventbus.EventBus;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: VideoInfoPresenter
 * Creator: yxc
 * date: 2016/9/21 15:35
 */
public class VideoInfoPresenter extends RxPresenter implements VideoInfoContract.Presenter {

    public final static String Refresh_Video_Info = "Refresh_Video_Info";

    @NonNull
    final VideoInfoContract.View mView;

    public VideoInfoPresenter(@NonNull VideoInfoContract.View addTaskView, VideoInfo videoInfo) {
        mView = StringUtils.checkNotNull(addTaskView);
        mView.setPresenter(this);
        mView.showContent(BeanUtil.VideoInfo2VideoRes(videoInfo, null));
        getDetailData(videoInfo.dataId);
    }

    @Override
    public void getDetailData(String dataId) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoInfo(dataId)
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(final VideoRes res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                mView.showContent(res);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(res, Refresh_Video_Info);
                                    }
                                }, 200);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView.isActive())
                            mView.hidLoading();
                        mView.showError("数据加载失败");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        if (mView.isActive())
                            mView.hidLoading();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
