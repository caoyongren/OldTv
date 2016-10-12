package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.presenter.SearchVideoListPresenter;
import com.zcy.ghost.vivideo.ui.view.SearchVideoListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends SwipeBackActivity {
    @BindView(R.id.search_video_info_view)
    SearchVideoListView searchVideoListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        searchVideoListView.setTitleName("搜索");
        mPresenter = new SearchVideoListPresenter(searchVideoListView,"一念天堂");
    }
}