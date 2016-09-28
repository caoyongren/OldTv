package com.zcy.ghost.vivideo.ui.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.ui.activitys.SettingActivity;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment4 extends BaseFragment {

    public static final String SET_THEME = "SET_THEME";

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.rl_them)
    RelativeLayout rlThem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("我的");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        titleName.setText("我的");
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
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

}
