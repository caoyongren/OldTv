package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.TwoPresenter;
import com.zcy.ghost.vivideo.ui.view.TwoView;

import butterknife.BindView;

/**
 * Description: Fragment2
 * Creator: yxc
 * date: 2016/9/21 17:45
 */
public class Fragment2 extends BaseFragment {

    @BindView(R.id.two_view)
    TwoView twoView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new TwoPresenter(twoView);
    }
}
