/**
 * 
 */
package com.mike.mylocation;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.mike.adapters.AllPagesAdapter;

/**
 * @author mickey20142014
 * 
 */
@SuppressLint("NewApi")
public class TabActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private AllPagesAdapter mPagesAdapter;
	private String[] tabs = { "Map Home", "Nearby Places" };

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.tab_activity_layout);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mActionBar = getActionBar();
		mPagesAdapter = new AllPagesAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagesAdapter);
		mActionBar.setHomeButtonEnabled(false);
		mActionBar.setSubtitle("Save your location now");
		mActionBar.setIcon(R.drawable.cameraicon);
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#CD6600")));
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {

			mActionBar.addTab(mActionBar.newTab().setText(tab_name)
					.setTabListener(this));

		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				mActionBar.setSelectedNavigationItem(position);

			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}
