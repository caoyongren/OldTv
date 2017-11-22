package com.zcy.ghost.vivideo.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.PreUtils;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.utils.system.ThemeUtil;
import com.zcy.ghost.vivideo.utils.system.ThemeUtils;
import com.zcy.ghost.vivideo.view.adapter.ContentViewPagerAdapter;
import com.zcy.ghost.vivideo.view.fragments.TabTopicFragment;
import com.zcy.ghost.vivideo.view.fragments.TabChoiceFragment;
import com.zcy.ghost.vivideo.view.fragments.TabFindFragment;
import com.zcy.ghost.vivideo.view.fragments.TabMySelfFragment;
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
 * date: 2017/9/6 14:57
 *
 * //viewPager 属于v4包
 * 1. 主要对四个fragments的控制；
 * 2. 对抽屉的控制
 */
public class MainActivity extends BaseActivity implements
                                  RadioGroup.OnCheckedChangeListener,
                                  ColorChooserDialog.ColorCallback {
    /**
     * 每个类中TAG用于本类的调试．打印log
     *
     * DEBUG -> 整个项目的log控制．
     *
     * //数据打印实例：　精选模块
     * FLAG --> 数据流程打印的log控制
     *
     * DATA --> 数据流程打印额检索
     * */
    private static final String TAG = "MaiActivity";
    public static final boolean DEBUG = true;
    public static final boolean FLAG = true;
    public static final String DATA = "DEBUG";

    public static final String Set_Theme_Color = "Set_Theme_Color";
    public final static String Banner_Stop = "Banner_Stop";

    private Long firstTime = 0L;
    final int WAIT_TIME = 200;
    @BindView(R.id.drawer_tv_collect)
    TextView tvCollect;
    @BindView(R.id.drawer_tv_download)
    TextView tvdownload;
    @BindView(R.id.drawer_tv_welfare)
    TextView tvWelfare;
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
    ContentViewPagerAdapter mPagerAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //EventBus是一款针对Android优化的发布/订阅事件总线。
        // 主要功能是替代Intent,Handler,BroadCast在Fragment，Activity，Service，线程之间传递消息.
        // 优点是开销小，代码更优雅。以及将发送者和接收者解耦。
        /**
         * 使用流程:
         * 1. 接收消息需注册: EventBus.getDefault().register(this);
         * 2. 发送消息: eventBus.post(new AnyEventType event);
         * 3. 接受消息实现: public void onEvent(AnyEvent event) {}
         * 4. 解除注册: EventBus.getDefault().unregister(this);
         * blog: http://blog.csdn.net/harvic880925/article/details/40660137
         * */
        EventBus.getDefault().register(this);
        List<Fragment> fragments = initFragments();
        //fragment: 精选/专题/发现/我的/　－－>可以向右滑动；
        mViewPagerContent.setScrollable(true);
        mPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPagerContent.setAdapter(mPagerAdapter);
        if (DEBUG) {
            Log.i(TAG, "fragment.size=" + fragments.size());
        }
        //setOffscreenPageLimit是小于<, 不是<=, so ＋　１．
        mViewPagerContent.setOffscreenPageLimit(fragments.size() + 1);

        initViewMenuIcon();
    }

    //menu : 收藏/下载/福利/分享/回馈/设置/关于/主题/－－＞前面的icon
    private void initViewMenuIcon() {
        StringUtils.setIconDrawable(mContext, tvCollect, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 10);
        StringUtils.setIconDrawable(mContext, tvdownload, MaterialDesignIconic.Icon.gmi_download, 16, 10);
        StringUtils.setIconDrawable(mContext, tvWelfare, MaterialDesignIconic.Icon.gmi_mood, 16, 10);
        StringUtils.setIconDrawable(mContext, tvShare, MaterialDesignIconic.Icon.gmi_share, 16, 10);
        StringUtils.setIconDrawable(mContext, tvFeedback, MaterialDesignIconic.Icon.gmi_android, 16, 10);
        StringUtils.setIconDrawable(mContext, tvSetting, MaterialDesignIconic.Icon.gmi_settings, 16, 10);
        StringUtils.setIconDrawable(mContext, about, MaterialDesignIconic.Icon.gmi_account, 16, 10);
        StringUtils.setIconDrawable(mContext, theme, MaterialDesignIconic.Icon.gmi_palette, 16, 10);
    }

    @Override
    protected void initEvent() {
        //点击一个radiobutton实现切换: the callback to call on checked state change
        tabRgMenu.setOnCheckedChangeListener(this);

        //viewPager 滑动监听.
        mViewPagerContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //
                if (DEBUG) {
                    Log.i(TAG, "viewPager: position" + position);
                }
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
        /**
         * 通过handler进行延时．
         * delayMillis WAIT_TIME: The delay (in milliseconds) until the Runnable will be executed.
         * */
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
            case R.id.main_tab_choice://精选
                mViewPagerContent.setCurrentItem(0, false);
                break;
            case R.id.main_tab_topic://专题
                mViewPagerContent.setCurrentItem(1, false);
                break;
            case R.id.main_tab_find://发现
                mViewPagerContent.setCurrentItem(2, false);
                break;
            case R.id.main_tab_myself://我的
                mViewPagerContent.setCurrentItem(3, false);
                break;
            default:
                Toast.makeText(mContext, "so sorry !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private List<Fragment> initFragments() {
        //精选-choice /主题-topic /　发现-find / 我的-myself
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragmentChoice = new TabChoiceFragment();
        Fragment fragmentTopic = new TabTopicFragment();
        Fragment fragmentFind = new TabFindFragment();
        Fragment fragmentMyself = new TabMySelfFragment();
        fragments.add(fragmentChoice);
        fragments.add(fragmentTopic);
        fragments.add(fragmentFind);
        fragments.add(fragmentMyself);
        return fragments;
    }

    @Subscriber(tag = TabMySelfFragment.SET_THEME)
    public void setTheme(String content) {
        new ColorChooserDialog.Builder(this, R.string.theme)
                .customColors(R.array.colors, null)
                .doneButton(R.string.done)
                .cancelButton(R.string.cancel)
                .allowUserColorInput(false)
                .allowUserColorInputAlpha(false)
                .show();
    }

    //抽屉中的menu: 收藏/ 下载/ 福利/ 分享/ 建议/ 设置/ 关于　/ 主题/ ? 应该使用碎片，而不是 fragment.TODO
    @OnClick({R.id.drawer_tv_collect, R.id.drawer_tv_download, R.id.drawer_tv_welfare,
              R.id.draw_tv_share, R.id.drawer_tv_feedback, R.id.drawer_tv_settings,
              R.id.main_tv_about, R.id.main_tv_theme})
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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

    //WelcomeActivity --> MainActivity
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}