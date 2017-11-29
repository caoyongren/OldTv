package com.master.old.tv.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/*****************************
 * Create by MasterMan
 * Description:
 *   项目中多有dialog的基础类.
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017年１１月２８日
 *****************************/

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(@NonNull Context context) {

        super(context);
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    @Override
    public void show() {
        super.show();
    }
}
