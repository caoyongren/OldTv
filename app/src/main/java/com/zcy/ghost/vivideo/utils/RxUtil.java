package com.zcy.ghost.vivideo.utils;

import android.text.TextUtils;

import com.zcy.ghost.vivideo.model.bean.GankHttpResponse;
import com.zcy.ghost.vivideo.model.net.ApiException;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: RxUtil
 * Creator: yxc
 * date: 2016/9/21 18:47
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<VideoHttpResponse<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<VideoHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<VideoHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<VideoHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(VideoHttpResponse<T> videoHttpResponse) {
                        if (videoHttpResponse.getCode() == 200) {
                            return createData(videoHttpResponse.getRet());
                        } else if (!TextUtils.isEmpty(videoHttpResponse.getMsg())) {
                            return Observable.error(new ApiException("*" + videoHttpResponse.getMsg()));
                        } else {
                            return Observable.error(new ApiException("*" + "服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    public static <T> Observable.Transformer<GankHttpResponse<T>, T> handleGankResult() {   //compose判断结果
        return new Observable.Transformer<GankHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<GankHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<GankHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(GankHttpResponse<T> tGankHttpResponse) {
                        if(!tGankHttpResponse.getError()) {
                            return createData(tGankHttpResponse.getResults());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
