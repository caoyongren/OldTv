package com.zcy.ghost.ghost.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.RelatedAdapter;
import com.zcy.ghost.ghost.app.BaseFragment;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.bean.VideoRes;
import com.zcy.ghost.ghost.utils.ScreenUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: VideoIntroFragment
 * Creator: yxc
 * date: 2016/9/9 9:54
 */
public class VideoRelatedFragment extends BaseFragment {
    final String TAG = VideoRelatedFragment.class.getSimpleName();

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    RelatedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_rela, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        recyclerView.setAdapterWithProgress(adapter = new RelatedAdapter(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), VideoInfoActivity.class);
                intent.putExtra("videoInfo", adapter.getItem(position));
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = "refresh_video_info")
    public void setData(VideoRes videoInfo) {
        if (videoInfo.list.size() > 1)
            adapter.addAll(videoInfo.list.get(1).childList);
        else
            adapter.addAll(videoInfo.list.get(0).childList);
    }

}
