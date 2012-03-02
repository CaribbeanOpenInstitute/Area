package jm.org.data.area;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AreaApplication extends Application {
	private String TAG = AreaActivity.class.getSimpleName();
	public SharedPreferences prefs;
	public boolean isOnline = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		String idsKey = getString(R.string.pref_idsKey); 
        String bingKey = getString(R.string.pref_bingKey);
        Log.d(TAG, String.format("IDS: %s. Bing: %s", idsKey, bingKey));
        
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
	}

}
