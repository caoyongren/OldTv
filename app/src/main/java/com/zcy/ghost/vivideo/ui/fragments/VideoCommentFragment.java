package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.CommentPresenter;
import com.zcy.ghost.vivideo.ui.view.CommentView;

import butterknife.BindView;

/**
 * Description: 详情--评论
 * Creator: yxc
 * date: 2016/9/9 9:54
 */
public class VideoCommentFragment extends BaseFragment {

    @BindView(R.id.comment_view)
    CommentView commentView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new CommentPresenter(commentView);
    }

    @Override
    protected void lazyFetchData() {
        ((CommentPresenter) mPresenter).onRefresh();
    }

}
