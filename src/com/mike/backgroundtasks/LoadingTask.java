package com.mike.backgroundtasks;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class LoadingTask extends AsyncTask<Void, Integer, Void> {

	int progress = 0 ;
	
	public interface LoadingTaskFinishedListener {
		void OnTaskFinished();
	}

	// This is the progress bar you want to update while the task is in progress
	private final ProgressBar progressBar;
	// This is the listener that will be told when this task is finished
	private final LoadingTaskFinishedListener finishedListener;

	public LoadingTask(ProgressBar mProgressBar,
			LoadingTaskFinishedListener mLoadingTaskFinishedListener) {
		this.progressBar = mProgressBar;
		this.finishedListener = mLoadingTaskFinishedListener;

	}

	private void downloadResources() {
		// We are just imitating some process thats takes a bit of time (loading of resources / downloading)
		/*int count = 10;
		for (int i = 0; i < count; i++) {

			// Update the progress bar after every step
			int progress = (int) ((i / (float) count) * 100);
			publishProgress(progress);

			// Do some long loading things
			try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
		}*/
		while(progress<100){
			
			progress++;
			publishProgress(progress);
			SystemClock.sleep(25);
		}
		
	}
	
	private boolean resourcesDontAlreadyExist() {
		// Here you would query your app's internal state to see if this download had been performed before
		// Perhaps once checked save this in a shared preference for speed of access next time
		return true; // returning true so we show the splash every time
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if(resourcesDontAlreadyExist()){
			downloadResources();
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		finishedListener.OnTaskFinished();
	}

}
