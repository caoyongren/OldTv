package com.cao.old.tv.presenter;

import com.cao.old.tv.base.RxPresenter;
import com.cao.old.tv.model.bean.VideoRes;
import com.cao.old.tv.model.http.response.VideoHttpResponse;
import com.cao.old.tv.model.net.RetrofitHelper;
import com.cao.old.tv.presenter.contract.CommentContract;
import com.cao.old.tv.utils.RxUtil;
import com.cao.old.tv.utils.StringUtils;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

public class CommentPresenter extends RxPresenter<CommentContract.View> implements CommentContract.Presenter {
    int page = 1;
    String mediaId = "";

    @Inject
    public CommentPresenter() {
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (mediaId != null && !mediaId.equals("")) {
            getComment(mediaId);
        }
    }

    private void getComment(String mediaId) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getCommentList(mediaId, page + "")
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes res) {
                        if (res != null) {
                            if (page == 1) {
                                mView.showContent(res.list);
                            } else {
                                mView.showMoreContent(res.list);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (page > 1) {
                            page--;
                        }
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void loadMore() {
        page++;
        if (mediaId != null && mediaId.equals("")) {
            getComment(mediaId);
        }
    }

    @Override
    public void setMediaId(String id) {
        this.mediaId = id;
    }

}
