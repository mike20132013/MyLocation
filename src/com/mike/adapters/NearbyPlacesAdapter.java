/**
 * 
 */
package com.mike.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.appmodel.AppModel;
import com.mike.imageutils.ImageLoader;
import com.mike.mylocation.R;

/**
 * @author mickey20142014
 * 
 */
public class NearbyPlacesAdapter extends PagerAdapter {

	ArrayList<AppModel> imagesArrayList = new ArrayList<AppModel>();
	ArrayList<AppModel> infoArrayList = new ArrayList<AppModel>();
	ArrayList<AppModel> addressArrayList = new ArrayList<AppModel>();
	private ImageLoader mImageLoader;
	private Context context;
	ArrayList<String> iArrays = new ArrayList<String>();
	
	/**
	 * 
	 */
	public NearbyPlacesAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NearbyPlacesAdapter(ArrayList<String> iArray){
		super();
		this.iArrays = iArray;
	}

	public NearbyPlacesAdapter(Context context,ArrayList<AppModel> imageArray){
		
		super();
		this.imagesArrayList = imageArray;
	}
	
	
	/**
	 * @param imagesArrayList
	 * @param infoArrayList
	 * @param addressArrayList
	 */
	public NearbyPlacesAdapter(Context context,
			ArrayList<AppModel> imagesArrayList,
			ArrayList<AppModel> infoArrayList,
			ArrayList<AppModel> addressArrayList) {
		super();
		this.context = context;
		this.imagesArrayList = imagesArrayList;
		this.infoArrayList = infoArrayList;
		this.addressArrayList = addressArrayList;
		mImageLoader = new ImageLoader(context.getApplicationContext());

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infoArrayList.size();
	}

	@Override
	public boolean isViewFromObject(View collection, Object object) {
		// TODO Auto-generated method stub
		return collection == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		ViewHolder mViewHolder;
		View convertView = container;
		
		LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		convertView = inflater.inflate(R.layout.places_row_layout, null);
		
		ImageView mImageView = (ImageView)convertView.findViewById(R.id.places_row_imageview);
		TextView mTextView = (TextView)convertView.findViewById(R.id.places_row_textview);
		mTextView.setText(iArrays.get(position));
		//mImageLoader.DisplayImage(imagesArrayList.get(position).getWeb_imageUrls(), R.drawable.cameraicon, mImageView);
		//mImageLoader.DisplayImage(imgArrays.get(position), R.drawable.cameraicon, mImageView);
		
/*		ViewHolder mViewHolder;
		View convertView = container;

		LayoutInflater inflater = (LayoutInflater) container.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.places_row_layout, null);

			mViewHolder = new ViewHolder();
			if (convertView != null) {

				mViewHolder.mImageView = (ImageView) convertView
						.findViewById(R.id.places_row_imageview);
				mViewHolder.mTextViewInfo = (TextView) convertView
						.findViewById(R.id.places_row_textview);
				mViewHolder.mTextViewAddress = (TextView) convertView
						.findViewById(R.id.places_row_textview_address);

			}

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		ImageView mImageView = mViewHolder.mImageView;
		TextView mTextViewInfo = mViewHolder.mTextViewInfo;
		TextView mTextViewAddress = mViewHolder.mTextViewAddress;

		try {

			mImageLoader.DisplayImage(imagesArrayList.get(position)
					.getWeb_imageUrls(), R.drawable.cameraicon, mImageView);
//			mTextViewInfo.setText(infoArrayList.get(position).getInfoList());
//			mTextViewAddress.setText(addressArrayList.get(position)
//					.getAddressList());

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Adapter Exception : ", e.getMessage());
		}
*/
		((ViewPager) container).addView(convertView, 0);
		return convertView;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	public static class ViewHolder {

		public ImageView mImageView;
		public TextView mTextViewInfo, mTextViewAddress;

	}

}
