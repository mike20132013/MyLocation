/**
 * 
 */
package com.mike.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.mike.adapters.ListAdapter;
import com.mike.adapters.ViewAdapter;
import com.mike.appmodel.Model;
import com.mike.customlistview.NestedListView;
import com.mike.mylocation.R;
import com.mike.utils.AppUtils;

/**
 * @author mickey20142014
 * 
 */
public class HistoryFragment extends Fragment {

	private static final String gasURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=42.29543187,-71.47470374&"
			+ "radius=10000&"
			+ "types=gas_station&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4&sensor=true";

	private Spinner mSpinner;

	private NestedListView mListView2;

	private ViewPager mViewPager;

	private AppUtils mAppUtils;

	private String delimiter = ",";
	
	private String delimiter2 = "[";
	
	private String delimiter3 = "]";
	
	private Model mAppModel;

	private ListAdapter mAdapter2;

	private ViewAdapter mViewAdapter;

	private ArrayList<Model> infoArrayList = new ArrayList<Model>();

	private ArrayList<Model> placeArrayList = new ArrayList<Model>();

	private ArrayList<Model> addressArrayList = new ArrayList<Model>();

	private ArrayList<Model> latitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> longitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> placetypeArrayList = new ArrayList<Model>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View historyView = inflater.inflate(R.layout.history_layout, container,
				false);

		return historyView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAppModel = new Model();

		new BackTask(gasURL).execute();

		mListView2 = (NestedListView) getActivity().findViewById(
				R.id.historyListView);

		mViewPager = (ViewPager) getActivity().findViewById(R.id.places_pager);

	}

	public void RunThis() {

		for (int i = 0; i < 20; i++) {

			String s = "Some Item : " + i;
			mAppModel.setSomeItem(s);
			infoArrayList.add(new Model(mAppModel.getSomeItem()));

		}

	}

	public static void replaceAll(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
	
	public void getGasLocations(String gasURL) {

		String iconUrl = null;
		String placeName = null;
		String placeAddress = null;
		String value= null;
		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJSONObject = new JSONObject(mAppUtils.loadJSON(gasURL));
			JSONArray mainJSONArray = mainJSONObject.getJSONArray("results");
			
			for(int ij = 0 ; ij<mainJSONArray.length();ij++){
				
				JSONObject obj = mainJSONArray.getJSONObject(ij);

				value = obj.getString("types");
				
				/*StringBuilder sb = new StringBuilder(value);
				
				replaceAll(sb, "[", "]");*/
				
				Log.d("PLACE TYPES ARRAY VALUE : ", value);
							
				mAppModel.setPlaceType(value);
		        
				placetypeArrayList.add(new Model(mAppModel.getPlaceType()));
				
				
			}
			/*
			 for(int j=0;j<mainJSONArray.length();j++)
			 {
				 	
				 	JSONObject jsonObject = mainJSONArray.getJSONObject(j);
				 	
				 	JSONArray jsonArray = jsonObject.getJSONArray("types");
				 	
				 	//value = jsonArray.getString(jsonArray.length());
				 	
				 	//Log.d("PLACE HASH STRING VALS FIRST: ", value);
				 	 
				 	for(int ju=0;ju<jsonArray.length();ju++)
				 	 {
				 	     value =  (String) jsonArray.get(ju); 
				 	     
				 	     //value.split(("(?!^)"));				 	    
				 	     Log.d("PLACE HASH STRING VALS : ", value);
				 	     
				 	     mAppModel.setPlaceType(value);
				        
						 placetypeArrayList.add(new Model(mAppModel.getPlaceType()));
				 	     
				 	 } 
				 
			        
			 }*/ 
			

			for (int i = 0; i < mainJSONArray.length(); i++) {

				JSONObject insideMainJSONArray = mainJSONArray.getJSONObject(i);
				if (insideMainJSONArray != null) {
					iconUrl = insideMainJSONArray.getString("icon");
					mAppModel.setSomeItem(iconUrl);

					placeName = insideMainJSONArray.getString("name");
					mAppModel.setPlaceName(placeName);

					placeAddress = insideMainJSONArray.getString("vicinity");
					mAppModel.setAddressList(placeAddress);

					infoArrayList.add(new Model(mAppModel.getSomeItem()));
					placeArrayList.add(new Model(mAppModel.getPlaceName()));
					addressArrayList.add(new Model(mAppModel.getAddressList()));

					for (int j = 0; j < mainJSONArray.length(); j++) {

						JSONObject geometryJsonObject = mainJSONArray
								.getJSONObject(j);

						if (geometryJsonObject != null) {

							JSONObject geometry = geometryJsonObject
									.getJSONObject("geometry");

							JSONObject locationJsonObject = geometry
									.getJSONObject("location");

							String latitudeString = locationJsonObject
									.getString("lat");

							String longitudeString = locationJsonObject
									.getString("lng");

							mAppModel.setLatitude(latitudeString);
							mAppModel.setLongitude(longitudeString);

							latitudeArrayList.add(new Model(mAppModel
									.getLatitude()));
							longitudeArrayList.add(new Model(mAppModel
									.getLongitude()));

						}

					}

				}
//				Log.d("ICONs Array : ", iconUrl);
//				Log.d("HISTORY MODEL IMAGE ARRAY : ", mAppModel.getSomeItem());
//				Log.d("HISTORY MODEL ARRAY : ", mAppModel.getSomeItem());
//				Log.d("HISTORY PLACE ARRAY : ", mAppModel.getPlaceName());
//				Log.d("HISTORY ADDRESS ARRAY : ", mAppModel.getAddressList());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public class BackTask extends AsyncTask<Void, Void, Void> {

		String URL;

		public BackTask(String URL) {
			super();
			this.URL = URL;

		}

		@Override
		protected Void doInBackground(Void... params) {

			getGasLocations(URL);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAdapter2 = new ListAdapter(getActivity().getApplicationContext(),
					infoArrayList, placeArrayList, addressArrayList);

			mViewAdapter = new ViewAdapter(getActivity()
					.getApplicationContext(), infoArrayList, addressArrayList,
					placeArrayList, latitudeArrayList, longitudeArrayList,
					placetypeArrayList);

			mListView2.setAdapter(mAdapter2);

			mViewPager.setAdapter(mViewAdapter);
		}
	}

}
