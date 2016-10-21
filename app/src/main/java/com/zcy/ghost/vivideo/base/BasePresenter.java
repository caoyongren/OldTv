package com.zcy.ghost.vivideo.base;

/**
 * Description: BasePresenter
 * Creator: yxc
 * date: 2016/9/21 10:42
 */
public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
