package com.master.old.tv.view.fragments.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.hintview.IconHintView;
import com.master.old.tv.R;
import com.master.old.tv.base.BaseMvpFragment;
import com.master.old.tv.model.bean.VideoInfo;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.presenter.contract.tab.TabChoiceContract;
import com.master.old.tv.presenter.tab.TabChoicePresenter;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.view.activitys.MainActivity;
import com.master.old.tv.view.activitys.SearchActivity;
import com.master.old.tv.view.activitys.VideoInfoActivity;
import com.master.old.tv.view.adapter.BannerAdapter;
import com.master.old.tv.view.adapter.TabChoiceAdapter;
import com.master.old.tv.widget.RollPagerView;
import com.master.old.tv.widget.theme.ColorRelativeLayout;
import com.master.old.tv.widget.theme.ColorTextView;

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
public class TabChoiceFragment extends BaseMvpFragment<TabChoicePresenter> implements
                                       TabChoiceContract.View, SwipeRefreshLayout.OnRefreshListener,
                                       View.OnClickListener {

    private static final String TAG = "TabChoiceFragment";

    private static final int BANNER_PLAY_DELAY = 3000;//3秒

    @BindView(R.id.fg_choice_recyclerView)
    EasyRecyclerView mChoiceRecyclerView;
    @Nullable
    @BindView(R.id.normal_title)
    ColorRelativeLayout titleLayout;
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
        titleLayout.setVisibility(View.GONE);
        titleName.setText(getResources().getString(R.string.good_choice));//str 需要从资源中获取；
        titleName.setBackgroundColor(R.color.title_color);

        //(int resource, @Nullable ViewGroup root)
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_choice_header, null);
        mHeaderBanner = ButterKnife.findById(headerView, R.id.fg_choice_banner);
        rlGoSearch = ButterKnife.findById(headerView, R.id.fg_choice_rlGoSearch);
        etSearchKey = ButterKnife.findById(headerView, R.id.fg_choice_etSearchKey);
        mHeaderBanner.setPlayDelay(BANNER_PLAY_DELAY);
        /**
         * EasyRecyclerView.java
         * 1.特点:
         *   1.规范了ViewHolder，把ViewHolder封装起来，并让我们继承这个抽象类RecyclerView.ViewHolder。
         *   2.把ItemView存放到RecyclerView.ViewHolder，通过复用RecyclerView.ViewHolder实现ItemView的复用。
         *   3.RecyclerView.Adapter
         *   4.compile 'com.camnter.easyrecyclerview:easyrecyclerview:1.1.0'
         * 2.配置
         *   1. LayoutMannager ＝ LinearLayoutManager
         *   2. ItemAnimator ＝ DefaultItemAnimator
         * 3. Decoration
         *   1. EasyDividerItemDecoration
         *   2. EasyBorderDividerItemDecoration
         *
         *   blog:
         *   http://blog.csdn.net/qq_16430735/article/details/49341563#gradle
         * */
        mTabChoiceAdapter = new TabChoiceAdapter(getContext());
        mChoiceRecyclerView.setAdapterWithProgress(mTabChoiceAdapter);
        mChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mChoiceRecyclerView.setErrorView(R.layout.view_error);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mChoiceRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (MainActivity.FLAG) {
            Log.i(MainActivity.DATA, "view: lazyFetchData");
        }
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
        //防止重复点击
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtil.isFastDoubleClick()) {
                    mChoiceRecyclerView.scrollToPosition(0);
                }
            }
        });
        mChoiceRecyclerView.setRefreshListener(this);
        mChoiceRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mChoiceRecyclerView, int dx, int dy) {
                if (getHeaderScroll() <= ScreenUtil.dip2px(mContext, 150)) {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            float percentage = (float) getHeaderScroll() / ScreenUtil.dip2px(mContext, 150);
                            titleLayout.setAlpha(percentage);
                            if (percentage > 0)
                                titleLayout.setVisibility(View.VISIBLE);
                            else
                                titleLayout.setVisibility(View.GONE);

                        }
                    }, 300);
                } else {
                    titleLayout.setAlpha(1.0f);
                    titleLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击进入视频播放
         *
         * 监听的接口在{@RecyclerArrayAdapter.jva}, 适配器继承该接口，　以前都是自己写回调；
         * */
        mTabChoiceAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                VideoInfoActivity.start(mContext, mTabChoiceAdapter.getItem(position));
            }
        });
        mChoiceRecyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChoiceRecyclerView.showProgress();
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
                if (videoRes.list.get(i).title.equals("精彩推荐")) {//进行类型匹配
                    videoInfos = videoRes.list.get(i).childList;
                    /**
                     * Adds the specified Collection at the end of the array.
                     * 适配器中添加所有数据．
                     * */
                    if (MainActivity.DEBUG) {
                        Log.i(TAG, "videoRes=" + videoRes.list.get(i).childList.get(i).title);
                    }
                    if (MainActivity.FLAG) {
                        Log.i(MainActivity.DATA, "view: showContent--> videoInfos" + videoInfos);
                    }
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
                        mHeaderBanner.setHintView(new IconHintView(getContext(),
                                R.mipmap.ic_page_indicator_focused,
                                R.mipmap.ic_page_indicator,
                                ScreenUtil.dip2px(getContext(),
                                        10)));
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
        mChoiceRecyclerView.showError();
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
        /**
         * 注解实例的成功的连接；
         * 通过{@FragmentComponent} method : void inject(TabChoiceFragment tabChoiceFragment);
         * 实现；
         * */
        getFragmentComponent().inject(this);
    }
}
