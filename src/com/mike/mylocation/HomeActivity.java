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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.mike.adapters.AllPagesAdapter;

/**
 * @author mickey20142014
 * 
 */
@SuppressLint("NewApi")
public class HomeActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public ViewPager viewPager;
	private AllPagesAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Home", "History" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

		// Add the tabs here
		/*for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}*/
		actionBar.addTab(actionBar.newTab().setText("Maps Home")
				
				.setTabListener(new TabListener() {
					
					@Override
					public void onTabUnselected(Tab tab, FragmentTransaction ft) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onTabSelected(Tab tab, FragmentTransaction ft) {
						viewPager.setCurrentItem(tab.getPosition());
						
					}
					
					@Override
					public void onTabReselected(Tab tab, FragmentTransaction ft) {
						viewPager.setCurrentItem(tab.getPosition());
						
					}
				}));
		
		actionBar.addTab(actionBar.newTab().setText("Location History")
				.setTabListener(new TabListener() {
					
					@Override
					public void onTabUnselected(Tab tab, FragmentTransaction ft) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onTabSelected(Tab tab, FragmentTransaction ft) {
						viewPager.setCurrentItem(tab.getPosition());
						
					}
					
					@Override
					public void onTabReselected(Tab tab, FragmentTransaction ft) {
						viewPager.setCurrentItem(tab.getPosition());
						
					}
				}));

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
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
