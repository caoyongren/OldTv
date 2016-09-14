package com.zcy.ghost.ghost.app.activitys;

import android.os.Bundle;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.SwipeBackActivity;
import com.zcy.ghost.ghost.app.presenter.VideoListTaskPresenter;
import com.zcy.ghost.ghost.app.viewinterface.VideoListTaskView;

public class VideoListActivity extends SwipeBackActivity {
    VideoListTaskView mAddEditTaskView;
    VideoListTaskPresenter mPresenter;
    String mTitle = "";
    String mCatalogId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mAddEditTaskView = (VideoListTaskView) findViewById(R.id.list_task_view);
        getIntentData();
        mPresenter = new VideoListTaskPresenter(mAddEditTaskView, mCatalogId);
        mPresenter.onRefresh();
    }

    private void getIntentData() {
        mCatalogId = getIntent().getStringExtra("catalogId");
        mTitle = getIntent().getStringExtra("title");
        mAddEditTaskView.setTitleName(mTitle);
    }
}
