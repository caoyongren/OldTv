package com.zcy.ghost.vivideo.ui.fragments;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.presenter.VideoInfoPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.utils.StringUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;

/**
 * Description: 详情——简介
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

    @Override
    protected int getLayoutResource() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_video_intro;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = VideoInfoPresenter.Refresh_Video_Info)
    public void setData(VideoRes videoInfo) {
        String dir = "导演：" + StringUtils.removeOtherCode(videoInfo.director);
        String act = "主演：" + StringUtils.removeOtherCode(videoInfo.actors);
        String des = "简介：" + StringUtils.removeOtherCode(videoInfo.description);
        tvDes.setText(videoInfo.description);
        tvDirector.setText(dir);
        tvActors.setText(act);
    }

}
