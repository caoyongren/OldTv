package com.zcy.ghost.ghost.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description: VideoType
 * Creator: yxc
 * date: $date $time
 */
public class VideoType {
    public String title;
    public
    @SerializedName("childList")
    List<VideoInfo> childList;
}
