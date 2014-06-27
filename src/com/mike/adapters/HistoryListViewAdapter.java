/**
 * 
 */
package com.mike.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.appmodel.Model;
import com.mike.imageutils.ImageLoader;
import com.mike.mylocation.R;

/**
 * @author mickey20142014
 * 
 */
public class HistoryListViewAdapter extends BaseAdapter {

	/**
	 * 
	 */
	public HistoryListViewAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Context context;

	private ArrayList<Model> imageArrayList = new ArrayList<Model>();

	private ArrayList<Model> addressArrayList = new ArrayList<Model>();

	private ArrayList<Model> placenameArrayList = new ArrayList<Model>();

	private ArrayList<Model> latitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> longitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> placetypeArrayList = new ArrayList<Model>();

	private ImageLoader mImageLoader;

	private static String splitChars;

	private StringBuilder sb;

	/**
	 * @param context
	 * @param infoArrayList
	 * @param addressArrayList
	 * @param placenameArrayList
	 * @param latitudeArrayList
	 * @param longitudeArrayList
	 * @param placetypeArrayList
	 * @param mImageLoader
	 */
	public HistoryListViewAdapter(Context context,
			ArrayList<Model> infoArrayList, 
			ArrayList<Model> addressArrayList,
			ArrayList<Model> placenameArrayList,
			ArrayList<Model> latitudeArrayList,
			ArrayList<Model> longitudeArrayList,
			ArrayList<Model> placetypeArrayList) {
		super();
		this.context = context;
		this.imageArrayList = infoArrayList;
		this.addressArrayList = addressArrayList;
		this.placenameArrayList = placenameArrayList;
		this.latitudeArrayList = latitudeArrayList;
		this.longitudeArrayList = longitudeArrayList;
		this.placetypeArrayList = placetypeArrayList;

		mImageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return latitudeArrayList.size();
	}

	@Override
	public Object getItem(int position) {

		return latitudeArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {

		TextView latText, lngText, infoText, addText, placeType, placeNameText;
		ImageView typeImg;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder;

		View view = convertView;

		mImageLoader = new ImageLoader(context);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (view == null) {

			view = inflater.inflate(R.layout.history_listview_items, null);

			mViewHolder = new ViewHolder();

			if (view != null) {

				mViewHolder.typeImg = (ImageView) view
						.findViewById(R.id.history_imageView1);
				mViewHolder.latText = (TextView) view
						.findViewById(R.id.history_row_latitude_textview);
				mViewHolder.lngText = (TextView) view
						.findViewById(R.id.history_row_longitude_textview);
				mViewHolder.placeNameText = (TextView) view
						.findViewById(R.id.history_row_info_textview);
				mViewHolder.addText = (TextView) view
						.findViewById(R.id.history_row_textview_address);
				mViewHolder.placeType = (TextView) view
						.findViewById(R.id.history_row_textview_placetype);

				view.setTag(mViewHolder);

			}

		}

		// Retrieving Data
		mViewHolder = (ViewHolder) view.getTag();

		TextView latitudeTextView = mViewHolder.latText;
		TextView longitudeTextView = mViewHolder.latText;
		TextView addressTextView = mViewHolder.addText;
		TextView placeTypeTextView = mViewHolder.placeType;
		TextView placeNameTextView = mViewHolder.placeNameText;
		ImageView placeImage = mViewHolder.typeImg;

		try {

			latitudeTextView.setText(latitudeArrayList.get(position)
					.getSomeItem());

			longitudeTextView.setText(longitudeArrayList.get(position)
					.getSomeItem());

			placeTypeTextView.setText(placetypeArrayList.get(position)
					.getSomeItem());

			placeNameTextView.setText(placenameArrayList.get(position)
					.getSomeItem());

			addressTextView.setText(addressArrayList.get(position)
					.getSomeItem());

			mImageLoader.DisplayImage(
					imageArrayList.get(position).getSomeItem(),
					R.drawable.ic_drawer, placeImage);

			splitChars = placetypeArrayList.get(position).getSomeItem();
			
			sb = new StringBuilder(splitChars);
			sb.replace(0, 2, "");
			sb.replace(
					Integer.valueOf(placeTypeTextView.getText().length() - 4),
					Integer.valueOf(placeTypeTextView.getText().length()), "");
			Log.d("TEXTVIEW CHARS : ", splitChars);

			if (placeTypeTextView != null) {
				placeTypeTextView.setText(sb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

}
