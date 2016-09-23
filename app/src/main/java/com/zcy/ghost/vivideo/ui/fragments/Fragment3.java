package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.ThreePresenter;
import com.zcy.ghost.vivideo.ui.view.ThreeView;

import butterknife.BindView;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment3 extends BaseFragment {

    @BindView(R.id.three_view)
    ThreeView threeView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new ThreePresenter(threeView);
    }
}
