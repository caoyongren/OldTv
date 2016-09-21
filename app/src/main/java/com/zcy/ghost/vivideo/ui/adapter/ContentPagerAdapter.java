package com.zcy.ghost.vivideo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description: ContentPagerAdapter
 * Creator: yxc
 * date: 2016/9/7 11:47
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

}
