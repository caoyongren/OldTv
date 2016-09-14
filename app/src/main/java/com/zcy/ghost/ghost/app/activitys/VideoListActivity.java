package com.zcy.ghost.ghost.app.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.VideoListAdapter;
import com.zcy.ghost.ghost.app.BaseActivity;
import com.zcy.ghost.ghost.app.SwipeBackActivity;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.bean.VideoResult;
import com.zcy.ghost.ghost.bean.VideoType;
import com.zcy.ghost.ghost.net.Network;
import com.zcy.ghost.ghost.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoListActivity extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    final String TAG = VideoListActivity.class.getSimpleName();
    Context mContext;
    Unbinder unbinder;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    List<VideoType> videos;
    VideoListAdapter adapter;
    String title = "";
    String catalogId = "";
    int page = 1;
    Handler handler = new Handler();
    VideoInfo videoInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        unbinder = ButterKnife.bind(this);

        getIntentData();
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        mContext = this;
        recyclerView.setAdapterWithProgress(adapter = new VideoListAdapter(this));
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(this, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        onRefresh();
    }

    private void getIntentData() {
        catalogId = getIntent().getStringExtra("catalogId");
        title = getIntent().getStringExtra("title");
        titleName.setText(title);
    }

    @Override
    protected void initEvent() {
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switchData(adapter.getItem(position));
                Intent intent = new Intent(mContext, VideoInfoActivity.class);
                intent.putExtra("videoInfo", videoInfo);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.rl_back)
    public void back() {
        finish();
    }

    Observer<VideoResult> observer = new Observer<VideoResult>() {
        @Override
        public void onCompleted() {
            adapter.pauseMore();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(mContext, R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(VideoResult result) {
            if (result != null) {
                videos = result.ret.list;
                if (page == 1)
                    adapter.clear();
                adapter.addAll(videos);
            }
        }
    };

    private void getVideoList(String catalogID, int page) {
        subscription = Network.getVideoApi(this)
                .getVideoList(catalogID, page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onRefresh() {
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                getVideoList(catalogId, page);
            }
        }, 1000);
    }

    private void switchData(VideoType videoType) {
        if (videoInfo == null)
            videoInfo = new VideoInfo();
        videoInfo.title = videoType.title;
        videoInfo.dataId = videoType.dataId;
        videoInfo.pic = videoType.pic;
        videoInfo.airTime = videoType.airTime;
        videoInfo.score = videoType.score;
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                page++;
                getVideoList(catalogId, page);
            }
        }, 1000);
    }
}
