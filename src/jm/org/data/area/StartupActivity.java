package jm.org.data.area;

import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * DESC: Called when the Area application is first created. Activity downloads
 * initial indicator names country listings, and other initial data from the
 * World Bank API
 * 
 **/
public class StartupActivity extends Activity {
	private static final String TAG = StartupActivity.class.getSimpleName();
	private boolean isRunning = false;

	protected boolean _active = true;
	protected int _splashTime = 1; // time to display the splash screen in ms

	private AreaApplication area;
	private ViewAnimator loadingAnimator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startupview);
		loadingAnimator = (ViewAnimator) findViewById(R.id.startupSwitcher); // Loading
																				// Animator
		area = (AreaApplication) getApplication();

		if (!area.checkNetworkConnection()) { // Check the Internet connection
			Log.e(TAG, "No Internet connectivity");
			Toast.makeText(
					StartupActivity.this,
					"There was an error connecting to the Internet. Please check your connection and start the application again",
					Toast.LENGTH_LONG).show();
		} else {
			if (!isRunning) // Check if initialization activity is already
							// running
				new startupRequest().execute();
		}
	}

	private class startupRequest extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			loadingAnimator.setDisplayedChild(0);
			area.initIsRunning = true;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				// initial pull of country and indicator data
				area.areaData.updateAPIs();
				area.areaData.updatePeriod();

				// Error when debugging needs to be tested
				area.areaData.updateIndicators();
				area.areaData.updateCountries();

				// to test generic search
				// area.areaData.genericSearch(WORLD_SEARCH,
				// "TX.VAL.AGRI.ZS.UN", new String[]{"Jamaica",
				// "Kenya","Barbados", "World"});

				return true;

			} catch (Exception e) {
				Log.e(TAG, "Exception updating Area Data " + e.toString());
				loadingAnimator.setDisplayedChild(1);
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean initResult) {
			super.onPostExecute(initResult);

			if (initResult) {
				Log.e(TAG, "Correctly completed initialization");
				area.initIsRunning = false;
				setResult(RESULT_OK, new Intent());

				finish();
			} else {
				Log.e(TAG, "Failed initialization");
				/*
				 * Toast.makeText( StartupActivity.this,
				 * "An error was encountered while completing application initilization. "
				 * +
				 * "Please check your internet connection and start activity again."
				 * , Toast.LENGTH_LONG).show();
				 */
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/*
	 * // TODO: default with 0 specify constants for apis
	 * 
	 * private void getCountryList() { int numOfCountries =
	 * mJsonParse.getWBTotal(mApiPull.HTTPRequest(0,
	 * "http://api.worldbank.org/country?per_page=1&format=json"));
	 * if(numOfCountries == 0 ){ // error in parsing JSON data Log.e(TAG,
	 * "Error In Parsing JSON data"); }else{
	 * mJsonParse.parseCountries(mApiPull.HTTPRequest(0,
	 * "http://api.worldbank.org/country?per_page="+ numOfCountries
	 * +"&format=json")); } }// end function
	 * 
	 * private void getIndicatorsList() {
	 * 
	 * int numOfIndicators = mJsonParse.getWBTotal(mApiPull.HTTPRequest(0,
	 * "http://api.worldbank.org/topic/1/Indicator?per_page=10&format=json"));
	 * if(numOfIndicators == 0 ){ // error in parsing JSON data Log.e(TAG,
	 * "Error In Parsing JSON data"); }else{
	 * mJsonParse.parseIndicators(mApiPull.HTTPRequest(0,
	 * "http://api.worldbank.org/topic/1/Indicator?per_page="+ numOfIndicators
	 * +"&format=json")); } }// end function
	 */
}