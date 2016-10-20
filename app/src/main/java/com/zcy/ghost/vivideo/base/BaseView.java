package com.zcy.ghost.vivideo.base;

/******************************************
 * 类名称：BaseView
 * 类描述：
 *
 * @version: 2.3.1
 * @author: caopeng
 * @time: 2016/9/12 10:18
 ******************************************/
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError(String msg);
}
