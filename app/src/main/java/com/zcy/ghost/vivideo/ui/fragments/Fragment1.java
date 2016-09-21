package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.OnePresenter;
import com.zcy.ghost.vivideo.ui.view.OneView;

import butterknife.BindView;

/**
 * Description: 精选
 * Creator: yxc
 * date: $date $time
 */
public class Fragment1 extends BaseFragment {


    @BindView(R.id.one_view)
    OneView oneView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new OnePresenter(oneView);
        attachView(oneView);
    }

}
