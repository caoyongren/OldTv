package com.zcy.ghost.ghost.app.viewinterface;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.app.fragments.VideoIntroFragment;
import com.zcy.ghost.ghost.app.fragments.VideoRelatedFragment;
import com.zcy.ghost.ghost.app.taskcontract.VideoInfoContract;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.utils.EventUtils;
import com.zcy.ghost.ghost.views.SwipeViewPager;
import com.zcy.ghost.ghost.views.circleprogress.CircleProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class VideoInfoTaskView extends LinearLayout implements VideoInfoContract.View {
    private VideoInfoContract.Presenter mPresenter;

    @BindView(R.id.title_name)
    TextView mTitleName;
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

    /**
     * 是否被销毁
     */
    private boolean mActive;
    private Context mContext;

    public VideoInfoTaskView(Context context) {
        super(context);
        init();
    }


    public VideoInfoTaskView(Context context, AttributeSet attrs) {
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
            EventUtils.showToast(mContext, "该视频暂时不能播放");
        } else {
            ((VideoInfoActivity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            JCVideoPlayerStandard.startFullscreen(mContext,
                    JCVideoPlayerStandard.class,
                    videoRes.getVideoUrl(), videoRes.title);
        }
    }

    @Override
    public Context getContexts() {
        return getContext();
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
    public void setViewData(VideoRes videoRes) {
        this.videoRes = videoRes;
        mTvScore.setText(videoRes.score);
        mTvType.setText(videoRes.videoType);
        mTvRegion.setText(videoRes.region);
        mTvAirTime.setText(videoRes.airTime);
        mTitleName.setText(videoRes.title);
        Glide.with(mContext)
                .load(videoRes.pic)
                .into(mImgVideo);
    }

    public void setTitleName(String title) {
        mTitleName.setText(title);
    }
}
