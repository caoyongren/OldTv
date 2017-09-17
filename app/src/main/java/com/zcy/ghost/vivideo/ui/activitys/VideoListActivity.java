package com.zcy.ghost.vivideo.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.presenter.VideoListPresenter;
import com.zcy.ghost.vivideo.presenter.contract.VideoListContract;
import com.zcy.ghost.vivideo.ui.adapter.VideoListAdapter;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 影片列表
 * Creator: yxc
 * date: 2017/9/6 14:57
 */
public class VideoListActivity extends SwipeBackActivity<VideoListPresenter> implements VideoListContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    String mTitle = "";
    String mCatalogId = "";
    @BindView(R.id.title_name)
    ColorTextView mTitleName;

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;

    VideoInfo videoInfo;
    int pageSize = 30;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void initView() {
        mTitleName.setText(mTitle);
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(mContext));
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        onRefresh();
    }

    @Override
    protected void initEvent() {
        mTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtil.isFastDoubleClick()) {
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });
        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
                VideoInfoActivity.start(mContext, videoInfo);
            }
        });
        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.resumeMore();
            }
        });
        mRecyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });
    }

    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof VideoListActivity) {
            finish();
        }
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mRecyclerView.showError();
    }

    @Override
    public void loadMoreFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mAdapter.pauseMore();
    }

    public void clearFooter() {
        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
    }

    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        if (list != null && list.size() < pageSize) {
            clearFooter();
        }
        mAdapter.addAll(list);
    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);
    }


    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(mCatalogId);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getIntentData() {
        mCatalogId = getIntent().getStringExtra("catalogId");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    public static void start(Context context, String catalogId, String title) {
        Intent starter = new Intent(context, VideoListActivity.class);
        starter.putExtra("catalogId", catalogId);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }
}