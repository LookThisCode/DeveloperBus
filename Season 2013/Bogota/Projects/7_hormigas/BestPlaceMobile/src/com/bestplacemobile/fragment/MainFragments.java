package com.bestplacemobile.fragment;

import com.bestplacemobile.adapters.CustomPagerAdapter;
import com.bestplacemobile.R;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
public class MainFragments extends Fragment implements
		ViewPager.OnPageChangeListener, ActionBar.TabListener {

	private ViewPager viewPager;
	private CustomPagerAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new CustomPagerAdapter(getActivity()
				.getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);

		/*ActionBar bar = getActivity().getActionBar();
		bar.removeAllTabs();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.addTab(bar.newTab().setText(R.string.tab_registro_distribuidor)
				.setTabListener(this));
		bar.addTab(bar.newTab().setText(R.string.tab_registro_productor)
				.setTabListener(this));*/
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		getActivity().getActionBar().setSelectedNavigationItem(arg0);
	}

}
