package com.zcy.ghost.ghost.app.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.taskcontract.VideoInfoContract;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.net.NetManager;
import com.zcy.ghost.ghost.utils.EventUtils;
import com.zcy.ghost.ghost.utils.StringUtils;

import org.simple.eventbus.EventBus;

import rx.Subscriber;
import rx.Subscription;

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

    Subscriber<VideoResult> subscriber = new Subscriber<VideoResult>() {
        @Override
        public void onCompleted() {
            if (mAddTaskView.isActive())
                mAddTaskView.hidLoading();
        }

        @Override
        public void onError(Throwable e) {
            if (mAddTaskView.isActive())
                mAddTaskView.hidLoading();
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
        subscription = NetManager.getInstance().getVideoInformation(subscriber, mAddTaskView.getContexts(), videoInfo.dataId);
    }
}
