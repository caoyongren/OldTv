package com.master.old.tv.view.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.old.tv.R;
import com.master.old.tv.base.BaseDialog;

import butterknife.BindView;

/*****************************
 * Create by MasterMan
 * Description:
 *   用户信息详情
 *   　
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017年11月29日
 *****************************/

public class DialogUser extends BaseDialog{

    private Context mContext;

    @BindView(R.id.dg_button_login)
    Button mButtonLogin;

    @BindView(R.id.dg_button_register)
    Button mButtonRegister;

    @BindView(R.id.dg_user_name)
    TextView mTextViewName;

    @BindView(R.id.dg_input_name)
    TextView mTextViewInputName;

    @BindView(R.id.dg_user_pwd)
    TextView mTextViewPwd;

    @BindView(R.id.dg_input_pwd)
    EditText mEditTextInputPwd;

    @BindView(R.id.dg_pwd_confirm)
    TextView mTextViewPwdConfirm;

    @BindView(R.id.dg_confirm)
    LinearLayout mLinearLayoutConfirm;

    private static DialogUser mDialogUser;

    public DialogUser(Context context) {
        super(context);
        mContentView = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_user_layout, null, false);
        setContentView(mContentView);
    }

    public static DialogUser getInstance(Context context) {
        if (mDialogUser == null) {
            mDialogUser = new DialogUser(context);
        }
        return mDialogUser;
    }

    @Override
    public void showDialog(View view) {
        super.showDialog(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
