package com.zcy.ghost.ghost.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.activitys.VideoInfoActivity;
import com.zcy.ghost.ghost.bean.VideoInfo;

import java.util.List;

public class BannerAdapter extends StaticPagerAdapter {

    private Context ctx;
    private List<VideoInfo> list;

    public BannerAdapter(Context ctx, List<VideoInfo> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //加载图片
        Glide.with(ctx)
                .load(list.get(position).pic)
                .placeholder(R.mipmap.default_320)
                .into(imageView);
        //点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, VideoInfoActivity.class);
                intent.putExtra("dataId", list.get(position).dataId);
                ctx.startActivity(intent);
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}