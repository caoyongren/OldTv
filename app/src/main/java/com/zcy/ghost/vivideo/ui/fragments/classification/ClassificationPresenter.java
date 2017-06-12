package com.zcy.ghost.vivideo.ui.fragments.classification;

import com.zcy.ghost.vivideo.model.DataManager;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.http.response.VideoHttpResponse;
import com.zcy.ghost.vivideo.newbase.RxPresenter;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Description: ClassificationPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class ClassificationPresenter extends RxPresenter<ClassificationContract.View> implements ClassificationContract.Presenter {
    int page = 0;
    private DataManager mDataManager;

    @Inject
    public ClassificationPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
        Subscription rxSubscription = mDataManager.fetchHomePage()
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                               @Override
                               public void call(final VideoRes res) {
                                   if (res != null) {
                                       mView.showContent(res);
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {

                                    mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                               }
                           }, new Action0() {
                               @Override
                               public void call() {
                               }
                           }
                );

        addSubscribe(rxSubscription);

    }
}
