// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcy.ghost.ghost.net.api;


import com.zcy.ghost.ghost.bean.VideoResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Description: VideoApi
 * Creator: yxc
 * date: 2016/9/8 14:05
 */

public interface VideoApi {
    /**
     * 首页
     *
     * @return
     */
//    @Headers("Cache-Control: public, max-age=3600")
    @GET("homePageApi/homePage.do")
    Observable<VideoResult> getHomePage();

    /**
     * 影片详情
     *
     * @param mediaId 影片id
     * @return
     */
//    @Headers("Cache-Control: public, max-age=3600")
    @GET("videoDetailApi/videoDetail.do")
    Observable<VideoResult> getVideoInfo(@Query("mediaId") String mediaId);

    /**
     * 影片分类列表
     *
     * @param catalogId
     * @param pnum
     * @return
     */
//    @Headers("Cache-Control: public, max-age=3600")
    @GET("columns/getVideoList.do")
    Observable<VideoResult> getVideoList(@Query("catalogId") String catalogId, @Query("pnum") String pnum);
}
