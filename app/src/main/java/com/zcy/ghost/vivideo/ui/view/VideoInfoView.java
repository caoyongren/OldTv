package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
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
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Description: VideoInfoView
 * Creator: yxc
 * date: 2016/9/21 15:54
 */
public class VideoInfoView extends LinearLayout implements VideoInfoContract.View {
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

    Unbinder unbinder;
    VideoRes videoRes;
    VideoInfo videoInfo;

    /**
     * 是否被销毁
     */
    private boolean mActive;
    private Context mContext;

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
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                ((VideoInfoActivity) mContext).getSupportFragmentManager(), FragmentPagerItems.with(mContext)
                .add(R.string.video_intro, VideoIntroFragment.class)
                .add(R.string.video_ralated, VideoRelatedFragment.class)
                .create());
        mViewpager.setAdapter(adapter);
        mViewpagertab.setViewPager(mViewpager);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
        if (unbinder != null)
            unbinder.unbind();
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
    public void setPresenter(VideoInfoContract.Presenter presenter) {
        mPresenter = com.google.common.base.Preconditions.checkNotNull(presenter);
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

}
