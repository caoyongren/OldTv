package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseActivity;
import com.zcy.ghost.vivideo.presenter.WelcomePresenter;
import com.zcy.ghost.vivideo.ui.view.WelcomeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_view)
    WelcomeView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        unbinder = ButterKnife.bind(this);
        mPresenter = new WelcomePresenter(welcomeView);
    }
}