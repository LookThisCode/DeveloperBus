package com.bestplacemobile.adapters;

import com.bestplacemobile.fragment.ConfiguracionFragment;
import com.bestplacemobile.fragment.RegistroFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

	private Fragment[] fragments;

	public CustomPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new Fragment[] { new ConfiguracionFragment(),
				new RegistroFragment() };
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments[arg0];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

}
