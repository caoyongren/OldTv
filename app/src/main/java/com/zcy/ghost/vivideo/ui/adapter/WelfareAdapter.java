package com.zcy.ghost.vivideo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zcy.ghost.vivideo.model.bean.GankItemBean;
import com.zcy.ghost.vivideo.ui.adapter.viewholder.WelfareViewHolder;

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

}
