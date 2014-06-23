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
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mike.appmodel.Model;
import com.mike.mylocation.R;

/**
 * @author mickey20142014
 * 
 */
public class ListAdapter extends BaseAdapter {

	Context context;

	private ArrayList<Model> infoArray2 = new ArrayList<Model>();
	private ArrayList<Model> addressArrayList = new ArrayList<Model>();;
	private ArrayList<Model> placenameArrayList = new ArrayList<Model>();;

	public ListAdapter() {
		super();
	}

	public ListAdapter(Context context, ArrayList<Model> infoArray2,
			ArrayList<Model> placeArray, ArrayList<Model> addressArray) {
		super();
		this.context = context;
		this.infoArray2 = infoArray2;
		this.placenameArrayList = placeArray;
		this.addressArrayList = addressArray;
	}

	@Override
	public int getCount() {

		return infoArray2.size();

	}

	@Override
	public Object getItem(int position) {

		return infoArray2.get(position);
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

		TextView infoText = (TextView) convertView
				.findViewById(R.id.historytextView1);
		TextView infoText2 = (TextView) convertView
				.findViewById(R.id.historytextView2);
		TextView infoText3 = (TextView) convertView
				.findViewById(R.id.historytextView3);
		
		infoText.setText(infoArray2.get(position).getSomeItem());
		infoText2.setText(placenameArrayList.get(position).getSomeItem());
		infoText3.setText(addressArrayList.get(position).getSomeItem());

		return convertView;
	}

}
