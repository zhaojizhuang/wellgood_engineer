package com.wellgood.fragment;

import info.hoang8f.android.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.wellgood.engineer.R;

public class Message extends Fragment implements RadioGroup.OnCheckedChangeListener {
	public static String CLASS_NAME = "Public";
	//fragment 适配器
	FragmentAdapter adapter = null;
	//fragment列表
	List<Fragment> fragments = new ArrayList<Fragment>();
	//fragment管理器
	FragmentManager fm;
	//fragment事务管理
	FragmentTransaction ft;
	private ViewPager mViewPager;
	SegmentedGroup segmented;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view==null) {
			view = inflater.inflate(R.layout.fragment_message, container, false);
		}
		
	     ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null) {
	            parent.removeView(view);
	        } 
	        
		initView();
		return view;
	}

	public List<Fragment> initFragments() {
		Log.d(CLASS_NAME, "initfragments");

		fragments = new ArrayList<Fragment>();
		TaskAccept taskAccept = new TaskAccept();
		TaskUnAccept taskUnAccept = new TaskUnAccept();
		fragments.add(taskAccept);
		fragments.add(taskUnAccept);

		// BaseFragment people = new People();
		// fragments.add(people);
		//
		// BaseFragment room = new Room();
		// fragments.add(room);
		return fragments;

	}

	void initView() {
		Log.d(CLASS_NAME, "initviews");
		segmented = (SegmentedGroup) view.findViewById(R.id.segmented3);
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		
		segmented.setOnCheckedChangeListener(this);
		// 默认选中
		segmented.check(R.id.button31);
		
		adapter = new FragmentAdapter(initFragments(),this.getChildFragmentManager());
		
		
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOffscreenPageLimit(2);
	

		
		Log.d(CLASS_NAME, "initviewsend");
	}

	/**
	 * 页面change适配器
	 * 
	 * @author Administrator
	 * 
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {

		case R.id.button31:

			mViewPager.setCurrentItem(0);
			return;
		case R.id.button32:

			mViewPager.setCurrentItem(1);
			return;

		default:
			// Nothing to do
		}
	}

	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				segmented.check(R.id.button31);
				break;
			case 1:
				segmented.check(R.id.button32);
				break;

			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	/**
	 * fragment适配器
	 * 
	 * @author Administrator
	 * 
	 */

	class FragmentAdapter extends FragmentPagerAdapter {
		private List<Fragment> mFragments;

		public FragmentAdapter(List<Fragment> fragments, FragmentManager fm) {
			super(fm);
			mFragments = fragments;
		}

		@Override
		public Fragment getItem(int i) {
			return mFragments.get(i);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

}
