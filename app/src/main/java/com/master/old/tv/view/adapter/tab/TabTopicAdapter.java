package com.master.old.tv.view.adapter.tab;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.master.old.tv.R;
import com.master.old.tv.utils.ImageLoaderUtil;
import com.master.old.tv.model.bean.VideoInfo;

/**
 * Description: 专题
 * Creator: yxc
 * date: 2016/9/30 11:07
 */
public class TabTopicAdapter extends RecyclerArrayAdapter<VideoInfo> {

    public TabTopicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassificationViewHolder(parent);
    }

    class ClassificationViewHolder extends BaseViewHolder<VideoInfo> {
        ImageView imgPicture;
        TextView tv_title;

        public ClassificationViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_tab_topic);
            imgPicture = $(R.id.item_topic_img_video);
            tv_title = $(R.id.item_topic_tv_title);
            imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void setData(VideoInfo data) {
            tv_title.setText(data.title);
            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int width = dm.widthPixels / 2;//宽度为屏幕宽度一半
//        int height = data.getHeight()*width/data.getWidth();//计算View的高度

            params.height = (int) (width / 1.8);
            imgPicture.setLayoutParams(params);
            ImageLoaderUtil.load(getContext(), data.pic, imgPicture);
        }
    }

}
