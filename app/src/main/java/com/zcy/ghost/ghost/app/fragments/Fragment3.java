package com.zcy.ghost.ghost.app.fragments;

import android.view.LayoutInflater;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.app.theme.ColorTextView;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment3 extends BaseFragment {
    final String TAG = Fragment3.class.getSimpleName();

    @BindView(R.id.title_name)
    ColorTextView titleName;

    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("发现");
    }

}
