package com.zcy.ghost.vivideo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.ui.adapter.viewholder.VideoTitleViewHolder;
import com.zcy.ghost.vivideo.ui.adapter.viewholder.VideoViewHolder;

public class VideoAdapter extends RecyclerArrayAdapter<VideoInfo> {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_INFO = 2;

    public VideoAdapter(Context context) {
        super(context);
    }


    @Override
    public int getViewType(int position) {
        if (getItem(position).isFirst())
            return TYPE_TITLE;
        else
            return TYPE_INFO;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new VideoTitleViewHolder(parent);
            case TYPE_INFO:
            default:
                return new VideoViewHolder(parent);
        }
    }

}
