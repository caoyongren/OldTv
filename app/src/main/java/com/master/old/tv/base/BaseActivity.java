package com.master.old.tv.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.master.old.tv.R;
import com.master.old.tv.app.App;
import com.master.old.tv.utils.PreUtils;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.widget.theme.ColorRelativeLayout;
import com.master.old.tv.widget.theme.Theme;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/*****************************
 * Create by MasterMan
 * Description:
 *   抽象类: BaseActivity是项目中所有的Activity的父类
 *   　　Method:
 *       1. getLayout()
 *       2. initView();
 *       3. initEvent();
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017年10月１１日
 *****************************/
public abstract class BaseActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(getLayout());
        getIntentData();
        mContext = this;
        mUnBinder = ButterKnife.bind(this);
        initView();
        initEvent();
    }

    /**
     * 设置主题的方式
     * */
    protected void init() {
        setTranslucentStatus(true);
        onPreCreate();
        App.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitleHeight(getRootView(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        App.getInstance().unregisterActivity(this);
        super.onDestroy();
    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private void setTitleHeight(View view) {
        if (view != null) {
            ColorRelativeLayout title = (ColorRelativeLayout) view.findViewById(R.id.normal_title);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                if (title != null) {
                    ViewGroup.LayoutParams lp = title.getLayoutParams();
                    lp.height = ScreenUtil.dip2px(this, 48);
                    title.setLayoutParams(lp);
                    title.setPadding(0, 0, 0, 0);
                }
            }
        }
    }

    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 抽象类中可以有抽象方法/非抽象方法
     *   抽象类中的抽象方法子类必须实现(除非子类是也是抽象类)
     * */
    protected abstract int getLayout();

    protected void initView() {
    }

    protected void initEvent() {
    }

    protected void getIntentData() {
    }
}
