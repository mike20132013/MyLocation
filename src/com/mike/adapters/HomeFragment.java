/**
 * 
 */
package com.mike.adapters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mike.mylocation.ClearingCodeActivity;
import com.mike.mylocation.R;
import com.mike.utils.AppUtils;
import com.mike.utils.FileCache;

/**
 * @author mickey20142014
 * 
 */
public class HomeFragment extends Fragment implements LocationListener,
		OnMapClickListener, OnMapLongClickListener {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	GoogleMap map;
	List<Address> matches;
	String addressText;
	String addressLine;
	double latitude;
	double longitude;
	AppUtils mAppUtils;
	FileCache mCache;
	Context context;
	private File cacheDir;
	Bitmap bDecode;
	File f;
	File outputFile;
	LocationManager locationManager;
	TextView addressTextV;
	Button attachButton;
	LinearLayout mLayout;
	BitmapFactory.Options options;
	Bitmap b;
	LatLng latLng;
	ProgressBar mProgressBar;
	ProgressDialog mProgressDialog;
	SupportMapFragment fm;

	public static final String MAIN_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=42.29543187,-71.47470374&radius=1000&types=food&sensor=true&key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4";
	public static String FIRST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	public static String SECOND_URL = "location=";
	public static String THIRD_URL = "&radius=";
	public static String FOURTH_URL = "&types=";
	public static String FIFTH_URL = "&sensor=true";
	public static String SIXTH_URL = "&key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4";

	ClearingCodeActivity activity;
	
	private static View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {

			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}

		}
		try {

			view = inflater.inflate(R.layout.activity_main, container, false);

		} catch (InflateException e) {

			e.printStackTrace();

		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		attachButton = (Button) getActivity().findViewById(R.id.attachButton);
		addressTextV = (TextView) getActivity().findViewById(
				R.id.addressTextView);
		mLayout = (LinearLayout) getActivity().findViewById(
				R.id.mainLinearLayout);
		fm = (SupportMapFragment) getFragmentManager().findFragmentById(
				R.id.map);

		mAppUtils = new AppUtils(getActivity());
		if (mAppUtils.servicesOK()) {
			// Toast.makeText(this, "Ready to map!!", Toast.LENGTH_LONG).show();
			// setContentView(R.layout.activity_main);

			if (MAIN_URL != null) {
				new BackTask(MAIN_URL).execute();
			}
			// mLayout = (LinearLayout)
			// getActivity().findViewById(R.id.mapsLayoutID);
			attachButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(getActivity(), "Location Saved",
							Toast.LENGTH_SHORT).show();
					if (locationManager != null) {
						CaptureScreenShot();
					}
				}
			});

			// Getting reference to the SupportMapFragment of activity_main.xml
			/*
			 * SupportMapFragment fm = (SupportMapFragment) getFragmentManager()
			 * .findFragmentById(R.id.map);
			 */
			((SupportMapFragment) (getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.map))).getMap();

			// Getting GoogleMap object from the fragment
			map = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			map.setMyLocationEnabled(true);

			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			locationManager = (LocationManager) getActivity().getSystemService(
					Context.LOCATION_SERVICE);

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

		}

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onLocationChanged(Location location) {

		addressLine = mAppUtils.getLocation(getActivity());

		// Getting latitude of the current location
		latitude = location.getLatitude();

		// Getting longitude of the current location
		longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		map.animateCamera(CameraUpdateFactory.zoomTo(14));

		// Working marker
		map.addMarker(
				new MarkerOptions().position(new LatLng(latitude, longitude))
						.title("My Location" + "\n").snippet(latLng.toString()))
				.showInfoWindow();

		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				marker.showInfoWindow();

				return true;
			}

		});

		Geocoder geoCoder = new Geocoder(getActivity());

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
		String latLong = "Latitude : " + String.valueOf(thisLat) + "\n"
				+ "Longitude : " + String.valueOf(thisLong) + "\n";
		if (addressLine != null) {
			// addressTextV.setText(addressLine);
			addressTextV.setText(latLong + addressLine);
		} else {
			Toast.makeText(getActivity(), "Location Data Null",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void PlaceApi(String URL) {

		try {
			JSONObject mainJsonObject = new JSONObject(mAppUtils.loadJSON(URL));
			String mainJsonString = mainJsonObject.toString();
			Log.d("JSON Object : ", mainJsonString);
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	public class BackTask extends AsyncTask<Void, Integer, Void> {

		String url;

		// ProgressDialog mProgressDialog;

		public BackTask(String URL) {

			this.url = URL;

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {

			PlaceApi(url);
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

			mLayout.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public void onMapLongClick(LatLng point) {

	}

	@Override
	public void onMapClick(LatLng point) {

	}

	public void CaptureScreenShot() {

		SnapshotReadyCallback callback = new SnapshotReadyCallback() {
			Bitmap bitmap;

			@Override
			public void onSnapshotReady(Bitmap snapshot) {

				bitmap = snapshot;

				try {
					if (android.os.Environment.getExternalStorageState()
							.equals(android.os.Environment.MEDIA_MOUNTED)) {
						cacheDir = new File(
								Environment.getExternalStorageDirectory(),
								"LocationApp");
						cacheDir.mkdirs();
						outputFile = new File(cacheDir, "Image");
					}
					FileOutputStream out = new FileOutputStream(outputFile + ""
							+ System.currentTimeMillis() + ".png");
					bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		map.snapshot(callback);

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		locationManager.removeUpdates(this);

	}

	@Override
	public void onPause() {

		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onResume() {

		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				1, this);
	}

	// @Override
	// public void onDestroyView() {
	// super.onDestroyView();
	//
	// Fragment f = getFragmentManager().findFragmentById(R.id.map);
	// if (f != null){
	// getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
	// }
	// }

}
