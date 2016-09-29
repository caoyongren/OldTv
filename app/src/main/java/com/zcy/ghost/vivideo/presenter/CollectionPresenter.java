package com.zcy.ghost.vivideo.presenter;

import android.support.annotation.NonNull;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.Collection;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.model.db.RealmHelper;
import com.zcy.ghost.vivideo.presenter.contract.CollectionContract;
import com.zcy.ghost.vivideo.utils.StringUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: CollectionPresenter
 * Creator: yxc
 * date: 2016/9/29 12:15
 */
public class CollectionPresenter extends RxPresenter implements CollectionContract.Presenter {
    CollectionContract.View mView;

    public CollectionPresenter(@NonNull CollectionContract.View oneView) {
        mView = StringUtils.checkNotNull(oneView);
        mView.setPresenter(this);
        EventBus.getDefault().register(this);
        getCollectData();
    }

    @Override
    public void getCollectData() {
        List<Collection> collections = RealmHelper.getInstance().getCollectionList();
        List<VideoType> list = new ArrayList<>();
        VideoType videoType;
        for (Collection collection : collections) {
            videoType = new VideoType();
            videoType.title = collection.title;
            videoType.pic = collection.pic;
            videoType.dataId = collection.getId();
            videoType.score = collection.getScore();
            videoType.airTime = collection.getAirTime();
            list.add(videoType);
        }
        mView.showContent(list);
    }

    @Override
    public void delAllCollects() {
        RealmHelper.getInstance().deleteAllCollection();
    }

    @Subscriber(tag = VideoInfoPresenter.Refresh_Collection_List)
    public void setData(String tag) {
        getCollectData();
    }
}
