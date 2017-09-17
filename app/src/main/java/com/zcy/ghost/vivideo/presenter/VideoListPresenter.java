package com.zcy.ghost.vivideo.presenter;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.http.response.VideoHttpResponse;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.presenter.contract.VideoListContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

public class VideoListPresenter extends RxPresenter<VideoListContract.View> implements VideoListContract.Presenter {
    int page = 1;
    String catalogId = "";

    @Inject
    public VideoListPresenter() {
    }

    @Override
    public void onRefresh(String catalogId) {
        this.catalogId = catalogId;
        page = 1;
        getVideoList(catalogId);
    }

    private void getVideoList(String catalogID) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoList(catalogID, page + "")
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

    /**
     * 搜索电影
     *
     * @param searchStr
     */
    private void getSearchVideoList(String searchStr) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoListByKeyWord(searchStr, page + "")
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
        getVideoList(catalogId);
    }

}
