package com.master.old.tv.presenter.tab;

import android.util.Log;

import com.master.old.tv.base.RxPresenter;
import com.master.old.tv.model.bean.Collection;
import com.master.old.tv.model.bean.Record;
import com.master.old.tv.model.bean.VideoInfo;
import com.master.old.tv.model.bean.VideoRes;
import com.master.old.tv.model.db.RealmHelper;
import com.master.old.tv.model.http.response.VideoHttpResponse;
import com.master.old.tv.model.net.RetrofitHelper;
import com.master.old.tv.presenter.contract.tab.TabChoiceContract;
import com.master.old.tv.presenter.contract.VideoInfoContract;
import com.master.old.tv.utils.BeanUtil;
import com.master.old.tv.utils.RxUtil;
import com.master.old.tv.utils.StringUtils;
import com.master.old.tv.view.activitys.MainActivity;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Create by MasterMan
 * Description:
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: 2017/9/21 16:26
 */
public class TabChoicePresenter extends RxPresenter<TabChoiceContract.View>
                                implements TabChoiceContract.Presenter {
    private static final String TAG = "TabChoicePresenter";
    int page = 0;

    @Inject
    public TabChoicePresenter() {}

    @Override
    public void onRefresh() {
        page = 0;
        if (MainActivity.FLAG) {
            Log.i(MainActivity.DATA, "presenter: onRefresh");
        }
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
        //主要应用于线程处理；
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getHomePage()  //
                .compose(RxUtil.<VideoHttpResponse<VideoRes>> rxSchedulerHelper()) //子线程
                .compose(RxUtil.<VideoRes> handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(final VideoRes res) {
                        if (res != null) {
                            if (MainActivity.FLAG) {
                                Log.i(MainActivity.DATA, "pageUrl:" + RetrofitHelper.getVideoApi().getHomePage());
                                Log.i(MainActivity.DATA, "Presenter: getPageHomeInfo: " + res);
                            }
                            //该方法实现在: {TabChoiceFragment.java} -> showContent(data);
                            mView.showContent(res);
                            if (MainActivity.DEBUG) {
                                Log.i(TAG, "subscribe call" + Thread.currentThread());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(rxSubscription);
    }

    /**
     * Description: VideoInfoPresenter
     * Creator: yxc
     * date: 2016/9/21 15:35
     */
    public static class VideoInfoPresenter extends RxPresenter<VideoInfoContract.View>
                                            implements VideoInfoContract.Presenter {

        public final static String Refresh_Video_Info = "Refresh_Video_Info";
        public final static String Put_DataId = "Put_DataId";
        public static final String Refresh_Collection_List = "Refresh_Collection_List";
        public static final String Refresh_History_List = "Refresh_History_List";
        final int WAIT_TIME = 200;
        VideoRes result;
        String dataId = "";
        String pic = "";

        @Inject
        public VideoInfoPresenter() {
        }

        public void prepareInfo(VideoInfo videoInfo) {
            mView.showContent(BeanUtil.VideoInfo2VideoRes(videoInfo, null));
            this.dataId = videoInfo.dataId;
            this.pic = videoInfo.pic;
            getDetailData(videoInfo.dataId);
            setCollectState();
            putMediaId();
        }


        @Override
        public void getDetailData(String dataId) {
            Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoInfo(dataId)
                    .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                    .compose(RxUtil.<VideoRes>handleResult())
                    .subscribe(new Action1<VideoRes>() {
                        @Override
                        public void call(final VideoRes res) {
                            if (res != null) {
                                mView.showContent(res);
                                result = res;
                                postData();
                                insertRecord();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.hidLoading();
                            mView.showError("数据加载失败");
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            mView.hidLoading();
                        }
                    });
            addSubscribe(rxSubscription);
        }

        private void postData() {
            Subscription rxSubscription = Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                    .compose(RxUtil.<Long>rxSchedulerHelper())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            EventBus.getDefault().post(result, Refresh_Video_Info);
                        }
                    });
            addSubscribe(rxSubscription);
        }

        private void putMediaId() {
            Subscription rxSubscription = Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                    .compose(RxUtil.<Long>rxSchedulerHelper())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            EventBus.getDefault().post(dataId, Put_DataId);
                        }
                    });
            addSubscribe(rxSubscription);
        }

        @Override
        public void collect() {
            if (RealmHelper.getInstance().queryCollectionId(dataId)) {
                RealmHelper.getInstance().deleteCollection(dataId);
                mView.disCollect();
            } else {
                if (result != null) {
                    Collection bean = new Collection();
                    bean.setId(String.valueOf(dataId));
                    bean.setPic(pic);
                    bean.setTitle(result.title);
                    bean.setAirTime(result.airTime);
                    bean.setScore(result.score);
                    bean.setTime(System.currentTimeMillis());
                    RealmHelper.getInstance().insertCollection(bean);
                    mView.collected();
                }
            }
            //刷新收藏列表
            Subscription rxSubscription = Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                    .compose(RxUtil.<Long>rxSchedulerHelper())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            EventBus.getDefault().post("", Refresh_Collection_List);
                        }
                    });
            addSubscribe(rxSubscription);
        }

        @Override
        public void insertRecord() {
            if (!RealmHelper.getInstance().queryRecordId(dataId)) {
                if (result != null) {
                    Record bean = new Record();
                    bean.setId(String.valueOf(dataId));
                    bean.setPic(pic);
                    bean.setTitle(result.title);
                    bean.setTime(System.currentTimeMillis());
                    RealmHelper.getInstance().insertRecord(bean, TabMyselfPresenter.maxSize);
                    //刷新收藏列表
                    Subscription rxSubscription = Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                            .compose(RxUtil.<Long>rxSchedulerHelper())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    EventBus.getDefault().post("", Refresh_History_List);
                                }
                            });
                    addSubscribe(rxSubscription);
                }
            }
        }

        private void setCollectState() {
            if (RealmHelper.getInstance().queryCollectionId(dataId)) {
                mView.collected();
            } else {
                mView.disCollect();
            }
        }
    }
}
