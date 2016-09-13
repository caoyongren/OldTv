package com.zcy.ghost.ghost.app.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseActivity;
import com.zcy.ghost.ghost.app.fragments.VideoIntroFragment;
import com.zcy.ghost.ghost.app.fragments.VideoRelatedFragment;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.net.Network;
import com.zcy.ghost.ghost.utils.StringUtils;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description: 视频详情
 * Creator: yxc
 * date: 2016/9/8 15:45
 */
public class VideoInfoActivity extends BaseActivity {
    final String TAG = VideoInfoActivity.class.getSimpleName();

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.tv_airTime)
    TextView tvAirTime;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.pb)
    ProgressBar pb;

    Unbinder unbinder;
    VideoInfo videoInfo;
    VideoRes videoRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        unbinder = ButterKnife.bind(this);

        getIntentData();
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.video_intro, VideoIntroFragment.class)
                .add(R.string.video_ralated, VideoRelatedFragment.class)
                .create());
        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
    }

    private void getIntentData() {
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");
        switchData();
        setData(videoRes);
        getVideoDetail();
    }

    private void switchData() {
        videoRes = new VideoRes();
        videoRes.title = StringUtils.isEmpty(videoInfo.title);
        videoRes.pic = StringUtils.isEmpty(videoInfo.pic);
        videoRes.score = StringUtils.isEmpty(videoInfo.score);
        videoRes.airTime = StringUtils.isEmpty(videoInfo.airTime);
//        videoRes.description = videoInfo.description;
    }

    private void setData(VideoRes info) {
        tvScore.setText(info.score);
        tvType.setText(info.videoType);
        tvRegion.setText(info.region);
        tvAirTime.setText(info.airTime);
        titleName.setText(info.title);
        Glide.with(this)
                .load(info.pic)
                .into(imgVideo);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.rl_back)
    public void back() {
        finish();
    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
            pb.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(VideoInfoActivity.this, R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(VideoResult result) {
            if (result != null) {
                videoRes = result.ret;
                videoRes.pic = videoInfo.pic;
                setData(videoRes);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(videoRes, "refresh_video_info");
                    }
                }, 200);
            }
        }
    };

    private void getVideoDetail() {
        subscription = Network.getVideoApi(this)
                .getVideoInfo(videoInfo.dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    @OnClick(R.id.btn_play)
    public void play() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
