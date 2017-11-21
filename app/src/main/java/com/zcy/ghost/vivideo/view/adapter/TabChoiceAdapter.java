package com.zcy.ghost.vivideo.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.component.ImageLoader;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.view.activitys.MainActivity;

/**
 * Description: 推荐
 * date: 2016/9/30 11:10
 */
public class TabChoiceAdapter extends RecyclerArrayAdapter<VideoInfo> {

    private static final String TAG = "MasterManChoiceAdapter";

    //只有上下文对象
    public TabChoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChoiceViewHolder(parent);
    }

    class ChoiceViewHolder extends BaseViewHolder<VideoInfo> {
        ImageView imgPicture;
        TextView tv_title;

        public ChoiceViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video);//第二个构造函数
            imgPicture = $(R.id.img_video);
            tv_title = $(R.id.tv_title);
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(VideoInfo data) {
            if (MainActivity.DEBUG) {
                Log.i(TAG, "data:" + data);
                Log.i(TAG, "data.title " + data.title);
            }
            tv_title.setText(data.title);
            ImageLoader.load(getContext(), data.pic, imgPicture);
        }
    }
}
