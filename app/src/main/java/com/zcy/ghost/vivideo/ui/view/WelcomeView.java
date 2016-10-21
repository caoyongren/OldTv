package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.google.common.base.Preconditions;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.presenter.contract.WelcomeContract;
import com.zcy.ghost.vivideo.ui.activitys.WelcomeActivity;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import java.util.List;

import butterknife.BindView;


/**
 * Description: WelcomeView
 * Creator: yxc
 * date: 2016/9/22 13:20
 */
public class WelcomeView extends RootView<WelcomeContract.Presenter> implements WelcomeContract.View {


    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;

    public WelcomeView(Context context) {
        super(context);
    }

    public WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_welcome_view, this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(List<String> list) {
        if (list != null) {
            int page = StringUtils.getRandomNumber(0, list.size() - 1);
            ImageLoader.load(mContext, list.get(page), ivWelcomeBg);
            ivWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
        }

    }

    @Override
    public void jumpToMain() {
        JumpUtil.go2MainActivity(mContext);
        ((WelcomeActivity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
