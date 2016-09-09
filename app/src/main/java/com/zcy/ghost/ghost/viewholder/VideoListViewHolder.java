package com.zcy.ghost.ghost.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.bean.VideoType;

/**
 * Created by zhuchenxi on 16/6/2.
 */

public class VideoListViewHolder extends BaseViewHolder<VideoType> {
    ImageView imgPicture;
    TextView tv_title;

    public VideoListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_related);
        imgPicture = $(R.id.img_video);
        tv_title = $(R.id.tv_title);
        imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(VideoType data) {
        tv_title.setText(data.title);
        ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 3;//宽度为屏幕宽度一半
//        int height = data.getHeight()*width/data.getWidth();//计算View的高度

        params.height = (int) (width * 1.1);
        imgPicture.setLayoutParams(params);
        Glide.with(getContext())
                .load(data.pic)
                .into(imgPicture);
    }
}
