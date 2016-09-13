package com.zcy.ghost.ghost.app.viewinterface;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.VideoListAdapter;
import com.zcy.ghost.ghost.app.activitys.MVPVideoListActivity;
import com.zcy.ghost.ghost.app.taskcontract.VideoListContract;
import com.zcy.ghost.ghost.utils.EventUtils;
import com.zcy.ghost.ghost.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class VideoListTaskView extends LinearLayout implements VideoListContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    private VideoListContract.Presenter mPresenter;

    Unbinder unbinder;

    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.title_name)
    TextView mTitleName;

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;

    /**
     * 是否被销毁
     */
    private boolean mActive;
    private Context mContext;

    public VideoListTaskView(Context context) {
        super(context);
        init();
    }


    public VideoListTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        inflate(mContext, R.layout.activity_video_list_view, this);
        unbinder = ButterKnife.bind(this);
        initView();
        initEvent();
        mActive = true;
    }

    private void initView() {
        Context context = getContext();
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(context));
        mAdapter.setMore(R.layout.view_more, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(context, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    protected void initEvent() {
        mTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtils.isFastDoubleClick()) {
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });
        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.onItemClickView(position);
            }
        });
        mAdapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.resumeMore();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
        unbinder.unbind();
    }

    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof MVPVideoListActivity) {
            ((MVPVideoListActivity) mContext).finish();
        }
    }

    @Override
    public VideoListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void showTitle(String title) {
        mTitleName.setText(title);
    }

    @Override
    public Context getContexts() {
        return getContext();
    }

    @Override
    public RecyclerArrayAdapter.OnLoadMoreListener getLoadMoreListener() {
        return this;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void clearFooter() {
        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
    }

    @Override
    public void setPresenter(VideoListContract.Presenter presenter) {
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

    public void setTitleName(String title) {
        mTitleName.setText(title);
    }
}
