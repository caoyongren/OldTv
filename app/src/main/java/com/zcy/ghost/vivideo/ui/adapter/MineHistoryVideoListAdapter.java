package com.zcy.ghost.vivideo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.ui.adapter.viewholder.MineHistoryVideoListViewHolder;
import com.zcy.ghost.vivideo.ui.adapter.viewholder.VideoListViewHolder;

public class MineHistoryVideoListAdapter extends RecyclerArrayAdapter<VideoType> {

    public MineHistoryVideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineHistoryVideoListViewHolder(parent);
    }

}
