package jm.org.data.area;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StartupActivity extends Activity {
	private static final String TAG = AreaData.class.getSimpleName();
	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms
	
	// not sure how this is gonna be used so just placing it here for the time being
	private JSONParse mJsonParse;
	
	private APIPull mApiPull;
	private AreaData appdata;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.startupview);

		// intialize JSONParser
		mJsonParse = new JSONParse(getBaseContext());
		appdata = new AreaData(getBaseContext());
		mApiPull = new APIPull();
		
		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					
					//	initial pull of country and indicator data
					getCountryList();
					getIndicatorsList();
					appdata.updateAPIs();
					appdata.updatePeriod();
					
					
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(getApplicationContext(),
							AreaActivity.class));
					//stop();
				}
			}
		};
		splashTread.start();
	}	
	

	// TODO: default with 0 specify constants for apis
	
	private void getCountryList() {
		int numOfCountries = mJsonParse.getWBTotal(mApiPull.HTTPRequest(0,
				"http://api.worldbank.org/country?per_page=1&format=json"));
		if(numOfCountries == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data");
		}else{
			mJsonParse.parseCountries(mApiPull.HTTPRequest(0,
					"http://api.worldbank.org/country?per_page="+ numOfCountries +"&format=json"));
		}
	}// end function
	
	private void getIndicatorsList() {
		
		int numOfIndicators = mJsonParse.getWBTotal(mApiPull.HTTPRequest(0,
				"http://api.worldbank.org/topic/1/Indicator?per_page=10&format=json"));
		if(numOfIndicators == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data");
		}else{
			mJsonParse.parseIndicators(mApiPull.HTTPRequest(0,
					"http://api.worldbank.org/topic/1/Indicator?per_page="+ numOfIndicators +"&format=json"));
		}
	}// end function


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}