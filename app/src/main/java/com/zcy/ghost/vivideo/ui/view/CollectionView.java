package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.presenter.contract.CollectionContract;
import com.zcy.ghost.vivideo.ui.activitys.CollectionActivity;
import com.zcy.ghost.vivideo.ui.activitys.HistoryActivity;
import com.zcy.ghost.vivideo.ui.adapter.VideoListAdapter;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Description: 收藏
 * Creator: yxc
 * date: 2016/9/29 12:16
 */
public class CollectionView extends RootView<CollectionContract.Presenter> implements CollectionContract.View {

    @BindView(R.id.rl_collect_clear)
    RelativeLayout rlCollectClear;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;
    VideoInfo videoInfo;

    public CollectionView(Context context) {
        super(context);
    }

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_collection_view, this);
    }

    @Override
    protected void initView() {
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
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
                JumpUtil.go2VideoInfoActivity(getContext(), videoInfo);
            }
        });
    }

    @Override
    public void setPresenter(CollectionContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }

    @OnClick({R.id.rl_back, R.id.rl_collect_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                if (mContext instanceof CollectionActivity) {
                    ((CollectionActivity) mContext).finish();
                } else if (mContext instanceof HistoryActivity) {
                    ((HistoryActivity) mContext).finish();
                }
                break;
            case R.id.rl_collect_clear:
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
}
