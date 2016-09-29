package com.zcy.ghost.vivideo.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Description: 收藏
 * Creator: yxc
 * date: 2016/9/23 11:29
 */
public class Collection extends RealmObject implements Serializable {
    String id;
    long time;
    public String title;
    public String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
