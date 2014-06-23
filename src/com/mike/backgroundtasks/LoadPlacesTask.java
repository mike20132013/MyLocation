/**
 * 
 *//*
package com.mike.backgroundtasks;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mike.appmodel.AppModel;
import com.mike.utils.AppUtils;

*//**
 * @author mickey20142014
 * 
 *//*
public class LoadPlacesTask extends AsyncTask<Void, Void, Void> {

	private static String foodURL;
	private String gas_stationURL;
	private static String atmURL;
	private static String cafeURL;
	private AppUtils mAppUtils;
	private Context context;

	private static ArrayList<AppModel> addressArrayList = new ArrayList<AppModel>();

	public LoadPlacesTask(Context context, String gasUrl) {

		super();
		this.context = context;
		this.gas_stationURL = gasUrl;

		mAppUtils = new AppUtils(context);

	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {

		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {

		super.onPostExecute(result);
	}

}
*/