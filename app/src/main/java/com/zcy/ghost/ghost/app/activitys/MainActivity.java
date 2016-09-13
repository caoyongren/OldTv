package com.zcy.ghost.ghost.app.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zcy.ghost.ghost.R;
import com.zcy.ghost.ghost.adapter.ContentPagerAdapter;
import com.zcy.ghost.ghost.app.BaseActivity;
import com.zcy.ghost.ghost.app.fragments.Fragment1;
import com.zcy.ghost.ghost.app.fragments.Fragment2;
import com.zcy.ghost.ghost.app.fragments.Fragment3;
import com.zcy.ghost.ghost.app.fragments.Fragment4;
import com.zcy.ghost.ghost.views.UnScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.tab_rg_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;

    ContentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTranslucentStatus(true);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(true);
        mPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
    }

    @Override
    protected void initEvent() {
        tabRgMenu.setOnCheckedChangeListener(this);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) tabRgMenu.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.tab_rb_1:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.tab_rb_2:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.tab_rb_3:
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.tab_rb_4:
                vpContent.setCurrentItem(3, false);
                break;
        }
    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new Fragment1();
        Fragment fragment2 = new Fragment2();
        Fragment fragment3 = new Fragment3();
        Fragment fragment4 = new Fragment4();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        return fragments;
    }
}