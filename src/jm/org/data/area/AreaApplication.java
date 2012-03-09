package jm.org.data.area;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.AreaConstants.*;


public class AreaApplication extends Application {
	private String TAG = AreaActivity.class.getSimpleName();
	public SharedPreferences prefs;
	public AreaData areaData;
	
	private Cursor wbCursor;
	private Cursor idsCursor;
	private Cursor bingCursor;
	
	
	public boolean isServiceRunning = false;
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
	
	public Cursor getSharedCursor(int apiCode) {
		switch(apiCode) {
		case WORLD_SEARCH:
			return wbCursor;
		case IDS_SEARCH:
			return idsCursor;
		case BING_SEARCH:
			return bingCursor;
		default:
			return null;
		}
	}
	
	public void setSharedCursor(int apiCode, Cursor cursor) {
		switch(apiCode) {
		case WORLD_SEARCH:
			wbCursor = cursor;
		case IDS_SEARCH:
			idsCursor = cursor;
		case BING_SEARCH:
			bingCursor = cursor;
		}
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
	
	public static int getTableCode(String tableName) {
		if (tableName.equals(INDICATOR))
			return INDICATOR_LIST;
		else if (tableName.equals(COUNTRY))
			return COUNTRY_LIST;
		else if (tableName.equals(SEARCH))
			return SEARCH_DATA;
		else if (tableName.equals(API))
			return API_LIST;
		else if (tableName.equals(PERIOD))
			return PERIOD_LIST;
		else if (tableName.equals(SEARCH_COUNTRY))
			return COUNTRY_SEARCH_DATA;
		else if (tableName.equals(WB_DATA))
			return WB_SEARCH_DATA;

		return -1;
		}

}
