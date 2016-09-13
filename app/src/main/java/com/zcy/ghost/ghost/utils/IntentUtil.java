package com.zcy.ghost.ghost.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.app.activitys.MVPVideoListActivity;

/******************************************
 * 类名称：IntentUtil
 * 类描述：
 *
 * @version: 2.3.1
 * @author: caopeng
 * @time: 2016/9/13 17:20
 ******************************************/
public class IntentUtil {

    public static void jumpVideoListActivity(Context context, String url, String title, Activity activity) {
        Intent intent = new Intent(context, MVPVideoListActivity.class);
        intent.putExtra("catalogId", StringUtils.getCatalogId(url));
        intent.putExtra("title", title);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }
}