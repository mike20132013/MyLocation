/**
 * 
 */
package com.mike.adapters;

import com.mike.fragments.HistoryFragment;
import com.mike.fragments.HomeFragment;
import com.mike.fragments.NearbyPlacesFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author mickey20142014
 * 
 */
public class AllPagesAdapter extends FragmentStatePagerAdapter{

	public AllPagesAdapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {

		case 0: 
			
			return new HomeFragment(); 
			
		
		case 1:

			return new HistoryFragment();
			
		}
		return null;

	}

	@Override
	public int getCount() {
		return 2;
	}

}
