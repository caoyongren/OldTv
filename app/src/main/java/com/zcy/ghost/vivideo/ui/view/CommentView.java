package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.presenter.contract.CommentContract;
import com.zcy.ghost.vivideo.ui.adapter.CommentAdapter;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;


/**
 * Description: 评论列表
 * Creator: yxc
 * date: 2016/9/21 14:57
 */
public class CommentView extends RootView<CommentContract.Presenter> implements CommentContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    TextView tv_empty;

    CommentAdapter adapter;

    public CommentView(Context context) {
        super(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_comment_view, this);
    }

    @Override
    protected void initView() {
        recyclerView.setAdapterWithProgress(adapter = new CommentAdapter(mContext));
        recyclerView.setErrorView(R.layout.view_error);
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        tv_empty = (TextView) recyclerView.getEmptyView();
        tv_empty.setText("暂无评论");
    }

    @Override
    protected void initEvent() {
        recyclerView.setRefreshListener(this);
        adapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        recyclerView.getErrorView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        recyclerView.showError();
    }


    @Override
    public boolean isActive() {
        return mActive;
    }

    public void clearFooter() {
        adapter.setMore(new View(mContext), this);
        adapter.setError(new View(mContext));
        adapter.setNoMore(new View(mContext));
    }

    @Override
    public void showContent(List<VideoType> list) {
        adapter.clear();
        if (list != null && list.size() < 30) {
            clearFooter();
        }
        adapter.addAll(list);
    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        adapter.addAll(list);
    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mPresenter = com.google.common.base.Preconditions.checkNotNull(presenter);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }
}
