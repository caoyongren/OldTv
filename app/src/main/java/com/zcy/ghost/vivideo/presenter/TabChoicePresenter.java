package com.zcy.ghost.vivideo.presenter;

import android.util.Log;

import com.zcy.ghost.vivideo.base.RxPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.model.http.response.VideoHttpResponse;
import com.zcy.ghost.vivideo.model.net.RetrofitHelper;
import com.zcy.ghost.vivideo.presenter.contract.RecommendContract;
import com.zcy.ghost.vivideo.utils.RxUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.view.activitys.MainActivity;

import javax.inject.Inject;

import rx.Subscription;
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
public class TabChoicePresenter extends RxPresenter<RecommendContract.View>
                                implements RecommendContract.Presenter {
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
}
