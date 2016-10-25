package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.presenter.SearchVideoListPresenter;
import com.zcy.ghost.vivideo.ui.view.SearchVideoListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends SwipeBackActivity {

    @BindView(R.id.search_video_info_view)
    SearchVideoListView searchVideoListView;
    List<VideoInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        getIntentData();
        mPresenter = new SearchVideoListPresenter(searchVideoListView,list);
    }
    private void getIntentData() {
        list = (List<VideoInfo>) getIntent().getSerializableExtra("recommend");
    }
}