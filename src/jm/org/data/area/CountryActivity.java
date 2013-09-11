package jm.org.data.area;

import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;

public class CountryActivity extends BaseActivity {
	
	private static final String TAG = CountryActivity.class.getSimpleName();
	private String selection, country;
	private int mSelection;
	
	public String getSelection() {
		return selection;
	}
	
	public int getParentNum() {
		return 1;
	}

	public int getSelectionID(){
		return mSelection;
	}
	
	public void setSelection(String indicator) {
		selection = indicator;
		Log.d(TAG, "Selection changed to " + selection);

	}
	
	public void setSelection(int lPos) {
		mSelection = lPos;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		EasyTracker.getInstance(this).activityStart(this);  // Add this method.
		}
	
	@Override
	public void onStop() {
		super.onStop();
	
		EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}

	public String getCountry() {
		// TODO Auto-generated method stub
		return country;
	}
}
