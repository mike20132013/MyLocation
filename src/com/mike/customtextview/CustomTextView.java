/**
 * 
 */
package com.mike.customtextview;

import com.mike.mylocation.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author mickey20142014
 * 
 */
public class CustomTextView extends TextView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public CustomTextView(Context context) {
		super(context);
		init(null);
		// TODO Auto-generated constructor stub
	}

	public void init(AttributeSet attrs) {

		if (attrs != null) {

			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.MyTextView);
			String fontName = a.getString(R.styleable.MyTextView_fontName);
			if (fontName != null) {

				Typeface myTypeface = Typeface.createFromAsset(getContext()
						.getAssets(), "fonts/" + fontName);
				setTypeface(myTypeface);

			}
			a.recycle();

		}

	}

}
