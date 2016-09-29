package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.presenter.contract.OneContract;
import com.zcy.ghost.vivideo.ui.adapter.BannerAdapter;
import com.zcy.ghost.vivideo.ui.adapter.VideoAdapter;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.widget.theme.ColorRelativeLayout;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Description: OneView
 * Creator: yxc
 * date: 2016/9/21 17:56
 */
public class OneView extends RootView implements OneContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    private OneContract.Presenter mPresenter;

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Nullable
    @BindView(R.id.title)
    ColorRelativeLayout title;
    @BindView(R.id.title_name)
    ColorTextView titleName;
    RollPagerView banner;
    View headerView;
    VideoAdapter adapter;
    TextView tvGO;
    EditText etSearchKey;
    public OneView(Context context) {
        super(context);
        init();
    }


    public OneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        inflate(mContext, R.layout.fragment_one_view, this);
        unbinder = ButterKnife.bind(this);
        initView();
        initEvent();
        mActive = true;
    }

    private void initView() {
        title.setVisibility(View.GONE);
        titleName.setText("精选");
        headerView = LayoutInflater.from(mContext).inflate(R.layout.choice_header, null);
        banner = ButterKnife.findById(headerView, R.id.banner);
        tvGO = ButterKnife.findById(headerView, R.id.tvGO);
        etSearchKey = ButterKnife.findById(headerView, R.id.etSearchKey);
        banner.setPlayDelay(2000);
        recyclerView.setAdapterWithProgress(adapter = new VideoAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setErrorView(R.layout.view_error);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
    }

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
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JumpUtil.go2VideoInfoActivity(mContext, adapter.getItem(position));
            }
        });
        recyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });
        tvGO.setOnClickListener(this);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }


    @Override
    public void setPresenter(OneContract.Presenter presenter) {
        mPresenter = com.google.common.base.Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public void showContent(final VideoRes videoRes) {
        if (videoRes != null) {
            adapter.clear();
            if (adapter.getHeaderCount() == 0) {
                adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {
                        banner.setHintView(new IconHintView(getContext(), R.mipmap.ic_page_indicator_focused, R.mipmap.ic_page_indicator, ScreenUtil.dip2px(getContext(), 10)));
                        banner.setHintPadding(0, 0, 0, ScreenUtil.dip2px(getContext(), 8));
                        banner.setAdapter(new BannerAdapter(getContext(), videoRes.list.get(0).childList));
                        return headerView;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });
            }
            List<VideoInfo> videoInfos;
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (videoRes.list.get(i).title.equals("精彩推荐")) {
                    videoInfos = videoRes.list.get(i).childList;
                    videoInfos.get(0).setFirst(true);
                    videoInfos.get(0).setType(videoRes.list.get(i).title);
                    adapter.addAll(videoInfos);
                    break;
                }
            }
        }
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        recyclerView.showError();
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
        switch (view.getId()){
            case R.id.tvGO:
                String searchStr=etSearchKey.getText().toString();
                if(searchStr!=null && !searchStr.equals("")){
                    JumpUtil.go2VideoListSearchActivity(mContext, searchStr,"搜索");
                }
                break;
        }
    }
}
