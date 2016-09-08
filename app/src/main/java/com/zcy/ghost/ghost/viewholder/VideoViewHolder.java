package com.zcy.ghost.ghost.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.bean.VideoInfo;

/**
 * Created by zhuchenxi on 16/6/2.
 */

public class VideoViewHolder extends BaseViewHolder<VideoInfo> {
    ImageView imgPicture;
    TextView tv_title;

    public VideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_video);
        imgPicture = $(R.id.img_video);
        tv_title = $(R.id.tv_title);
        imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void setData(VideoInfo data) {
        tv_title.setText(data.title);
        Glide.with(getContext())
                .load(data.pic)
                .into(imgPicture);
    }
}
