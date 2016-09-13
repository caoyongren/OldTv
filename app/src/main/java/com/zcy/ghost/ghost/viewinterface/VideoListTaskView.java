package com.zcy.ghost.ghost.viewinterface;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.VideoListAdapter;
import com.zcy.ghost.ghost.app.activitys.MVPVideoListActivity;
import com.zcy.ghost.ghost.taskcontract.VideoListContract;
import com.zcy.ghost.ghost.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

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
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(context, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    protected void initEvent() {
        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.onItemClickView(position);
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
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setPresenter(VideoListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onLoadMore() {
        mPresenter.pauseMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    public void setTitleName(String title) {
        mTitleName.setText(title);
    }
}
