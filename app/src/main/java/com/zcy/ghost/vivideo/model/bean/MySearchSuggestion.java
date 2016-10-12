package com.zcy.ghost.vivideo.model.bean;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import io.realm.RealmObject;

/**
 * Created by zjg on 2016/10/11.
 */

public class MySearchSuggestion extends RealmObject implements SearchSuggestion {
    public String strHistorySearchKey;
    public long insertTime;//插入时间

    public MySearchSuggestion() {
    }

    public MySearchSuggestion(String suggestion, long insertTime) {
        this.strHistorySearchKey = suggestion;
        this.insertTime = insertTime;
    }

    public MySearchSuggestion(Parcel source) {
        this.strHistorySearchKey = source.readString();
        this.insertTime = source.readLong();
    }

    @Override
    public String getBody() {
        return strHistorySearchKey;
    }

    public static final Creator<MySearchSuggestion> CREATOR = new Creator<MySearchSuggestion>() {
        @Override
        public MySearchSuggestion createFromParcel(Parcel in) {
            return new MySearchSuggestion(in);
        }

        @Override
        public MySearchSuggestion[] newArray(int size) {
            return new MySearchSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strHistorySearchKey);
        dest.writeLong(insertTime);
    }
}
