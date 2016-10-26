package com.zcy.ghost.vivideo.model.bean;

import io.realm.RealmObject;

/**
 * Created by zjg on 2016/10/11.
 */

public class SearchKey extends RealmObject {
    public String searchKey;
    public long insertTime;//插入时间

    public SearchKey() {
    }

    public SearchKey(String suggestion, long insertTime) {
        this.searchKey = suggestion;
        this.insertTime = insertTime;
    }

    public String getSearchKey() {
        return searchKey;
    }
}
