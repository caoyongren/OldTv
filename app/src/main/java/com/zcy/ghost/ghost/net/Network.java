package com.zcy.ghost.ghost.net;

import android.content.Context;

import com.zcy.ghost.ghost.net.api.GankApi;
import com.zcy.ghost.ghost.net.api.VideoApi;
import com.zcy.ghost.ghost.net.api.ZhuangbiApi;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static ZhuangbiApi zhuangbiApi;
    private static GankApi gankApi;
    private static VideoApi videoApi;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static ZhuangbiApi getZhuangbiApi(Context context) {
        if (zhuangbiApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(CatcheHttpClient.getMyOkHttpClient().init(context))
                    .baseUrl("http://zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            zhuangbiApi = retrofit.create(ZhuangbiApi.class);
        }
        return zhuangbiApi;
    }

    public static GankApi getGankApi(Context context) {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(CatcheHttpClient.getMyOkHttpClient().init(context))
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }
    public static VideoApi getVideoApi(Context context) {
        if (videoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(CatcheHttpClient.getMyOkHttpClient().init(context))
                    .baseUrl("http://api.svipmovie.com/front/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            videoApi = retrofit.create(VideoApi.class);
        }
        return videoApi;
    }
}
