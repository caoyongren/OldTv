package com.zcy.ghost.ghost.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.ghost.MyApplication;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.app.activitys.VideoListActivity;
import com.zcy.ghost.ghost.app.taskcontract.VideoListContract;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.bean.VideoType;
import com.zcy.ghost.ghost.net.NetManager;
import com.zcy.ghost.ghost.utils.StringUtils;

import java.util.List;

import rx.Observer;
import rx.Subscription;

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
        subscription = NetManager.getInstance().getVideoList(observer, context, catalogID, page + "");
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
        if (mAddTaskView.getContexts() instanceof VideoListActivity) {
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
