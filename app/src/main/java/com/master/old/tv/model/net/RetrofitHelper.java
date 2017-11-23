package com.master.old.tv.model.net;

import android.util.Log;

import com.master.old.tv.BuildConfig;
import com.master.old.tv.app.Constants;
import com.master.old.tv.model.http.api.GankApis;
import com.master.old.tv.model.http.api.VideoApis;
import com.master.old.tv.utils.system.SystemUtils;
import com.master.old.tv.view.activitys.MainActivity;

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

/**
 * Create by MasterMan
 * Description:
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017-05-01
 */
public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";
    private static OkHttpClient okHttpClient = null;
    private static VideoApis videoApi;
    private static GankApis gankApis;

    public static VideoApis getVideoApi() {
        initOkHttp();
        if (videoApi == null) {
            /**
             * Retrofit:是Square公司开发的一款针对Android网络请求的框架，
             * Retrofit2底层基于Okhttp实现的，　Okhttp现在已经得到google官方
             * 认可，　大量的app都采用okHttp做网络请求，
             *　源码: https://github.com/square/okhttp
             * --------------------------------------------------------
             *  //简单实例：
             * 1. 创建业务请求接口.
             *   public interface ServicesApi {
             *       @GET("book/search")
             *       Call<BookSearchResponse> getSearchBooks(
             *       @Query("q") String name,
             *       @Query("tag") String tag,
             *       @Query("start" int start,
             *       @Query("count") int count);
             *   }
             *   //@GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面.
             * 2. 创建一个Retrofit的示例
             *   Retrofit retrofit = new Retrofit.Build()
             *       .baseUrl("https://api.douban.com/v2/")
             *       .addConverterFactory(GsonConverterFactory.create()) //解析gson
             *       .build();
             *    BlueService service = retrofit.create(BlueService.class);
             *
             * 3. 调用请求方法
             *    Call<BookSearchResponse> call = mBlueService.getSearchBooks("小王子", "", 0, 3);
             *
             * 4. Call其实在Retrofit中就是行使网络请求并处理返回值的类，
             *    调用的时候会把需要拼接的参数传递进去，此处最后得到的url完整地址为
             *    https://api.douban.com/v2/book/search?q=%E5%B0%8F%E7%8E%8B%E5%AD%90&tag=&start=0&count=3
             *
             * 5. 使用Call实例完成同步或异步请求．
             *    BookSearchResponse response = call.execute().body();//子线程进行；
             *
             * 6. 进行异步
             * 　　call.enqueue(new Callback<BookSearchResponse>() {
             *           @Override
             *           public void onResponse(Call<BookSearchResponse> call, Response<BookSearchResponse> response) {
             *               asyncText.setText("异步请求结果: " + response.body().books.get(0).altTitle);
             *           }
             *           @Override
             *           public void onFailure(Call<BookSearchResponse> call, Throwable t) {
             *
             *           }
             * 　　});
             *
             * 7. build.gradle
             *     compile 'com.squareup.retrofit2:retrofit:2.1.0'
             *     compile 'com.squareup.retrofit2:converter-gson:2.1.0'
             *     compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
             * ================================================================
             *
             * Retrofit + RxJava结合使用
             * 1. build.gradle
             *    compile 'com.squareup.retrofit2:adapter-rxjava:2.3.0'
             *    compile 'io.reactivex:rxandroid:1.2.1'
             * 2. 将RetrofitService类中getPostInfo方法的返回值修改为Observable（被观察者）
             *   public interface RetrofitService {
             *       //获取快递信息
             *       //Rx方式
             *       //
             *       //@param type 快递类型
             *       //@param postid 快递单号
             *       //@return Observable<PostInfo></>
             *
             *       @GET("query")
             *       Observable<PostInfo> getPostInfoRx(
             *       @Query("type" String type,
             *       @Query("postid") String postid));
             *   }
             *
             * 3. 在创建Retrofit时添加RxJava支持.
             *   Retrofit retrofit = new Retrofit.Builder()
             *       .baseUrl("http://www.kuaidi100.com/")
             *       .addConvertFactory(GsonConverterFactory.create())
             *       .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //支持Rxjava
             *       .build();
             *
             *   获取被观察者：
             *   RetrofitService service = retrofit.create(RetrofitService.class);
             *   Observable<PostInfo> observable = service.getPostInfoRx("master", "1111111");
             *
             * 4. 订阅
             *   observable.subscribeOn(Schedulers.io())
             *       .observeOn(AndroidSchedulers.mainThread())
             *       .subscribe(new Observer<PostInfo>() { //订阅
             *　　　　     @Override
             *           public void onCompleted() {
             *
             *           }
             *
             *           @Override
             *           public void onError(Throwable e){
             *
             *           }
             *
             *           @Override
             *           public void onNext(PostInfo postInfo) {
             *               Log.i("http", "postInfo.toString()");
             *           }
             *       });
             *
             * Blog: http://www.jianshu.com/p/021a2c6e128b
             * */
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(VideoApis.HOST)    //baseUrl
                    .addConverterFactory(GsonConverterFactory.create())    //ｇson解析
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持Rxjava
                    .build();
            videoApi = retrofit.create(VideoApis.class);
            if (MainActivity.FLAG) {
                Log.i(MainActivity.DATA, "Retrofit: basUrl-->" + VideoApis.HOST);
                Log.i(MainActivity.DATA, "Retrofit: getVideoApi-->" + videoApi);
            }
        }
        return videoApi;
    }

    public static GankApis getGankApis() {
        initOkHttp();
        if (gankApis == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GankApis.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            gankApis = retrofit.create(GankApis.class);
        }
        return gankApis;
    }
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
}
