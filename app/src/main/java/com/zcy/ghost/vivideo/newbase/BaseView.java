package com.zcy.ghost.vivideo.newbase;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showErrorMsg(String msg);

    void useNightMode(boolean isNight);

    //=======  State  =======
    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
