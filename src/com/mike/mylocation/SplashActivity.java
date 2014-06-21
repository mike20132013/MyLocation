/**
 * 
 */
package com.mike.mylocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mike.backgroundtasks.LoadingTask;
import com.mike.backgroundtasks.LoadingTask.LoadingTaskFinishedListener;
import com.mike.customtextview.CustomTextView;

/**
 * @author mickey20142014
 * 
 */
public class SplashActivity extends Activity implements
		LoadingTaskFinishedListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progressBar);
		TextView tv1 = (TextView) findViewById(R.id.splashTextView1);
		TextView tv2 = (TextView) findViewById(R.id.splashTextView2);
		CustomTextView ctv = (CustomTextView) findViewById(R.id.customTextView1);

		new LoadingTask(progressBar, this).execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mike.backgroundtasks.LoadingTask.LoadingTaskFinishedListener#
	 * OnTaskFinished()
	 */
	@Override
	public void OnTaskFinished() {

		completeSplash();

	}

	private void completeSplash() {
		startApp();
		finish(); // Don't forget to finish this Splash Activity so the user
					// can't return to it!
	}

	private void startApp() {
		// Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		Intent intent = new Intent(SplashActivity.this,
				TabActivity.class);
		startActivity(intent);
	}

}
