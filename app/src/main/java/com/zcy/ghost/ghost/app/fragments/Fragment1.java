package com.zcy.ghost.ghost.app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.BannerAdapter;
import com.zcy.ghost.ghost.adapter.VideoAdapter;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.net.Network;
import com.zcy.ghost.ghost.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class Fragment1 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    VideoAdapter adapter;
    int page = 0;
    Handler handler = new Handler();
    VideoResult homeResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        initData();
    }

    private void initView(View view) {
        recyclerView.setAdapterWithProgress(adapter = new VideoAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
//        adapter.addAll(DataProvider.getPictures(page));
    }

    private void initEvent() {
        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void initData() {
    }

    @Override
    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                getPageHomeInfo();
            }
        }, 1000);
    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(VideoResult videoResult) {
            homeResult = videoResult;
            setAdapter();
        }
    };

    private void getPageHomeInfo() {
        subscription = Network.getVideoApi(getContext())
                .getHomePage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void setAdapter() {
        if (homeResult != null) {
            adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    RollPagerView header = new RollPagerView(getContext());
                    header.setHintView(new IconHintView(getContext(), R.mipmap.ic_page_indicator_focused, R.mipmap.ic_page_indicator, ScreenUtil.dip2px(getContext(), 10)));
                    header.setHintPadding(0, 0, 0, ScreenUtil.dip2px(getContext(), 8));
                    header.setPlayDelay(2000);
                    header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ScreenUtil.dip2px(getContext(), 200)));
                    header.setAdapter(new BannerAdapter(getContext(), homeResult.ret.list.get(0).childList));
                    return header;
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            List<VideoInfo> videoInfos;
            for (int i = 1; i < homeResult.ret.list.size(); i++) {
                videoInfos = homeResult.ret.list.get(i).childList;
                videoInfos.get(0).setFirst(true);
                videoInfos.get(0).setType(homeResult.ret.list.get(i).title);
//                videoInfos.add(1,new VideoInfo(true));
//                videoInfos.add(1,new VideoInfo(true));
                adapter.addAll(videoInfos);
            }

        }
    }

}
