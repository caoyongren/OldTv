package com.zcy.ghost.vivideo.presenter;

import android.support.annotation.NonNull;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.exception.ApiException;
import com.zcy.ghost.vivideo.model.net.HttpMethods;
import com.zcy.ghost.vivideo.model.net.MyObserver;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;
import com.zcy.ghost.vivideo.presenter.contract.ClassificationContract;
import com.zcy.ghost.vivideo.utils.Preconditions;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Description: ClassificationPresenter
 * Creator: yxc
 * date: 2016/9/21 17:55
 */
public class ClassificationPresenter extends RxPresenter implements ClassificationContract.Presenter {
    ClassificationContract.View mView;
    int page = 0;

    public ClassificationPresenter(@NonNull ClassificationContract.View twoView) {
        mView = Preconditions.checkNotNull(twoView);
        mView.setPresenter(this);
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
                            if (mView.isActive()) {
                                mView.showContent(res);
                            }
                        }
                    }
                });
    }
}
