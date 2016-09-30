package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.presenter.VideoListPresenter;
import com.zcy.ghost.vivideo.ui.view.VideoListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends SwipeBackActivity {
    String mTitle = "";
    String mCatalogId = "";
    String searchStr = "";
    @BindView(R.id.video_list_view)
    VideoListView videlListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        unbinder = ButterKnife.bind(this);
        getIntentData();
        mPresenter = new VideoListPresenter(videlListView, mCatalogId,searchStr);
    }

    private void getIntentData() {
        mCatalogId = getIntent().getStringExtra("catalogId");
        mTitle = getIntent().getStringExtra("title");
        searchStr = getIntent().getStringExtra("searchStr");
        videlListView.setTitleName(mTitle);
    }
}
