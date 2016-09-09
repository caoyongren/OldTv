package com.zcy.ghost.ghost.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.utils.StringUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: VideoIntroFragment
 * Creator: yxc
 * date: 2016/9/9 9:54
 */
public class VideoIntroFragment extends BaseFragment {
    final String TAG = VideoIntroFragment.class.getSimpleName();

    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_actors)
    TextView tvActors;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_intro, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = "refresh_video_info")
    public void setData(VideoRes videoInfo) {
        String dir = "导演：" + StringUtils.removeOtherCode(videoInfo.director);
        String act = "主演：" + StringUtils.removeOtherCode(videoInfo.actors);
        String des = "简介：" + StringUtils.removeOtherCode(videoInfo.description);
        tvDes.setText(videoInfo.description);
        tvDirector.setText(dir);
        tvActors.setText(act);
    }

}
