package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.presenter.contract.VideoInfoContract;
import com.zcy.ghost.vivideo.ui.activitys.VideoInfoActivity;
import com.zcy.ghost.vivideo.ui.fragments.VideoIntroFragment;
import com.zcy.ghost.vivideo.ui.fragments.VideoRelatedFragment;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.widget.SwipeViewPager;
import com.zcy.ghost.vivideo.widget.circleprogress.CircleProgress;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Description: VideoInfoView
 * Creator: yxc
 * date: 2016/9/21 15:54
 */
public class VideoInfoView extends RootView implements VideoInfoContract.View {
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    private VideoInfoContract.Presenter mPresenter;

    @BindView(R.id.title_name)
    ColorTextView mTitleName;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_region)
    TextView mTvRegion;
    @BindView(R.id.tv_airTime)
    TextView mTvAirTime;
    @BindView(R.id.img_video)
    ImageView mImgVideo;
    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewpagertab;
    @BindView(R.id.viewpager)
    SwipeViewPager mViewpager;
    @BindView(R.id.loading)
    CircleProgress mLoading;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;

    VideoRes videoRes;
    private Animation animation;

    public VideoInfoView(Context context) {
        super(context);
        init();
    }


    public VideoInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        inflate(mContext, R.layout.activity_video_info_view, this);
        unbinder = ButterKnife.bind(this);
        initView();
        mActive = true;
    }

    private void initView() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.view_hand);
        rlCollect.setVisibility(View.VISIBLE);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                ((VideoInfoActivity) mContext).getSupportFragmentManager(), FragmentPagerItems.with(mContext)
                .add(R.string.video_intro, VideoIntroFragment.class)
                .add(R.string.video_ralated, VideoRelatedFragment.class)
                .create());
        mViewpager.setAdapter(adapter);
        mViewpagertab.setViewPager(mViewpager);
    }

    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof VideoInfoActivity) {
            ((VideoInfoActivity) mContext).finish();
        }
    }

    @OnClick(R.id.btn_play)
    public void play() {
        if (TextUtils.isEmpty(videoRes.getVideoUrl())) {
            EventUtil.showToast(mContext, "该视频暂时不能播放");
        } else {
            mPresenter.insertRecord();
            ((VideoInfoActivity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            JCVideoPlayerStandard.startFullscreen(mContext,
                    JCVideoPlayerStandard.class,
                    videoRes.getVideoUrl(), videoRes.title);
        }
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void hidLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void collected() {
        ivCollect.setBackgroundResource(R.mipmap.collection_select);
    }

    @Override
    public void disCollect() {
        ivCollect.setBackgroundResource(R.mipmap.collection);
    }


    @Override
    public void setPresenter(VideoInfoContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public void showContent(VideoRes videoRes) {
        this.videoRes = videoRes;
        mTvScore.setText(videoRes.score);
        mTvType.setText(videoRes.videoType);
        mTvRegion.setText(videoRes.region);
        mTvAirTime.setText(videoRes.airTime);
        mTitleName.setText(videoRes.title);
        if (!TextUtils.isEmpty(videoRes.pic))
            ImageLoader.load(mContext, videoRes.pic, mImgVideo);
    }

    @OnClick(R.id.rl_collect)
    public void onClick() {
        if (videoRes != null) {
            ivCollect.startAnimation(animation);
            mPresenter.collect();
        }
    }
}
