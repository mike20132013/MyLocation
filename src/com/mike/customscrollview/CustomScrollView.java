/**
 * 
 */
package com.mike.customscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author mickey20142014
 * 
 */
public class CustomScrollView extends ScrollView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public CustomScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i("VerticalScrollview",
					"onInterceptTouchEvent: DOWN super false");
			super.onTouchEvent(ev);
			break;

		case MotionEvent.ACTION_MOVE:
			return false; // redirect MotionEvents to ourself

		case MotionEvent.ACTION_CANCEL:
			Log.i("VerticalScrollview",
					"onInterceptTouchEvent: CANCEL super false");
			super.onTouchEvent(ev);
			break;

		case MotionEvent.ACTION_UP:
			Log.i("VerticalScrollview", "onInterceptTouchEvent: UP super false");
			return false;

		default:
			Log.i("VerticalScrollview", "onInterceptTouchEvent: " + action);
			break;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		Log.i("VerticalScrollview", "onTouchEvent. action: " + ev.getAction());
		return true;
	}

}
