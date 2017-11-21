package com.zcy.ghost.vivideo.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description: ContentViewPagerAdapter
 * Creator: yxc
 * date: 2016/9/7 11:47
 */
public class ContentViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragments;
    //参数: getSupportFragmentManager() / fragments.
	public ContentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.mFragments = fragments;
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}
}
