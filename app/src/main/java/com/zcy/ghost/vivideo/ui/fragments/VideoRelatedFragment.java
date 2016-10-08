package com.zcy.ghost.vivideo.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.ui.adapter.RelatedAdapter;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.ui.activitys.VideoInfoActivity;
import com.zcy.ghost.vivideo.presenter.VideoInfoPresenter;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;

/**
 * Description: 详情--推荐
 * Creator: yxc
 * date: 2016/9/9 9:54
 */
public class VideoRelatedFragment extends BaseFragment {
    final String TAG = VideoRelatedFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    RelatedAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video_rela;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        recyclerView.setAdapter(adapter = new RelatedAdapter(getContext()));
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
                JumpUtil.go2VideoInfoActivity(getContext(), adapter.getItem(position));
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Subscriber(tag = VideoInfoPresenter.Refresh_Video_Info)
    public void setData(VideoRes videoInfo) {
        if (videoInfo.list.size() > 1)
            adapter.addAll(videoInfo.list.get(1).childList);
        else
            adapter.addAll(videoInfo.list.get(0).childList);
    }

}
