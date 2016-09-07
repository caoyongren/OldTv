// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcy.ghost.ghost.net.api;


import com.zcy.ghost.ghost.bean.ZhuangbiImage;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface ZhuangbiApi {

    //使用 RxJava 的方法,返回一个 Observable
    @Headers("Cache-Control: public, max-age=3600")
    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);
}
