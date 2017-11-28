package com.master.old.tv.view.adapter.drawer;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.master.old.tv.R;
import com.master.old.tv.model.bean.GankItemBean;

/**
 * Description: 福利
 * Creator: yxc
 * date: 2016/10/24 14:16
 */
public class WelfareAdapter extends RecyclerArrayAdapter<GankItemBean> {

    public WelfareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareViewHolder(parent);
    }

    class WelfareViewHolder extends BaseViewHolder<GankItemBean> {
        ImageView imgPicture;

        public WelfareViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView) itemView;
            imgPicture.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(GankItemBean data) {
            imgPicture.setBackgroundResource(R.mipmap.default_200);
            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();
            params.height = data.getHeight();
            imgPicture.setLayoutParams(params);
            Glide.with(getContext())
                    .load(data.getUrl())
                    .into(imgPicture);
        }
    }
}
