package com.zcy.ghost.ghost.app.fragments;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseFragment;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
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
    TextView titleName;
    @BindView(R.id.tv_set_theme)
    TextView tvSetTheme;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("我的");
    }


    @OnClick(R.id.tv_set_theme)
    public void onClick() {
        EventBus.getDefault().post("", SET_THEME);
    }
}
