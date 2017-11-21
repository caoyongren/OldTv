package com.zcy.ghost.vivideo.view.activitys;

import android.widget.ImageView;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseMvpActivity;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.presenter.WelcomePresenter;
import com.zcy.ghost.vivideo.presenter.contract.WelcomeContract;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
/**
 * Description: 首页
 * //设计Ｍvp中presenter解释:
 * 1. Presenter 主要负责的是控制　数据data 和　视图view
 * 2. view 中在initView中填充视图, 其实真正的填充是在presenter中
 * 3. 既然获取了数据自然要放到view中，则需要showContent, 则展示还是在view中．
 * 4. 总而言之: presenter 就是为了控制(调用各种方法), View负责展示；
 * 5. 以前的实现是通过：View中初始化一个presenter, 现在使用<WelcomPresenter> 例如: news</>
 */
public class WelcomeActivity extends BaseMvpActivity<WelcomePresenter>
        implements WelcomeContract.View {

    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        mPresenter.getWelcomeData();
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    /**
     * showContent(data)方法
     * 该方法在presenter中调用就是mvp中p在控制．
     * 控制逻辑：
     * 　　1. view 和presenter　都实现接口WelcomeContract.View
     *    2. void showContent(data)
     * */
    @Override
    public void showContent(List<String> list) {
        if (list != null) {
            int page = StringUtils.getRandomNumber(0, list.size() - 1);
            //图片加载工具,使用的是Glide;
            ImageLoader.load(mContext, list.get(page), ivWelcomeBg);
            ivWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
        }

    }

    @Override
    public void jumpToMain() {
        MainActivity.start(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}