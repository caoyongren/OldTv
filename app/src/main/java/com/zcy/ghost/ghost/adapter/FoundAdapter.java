package com.zcy.ghost.ghost.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.ghost.bean.VideoInfo;
import com.zcy.ghost.ghost.viewholder.FoundViewHolder;

public class FoundAdapter extends RecyclerArrayAdapter<VideoInfo> {

    public FoundAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoundViewHolder(parent);
    }

}
