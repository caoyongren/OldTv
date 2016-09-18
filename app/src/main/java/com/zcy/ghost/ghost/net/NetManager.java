package com.zcy.ghost.ghost.net;

import android.content.Context;

import com.zcy.ghost.ghost.bean.VideoResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class NetManager {

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    //获取单例
    @org.jetbrains.annotations.Contract(pure = true)
    public static NetManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Subscription getHomePage(Subscriber<VideoResult> subscriber, Context context) {
        return Network.getVideoApi(context)
                .getHomePage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getVideoInformation(Subscriber<VideoResult> subscriber, Context context, String dataId) {
        return Network.getVideoApi(context)
                .getVideoInfo(dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public Subscription getVideoList(Subscriber<VideoResult> subscriber, Context context, String catalogID, String page ) {
        return Network.getVideoApi(context)
                .getVideoList(catalogID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}