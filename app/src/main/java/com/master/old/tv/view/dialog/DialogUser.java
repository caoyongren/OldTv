package com.master.old.tv.view.dialog;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.old.tv.R;
import com.master.old.tv.base.BaseDialog;
import com.master.old.tv.view.activitys.MainActivity;

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

public class DialogUser extends BaseDialog implements View.OnClickListener{

    private static final String TAG = "DialogUser";
    private Context mContext;
    Button mButtonLogin;
    Button mButtonRegister;
    TextView mTextViewName;
    TextView mTextViewInputName;
    TextView mTextViewPwd;
    EditText mEditTextInputPwd;
    TextView mTextViewPwdConfirm;
    LinearLayout mLinearLayoutConfirm;

    private static DialogUser mDialogUser;

    public DialogUser(Context context) {
        super(context);
        mContentView = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_user_layout, null, false);
        setContentView(mContentView);
    }

    public static DialogUser getInstanceDialog(Context context) {
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
        mButtonLogin = (Button) findViewById(R.id.dg_button_login);
        mButtonRegister = (Button) findViewById(R.id.dg_button_register);
        mTextViewName = (TextView) findViewById(R.id.dg_user_name);
        mTextViewInputName = (TextView) findViewById(R.id.dg_input_name);
        mTextViewPwd = (TextView) findViewById(R.id.dg_user_pwd);
        mEditTextInputPwd = (EditText) findViewById(R.id.dg_input_pwd);
        mTextViewPwdConfirm = (TextView) findViewById(R.id.dg_pwd_confirm);
        mLinearLayoutConfirm = (LinearLayout) findViewById(R.id.dg_confirm);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dg_button_register:
                if (MainActivity.DEBUG) {
                    Log.i(TAG, "master");
                }
                break;
            case R.id.dg_button_login:
                break;
        }
    }
}
