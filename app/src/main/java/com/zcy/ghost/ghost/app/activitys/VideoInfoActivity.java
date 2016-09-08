package com.zcy.ghost.ghost.app.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseActivity;
import com.zcy.ghost.ghost.bean.VideoInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description: 视频详情
 * Creator: yxc
 * date: 2016/9/8 15:45
 */
public class VideoInfoActivity extends BaseActivity {
    final String TAG = VideoInfoActivity.class.getSimpleName();

    @BindView(R.id.title_name)
    TextView titleName;

    Unbinder unbinder;
    VideoInfo videoInfo;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_airTime)
    TextView tvAirTime;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        unbinder = ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void initView() {
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");
        setData(videoInfo);
    }

    private void setData(VideoInfo info) {
        tvScore.setText(info.score);
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
    public void onClick() {
        finish();
    }
}
