package com.cao.old.tv.presenter;

import com.cao.old.tv.base.RxPresenter;
import com.cao.old.tv.model.bean.VideoRes;
import com.cao.old.tv.model.exception.ApiException;
import com.cao.old.tv.model.net.HttpMethods;
import com.cao.old.tv.model.net.MyObserver;
import com.cao.old.tv.presenter.contract.ClassificationContract;

import javax.inject.Inject;

/**
 * Description: ClassificationPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class ClassificationPresenter extends RxPresenter<ClassificationContract.View> implements ClassificationContract.Presenter {
    int page = 0;

    @Inject
    public ClassificationPresenter() {
    }

    @Override
    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
        HttpMethods.getInstance().queryClassification()
                .subscribe(new MyObserver<VideoRes>() {
                    @Override
                    protected void onError(ApiException ex) {
                        mView.refreshFaild(ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onNext(VideoRes res) {
                        if (res != null) {
                            mView.showContent(res);
                        }
                    }
                });
    }
}
