package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.DiscoverPresenter;
import com.zcy.ghost.vivideo.ui.view.DiscoverView;

import butterknife.BindView;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.three_view)
    DiscoverView threeView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new DiscoverPresenter(threeView);
    }

    @Override
    protected void lazyFetchData() {
        ((DiscoverPresenter) mPresenter).getData();
    }
}
