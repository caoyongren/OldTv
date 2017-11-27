package com.master.old.tv.view.fragments.tab;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeFrameLayout;
import com.master.old.tv.R;
import com.master.old.tv.app.Constants;
import com.master.old.tv.base.BaseMvpFragment;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.model.bean.VideoType;
import com.master.old.tv.presenter.tab.TabFindPresenter;
import com.master.old.tv.presenter.contract.tab.TabFinderContract;
import com.master.old.tv.view.adapter.SwipeDeckAdapter;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.PreUtils;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.widget.OldTvView;
import com.master.old.tv.widget.SwipeDeck;
import com.master.old.tv.widget.theme.ColorTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 发现页
 * Creator: yxc
 * date: $date $time
 */
public class TabFindFragment extends BaseMvpFragment<TabFindPresenter> implements TabFinderContract.View {

    @BindView(R.id.fg_title_name)
    ColorTextView titleName;
    @BindView(R.id.swipe_deck)
    SwipeDeck swipeDeck;
    @BindView(R.id.swipeLayout)
    SwipeFrameLayout swipeLayout;
    @BindView(R.id.loading)
    OldTvView loading;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    private SwipeDeckAdapter adapter;
    private List<VideoType> videos = new ArrayList<>();

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("发现");
        ViewGroup.LayoutParams lp = swipeDeck.getLayoutParams();
        lp.height = ScreenUtil.getScreenHeight(getContext()) / 3 * 2;
        swipeDeck.setLayoutParams(lp);
        swipeDeck.setHardwareAccelerationEnabled(true);
    }

    @Override
    protected void initEvent() {
        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

            }

            @Override
            public void cardSwipedRight(int position) {

            }

            @Override
            public void cardsDepleted() {
                swipeDeck.setVisibility(View.GONE);
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
        mPresenter.getData();
    }

    @Override
    public void showContent(VideoRes videoRes) {
        if (videoRes != null) {
            videos.clear();
            videos.addAll(videoRes.list);
            swipeDeck.removeAllViews();
            swipeDeck.removeAllViews();
            adapter = new SwipeDeckAdapter(videos, getContext());
            swipeDeck.setAdapter(adapter);
            tvNomore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshFaild(String msg) {
        hidLoading();
        if (!TextUtils.isEmpty(msg))
            EventUtil.showToast(mContext, msg);
    }

    @Override
    public void hidLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public int getLastPage() {
        return PreUtils.getInt(getContext(), Constants.DISCOVERlASTpAGE, 1);
    }

    @Override
    public void setLastPage(int page) {
        PreUtils.putInt(getContext(), Constants.DISCOVERlASTpAGE, page);
    }

    private void nextVideos() {
        swipeDeck.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        tvNomore.setVisibility(View.GONE);
        mPresenter.getData();
    }


    @OnClick({R.id.btn_next, R.id.tv_nomore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
            case R.id.tv_nomore:
                nextVideos();
                break;
        }
    }

    @Override
    public void showError(String msg) {

    }
}
