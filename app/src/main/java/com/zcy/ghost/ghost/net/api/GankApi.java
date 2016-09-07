// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcy.ghost.ghost.net.api;


import com.zcy.ghost.ghost.bean.GankBeautyResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
