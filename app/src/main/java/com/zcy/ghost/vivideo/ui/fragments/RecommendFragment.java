package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.RecommendPresenter;
import com.zcy.ghost.vivideo.ui.view.RecommendView;

import butterknife.BindView;

/**
 * Description: 精选
 * Creator: yxc
 * date: $date $time
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.one_view)
    RecommendView oneView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new RecommendPresenter(oneView);
    }

    @Override
    protected void lazyFetchData() {
        ((RecommendPresenter) mPresenter).onRefresh();
    }
}
