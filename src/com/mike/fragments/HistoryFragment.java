/**
 * 
 */
package com.mike.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.mike.adapters.CustomListAdapter;
import com.mike.adapters.ListAdapter;
import com.mike.appmodel.AppModel;
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
			+ "radius=1000&"
			+ "types=gas_station&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4&sensor=true";

	private Spinner mSpinner;

	private NestedListView mListView2;

	private AppUtils mAppUtils;

	private Model mAppModel;

	private CustomListAdapter mAdapter;

	private ListAdapter mAdapter2;

	private ArrayList<Model> infoArrayList = new ArrayList<Model>();

	private ArrayList<Model> placeArrayList = new ArrayList<Model>();

	private ArrayList<Model> addressArrayList = new ArrayList<Model>();

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

	}

	public void RunThis() {

		for (int i = 0; i < 20; i++) {

			String s = "Some Item : " + i;
			mAppModel.setSomeItem(s);
			infoArrayList.add(new Model(mAppModel.getSomeItem()));

		}

	}

	public void getGasLocations(String gasURL) {

		String iconUrl = null;
		String placeName = null;
		String placeAddress = null;
		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJSONObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));
			JSONArray mainJSONArray = mainJSONObject.getJSONArray("results");

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

				}
				Log.d("ICONs Array : ", iconUrl);
				Log.d("HISTORY MODEL IMAGE ARRAY : ", mAppModel.getSomeItem());
				Log.d("HISTORY MODEL ARRAY : ", mAppModel.getSomeItem());
				Log.d("HISTORY PLACE ARRAY : ", mAppModel.getPlaceName());
				Log.d("HISTORY ADDRESS ARRAY : ", mAppModel.getAddressList());
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
			// RunThis();
			getGasLocations(URL);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAdapter2 = new ListAdapter(getActivity().getApplicationContext(),
					infoArrayList, placeArrayList, addressArrayList);
			mListView2.setAdapter(mAdapter2);
		}

	}

}
