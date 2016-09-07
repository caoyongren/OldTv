package com.zcy.ghost.ghost.net;

import android.content.Context;
import android.text.TextUtils;

import com.zcy.ghost.ghost.utils.SystemUtils;

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

/**
 * Description: httpclient缓存
 * Creator: yxc
 * date: 2016/9/7 10:14
 */
public class CatcheHttpClient {

    private String TAG = CatcheHttpClient.class.getSimpleName();

    //设缓存有效期为15天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 15;
    //查询缓存的Cache-Control设置，为only-if-cached时会只读取缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private OkHttpClient okHttpClient;
    private static CatcheHttpClient myOkHttpClient;

    public static CatcheHttpClient getMyOkHttpClient() {
        if (myOkHttpClient == null) {
            myOkHttpClient = new CatcheHttpClient();

        }

        return myOkHttpClient;
    }

    /**
     * 初始化
     *
     * @param mContext
     */
    public OkHttpClient init(final Context mContext) {

        if (okHttpClient == null) {
            synchronized (CatcheHttpClient.class) {
                if (okHttpClient == null) {
                    File cacheFile = new File(
                            mContext.getCacheDir(),
                            "HttpCache"
                    ); // 指定缓存路径
                    final Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
                    // 云端响应头拦截器，用来配置缓存策略
                    Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            String cacheControl = request.cacheControl().toString();
                            if (TextUtils.isEmpty(cacheControl)) {
                                request = request.newBuilder().cacheControl(new CacheControl.Builder().noStore().build()).build();
                                cacheControl = "noStore";
                            } else if (!SystemUtils.checkNet(mContext)) {
                                request = request.newBuilder()
                                        .cacheControl(CacheControl.FORCE_CACHE).build();
                                cacheControl = CACHE_CONTROL_CACHE;
                            }
                            Response originalResponse = chain.proceed(request);
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", cacheControl)
                                    .removeHeader("Pragma").build();
                        }
                    };

                    okHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(getHttpLoggingInterceptor())
                            .cache(cache)
                            .addNetworkInterceptor(rewriteCacheControlInterceptor)
                            .addInterceptor(rewriteCacheControlInterceptor)
                            .connectTimeout(60, TimeUnit.SECONDS).build();
                }
            }
        }
        return okHttpClient;

    }

    /**
     * 开启打印连接日志
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        boolean isDebug = true;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (isDebug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

//        开启打印连接日志
        return interceptor;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}
