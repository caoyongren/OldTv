package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.model.bean.MySearchSuggestion;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.model.db.RealmHelper;
import com.zcy.ghost.vivideo.presenter.contract.SearchVideoListContract;
import com.zcy.ghost.vivideo.ui.activitys.SearchActivity;
import com.zcy.ghost.vivideo.ui.adapter.VideoListAdapter;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.LogUtils;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Description: 搜索影片列表
 * Creator: yxc
 * date: 2016/9/21 14:57
 */
public class SearchVideoListView extends RootView implements SearchVideoListContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    private SearchVideoListContract.Presenter mPresenter;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.title_name)
    ColorTextView mTitleName;

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;

    VideoInfo videoInfo;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    public SearchVideoListView(Context context) {
        super(context);
        init();
    }

    public SearchVideoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        inflate(mContext, R.layout.activity_search_view, this);
        unbinder = ButterKnife.bind(this);
        mActive = true;
        initView();
        initEvent();
    }

    private void initView() {
        Context context = getContext();
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(context));
        mRecyclerView.setErrorView(R.layout.view_error);
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
        mTitleName.setOnClickListener(new OnClickListener() {
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
                JumpUtil.go2VideoInfoActivity(getContext(), videoInfo);
            }
        });
        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.resumeMore();
            }
        });
        mRecyclerView.getErrorView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                LogUtils.d("TAG", "onSuggestionClicked()");
                if (searchSuggestion.getBody() != null && !searchSuggestion.getBody().equals("")) {
                    mPresenter.setSearchKey(searchSuggestion.getBody());
                    mPresenter.onRefresh();
                } else {
                    EventUtil.showToast(mContext, "请输入搜索关键字...");
                }
            }

            @Override
            public void onSearchAction(String query) {
                LogUtils.d("TAG", "onSearchAction()" + query);
                if (query != null && !query.equals("")) {
                    MySearchSuggestion search = new MySearchSuggestion(query, System.currentTimeMillis());
                    RealmHelper.getInstance().insertSearchHistory(search);
                    mPresenter.setSearchKey(query);
                    mPresenter.onRefresh();
                } else {
                    EventUtil.showToast(mContext, "请输入搜索关键字...");
                }
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                mSearchView.showProgress();
                List<MySearchSuggestion> newSuggestions = RealmHelper.getInstance().getSearchHistoryList(newQuery);
                if (newSuggestions != null && newSuggestions.size() > 0) {
                    mSearchView.swapSuggestions(newSuggestions);
                }
                mSearchView.hideProgress();
            }
        });
    }

    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof SearchActivity) {
            ((SearchActivity) mContext).finish();
        }
    }


    public void clearFooter() {
        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
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

    @Override
    public void showTitle(String title) {
        mTitleName.setText(title);
    }

    @Override
    public boolean isActive() {
        return mActive;
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

    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        if (list != null && list.size() < 30) {
            clearFooter();
        }
        mAdapter.addAll(list);
    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void setPresenter(SearchVideoListContract.Presenter presenter) {
        mPresenter = com.google.common.base.Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }
}
