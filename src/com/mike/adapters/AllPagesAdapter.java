/**
 * 
 */
package com.mike.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author mickey20142014
 * 
 */
public class AllPagesAdapter extends FragmentPagerAdapter {

	public AllPagesAdapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:

			return new HomeFragment();

		case 1:

			return new NearbyPlaces();

		case 2:

			return new HistoryFragment();
			
		}
		return null;

	}

	@Override
	public int getCount() {
		return 3;
	}

}
