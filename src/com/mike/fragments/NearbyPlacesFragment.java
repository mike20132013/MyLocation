/**
 * 
 */
package com.mike.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mike.adapters.CustomListAdapter;
import com.mike.appmodel.AppModel;
import com.mike.appmodel.Model;
import com.mike.customlistview.NestedListView;
import com.mike.mylocation.R;
import com.mike.utils.AppUtils;

/**
 * @author mickey20142014
 * 
 */
public class NearbyPlacesFragment extends Fragment {

	private static final String MAIN_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=42.29543187,-71.47470374&"
			+ "radius=1000&"
			+ "types=food&"
			+ "sensor=true&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4";
	private static String FIRST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static String SECOND_URL = "location=";
	private static String THIRD_URL = "&radius=";
	private static String FOURTH_URL = "&types=";
	private static String FIFTH_URL = "&sensor=true";
	private static String SIXTH_URL = "&key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4";

	private static final String gasURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=42.29543187,-71.47470374&"
			+ "radius=1000&"
			+ "types=gas_station&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4&sensor=true";
	private ViewPager placeViewPager;
	private ListView placeListView;
	private ArrayList<AppModel> streetAddressArrayList = new ArrayList<AppModel>();
	ArrayList<Model> infoArrayList;
	private ArrayList<AppModel> imageArrayList = new ArrayList<AppModel>();

	private Context context;
	private AppUtils mAppUtils;
	private static final String status = "OK";
	private Model mAppModel;
	private Spinner mSpinner;
	private ArrayList<String> spinnerArrayList = new ArrayList<String>();

	private ArrayList<String> simpleImageUrls = new ArrayList<String>();
	CustomListAdapter mAdapter;
	
	LoadPlacesTask mLoadPlacesTask;
	
	//ListView mListView;
	NestedListView mListView;
	//ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View nearbyPlacesView = inflater.inflate(R.layout.places_layout,
				container, false);

		return nearbyPlacesView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAppUtils = new AppUtils(context);
		
		for (int i = 0; i < 20; i++) {

			spinnerArrayList.add("Item : " + i);
		}

		placeViewPager = (ViewPager) getActivity().findViewById(
				R.id.places_pager);
		mSpinner = (Spinner) getActivity().findViewById(R.id.spinnerItems);
		mListView = (NestedListView)getActivity().findViewById(R.id.crewList);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Toast.makeText(getActivity(), "Item Clicked : " + position, Toast.LENGTH_SHORT).show();
				
			}
		});
		/*mListView = (ListView) getActivity().findViewById(R.id.places_listview);

		mListView.setOnTouchListener(new ListView.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
	        }
	    });*/
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				spinnerArrayList);
		mSpinner.setAdapter(dataAdapter);
		mLoadPlacesTask = new LoadPlacesTask(getActivity(), gasURL);
		Thread.currentThread().setContextClassLoader(mLoadPlacesTask.getClass().getClassLoader());
		mLoadPlacesTask.execute();
		/*LoadPlacesTask mLoadPlacesTask = new LoadPlacesTask(context.getApplicationContext(), gasURL);
		Thread.currentThread().setContextClassLoader(
				mLoadPlacesTask.getClass().getClassLoader());
		mLoadPlacesTask.execute();*/
		/*Thread.currentThread().setContextClassLoader(new LoadPlacesTask(getActivity(), gasURL).getClass().getClassLoader());
		new LoadPlacesTask(getActivity(), gasURL).execute();*/
		

	}

	private void getGasLocations(String gasURL) {
		mAppModel = new Model();
		infoArrayList = new ArrayList<Model>();
		imageArrayList = new ArrayList<AppModel>();
		simpleImageUrls = new ArrayList<String>();

		try {

			JSONObject mainJSONObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));
			Log.d("Gas Json : ", mainJSONObject.toString());
			String getStatus = mainJSONObject.getString("status");
			Log.d("Status : ", getStatus);

			JSONArray mainJSONArray = mainJSONObject.getJSONArray("results");
			Log.d("JSON Array : ", mainJSONArray.toString());

			if (getStatus.equals(status)) {

				for (int i = 0; i < mainJSONArray.length(); i++) {

					JSONObject insideMainJSONArray = mainJSONArray
							.getJSONObject(i);
					if (insideMainJSONArray != null) {
						String iconUrl = insideMainJSONArray.getString("icon");
						Log.d("ICONs Array : ", iconUrl);
						//imageArrayList.add(new AppModel(iconUrl));
						//mAppModel.setWeb_imageUrls(iconUrl);
						mAppModel.setInfoList(iconUrl);
						Log.d("APP MODEL IMAGE ARRAY : ", mAppModel.getInfoList());
						infoArrayList.add(new Model(mAppModel.getInfoList()));
						Log.d("APP MODEL ARRAY : ", mAppModel.getInfoList());
						//Log.d("ICONs Array from App MODEL : ",mAppModel.getWeb_imageUrls());
						
						
						
					}

				}

			}	
			

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("GAS URL STATUS: ", e.toString());
		}

	}

	private void getFoodLocations() {

	}

	/**
	 * @author mickey20142014
	 * 
	 */
	public class LoadPlacesTask extends AsyncTask<Void, Void, Void> {

		private String foodURL;
		private String gas_stationURL;
		private String atmURL;
		private String cafeURL;
		private AppUtils mAppUtils;

		public LoadPlacesTask(Context context, String gasUrl) {

			super();
			this.gas_stationURL = gasUrl;

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getGasLocations(gas_stationURL);
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);		

			mAdapter = new CustomListAdapter(getActivity().getApplicationContext(), infoArrayList);
			mListView.setAdapter(mAdapter);
		
		}

	}

}
