package jm.org.data.area;

import jm.org.data.area.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class StartupActivity extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms
	
	// not sure how this is gonna be used so just placing it here for the time being
	private JSONParse mJsonParse;
	
	private APIPull mApiPull;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.startupview);

		// intialize JSONParser
		mJsonParse = new JSONParse(getBaseContext());
		
		mApiPull = new APIPull();
		
		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					
					//	initial pull of country and indicator data
					getCountryList();
					
					getIndicatorsList();
					
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
					stop();
				}
			}
		};
		splashTread.start();
	}	
	

	// TODO: default with 0 specify constants for apis
	private void getCountryList() {
		// pull data and put in database
		mJsonParse.parseCountries(mApiPull.HTTPRequest(0,
				"http://api.worldbank.org/country?per_page=50&format=json"));
	}
	
	private void getIndicatorsList() {
		mJsonParse.parseCountries(mApiPull.HTTPRequest(0,
				"http://api.worldbank.org/indicator?per_page=50&format=json"));
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}