package com.zcy.ghost.vivideo.ui.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseFragment;
import com.zcy.ghost.vivideo.model.bean.VideoRes;
import com.zcy.ghost.vivideo.presenter.VideoInfoPresenter;
import com.zcy.ghost.vivideo.ui.adapter.RelatedAdapter;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.widget.TextViewExpandableAnimation;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 详情——简介
 * Creator: yxc
 * date: 2016/9/9 9:54
 */
public class VideoIntroFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    TextViewExpandableAnimation tvExpand;
    View headerView;

    RelatedAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_video_intro;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.intro_header, null);
        tvExpand = ButterKnife.findById(headerView, R.id.tv_expand);
        recyclerView.setAdapter(adapter = new RelatedAdapter(getContext()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(3));
        recyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JumpUtil.go2VideoInfoActivity(getContext(), adapter.getItem(position));
                getActivity().finish();
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = VideoInfoPresenter.Refresh_Video_Info)
    public void setData(VideoRes videoInfo) {
        String dir = "导演：" + StringUtils.removeOtherCode(videoInfo.director);
        String act = "主演：" + StringUtils.removeOtherCode(videoInfo.actors);
        String des = dir + "\n" + act + "\n" + "简介：" + StringUtils.removeOtherCode(videoInfo.description);
        tvExpand.setText(des);
        if (videoInfo.list.size() > 1)
            adapter.addAll(videoInfo.list.get(1).childList);
        else
            adapter.addAll(videoInfo.list.get(0).childList);
    }
}
