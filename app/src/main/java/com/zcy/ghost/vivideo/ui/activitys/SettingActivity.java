package com.zcy.ghost.vivideo.ui.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.app.Constants;
import com.zcy.ghost.vivideo.base.SwipeBackActivity;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.PreUtils;
import com.zcy.ghost.vivideo.utils.ThemeUtils;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends SwipeBackActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.rl_recommend)
    RelativeLayout rlRecommend;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.title_name)
    ColorTextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);

        titleName.setText("设置");
        tvCache.setText(EventUtil.getFormatSize(Glide.getPhotoCacheDir(this).length()));
    }

    @OnClick({R.id.rl_back, R.id.rl_recommend, R.id.rl_about, R.id.rl_feedback, R.id.rl_clearcache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_recommend:
                new MaterialDialog.Builder(this)
                        .content(R.string.setting_recommend_content)
                        .contentColor(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                        .positiveText(R.string.close)
                        .negativeText(R.string.setting_recommend_copy).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cmb.setText(getResources().getString(R.string.setting_recommend_url));
                        EventUtil.showToast(SettingActivity.this, "已复制到粘贴板");
                    }
                })
                        .show();
                break;
            case R.id.rl_about:
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
            case R.id.rl_feedback:
                PgyerDialog.setDialogTitleBackgroundColor(PreUtils.getString(this, Constants.PRIMARYCOLOR, "#000000"));
                PgyerDialog.setDialogTitleTextColor(PreUtils.getString(this, Constants.TITLECOLOR, "#0aa485"));
                PgyFeedback.getInstance().showDialog(this);
                PgyFeedback.getInstance().showDialog(this).d().setChecked(false);
                break;
            case R.id.rl_clearcache:
                tvCache.setText("0kb");
                EventUtil.showToast(this, "已清理缓存");
                break;
        }
    }
}
