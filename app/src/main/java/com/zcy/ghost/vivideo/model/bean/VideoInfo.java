package com.zcy.ghost.vivideo.model.bean;

import java.io.Serializable;

/**
 * Description: 影片详情
 * Creator: yxc
 * date: 2016/9/29 9:39
 */
public class VideoInfo implements Serializable {
    public String title;
    public String pic;
    public String dataId;
    public String score;
    public String airTime;
    public String moreURL;
    //    public String description;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
