package com.master.old.tv.model.net;


import com.master.old.tv.BuildConfig;
import com.master.old.tv.app.Constants;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.model.exception.ExceptionEngine;
import com.master.old.tv.model.exception.ServerException;
import com.master.old.tv.model.http.api.VideoApis;
import com.master.old.tv.model.http.response.VideoHttpResponse;
import com.master.old.tv.utils.system.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class HttpMethods {
    public static final String BASE_HOST_URL = VideoApis.HOST;

    private Retrofit retrofit;
    private VideoApis mService;
    private static OkHttpClient okHttpClient;

    private static void initOkHttp() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                builder.addInterceptor(loggingInterceptor);
            }
            File cacheFile = new File(Constants.PATH_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!SystemUtils.isNetworkConnected()) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    int tryCount = 0;
                    Response response = chain.proceed(request);
                    while (!response.isSuccessful() && tryCount < 3) {

                        tryCount++;

                        // retry the request
                        response = chain.proceed(request);
                    }


                    if (SystemUtils.isNetworkConnected()) {
                        int maxAge = 0;
                        // 有网络时, 不缓存, 最大保存时长为0
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("Pragma")
                                .build();
                    }
                    return response;
                }
            };
            //设置缓存
            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);
            builder.cache(cache);
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            okHttpClient = builder.build();
        }
    }

    //构造方法私有
    private HttpMethods() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_HOST_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(VideoApis.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        initOkHttp();
        return SingletonHolder.INSTANCE;
    }

    /**
     * TabTopicPresenter
     *
     * @return
     */
    public Observable<VideoRes> queryClassification() {
        return mService.getHomePage()
                .map(new ServerResultFunc<VideoRes>())
                .onErrorResumeNext(new HttpResultFunc<VideoRes>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class ServerResultFunc<T> implements Func1<VideoHttpResponse<T>, T> {
        @Override
        public T call(VideoHttpResponse<T> httpResult) {
            if (httpResult.getCode() != 200) {
                throw new ServerException(httpResult.getCode(), httpResult.getMsg());
            }
            return httpResult.getRet();
        }
    }

    private class HttpResultFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }
}
