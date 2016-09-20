package com.zcy.ghost.ghost.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.daprlabs.cardstack.SwipeFrameLayout;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.SwipeDeckAdapter;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.app.theme.ColorTextView;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.bean.VideoType;
import com.zcy.ghost.ghost.net.NetManager;
import com.zcy.ghost.ghost.utils.EventUtils;
import com.zcy.ghost.ghost.utils.ScreenUtil;
import com.zcy.ghost.ghost.views.circleprogress.CircleProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment3 extends BaseFragment {
    final String TAG = Fragment3.class.getSimpleName();
    final String catalogId = "402834815584e463015584e53843000b";

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.swipe_deck)
    SwipeDeck swipeDeck;
    @BindView(R.id.swipeLayout)
    SwipeFrameLayout swipeLayout;
    @BindView(R.id.loading)
    CircleProgress loading;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    private SwipeDeckAdapter adapter;
    private List<VideoType> videos = new ArrayList<>();
    int max = 90;
    int min = 1;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("发现");

        ViewGroup.LayoutParams lp = swipeDeck.getLayoutParams();
        lp.height = ScreenUtil.getScreenHeight(getContext()) / 3 * 2;
        swipeDeck.setLayoutParams(lp);

        swipeDeck = (SwipeDeck) rootView.findViewById(R.id.swipe_deck);
        swipeDeck.setHardwareAccelerationEnabled(true);

        swipeDeck.setLeftImage(R.id.left_image);
        swipeDeck.setRightImage(R.id.right_image);

        initEvent();
        nextVideos();
    }


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void nextVideos() {
        swipeDeck.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        tvNomore.setVisibility(View.GONE);
        subscription = NetManager.getInstance().getVideoList(observer, getContext(), catalogId, getNextPage() + "");
    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
            loading.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            loading.setVisibility(View.GONE);
            EventUtils.showToast(getContext(), R.string.loading_failed);
        }

        @Override
        public void onNext(VideoResult result) {
            if (result != null) {
                videos.clear();
                videos.addAll(result.ret.list);
                swipeDeck.removeAllViews();
                setAdapter();
            }
        }
    };

    private void setAdapter() {
        swipeDeck.removeAllViews();
        adapter = new SwipeDeckAdapter(videos, getContext());
        swipeDeck.setAdapter(adapter);
        tvNomore.setVisibility(View.VISIBLE);
    }

    private int getNextPage() {
        return new Random().nextInt(90) % (max - min + 1) + min;
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
}
