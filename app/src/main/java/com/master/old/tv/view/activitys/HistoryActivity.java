package com.master.old.tv.view.activitys;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.master.old.tv.R;
import com.master.old.tv.base.SwipeBackActivity;
import com.master.old.tv.model.bean.VideoInfo;
import com.master.old.tv.model.bean.VideoType;
import com.master.old.tv.presenter.CollectionPresenter;
import com.master.old.tv.presenter.tab.TabChoicePresenter;
import com.master.old.tv.presenter.contract.CollectionContract;
import com.master.old.tv.view.activitys.menu.CollectionActivity;
import com.master.old.tv.view.adapter.VideoListAdapter;
import com.master.old.tv.utils.BeanUtil;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.widget.theme.ColorTextView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 播放历史
 * Creator: yxc
 * date: 2017/9/6 14:57
 */
public class HistoryActivity extends SwipeBackActivity<CollectionPresenter> implements CollectionContract.View {

    @BindView(R.id.video_header_collect_clear)
    RelativeLayout rlCollectClear;
    @BindView(R.id.video_header_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.fg_title_name)
    ColorTextView titleName;
    @BindView(R.id.fg_choice_recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;
    VideoInfo videoInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setTitle();
        rlCollectClear.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(mContext));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);

        mPresenter.getData(1);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
                VideoInfoActivity.start(mContext, videoInfo);
            }
        });
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }


    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }

    @OnClick({R.id.video_header_rl_back, R.id.video_header_collect_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_header_rl_back:
                if (mContext instanceof CollectionActivity) {
                    finish();
                } else if (mContext instanceof HistoryActivity) {
                    finish();
                }
                break;
            case R.id.video_header_collect_clear:
                mAdapter.clear();
                mPresenter.delAllDatas();
                break;
        }
    }

    private void setTitle() {
        if (mContext instanceof CollectionActivity) {
            titleName.setText("收藏");
        } else if (mContext instanceof HistoryActivity) {
            titleName.setText("历史");
        }
    }

    @Subscriber(tag = TabChoicePresenter.VideoInfoPresenter.Refresh_Collection_List)
    public void setData(String tag) {
        mPresenter.getCollectData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
