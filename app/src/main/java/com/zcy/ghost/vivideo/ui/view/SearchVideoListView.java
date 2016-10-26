package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.SearchKey;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.model.db.RealmHelper;
import com.zcy.ghost.vivideo.presenter.contract.SearchVideoListContract;
import com.zcy.ghost.vivideo.ui.activitys.SearchActivity;
import com.zcy.ghost.vivideo.ui.adapter.VideoListAdapter;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.widget.WordWrapView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zcy.ghost.vivideo.R.id.recyclerView;


/**
 * Description: 搜索影片列表
 * Creator: yxc
 * date: 2016/9/21 14:57
 */
public class SearchVideoListView extends RootView<SearchVideoListContract.Presenter> implements SearchVideoListContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    VideoInfo videoInfo;
    @BindView(recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;
    int pageSize = 30;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.tv_operate)
    TextView tvOperate;
    @BindView(R.id.img_search_clear)
    ImageView imgSearchClear;
    @BindView(R.id.wv_search_history)
    WordWrapView wvSearchHistory;
    @BindView(R.id.rl_his_rec)
    RelativeLayout rlHisRec;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_video1)
    ImageView imgVideo1;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    List<VideoInfo> recommend;

    public SearchVideoListView(Context context) {
        super(context);
    }

    public SearchVideoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_search_view, this);
    }

    @Override
    protected void initView() {
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
        setHistory();
    }

    @Override
    protected void initEvent() {
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

        edtSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    imgClear.setVisibility(View.VISIBLE);
                    tvOperate.setText("搜索");
                } else {
                    imgClear.setVisibility(View.INVISIBLE);
                    tvOperate.setText("取消");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setHistory() {
        final List<SearchKey> searchHistory = RealmHelper.getInstance().getSearchHistoryListAll();
        if (searchHistory != null && searchHistory.size() > 0) {
            wvSearchHistory.removeAllViewsInLayout();
            int size = searchHistory.size();
            for (int i = 0; i < size; i++) {
                final String query = searchHistory.get(i).getSearchKey();
                TextView textView = new TextView(getContext());
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setText(query);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtSearch.setText(query);
                        search(query);
                    }
                });
                wvSearchHistory.addView(textView);
            }
        }
    }

    @OnClick({R.id.tv_operate, R.id.img_clear, R.id.img_search_clear, R.id.ll_reco, R.id.ll_reco1})
    public void back(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                String searchStr = edtSearch.getText().toString();
                if (!TextUtils.isEmpty(searchStr)) {
                    SearchKey search = new SearchKey(searchStr, System.currentTimeMillis());
                    RealmHelper.getInstance().insertSearchHistory(search);
                    search(searchStr);
                } else {
                    if (mContext instanceof SearchActivity) {
                        ((SearchActivity) mContext).finish();
                    }
                }
                break;
            case R.id.img_clear:
                edtSearch.setText("");
                break;
            case R.id.img_search_clear:
                RealmHelper.getInstance().deleteSearchHistoryAll();
                wvSearchHistory.removeAllViews();
                break;
            case R.id.ll_reco:
                JumpUtil.go2VideoInfoActivity(getContext(), recommend.get(0));
                break;
            case R.id.ll_reco1:
                JumpUtil.go2VideoInfoActivity(getContext(), recommend.get(1));
                break;
        }

    }

    private void search(String searchStr) {
        mRecyclerView.getProgressView().setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPresenter.setSearchKey(searchStr);
        mPresenter.onRefresh();
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
        if (list != null && list.size() < pageSize) {
            clearFooter();
        }
        mAdapter.addAll(list);
        mRecyclerView.getProgressView().setVisibility(View.GONE);
        rlHisRec.setVisibility(View.GONE);
    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showRecommend(List<VideoInfo> list) {
        if (list != null && list.size() == 2) {
            recommend = list;
            VideoInfo videoInfo = list.get(0);
            ImageLoader.load(getContext(), videoInfo.pic, imgVideo);
            tvTitle.setText(videoInfo.title);
            videoInfo = list.get(1);
            ImageLoader.load(getContext(), videoInfo.pic, imgVideo1);
            tvTitle1.setText(videoInfo.title);
        } else
            llRecommend.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(SearchVideoListContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }
}
