// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcy.ghost.ghost.net.api;


import com.zcy.ghost.ghost.bean.VideoResult;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

public interface VideoApi {
    @Headers("Cache-Control: public, max-age=3600")
    @GET("homePageApi/homePage.do")
    Observable<VideoResult> getHomePage();
}
