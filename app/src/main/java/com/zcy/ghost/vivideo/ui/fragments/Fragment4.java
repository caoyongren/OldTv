package com.zcy.ghost.vivideo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;
import com.zcy.ghost.vivideo.utils.EventUtil;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment4 extends BaseFragment {

    public static final String SET_THEME = "SET_THEME";
    Unbinder unbinder;

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.rl_them)
    RelativeLayout rlThem;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("我的");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.rl_record, R.id.rl_down, R.id.rl_collection, R.id.rl_them, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_record:
                EventUtil.showToast(getContext(), "本地");
                break;
            case R.id.rl_down:
                EventUtil.showToast(getContext(), "暂定下载功能");
                break;
            case R.id.rl_collection:
                EventUtil.showToast(getContext(), "本地数据库：realm");
                break;
            case R.id.rl_them:
                EventBus.getDefault().post("", SET_THEME);
                break;
            case R.id.img_setting:
                EventUtil.showToast(getContext(), "待定");
                break;
        }
    }

}
