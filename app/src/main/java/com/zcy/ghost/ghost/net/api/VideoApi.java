// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcy.ghost.ghost.net.api;


import com.zcy.ghost.ghost.bean.VideoResult;

import retrofit2.http.GET;
import rx.Observable;

public interface VideoApi {
    @GET("homePageApi/homePage.do")
    Observable<VideoResult> getHomePage();
}
