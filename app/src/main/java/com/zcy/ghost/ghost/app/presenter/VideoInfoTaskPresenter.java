package com.zcy.ghost.ghost.app.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.taskcontract.VideoInfoContract;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.net.Network;
import com.zcy.ghost.ghost.utils.EventUtils;
import com.zcy.ghost.ghost.utils.StringUtils;

import org.simple.eventbus.EventBus;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoInfoTaskPresenter implements VideoInfoContract.Presenter {
    @NonNull
    final VideoInfoContract.View mAddTaskView;
    VideoInfo videoInfo;
    VideoRes videoRes;
    Subscription subscription;

    public VideoInfoTaskPresenter(@NonNull VideoInfoContract.View addTaskView, VideoInfo videoInfo) {
        mAddTaskView = StringUtils.checkNotNull(addTaskView);
        mAddTaskView.setPresenter(this);
        this.videoInfo = videoInfo;
        switchData();
        mAddTaskView.setViewData(videoRes);
        getVideoInformation();
    }

    @Override
    public void start() {

    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
            if (mAddTaskView.isActive())
                mAddTaskView.hidLoading();
        }

        @Override
        public void onError(Throwable e) {
            EventUtils.showToast(mAddTaskView.getContexts(), R.string.loading_failed);
        }

        @Override
        public void onNext(VideoResult result) {
            if (result != null) {
                if (mAddTaskView.isActive()) {
                    videoRes = result.ret;
                    videoRes.pic = videoInfo.pic;
                    mAddTaskView.setViewData(videoRes);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(videoRes, "refresh_video_info");
                        }
                    }, 200);
                }
            }
        }
    };

    private void switchData() {
        videoRes = new VideoRes();
        videoRes.title = StringUtils.isEmpty(videoInfo.title);
        videoRes.pic = StringUtils.isEmpty(videoInfo.pic);
        videoRes.score = StringUtils.isEmpty(videoInfo.score);
        videoRes.airTime = StringUtils.isEmpty(videoInfo.airTime);
//        videoRes.description = videoInfo.description;
    }

    @Override
    public void getVideoInformation() {
        subscription = Network.getVideoApi(mAddTaskView.getContexts())
                .getVideoInfo(videoInfo.dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
