package com.zcy.ghost.vivideo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;

/**
 * Description: 推荐
 * Creator: yxc
 * date: 2016/9/30 11:10
 */
public class RecommendAdapter extends RecyclerArrayAdapter<VideoInfo> {

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(parent);
    }

    class RecommendViewHolder extends BaseViewHolder<VideoInfo> {
        ImageView imgPicture;
        TextView tv_title;

        public RecommendViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video);
            imgPicture = $(R.id.img_video);
            tv_title = $(R.id.tv_title);
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(VideoInfo data) {
            tv_title.setText(data.title);
            ImageLoader.load(getContext(), data.pic, imgPicture);
        }
    }

}
