/**
 * 
 */
package com.mike.adapters;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mike.appmodel.AppModel;
import com.mike.mylocation.R;

/**
 * @author mickey20142014
 *
 */
public class NearbyPlaces extends Fragment {

	private ViewPager placeViewPager;
	private ListView placeListView;
	ArrayList<AppModel> streetAddressArrayList = new ArrayList<AppModel>();
	ArrayList<AppModel> zipCodeArrayList = new ArrayList<AppModel>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		
		View nearbyPlacesView = inflater.inflate(R.layout.places_layout, container,false);
		
	    return nearbyPlacesView;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		
	}
	
}
