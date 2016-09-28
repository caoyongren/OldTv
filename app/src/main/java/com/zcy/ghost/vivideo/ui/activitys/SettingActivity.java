package com.zcy.ghost.vivideo.ui.activitys;

import android.os.Bundle;
import android.widget.ImageView;

import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.BaseActivity;
import com.zcy.ghost.vivideo.component.ImageLoader;

public class SettingActivity extends BaseActivity {

    ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        iv_img = (ImageView) findViewById(R.id.iv_img);
        ImageLoader.load(this, "http://cache.attach.yuanobao.com/sys/unni/def/4.jpg", iv_img);

        String result = "{\n" +
                "msg: \"成功\",\n" +
                "ret: {\n" +
                "list: [\n" +
                "]\n" +
                "},\n" +
                "code: \"200\"\n" +
                "}";

//        VideoResult videoResult = JSON.parseObject(result,VideoResult.class);
//        Log.e("sdfasdf",videoResult.code);

    }

}
