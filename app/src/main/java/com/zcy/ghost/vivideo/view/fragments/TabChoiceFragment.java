package com.zcy.ghost.vivideo.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseMvpFragment;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.presenter.RecommendPresenter;
import com.zcy.ghost.vivideo.presenter.contract.RecommendContract;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.view.activitys.MainActivity;
import com.zcy.ghost.vivideo.view.activitys.SearchActivity;
import com.zcy.ghost.vivideo.view.activitys.VideoInfoActivity;
import com.zcy.ghost.vivideo.view.adapter.BannerAdapter;
import com.zcy.ghost.vivideo.view.adapter.TabChoiceAdapter;
import com.zcy.ghost.vivideo.widget.RollPagerView;
import com.zcy.ghost.vivideo.widget.theme.ColorRelativeLayout;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 精选
 * 应用mvp
 *
 */
public class TabChoiceFragment extends BaseMvpFragment<RecommendPresenter> implements
                                       RecommendContract.View, SwipeRefreshLayout.OnRefreshListener,
                                       View.OnClickListener {

    @BindView(R.id.fg_choice_recyclerView)
    EasyRecyclerView recyclerView;
    @Nullable
    @BindView(R.id.fg_choice_title)
    ColorRelativeLayout title;
    @BindView(R.id.fg_title_name)
    ColorTextView titleName;
    RollPagerView mHeaderBanner;
    View headerView;
    TabChoiceAdapter mTabChoiceAdapter;
    TextView etSearchKey;
    RelativeLayout rlGoSearch;
    List<VideoInfo> recommend;

    @Override
    protected int getLayout() {
        return R.layout.fragment_choice;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        title.setVisibility(View.GONE);
        titleName.setText(getResources().getString(R.string.good_choice));//str 需要从资源中获取；
        titleName.setBackgroundColor(R.color.title_color);

        headerView = LayoutInflater.from(mContext).inflate(R.layout.fragment_choice_header, null);
        mHeaderBanner = ButterKnife.findById(headerView, R.id.fg_choice_banner);
        rlGoSearch = ButterKnife.findById(headerView, R.id.fg_choice_rlGoSearch);
        etSearchKey = ButterKnife.findById(headerView, R.id.fg_choice_etSearchKey);
        mHeaderBanner.setPlayDelay(2000);

        recyclerView.setAdapterWithProgress(mTabChoiceAdapter = new TabChoiceAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setErrorView(R.layout.view_error);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mPresenter.onRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        stopBanner(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopBanner(true);
    }


    @Override
    protected void initEvent() {
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtil.isFastDoubleClick()) {
                    recyclerView.scrollToPosition(0);
                }
            }
        });
        recyclerView.setRefreshListener(this);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (getHeaderScroll() <= ScreenUtil.dip2px(mContext, 150)) {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            float percentage = (float) getHeaderScroll() / ScreenUtil.dip2px(mContext, 150);
                            title.setAlpha(percentage);
                            if (percentage > 0)
                                title.setVisibility(View.VISIBLE);
                            else
                                title.setVisibility(View.GONE);

                        }
                    }, 300);
                } else {
                    title.setAlpha(1.0f);
                    title.setVisibility(View.VISIBLE);
                }
            }
        });
        mTabChoiceAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                VideoInfoActivity.start(mContext, mTabChoiceAdapter.getItem(position));
            }
        });
        recyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });
        rlGoSearch.setOnClickListener(this);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public void showContent(final VideoRes videoRes) {
        if (videoRes != null) {
            mTabChoiceAdapter.clear();
            List<VideoInfo> videoInfos;
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (videoRes.list.get(i).title.equals("精彩推荐")) {
                    videoInfos = videoRes.list.get(i).childList;
                    mTabChoiceAdapter.addAll(videoInfos);
                    break;
                }
            }
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (videoRes.list.get(i).title.equals("免费推荐")) {
                    recommend = videoRes.list.get(i).childList;
                    break;
                }
            }
            if (mTabChoiceAdapter.getHeaderCount() == 0) {
                mTabChoiceAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {
                        mHeaderBanner.setHintView(new IconHintView(getContext(), R.mipmap.ic_page_indicator_focused, R.mipmap.ic_page_indicator, ScreenUtil.dip2px(getContext(), 10)));
                        mHeaderBanner.setHintPadding(0, 0, 0, ScreenUtil.dip2px(getContext(), 8));
                        mHeaderBanner.setAdapter(new BannerAdapter(getContext(), videoRes.list.get(0).childList));
                        return headerView;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });
            }
        }
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        recyclerView.showError();
    }

    @Subscriber(tag = MainActivity.Banner_Stop)
    public void stopBanner(boolean stop) {
        if (stop) {
            mHeaderBanner.pause();
        } else {
            mHeaderBanner.resume();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    private int getHeaderScroll() {
        if (headerView == null) {
            return 0;
        }
        int distance = headerView.getTop();
        distance = Math.abs(distance);
        return distance;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_choice_rlGoSearch:
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("recommend", (Serializable) recommend);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
