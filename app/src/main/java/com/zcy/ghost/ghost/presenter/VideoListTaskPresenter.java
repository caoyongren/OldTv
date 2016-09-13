package com.zcy.ghost.ghost.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.ghost.MyApplication;
import com.zcy.ghost.ghost.app.activitys.MVPVideoListActivity;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.bean.VideoType;
import com.zcy.ghost.ghost.net.Network;
import com.zcy.ghost.ghost.taskcontract.VideoListContract;
import com.zcy.ghost.ghost.utils.StringUtils;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoListTaskPresenter implements VideoListContract.Presenter {
    @NonNull
    final VideoListContract.View mAddTaskView;
    int page = 1;
    String catalogId = "";
    Handler handler = new Handler();
    Subscription subscription;
    List<VideoType> videos;

    public VideoListTaskPresenter(@NonNull VideoListContract.View addTaskView, String catalogId) {
        mAddTaskView = StringUtils.checkNotNull(addTaskView);
        mAddTaskView.setPresenter(this);
        this.catalogId = catalogId;
    }

    @Override
    public void start() {

    }


    @Override
    public void onRefresh() {
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVideoList(MyApplication.mContext, catalogId, page);
            }
        }, 1000);
    }

    private void getVideoList(Context context, String catalogID, int page) {
        subscription = Network.getVideoApi(context)
                .getVideoList(catalogID, page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
            if (mAddTaskView.isActive()) {
            }
        }

        @Override
        public void onError(Throwable e) {
            if (page > 1) {
                mAddTaskView.getAdapter().pauseMore();
                page--;
            }
        }

        @Override
        public void onNext(VideoResult result) {
            if (result != null) {
                if (mAddTaskView.isActive()) {
                    videos = result.ret.list;
                    if (page == 1) {
                        mAddTaskView.getAdapter().clear();
                        if (videos != null && videos.size() < 30) {
                            mAddTaskView.clearFooter();
                        }
                    }
                    mAddTaskView.getAdapter().addAll(videos);
                }
            }
        }
    };

    @Override
    public void loadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                page++;
                getVideoList(mAddTaskView.getContexts(), catalogId, page);
            }
        }, 1000);
    }

    @Override
    public void onItemClickView(int position) {
        switchData(mAddTaskView.getAdapter().getItem(position));
        if (mAddTaskView.getContexts() instanceof MVPVideoListActivity) {
            Intent intent = new Intent(mAddTaskView.getContexts(), VideoInfoActivity.class);
            intent.putExtra("videoInfo", videoInfo);
            mAddTaskView.getContexts().startActivity(intent);
        }
    }

    VideoInfo videoInfo;

    private void switchData(VideoType videoType) {
        if (videoInfo == null)
            videoInfo = new VideoInfo();
        videoInfo.title = videoType.title;
        videoInfo.dataId = videoType.dataId;
        videoInfo.pic = videoType.pic;
        videoInfo.airTime = videoType.airTime;
        videoInfo.score = videoType.score;
    }

}
