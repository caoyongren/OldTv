package com.zcy.ghost.vivideo.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.utils.ImageLoaderUtil;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.presenter.VideoInfoPresenter;
import com.zcy.ghost.vivideo.presenter.contract.VideoInfoContract;
import com.zcy.ghost.vivideo.view.fragments.CommentFragment;
import com.zcy.ghost.vivideo.view.fragments.VideoIntroFragment;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.widget.LVGhost;
import com.zcy.ghost.vivideo.widget.SwipeViewPager;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Create by MasterMan
 * Description:
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017-11-02
 */
public class VideoInfoActivity extends SwipeBackActivity<VideoInfoPresenter> implements VideoInfoContract.View {

    public static final String VIDEO_INFO = "videoInfo";

    VideoInfo videoInfo;
    @BindView(R.id.video_header_iv_collect)
    ImageView ivCollect;
    @BindView(R.id.video_info_player)
    JCVideoPlayerStandard videoplayer;
    @BindView(R.id.fg_title_name)
    ColorTextView mTitleName;
    @BindView(R.id.video_info_tab_view_pager)
    SmartTabLayout mVideoInfoTabViewpager;
    @BindView(R.id.video_info_viewpager)
    SwipeViewPager mViewpager;

    @BindView(R.id.video_info_circle_loading)
    LVGhost mLoading;
    @BindView(R.id.video_header_rl_collect)
    RelativeLayout rlCollect;

    VideoRes videoRes;
    private Animation animation;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_info;
    }

    @Override
    protected void initView() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.view_hand);
        rlCollect.setVisibility(View.VISIBLE);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(mContext)
                .add(R.string.video_intro, VideoIntroFragment.class)
                .add(R.string.video_comment, CommentFragment.class)
                .create());
        mViewpager.setAdapter(adapter);
        mVideoInfoTabViewpager.setViewPager(mViewpager);
        videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        videoplayer.backButton.setVisibility(View.GONE);
        videoplayer.titleTextView.setVisibility(View.GONE);
        videoplayer.tinyBackImageView.setVisibility(View.GONE);

        mPresenter.prepareInfo(videoInfo);
    }

    @Override
    protected void getIntentData() {
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.video_header_rl_back)
    public void back() {
        finish();
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
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public void showContent(VideoRes videoRes) {
        this.videoRes = videoRes;
        mTitleName.setText(videoRes.title);
        if (!TextUtils.isEmpty(videoRes.pic))
            ImageLoaderUtil.load(mContext, videoRes.pic, videoplayer.thumbImageView);
        if (!TextUtils.isEmpty(videoRes.getVideoUrl())) {
            videoplayer.setUp(videoRes.getVideoUrl()
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, videoRes.title);
            videoplayer.onClick(videoplayer.thumbImageView);
        }
    }

    @OnClick(R.id.video_header_rl_collect)
    public void onClick() {
        if (videoRes != null) {
            ivCollect.startAnimation(animation);
            mPresenter.collect();
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    /**
     * 所有的视频的打开都是通过该方法；
     * VideoInfoActivity.start(context, videoInfo);
     * */
    public static void start(Context context, VideoInfo videoInfo) {
        /**
         * 视频的启动；
         * Intent intent = new Intent(Intent.ACTION_VIEW);
         * String type = "video/mp4";
         * Uri name = Uri.parse("file://sdcard/test.mp4");
         * intent.setDataAndType(name, type);
         * intent.setClassName(com.cooliris.media, com.cooliris.media.MovieView);
         * startActivity(intent);
         * */
        Intent starter = new Intent(context, VideoInfoActivity.class);
        starter.putExtra(VIDEO_INFO, videoInfo);
        //starter.setClassName("com.cooliris.media",
        //                     "com.cooliris.media.MovieView");
        context.startActivity(starter);
    }
}