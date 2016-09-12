package com.zcy.ghost.ghost.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.mvptest.AddEditTaskActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment4 extends BaseFragment {
    final String TAG = Fragment4.class.getSimpleName();

    @BindView(R.id.title_name)
    TextView titleName;

    Unbinder unbinder;
    @BindView(R.id.tv_mvp)
    TextView tvMvp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_four, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        return rootView;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        titleName.setText("我的");
        tvMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
