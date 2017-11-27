package com.master.old.tv.view.fragments.tab;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.master.old.tv.R;
import com.master.old.tv.base.BaseMvpFragment;
import com.master.old.tv.model.bean.VideoInfo;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.presenter.tab.TabTopicPresenter;
import com.master.old.tv.presenter.contract.tab.TabTopicContract;
import com.master.old.tv.view.activitys.VideoListActivity;
import com.master.old.tv.view.adapter.TabTopicAdapter;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.utils.StringUtils;
import com.master.old.tv.widget.theme.ColorTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description: TabTopicFragment
 * Creator: yxc
 * date: 2016/9/21 17:45
 */
public class TabTopicFragment extends BaseMvpFragment<TabTopicPresenter> implements TabTopicContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fg_title_name)
    ColorTextView titleName;
    @BindView(R.id.fg_choice_recyclerView)
    EasyRecyclerView recyclerView;
    TabTopicAdapter adapter;

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("专题");
        recyclerView.setAdapterWithProgress(adapter = new TabTopicAdapter(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setErrorView(R.layout.view_error);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                VideoListActivity.start(mContext, StringUtils.getCatalogId(adapter.getItem(position).moreURL), adapter.getItem(position).title);
            }
        });
        recyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });

        mPresenter.onRefresh();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showContent(VideoRes videoRes) {
        if (videoRes != null) {
            adapter.clear();
            List<VideoInfo> videoInfos = new ArrayList<>();
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (!TextUtils.isEmpty(videoRes.list.get(i).moreURL) && !TextUtils.isEmpty(videoRes.list.get(i).title)) {
                    VideoInfo videoInfo = videoRes.list.get(i).childList.get(0);
                    videoInfo.title = videoRes.list.get(i).title;
                    videoInfo.moreURL = videoRes.list.get(i).moreURL;
                    videoInfos.add(videoInfo);
                }
            }
            adapter.addAll(videoInfos);
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

    @Override
    protected int getLayout() {
        return R.layout.fragment_topic;
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }
}
