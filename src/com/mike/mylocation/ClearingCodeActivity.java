/**
 * 
 */
package com.mike.mylocation;

import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mike.customviews.CustomImageView;
import com.mike.utils.AppUtils;

/**
 * @author mickey20142014
 * 
 */
public class ClearingCodeActivity extends FragmentActivity implements
		LocationListener, OnMapClickListener, OnMapLongClickListener {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	LocationManager locationManager;
	// Views
	GoogleMap map;
	TextView addressTextV;
	//Button attachButton;
	CustomImageView attachButton;

	// Utils
	AppUtils mAppUtils;

	Context context;
	List<Address> matches;
	String addressText;
	String addressLine;
	double latitude;
	double longitude;
	String LatAndLong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		context = this;

		mAppUtils = new AppUtils(this);
		if (mAppUtils.servicesOK()) {
			// Toast.makeText(this, "Ready to map!!", Toast.LENGTH_LONG).show();
			setContentView(R.layout.activity_main);
			
			//attachButton = (Button) findViewById(R.id.attachButton);
			addressTextV = (TextView) findViewById(R.id.addressTextView);
			attachButton = (CustomImageView)findViewById(R.id.attachButton);
				
			addressTextV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					addressTextV.setBackgroundColor(Color.parseColor("#1C8B98"));
					
				}
			});
			
			/*addressTextV.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					if(event.getAction()==MotionEvent.ACTION_UP){
						
						addressTextV.setAlpha(15);
						return true;
					}
					
					return false;
				}
			});*/
			
			attachButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(context, "Location Saved",
							Toast.LENGTH_SHORT).show();
					if (locationManager != null) {
						// CaptureScreenShot();
					}
				}
			});
				
			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting GoogleMap object from the fragment
			map = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			map.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(provider, 20000, 0, this);

		} else {
			setContentView(R.layout.activity_main);

		}
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {

		addressLine = mAppUtils.getLocation(context);
		// Getting latitude of the current location
		latitude = location.getLatitude();

		// Getting longitude of the current location
		longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		map.animateCamera(CameraUpdateFactory.zoomTo(12));

		
		if(latLng!=null){
				
			// Working marker
			map.addMarker(
					new MarkerOptions().position(new LatLng(latitude, longitude))
					.title("My Location" + "\n").snippet(latLng.toString()))
					.showInfoWindow();
			
			map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					
					marker.showInfoWindow();
					
					return false;
				}
				
			});
		}

		Geocoder geoCoder = new Geocoder(this);

		try {
			matches = geoCoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e) {

			e.printStackTrace();
		}
		Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
		addressText = String.format("%s, %s, %s", bestMatch
				.getMaxAddressLineIndex() > 0 ? bestMatch.getAddressLine(0)
				: "", bestMatch.getLocality(), bestMatch.getCountryName());

		Double thisLat = latitude;
		Double thisLong = longitude;
		
		
		if(thisLat==null){
			
			LatAndLong = "Latitude : " + "-NA-" + "\n"
					+ "Longitude : " + String.valueOf(thisLong) + "\n";
			
		}else if(thisLong==null){
			
			LatAndLong = "Latitude : " + String.valueOf(thisLat) + "\n"
					+ "Longitude : " + "-NA-" + "\n";
			
		}else{
			
			LatAndLong = "Latitude : " + String.valueOf(thisLat) + "\n"
					+ "Longitude : " + String.valueOf(thisLong) + "\n";
		}
		
		if (addressLine != null) {
			// addressTextV.setText(addressLine);
			addressTextV.setText(LatAndLong + addressLine);
		} else {
			Toast.makeText(context, "Location Data Null", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	
	public boolean servicesOK() {

		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (isAvailable == ConnectionResult.SUCCESS) {
								
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
			.show();
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
					.show();

		}
		return false;

	}
	
	
	

	@Override
	protected void onPause() {

		super.onPause();
		locationManager.removeUpdates(this);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {

		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				1, this);
	}

}
