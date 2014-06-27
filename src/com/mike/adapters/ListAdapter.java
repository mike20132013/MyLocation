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
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.appmodel.Model;
import com.mike.imageutils.ImageLoader;
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

	private ImageLoader mImageLoader;
	
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
		mImageLoader = new ImageLoader(context.getApplicationContext());
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

		ViewHolder mViewHolder;
		View view = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(view == null){
			
			view = inflater.inflate(R.layout.history_listview_items, null);
			
			mViewHolder = new ViewHolder();
			
			if(view!=null){
				
				mViewHolder.tv1 = (TextView) view.findViewById(R.id.historytextView1);
				mViewHolder.tv2 = (TextView) view.findViewById(R.id.historytextView2);
				mViewHolder.tv3 = (TextView) view.findViewById(R.id.historytextView3);
				mViewHolder.img1 = (ImageView)view.findViewById(R.id.historyImageView);
			}
			
			view.setTag(mViewHolder);
			
		}else{
			
			mViewHolder = (ViewHolder) view.getTag();
			
		}
		
		TextView infoText = mViewHolder.tv1;
		TextView infoText2 = mViewHolder.tv2;
		TextView infoText3 = mViewHolder.tv3;
		ImageView img1 = mViewHolder.img1;
		
		try{
			
			mImageLoader.DisplayImage(infoArray2.get(position).getSomeItem(),R.drawable.ic_drawer, img1);
//			infoText.setText(infoArray2.get(position).getSomeItem());
//			infoText2.setText(placenameArrayList.get(position).getSomeItem());
//			infoText3.setText(addressArrayList.get(position).getSomeItem());
			
		}catch(Exception e){
			e.printStackTrace();			
		}
		
		return view;
	}
	
	public static class ViewHolder{
		
		TextView tv1,tv2,tv3;
		ImageView img1;
		
	}

}
