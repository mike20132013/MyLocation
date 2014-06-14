/**
 * 
 */
package com.mike.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mike.mylocation.MainActivity;

/**
 * @author mickey20142014
 * 
 */
public class AppUtils {

	Context context;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	MainActivity activity;

	public AppUtils(Context newContext) {

		super();
		this.context = newContext;

	}

	public boolean serviceOK(Context context) {

		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (isAvailable == ConnectionResult.SUCCESS) {

			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
			.show();
			/*Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					activity, GPS_ERRORDIALOG_REQUEST);
			dialog.show();*/
		} else {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
					.show();

		}
		return false;

	}

}
