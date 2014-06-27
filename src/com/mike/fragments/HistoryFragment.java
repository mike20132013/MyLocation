/**
 * 
 */
package com.mike.fragments;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.mike.adapters.HistoryListViewAdapter;
import com.mike.adapters.ListAdapter;
import com.mike.adapters.ViewAdapter;
import com.mike.appmodel.Model;
import com.mike.customlistview.NestedListView;
import com.mike.fragments.NearbyPlacesFragment.LoadPlacesTask;
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

	private NestedListView historyListView;

	private ViewPager mViewPager;

	private AppUtils mAppUtils;

	private Model mAppModel;

	private ListAdapter mAdapter2;

	private ViewAdapter mViewAdapter;

	private HistoryListViewAdapter mHistoryListViewAdapter;

	private ArrayList<Model> infoArrayList = new ArrayList<Model>();

	private ArrayList<Model> placeArrayList = new ArrayList<Model>();

	private ArrayList<Model> addressArrayList = new ArrayList<Model>();

	private ArrayList<Model> latitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> longitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> placetypeArrayList = new ArrayList<Model>();

	// Trying to get img from SD
	private Uri[] mUrls;
	String[] mFiles = null;
	String uris;
	
	File[] listFile;
	
	BackTask mBackTask;

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

		//getImageFromSD();
		getFromSdcard();

		historyListView = (NestedListView) getActivity().findViewById(
				R.id.historyListView);

		mViewPager = (ViewPager) getActivity().findViewById(R.id.places_pager);
		
		mBackTask = new BackTask(gasURL);
		Thread.currentThread().setContextClassLoader(mBackTask.getClass().getClassLoader());
		mBackTask.execute();
		
	}

	public void RunThis() {

		for (int i = 0; i < 20; i++) {

			String s = "Some Item : " + i;
			mAppModel.setSomeItem(s);
			infoArrayList.add(new Model(mAppModel.getSomeItem()));

		}

	}

	public static void replaceAll(StringBuilder builder, String from, String to) {
		int index = builder.indexOf(from);
		while (index != -1) {
			builder.replace(index, index + from.length(), to);
			index += to.length(); // Move to the end of the replacement
			index = builder.indexOf(from, index);
		}
	}

	public void getFromSdcard()
	{
	    File file= new File(android.os.Environment.getExternalStorageDirectory(),"LocationApp");

	        if (file.isDirectory())
	        {
	            listFile = file.listFiles();


	            for (int i = 0; i < listFile.length; i++)
	            {
	            	
	            	String allURLS = listFile[i].toString();
	            	Log.d("URLS FROM SD CARD : ", allURLS);
	                //f.add(listFile[i].getAbsolutePath());

	            }
	        }
	}
	
	public void getGasLocations(String gasURL) {

		String iconUrl = null;
		String placeName = null;
		String placeAddress = null;
		String value = null;
		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJSONObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));
			JSONArray mainJSONArray = mainJSONObject.getJSONArray("results");

			for (int ij = 0; ij < mainJSONArray.length(); ij++) {

				JSONObject obj = mainJSONArray.getJSONObject(ij);

				value = obj.getString("types");

				Log.d("PLACE TYPES ARRAY VALUE : ", value);

				mAppModel.setPlaceType(value);

				placetypeArrayList.add(new Model(mAppModel.getPlaceType()));

			}

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
				// Log.d("ICONs Array : ", iconUrl);
				// Log.d("HISTORY MODEL IMAGE ARRAY : ",
				// mAppModel.getSomeItem());
				// Log.d("HISTORY MODEL ARRAY : ", mAppModel.getSomeItem());
				// Log.d("HISTORY PLACE ARRAY : ", mAppModel.getPlaceName());
				// Log.d("HISTORY ADDRESS ARRAY : ",
				// mAppModel.getAddressList());
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

			super.onPostExecute(result);
			mAdapter2 = new ListAdapter(getActivity().getApplicationContext(),
					infoArrayList, placeArrayList, addressArrayList);

			// mHistoryListViewAdapter = new
			// HistoryListViewAdapter(getActivity()
			// .getApplicationContext(), infoArrayList, addressArrayList,
			// placeArrayList, latitudeArrayList, longitudeArrayList,
			// placetypeArrayList);

			mViewAdapter = new ViewAdapter(getActivity()
					.getApplicationContext(), infoArrayList, addressArrayList,
					placeArrayList, latitudeArrayList, longitudeArrayList,
					placetypeArrayList);

			// historyListView.setAdapter(mHistoryListViewAdapter);

			//historyListView.setAdapter(mAdapter2);
			mViewPager.setAdapter(mViewAdapter);
		}
	}

	public ArrayList<String> getFilePaths() {

		Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = { MediaStore.Images.ImageColumns.DATA };
		Cursor c = null;
		SortedSet<String> dirList = new TreeSet<String>();
		ArrayList<String> resultIAV = new ArrayList<String>();

		String[] directories = null;
		if (u != null) {
			// c = managedQuery(u, projection, null, null, null);
			c = getActivity().getContentResolver().query(u, projection, null,
					null, null);
		}

		if ((c != null) && (c.moveToFirst())) {
			do {
				String tempDir = c.getString(0);
				tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
				try {
					dirList.add(tempDir);
				} catch (Exception e) {

				}
			} while (c.moveToNext());
			directories = new String[dirList.size()];
			dirList.toArray(directories);

		}

		for (int i = 0; i < dirList.size(); i++) {
			File imageDir = new File(directories[i]);
			File[] imageList = imageDir.listFiles();
			if (imageList == null)
				continue;
			for (File imagePath : imageList) {
				try {

					if (imagePath.isDirectory()) {
						imageList = imagePath.listFiles();

					}
					if (imagePath.getName().contains(".jpg")
							|| imagePath.getName().contains(".JPG")
							|| imagePath.getName().contains(".jpeg")
							|| imagePath.getName().contains(".JPEG")
							|| imagePath.getName().contains(".png")
							|| imagePath.getName().contains(".PNG")
							|| imagePath.getName().contains(".gif")
							|| imagePath.getName().contains(".GIF")
							|| imagePath.getName().contains(".bmp")
							|| imagePath.getName().contains(".BMP")) {

						String path = imagePath.getAbsolutePath();
						resultIAV.add(path);

					}
				}
				// }
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return resultIAV;

	}

	public void getImageFromSD() {

		File images = new File(Environment.getExternalStorageDirectory(),
				"LocationApp");

		File[] imagelist = images.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				return ((filename.endsWith(".jpg")) || (filename
						.endsWith(".png")));
			}
		});
		mFiles = new String[imagelist.length];

		for (int i = 0; i < imagelist.length; i++) {
			mFiles[i] = imagelist[i].getAbsolutePath();
		}
		mUrls = new Uri[mFiles.length];

		for (int i = 0; i < mFiles.length; i++) {
			mUrls[i] = Uri.parse(mFiles[i]);
			Log.d("SD CARD URLS : ", mUrls[i].toString());
			
			String allUrls = mUrls[i].toString();
//			mAppModel.setSomeItem(allUrls);
//			infoArrayList.add(new Model(mAppModel.getSomeItem()));
//			Log.d("SD CARD URLS NEW : ", mAppModel.getSomeItem());
			
		}

	}
}
