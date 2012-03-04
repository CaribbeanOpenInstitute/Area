package jm.org.data.area;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class AreaApplication extends Application {
	private String TAG = AreaActivity.class.getSimpleName();
	public SharedPreferences prefs;
	public boolean isOnline = false;
	
	private Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mContext = getBaseContext();
		
		// check if app is online
		isOnline = checkNetworkConnection(mContext);
		
		String idsKey = getString(R.string.pref_idsKey); 
        String bingKey = getString(R.string.pref_bingKey);
        Log.d(TAG, String.format("IDS: %s. Bing: %s", idsKey, bingKey));
        
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        
	}
	
	/**
	 * Checks if the device has Internet connection.
	 * 
	 * @return <code>true</code> if the phone is connected to the Internet.
	 */
	public static boolean checkNetworkConnection(Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		final boolean connected = (wifi != null && mobile != null
				&& connMgr.getActiveNetworkInfo().isAvailable() && connMgr
				.getActiveNetworkInfo().isConnected());

		if (connected)
			return true;
		else
			return false;
	}

}
