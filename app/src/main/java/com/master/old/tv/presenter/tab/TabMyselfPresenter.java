package com.master.old.tv.presenter.tab;

import com.master.old.tv.base.RxPresenter;
import com.master.old.tv.model.bean.Record;
import com.master.old.tv.model.bean.VideoType;
import com.master.old.tv.model.db.RealmHelper;
import com.master.old.tv.presenter.contract.tab.TabMyselfContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description: CollectionPresenter
 * Creator: yxc
 * date: 2016/9/29 12:19
 */
public class TabMyselfPresenter extends RxPresenter<TabMyselfContract.View> implements TabMyselfContract.Presenter {
    public static final int maxSize = 30;

    @Inject
    public TabMyselfPresenter() {
    }

    @Override
    public void getHistoryData() {
        List<Record> records = RealmHelper.getInstance().getRecordList();
        List<VideoType> list = new ArrayList<>();
        VideoType videoType;
        int maxlinth = records.size() <= 3 ? records.size() : 3;
        for (int i = 0; i < maxlinth; i++) {
            Record record = records.get(i);
            videoType = new VideoType();
            videoType.title = record.title;
            videoType.pic = record.pic;
            videoType.dataId = record.getId();
            list.add(videoType);
        }
        mView.showContent(list);
    }

    @Override
    public void delAllHistory() {
        RealmHelper.getInstance().deleteAllRecord();
    }

}
