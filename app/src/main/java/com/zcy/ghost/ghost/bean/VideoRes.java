package com.zcy.ghost.ghost.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class VideoRes {
    public
    @SerializedName("list")
    List<VideoType> list;
    public String title;
    public String score;
    public String videoType;
    public String region;
    public String airTime;
    public String director;
    public String actors;
    public String pic;
    public String description;
}
