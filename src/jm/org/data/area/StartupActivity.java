package jm.org.data.area;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class StartupActivity extends Activity {
	private static final String TAG = AreaData.class.getSimpleName();

	protected boolean _active = true;
	protected int _splashTime = 1; // time to display the splash screen in ms
	// not sure how this is gonna be used so just placing it here for the time
	// being
	// private JSONParse mJsonParse;

	private AreaApplication area;

	private boolean isOnline;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.startupview);

		area = (AreaApplication) getApplication();

		// NOT USED
		// Initialise JSONParser
		// mJsonParse = new JSONParse(getBaseContext());
		// mApiPull = new APIPull();

		if (!area.checkNetworkConnection()) {
			Log.e(StartupActivity.class.toString(), "No Internet connectivity");
			Toast.makeText(
					StartupActivity.this,
					"There was an error in completing application initilization. Please check your internet connection and start the application again",
					Toast.LENGTH_LONG).show();
		} else {
			new startupRequest().execute();
		}

	}

	private class startupRequest extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// Initiate loading image
			// loadingAnimator.setDisplayedChild(1);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {

				// initial pull of country and indicator data
				// getCountryList();
				// getIndicatorsList();

				// initial pull of country and indicator data
				area.areaData.updateAPIs();
				area.areaData.updatePeriod();
				// appdata.updateCountries();

				// Error when debugging needs to be tested
				area.areaData.updateIndicators();

				/*
				 * int waited = 0; while (_active && (waited < _splashTime)) {
				 * sleep(100); if (_active) { waited += 100; } }
				 */
				return true;
			} catch (Exception e) {
				Log.e(TAG, "Exception updating Area Data " + e.toString());
			}
			return false;
		}

		protected void onPostExecute(boolean startupResult) {
			// stop loading message
			if (startupResult) {
				setResult(RESULT_OK, new Intent());
				finish();
			} else {
				// Message to say internet is required or there was some error
				// with completing the intialization
				Toast.makeText(
						StartupActivity.this,
						"There was an error in completing application initilization. Please check your internet connection and start activity again.",
						Toast.LENGTH_LONG).show();

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