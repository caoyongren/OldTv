package com.zcy.ghost.ghost.utils;

import com.zcy.ghost.ghost.bean.VideoRes;

/**
 * @author yxc
 *         事件管理(EventBus)
 */
public class Events {

    public static class GetVideoInfo {
        VideoRes videoRes;

        //图片预览里删除图片
        public GetVideoInfo() {
        }

        public GetVideoInfo(VideoRes videoRes) {
            this.videoRes = videoRes;
        }

        public VideoRes getVideoRes() {
            return videoRes;
        }
    }
}
