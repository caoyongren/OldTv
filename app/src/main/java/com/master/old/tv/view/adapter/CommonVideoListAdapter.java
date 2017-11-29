package com.master.old.tv.view.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.master.old.tv.R;
import com.master.old.tv.utils.ImageLoaderUtil;
import com.master.old.tv.model.bean.VideoType;

/**
 * Description: 影片列表
 * Creator: yxc
 * date: 2016/9/30 11:10
 *
 * 该是适配器应用在: CollectionListActivity / videoListActivity / SearchListActivity / ＨistoryActivity 共用
 */
public class CommonVideoListAdapter extends RecyclerArrayAdapter<VideoType> {

    public CommonVideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(parent);
    }

    class VideoListViewHolder extends BaseViewHolder<VideoType> {
        ImageView imgPicture;
        TextView tv_title;

        public VideoListViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_list_videos);
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
            ImageLoaderUtil.load(getContext(), data.pic, imgPicture);
        }
    }

}
