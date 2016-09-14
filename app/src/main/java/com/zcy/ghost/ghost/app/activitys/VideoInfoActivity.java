package com.zcy.ghost.ghost.app.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.presenter.VideoInfoTaskPresenter;
import com.zcy.ghost.ghost.app.viewinterface.VideoInfoTaskView;
import com.zcy.ghost.ghost.bean.VideoInfo;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoInfoActivity extends AppCompatActivity {
    VideoInfoTaskView mAddEditTaskView;
    VideoInfoTaskPresenter mPresenter;
    VideoInfo videoInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        ButterKnife.bind(this);
        mAddEditTaskView = (VideoInfoTaskView) findViewById(R.id.video_info_view);
        getIntentData();
        mPresenter = new VideoInfoTaskPresenter(mAddEditTaskView, videoInfo);
    }

    private void getIntentData() {
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");
    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}