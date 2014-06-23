/**
 * 
 */
package com.mike.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mike.appmodel.AppModel;
import com.mike.appmodel.Model;
import com.mike.mylocation.R;

/**
 * @author mickey20142014
 * 
 */
public class CustomListAdapter extends ArrayAdapter<Model> {

	private Context context;
	private ArrayList<AppModel> imageArrayList;
	private ArrayList<Model> infoArrayList;
	private ArrayList<Model> addressArrayList;
	private ArrayList<Model> placenameArrayList;
	private int lastPosition = -1;
	private int selectedIndex;

	public CustomListAdapter(Context context, ArrayList<Model> infoArray) {

		super(context, R.layout.history_list_item, infoArray);
		this.context = context;
		this.infoArrayList = infoArray;

	}

	public CustomListAdapter(Context context, ArrayList<Model> infoArray,
			ArrayList<Model> placeArray, ArrayList<Model> addressArray) {
		super(context, R.layout.history_list_item, infoArray);
		this.context = context;
		this.infoArrayList = infoArray;
		this.placenameArrayList = placeArray;
		this.addressArrayList = addressArray;
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infoArrayList.size();

	}

	@Override
	public Model getItem(int position) {

		return infoArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.history_list_item, null);
		}

		lastPosition = position;

		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.historytextView1);
		TextView txtTitle2 = (TextView) convertView
				.findViewById(R.id.historytextView2);
		TextView txtTitle3 = (TextView) convertView
				.findViewById(R.id.historytextView3);
		
		if (infoArrayList != null||placenameArrayList!=null||addressArrayList!=null) {
			txtTitle.setText(infoArrayList.get(position).getInfoList());
			
		}

		return convertView;
	}

}
