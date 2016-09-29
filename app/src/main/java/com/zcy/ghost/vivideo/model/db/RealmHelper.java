package com.zcy.ghost.vivideo.model.db;

import com.zcy.ghost.vivideo.model.bean.Collection;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Description: RealmHelper
 * Creator: yxc
 * date: 2016/9/21 17:46
 */

public class RealmHelper {

    public static final String DB_NAME = "ghost.realm";
    private Realm mRealm;
    private static RealmHelper instance;

    private RealmHelper() {
    }

    public static RealmHelper getInstance() {
        if (instance == null)
            instance = new RealmHelper();
        return instance;
    }


    protected Realm getRealm() {
        if (mRealm == null || mRealm.isClosed())
            mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    /**
     * 增加 收藏记录
     *
     * @param bean
     */
    public void insertCollection(Collection bean) {
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }

    /**
     * 删除 收藏记录
     *
     * @param id
     */
    public void deleteCollection(String id) {
        Collection data = getRealm().where(Collection.class).equalTo("id", id).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    /**
     * 查询 收藏记录
     *
     * @param id
     * @return
     */
    public boolean queryCollectionId(String id) {
        RealmResults<Collection> results = getRealm().where(Collection.class).findAll();
        for (Collection item : results) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Collection> getCollectionList() {
        //使用findAllSort ,先findAll再result.sort排序
        RealmResults<Collection> results = getRealm().where(Collection.class).findAllSorted("time");
        return getRealm().copyFromRealm(results);
    }
}
