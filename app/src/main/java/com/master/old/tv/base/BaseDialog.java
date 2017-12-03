package com.master.old.tv.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.master.old.tv.R;

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

    public View mContentView;
    private static Point mPoint;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        if (mPoint == null) {
            Display defaultDisplay = ((WindowManager)getContext().
                                        getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            mPoint = new Point();
            defaultDisplay.getRealSize(mPoint);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    @Override
    public void hide() {
        super.hide();
    }

    public void showDialog(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (mContentView != null) {
            mContentView.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
        }
        Window dialogWindow = getWindow();
        dialogWindow.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        dialogWindow.setWindowAnimations(R.style.showDialog);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.format = PixelFormat.TRANSLUCENT;
        lp.dimAmount = 0;

        /*if (mContentView.getMeasuredWidth() / 2 > location[0]) {
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            lp.x = 0;
        } else if (location[0] + view.getMeasuredWidth()/2 +
                mContentView.getMeasuredWidth() / 2 > mPoint.x) {
            dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            lp.x = 0;
        } else {
            dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            lp.x = location[0] + view.getMeasuredWidth() / 2 - mPoint.x / 2;
        }
        lp.y = 0;*/
        dialogWindow.setGravity(Gravity.CENTER);
        lp.x = 100;
        lp.y = -300;
        dialogWindow.setAttributes(lp);
        show();
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }
}
