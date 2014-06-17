/**
 * 
 */
package com.mike.mylocation;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.google.android.gms.maps.SupportMapFragment;
import com.mike.adapters.AllPagesAdapter;
import com.mike.adapters.HistoryFragment;
import com.mike.adapters.HomeFragment;
import com.mike.adapters.NearbyPlaces;
import com.mike.listeners.FragmentTabListener;

/**
 * @author mickey20142014
 * 
 */
@SuppressLint("NewApi")
public class HomeActivity extends FragmentActivity implements
		ActionBar.TabListener, OnPageChangeListener {

	public ViewPager viewPager;
	private AllPagesAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);

		// Initializing all stuff
		viewPager = (ViewPager) findViewById(R.id.pager);

		actionBar = getActionBar();
		mAdapter = new AllPagesAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setSubtitle("Save you location anytime and at anyplace");

		actionBar.addTab(actionBar.newTab().setText("Maps Home")
				.setTabListener(this));

		actionBar.addTab(actionBar.newTab().setText("Nearby Places")
				.setTabListener(this));

		actionBar.addTab(actionBar.newTab().setText("Location History")
				.setTabListener(this));

		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {

						// on Page change, that particular page should be
						// selected
						actionBar.setSelectedNavigationItem(position);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int position) {

					}

				});

	}

	/*
	 * Tab Listener
	 */

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	/*
	 * Scroll Listener
	 */
	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Fragment f = getSupportFragmentManager().findFragmentById(R.id.map);
//	    if (f != null){ 
//	        getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
//	    }
	}

	@Override
	public void onPageSelected(int position) {
		actionBar.setSelectedNavigationItem(position);
	}

}
