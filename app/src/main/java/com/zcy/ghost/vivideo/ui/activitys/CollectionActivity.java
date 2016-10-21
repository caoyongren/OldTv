package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.presenter.CollectionPresenter;
import com.zcy.ghost.vivideo.ui.view.CollectionView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends SwipeBackActivity {

    @BindView(R.id.collect_view)
    CollectionView collectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        unbinder = ButterKnife.bind(this);
        mPresenter = new CollectionPresenter(collectView, 0);
    }
}
