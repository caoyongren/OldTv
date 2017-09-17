package com.zcy.ghost.vivideo.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.VideoType;

public class MineHistoryVideoListAdapter extends RecyclerArrayAdapter<VideoType> {

    public MineHistoryVideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineHistoryVideoListViewHolder(parent);
    }

    class MineHistoryVideoListViewHolder extends BaseViewHolder<VideoType> {
        ImageView imgPicture;
        TextView tv_title;

        public MineHistoryVideoListViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_mine_history);
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

            params.height = width;
            imgPicture.setLayoutParams(params);
            ImageLoader.load(getContext(), data.pic, imgPicture);
        }
    }
}
