package com.zcy.ghost.vivideo.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.app.App;
import com.zcy.ghost.vivideo.app.Constants;
import com.zcy.ghost.vivideo.base.BaseActivity;
import com.zcy.ghost.vivideo.ui.adapter.ContentPagerAdapter;
import com.zcy.ghost.vivideo.ui.fragments.ClassificationFragment;
import com.zcy.ghost.vivideo.ui.fragments.DiscoverFragment;
import com.zcy.ghost.vivideo.ui.fragments.MineFragment;
import com.zcy.ghost.vivideo.ui.fragments.RecommendFragment;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.PreUtils;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.utils.ThemeUtils;
import com.zcy.ghost.vivideo.widget.ResideLayout;
import com.zcy.ghost.vivideo.widget.UnScrollViewPager;
import com.zcy.ghost.vivideo.widget.theme.Theme;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ColorChooserDialog.ColorCallback {

    public static final String Set_Theme_Color = "Set_Theme_Color";
    public final static String Banner_Stop = "Banner_Stop";
    final int WAIT_TIME = 200;
    private Long firstTime = 0L;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_mydown)
    TextView tvMydown;
    @BindView(R.id.tv_fuli)
    TextView tvFuli;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.theme)
    TextView theme;
    @BindView(R.id.tab_rg_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;
    @BindView(R.id.resideLayout)
    ResideLayout mResideLayout;
    ContentPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(false);
        mPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        setIconDrawable(tvCollect, MaterialDesignIconic.Icon.gmi_collection_add);
        setIconDrawable(tvMydown, MaterialDesignIconic.Icon.gmi_download);
        setIconDrawable(tvFuli, MaterialDesignIconic.Icon.gmi_mood);
        setIconDrawable(tvShare, MaterialDesignIconic.Icon.gmi_share);
        setIconDrawable(tvFeedback, MaterialDesignIconic.Icon.gmi_android);
        setIconDrawable(tvSetting, MaterialDesignIconic.Icon.gmi_settings);
        setIconDrawable(about, MaterialDesignIconic.Icon.gmi_account);
        setIconDrawable(theme, MaterialDesignIconic.Icon.gmi_palette);
    }

    @Override
    protected void initEvent() {
        tabRgMenu.setOnCheckedChangeListener(this);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            case R.id.tab_rb_1:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.tab_rb_2:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.tab_rb_3:
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.tab_rb_4:
                vpContent.setCurrentItem(3, false);
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

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#2196F3");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#F44336");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#795548");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#4CAF50");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#9c27b0");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#009688");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#E91E63");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#673AB7");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#FF9800");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#3F51B5");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#8BC34A");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "##FF5722");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#CDDC39");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#CDDC39");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#00BCD4");
            PreUtils.putString(this, Constants.TITLECOLOR, "#ffffff");
        } else if (selectedColor == getResources().getColor(android.R.color.black)) {
            setTheme(R.style.BlackTheme);
            PreUtils.setCurrentTheme(this, Theme.Black);
            PreUtils.putString(this, Constants.PRIMARYCOLOR, "#000000");
            PreUtils.putString(this, Constants.TITLECOLOR, "#0aa485");
        }
        EventBus.getDefault().post("", Set_Theme_Color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(ScreenUtil.dip2px(this, 10));
    }

    @OnClick({R.id.tv_collect, R.id.tv_mydown, R.id.tv_fuli, R.id.tv_share, R.id.tv_feedback, R.id.tv_setting, R.id.about, R.id.theme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                startActivity(new Intent(this, CollectionActivity.class));
                break;
            case R.id.tv_mydown:
                EventUtil.showToast(this, "我的下载");
                break;
            case R.id.tv_fuli:
                EventUtil.showToast(this, "福利");
                break;
            case R.id.tv_share:
                EventUtil.showToast(this, "分享");
                break;
            case R.id.tv_feedback:
                // 以对话框的形式弹出
                PgyerDialog.setDialogTitleBackgroundColor(PreUtils.getString(this, Constants.PRIMARYCOLOR, "#000000"));
                PgyerDialog.setDialogTitleTextColor(PreUtils.getString(this, Constants.TITLECOLOR, "#0aa485"));
                PgyFeedback.getInstance().showDialog(MainActivity.this);
                break;
            case R.id.tv_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.about:
                new MaterialDialog.Builder(this)
                        .title(R.string.about)
                        .titleColor(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                        .icon(new IconicsDrawable(this)
                                .color(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                                .icon(MaterialDesignIconic.Icon.gmi_account)
                                .sizeDp(20))
                        .content(R.string.about_me)
                        .contentColor(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.theme:
                setTheme("");
                break;
        }
    }
}