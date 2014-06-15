package com.mike.mylocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mike.utils.AppUtils;
import com.mike.utils.FileCache;
import com.mike.utils.Utils;

public class MainActivity extends FragmentActivity implements LocationListener,
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		mAppUtils = new AppUtils(this);
		if (mAppUtils.servicesOK()) {
			//Toast.makeText(this, "Ready to map!!", Toast.LENGTH_LONG).show();
			setContentView(R.layout.activity_main);

			attachButton = (Button) findViewById(R.id.attachButton);
			addressTextV = (TextView) findViewById(R.id.addressTextView);
			attachButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(context, "Location Saved",
							Toast.LENGTH_SHORT).show();
					if (locationManager != null) {
						CaptureScreenShot();
						// stopGps();
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

	@Override
	public void onLocationChanged(Location location) {

		addressLine = mAppUtils.getLocation(context, "");

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

		map.addMarker(
				new MarkerOptions().position(new LatLng(latitude, longitude))
						.title("My Location" + "\n")).showInfoWindow();

		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				marker.showInfoWindow();

				return false;
			}

		});
		//For Custom Info
		/*map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View myContentsView = getLayoutInflater().inflate(
						R.layout.custom_info, null);
				TextView tvTitle = ((TextView) myContentsView
						.findViewById(R.id.headText));
				tvTitle.setText(marker.getTitle());
				TextView tvSnippet = ((TextView) myContentsView
						.findViewById(R.id.contentText));
				tvSnippet.setText(marker.getSnippet());
				return myContentsView;

			}
		});*/

		Geocoder geoCoder = new Geocoder(this);

		try {
			matches = geoCoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
		addressText = String.format("%s, %s, %s", bestMatch
				.getMaxAddressLineIndex() > 0 ? bestMatch.getAddressLine(0)
				: "", bestMatch.getLocality(), bestMatch.getCountryName());

		if (addressLine != null) {
			addressTextV.setText(addressLine);
		} else {
			Toast.makeText(context, "Location Data Null", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean servicesOK() {

		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {

			return true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();

		} else {

			Toast.makeText(this, "Cant connect!!", Toast.LENGTH_SHORT).show();

		}
		return false;
	}

	public Bitmap getBitmapShot(String url) {

		mCache = new FileCache(context);
		f = mCache.getFile(url);
		bDecode = decodeFile(f);
		if (bDecode != null)
			return bDecode;
		SnapshotReadyCallback callback = new SnapshotReadyCallback() {

			@Override
			public void onSnapshotReady(Bitmap snapshot) {
				// TODO Auto-generated method stub
				bDecode = snapshot;
				try {
					FileInputStream fin = new FileInputStream(f);
					FileOutputStream fout = new FileOutputStream(f + ".png");
					Utils.CopyStream(fin, fout);
					fout.close();
					bDecode = decodeFile(f);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		map.snapshot(callback);
		return bDecode;
	}

	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {

		}
		return null;
	}

	public void CaptureScreenShot() {

		mCache = new FileCache(context);

		SnapshotReadyCallback callback = new SnapshotReadyCallback() {
			Bitmap bitmap;

			@Override
			public void onSnapshotReady(Bitmap snapshot) {
				// TODO Auto-generated method stub
				bitmap = snapshot;
				// bitmap = decodeFile(cacheDir);
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
					// bitmap = decodeFile(outputFile);
					bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		map.snapshot(callback);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {

		super.onPause();
		locationManager.removeUpdates(this);

	}

	void stopGps() {

		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
		locationManager = null;
		Toast.makeText(context, "Location Removed Temperorily",
				Toast.LENGTH_SHORT).show();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				1, this);
	}

}
