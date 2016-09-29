package com.zcy.ghost.vivideo.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.model.net.VideoHttpResponse;
import com.zcy.ghost.vivideo.presenter.contract.VideoListContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class VideoListPresenter extends RxPresenter implements VideoListContract.Presenter {
    @NonNull
    final VideoListContract.View mView;
    int page = 1;
    String catalogId = "";
    String searchStr = "";
    Handler handler = new Handler();


    public VideoListPresenter(@NonNull VideoListContract.View addTaskView, String catalogId,String searchStr) {
        mView = StringUtils.checkNotNull(addTaskView);
        mView.setPresenter(this);
        this.catalogId = catalogId;
        this.searchStr = searchStr;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        if(catalogId!=null && !catalogId.equals("")) {
            getVideoList(catalogId);
        }else if(searchStr!=null && !searchStr.equals("")){
            getSearchVideoList(searchStr);
        }
    }

    private void getVideoList(String catalogID) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoList(catalogID, page + "")
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                if (page == 1) {
                                    mView.showContent(res.list);
                                } else {
                                    mView.showMoreContent(res.list);
                                }
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
        addSubscrebe(rxSubscription);
    }

    /**
     * 搜索电影
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
                            if (mView.isActive()) {
                                if (page == 1) {
                                    mView.showContent(res.list);
                                } else {
                                    mView.showMoreContent(res.list);
                                }
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
        addSubscrebe(rxSubscription);
    }

    @Override
    public void loadMore() {
        page++;
        if(catalogId!=null && catalogId.equals("")) {
            getVideoList(catalogId);
        }else if(searchStr!=null && searchStr.equals("")){
            getSearchVideoList(searchStr);
        }
    }

}
