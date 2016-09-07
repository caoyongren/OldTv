package com.zcy.ghost.ghost.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.bean.VideoInfo;

public class VideoTitleViewHolder extends BaseViewHolder<VideoInfo> {
    TextView tv_type;
    LinearLayout ll_type;

    public VideoTitleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_title);
        tv_type = $(R.id.tv_type);
        ll_type = $(R.id.ll_type);
    }

    @Override
    public void setData(VideoInfo data) {
        if (!TextUtils.isEmpty(data.getType())) {
            ll_type.setVisibility(View.VISIBLE);
            tv_type.setText(data.getType());
        } else {
            ll_type.setVisibility(View.GONE);
        }

    }
}
