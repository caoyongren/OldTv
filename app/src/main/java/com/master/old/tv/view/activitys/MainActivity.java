package com.master.old.tv.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.master.old.tv.R;
import com.master.old.tv.app.App;
import com.master.old.tv.app.Constants;
import com.master.old.tv.base.BaseActivity;
import com.master.old.tv.base.BaseDialog;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.PreUtils;
import com.master.old.tv.utils.StringUtils;
import com.master.old.tv.utils.system.ThemeUtil;
import com.master.old.tv.utils.system.ThemeUtils;
import com.master.old.tv.view.activitys.drawer.CollectionListActivity;
import com.master.old.tv.view.activitys.drawer.SettingActivity;
import com.master.old.tv.view.activitys.drawer.WelfareActivity;
import com.master.old.tv.view.adapter.ContentViewPagerAdapter;
import com.master.old.tv.view.dialog.DialogUser;
import com.master.old.tv.view.fragments.tab.TabChoiceFragment;
import com.master.old.tv.view.fragments.tab.TabFindFragment;
import com.master.old.tv.view.fragments.tab.TabMySelfFragment;
import com.master.old.tv.view.fragments.tab.TabTopicFragment;
import com.master.old.tv.widget.layout.MainPagerLayout;
import com.master.old.tv.widget.customview.UnScrollPagerView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;

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
     * 　　　=====>>>>>>>   写在前面的话　　<<<<<<<=========
     * 每个类中TAG用于本类的调试．打印log
     *
     * MainActivity.DEBUG -> 整个项目的log控制．
     *
     * //数据打印实例：　精选模块
     * FLAG --> 数据流程打印的log控制
     * DATA --> 数据流程打印额检索
     * ========================
     * 类名命名规范:
     *   1. 特殊类名+位置;例如: tab draw
     *   2. +该类的意思; 例如: Choice
     *   3. +该类的类型; 例如: Activity / fragment
     *
     *   ---
     *   布局文件命名
     *   1. 根据类型: activity+/ fragment+ /item +
     *   2. + 从属(意义) /choice
     *   3. + 前面不够充分就补充
     *---------
     *
     * 分包:(根据mvp模式)
     *   1. base 基础类
     *   2. util　工具类
     *     - contants //常量
     *     - helper
     *       - db
     *       - net
     *   3. widget
     *     - 1. system
     *     - 2. 自定义View
     *   4. app
     *     - application
     *   5. dl(dagger)
     *
     *   6. view
     *     - adapter
     *     - activity
     *     - fragment
     *   7. module
     * ===================
     * 驼峰规则这个就不说了
     * 其他:(java)
     *   1. 全局变量+ m
     *   2. 字符常量+ 大写_NAME(只定义一份)
     *     - A中定义, B中用直接调用。
     *   3. 继承的方法保持顺序.
     *   4. 方法要重复利用
     *     - 在方法内完成需求。
     *　　5. 方法注意参数后期扩展.
     *   6. 切记不使用static.
     *   7. 注意: OOM问题
     *
     *   (xml)
     *   1. include 布局
     *   2. style 样式
     *   3. string/dimen/
     * */
    private static final String TAG = "MaiActivity";
    public static final boolean DEBUG = true;
    public static final boolean FLAG = true;
    public static final String DATA = "DEBUG";
    public static final String Set_Theme_Color = "Set_Theme_Color";
    public final static String Banner_Stop = "Banner_Stop";

    //private BaseDialog mCurrentDialog;
    private BaseDialog mDialogUser;
    private BaseDialog mDialogLogin;

    private Long firstTime = 0L;
    final int WAIT_TIME = 200;

    @BindView(R.id.main_user_icon)
    ImageView mImageViewUser;
    @BindView(R.id.drawer_tv_collect)
    TextView tvCollect;
    @BindView(R.id.drawer_tv_download)
    TextView tvdownload;
    @BindView(R.id.drawer_tv_welfare)
    TextView tvWelfare;
    @BindView(R.id.draw_tv_share)
    TextView tvShare;
    @BindView(R.id.drawer_tv_advice)
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
    UnScrollPagerView mViewPagerContent;
    @BindView(R.id.resideLayout)
    MainPagerLayout mResideLayout;
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
        mDialogUser = DialogUser.getInstanceDialog(mContext);
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

        mResideLayout.setPanelSlideListener(new MainPagerLayout.PanelSlideListener() {
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
    @OnClick({R.id.main_user_icon,R.id.drawer_tv_collect, R.id.drawer_tv_download, R.id.drawer_tv_welfare,
              R.id.draw_tv_share, R.id.drawer_tv_advice, R.id.drawer_tv_settings,
              R.id.main_tv_about, R.id.main_tv_theme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_user_icon:
                Toast.makeText(mContext, "user icn", Toast.LENGTH_SHORT).show();
                //showDialog(mImageViewUser, mDialogUser);
                mDialogUser.showDialog(mImageViewUser);
                break;
            case R.id.drawer_tv_collect:
                mContext.startActivity(new Intent(mContext, CollectionListActivity.class));
                break;
            case R.id.drawer_tv_download:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.drawer_tv_welfare:
                mContext.startActivity(new Intent(mContext, WelfareActivity.class));
                break;
            case R.id.draw_tv_share:
                /**
                 * 更多关于分享知识:
                 * blog:
                 * http://www.wizardev.com/2017/08/20/Android%E8%BD%BB%E6%9D%BE%E5%AE%9E%E7%8E%B0%E5%88%86%E4%BA%AB%E5%8A%9F%E8%83%BD/
                 * */
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().
                                            getString(R.string.setting_recommend_content));
                shareIntent.setType("text/plain");//更多支持分享的途径

                //设置分享列表的标题，并且每次都显示分享列表
                mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.drawer_tv_advice:
                // 以对话框的形式弹出
                //PgyerDialog的独有形式；
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