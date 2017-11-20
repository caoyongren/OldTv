package com.zcy.ghost.vivideo.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.app.App;
import com.zcy.ghost.vivideo.app.Constants;
import com.zcy.ghost.vivideo.base.BaseActivity;
import com.zcy.ghost.vivideo.view.adapter.ContentPagerAdapter;
import com.zcy.ghost.vivideo.view.fragments.ClassificationFragment;
import com.zcy.ghost.vivideo.view.fragments.DiscoverFragment;
import com.zcy.ghost.vivideo.view.fragments.MineFragment;
import com.zcy.ghost.vivideo.view.fragments.RecommendFragment;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.PreUtils;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.utils.ThemeUtil;
import com.zcy.ghost.vivideo.utils.ThemeUtils;
import com.zcy.ghost.vivideo.widget.ResideLayout;
import com.zcy.ghost.vivideo.widget.UnScrollViewPager;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 主页
 * Creator: yxc
 * date: 2017/9/6 14:57
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ColorChooserDialog.ColorCallback {

    private static final String TAG = "MaiActivity";
    public static final String Set_Theme_Color = "Set_Theme_Color";
    public final static String Banner_Stop = "Banner_Stop";

    private Long firstTime = 0L;
    final int WAIT_TIME = 200;
    @BindView(R.id.drawer_tv_collect)
    TextView tvCollect;
    @BindView(R.id.drawer_tv_download)
    TextView tvMydown;
    @BindView(R.id.drawer_tv_welfare)
    TextView tvFuli;
    @BindView(R.id.draw_tv_share)
    TextView tvShare;
    @BindView(R.id.drawer_tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.drawer_tv_settings)
    TextView tvSetting;
    @BindView(R.id.main_tv_about)
    TextView about;
    @BindView(R.id.main_tv_theme)
    TextView theme;
    @BindView(R.id.main_tab_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.viewpager_content)
    UnScrollViewPager mViewPagerContent;
    @BindView(R.id.resideLayout)
    ResideLayout mResideLayout;
    ContentPagerAdapter mPagerAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        List<Fragment> fragments = initFragments();
        mViewPagerContent.setScrollable(false);
        mPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPagerContent.setAdapter(mPagerAdapter);
        mViewPagerContent.setOffscreenPageLimit(fragments.size());
        StringUtils.setIconDrawable(mContext, tvCollect, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 10);
        StringUtils.setIconDrawable(mContext, tvMydown, MaterialDesignIconic.Icon.gmi_download, 16, 10);
        StringUtils.setIconDrawable(mContext, tvFuli, MaterialDesignIconic.Icon.gmi_mood, 16, 10);
        StringUtils.setIconDrawable(mContext, tvShare, MaterialDesignIconic.Icon.gmi_share, 16, 10);
        StringUtils.setIconDrawable(mContext, tvFeedback, MaterialDesignIconic.Icon.gmi_android, 16, 10);
        StringUtils.setIconDrawable(mContext, tvSetting, MaterialDesignIconic.Icon.gmi_settings, 16, 10);
        StringUtils.setIconDrawable(mContext, about, MaterialDesignIconic.Icon.gmi_account, 16, 10);
        StringUtils.setIconDrawable(mContext, theme, MaterialDesignIconic.Icon.gmi_palette, 16, 10);
    }

    @Override
    protected void initEvent() {
        tabRgMenu.setOnCheckedChangeListener(this);
        mViewPagerContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) tabRgMenu.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mResideLayout.setPanelSlideListener(new ResideLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                postBannerState(true);
            }

            @Override
            public void onPanelOpened(View panel) {
                postBannerState(true);
            }

            @Override
            public void onPanelClosed(View panel) {
                postBannerState(false);
            }
        });
    }

    private void postBannerState(final boolean stop) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(stop, Banner_Stop);
            }
        }, WAIT_TIME);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.main_tab_choice:
                mViewPagerContent.setCurrentItem(0, false);
                break;
            case R.id.main_tab_topic:
                mViewPagerContent.setCurrentItem(1, false);
                break;
            case R.id.main_tab_find:
                mViewPagerContent.setCurrentItem(2, false);
                break;
            case R.id.main_tab_myself:
                mViewPagerContent.setCurrentItem(3, false);
                break;
        }
    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new RecommendFragment();
        Fragment fragment2 = new ClassificationFragment();
        Fragment fragment3 = new DiscoverFragment();
        Fragment mineFragment = new MineFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(mineFragment);
        return fragments;
    }

    @Subscriber(tag = MineFragment.SET_THEME)
    public void setTheme(String content) {
        new ColorChooserDialog.Builder(this, R.string.theme)
                .customColors(R.array.colors, null)
                .doneButton(R.string.done)
                .cancelButton(R.string.cancel)
                .allowUserColorInput(false)
                .allowUserColorInputAlpha(false)
                .show();
    }

    @OnClick({R.id.drawer_tv_collect, R.id.drawer_tv_download, R.id.drawer_tv_welfare, R.id.draw_tv_share, R.id.drawer_tv_feedback, R.id.drawer_tv_settings, R.id.main_tv_about, R.id.main_tv_theme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_tv_collect:
                mContext.startActivity(new Intent(mContext, CollectionActivity.class));
                break;
            case R.id.drawer_tv_download:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.drawer_tv_welfare:
                mContext.startActivity(new Intent(mContext, WelfareActivity.class));
                break;
            case R.id.draw_tv_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.setting_recommend_content));
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.drawer_tv_feedback:
                // 以对话框的形式弹出
                PgyerDialog.setDialogTitleBackgroundColor(PreUtils.getString(mContext, Constants.PRIMARYCOLOR, "#000000"));
                PgyerDialog.setDialogTitleTextColor(PreUtils.getString(mContext, Constants.TITLECOLOR, "#0aa485"));
                PgyFeedback.getInstance().showDialog(mContext).d().setChecked(false);
                break;
            case R.id.drawer_tv_settings:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.main_tv_about:
                new MaterialDialog.Builder(mContext)
                        .title(R.string.about)
                        .titleColor(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                        .icon(new IconicsDrawable(mContext)
                                .color(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                                .icon(MaterialDesignIconic.Icon.gmi_account)
                                .sizeDp(20))
                        .content(R.string.about_me)
                        .contentColor(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.main_tv_theme:
                setTheme("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onColorSelection(ColorChooserDialog dialog, int selectedColor) {
        ThemeUtil.onColorSelection(this, dialog, selectedColor);
        EventBus.getDefault().post("", Set_Theme_Color);
    }

    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1500) {
                EventUtil.showToast(this, "再按一次退出");
                firstTime = secondTime;
            } else {
                App.getInstance().exitApp();
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}