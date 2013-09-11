package jm.org.data.area;

import static jm.org.data.area.AreaConstants.*;
import static jm.org.data.area.AreaConstants.BING_RESULT_DATA;
import static jm.org.data.area.AreaConstants.BING_SEARCH;
import static jm.org.data.area.AreaConstants.BING_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.CATEGORY_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.IDS_PARAM_DATA;
import static jm.org.data.area.AreaConstants.IDS_RESULT_DATA;
import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.IDS_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.INDICATOR_LIST;
import static jm.org.data.area.AreaConstants.IND_CATEGORIES_DATA;
import static jm.org.data.area.AreaConstants.PERIOD_LIST;
import static jm.org.data.area.AreaConstants.SEARCH_DATA;
import static jm.org.data.area.AreaConstants.WB_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.DBConstants.BING_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.BING_SEARCH_TABLE;
import static jm.org.data.area.DBConstants.COUNTRY;
import static jm.org.data.area.DBConstants.IDS_SEARCH_PARAMS;
import static jm.org.data.area.DBConstants.IDS_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.IDS_SEARCH_TABLE;
import static jm.org.data.area.DBConstants.INDICATOR;
import static jm.org.data.area.DBConstants.IND_CATEGORIES;
import static jm.org.data.area.DBConstants.PERIOD;
import static jm.org.data.area.DBConstants.SEARCH;
import static jm.org.data.area.DBConstants.SEARCH_COUNTRY;
import static jm.org.data.area.DBConstants.WB_CATEGORY;
import static jm.org.data.area.DBConstants.WB_DATA;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class AreaApplication extends Application {
	private String TAG = AreaActivity.class.getSimpleName();
	public SharedPreferences prefs;
	public AreaData areaData;
	public APIPull netserv;
	// public AreaService areaService;

	private Cursor wbCursor;
	private Cursor idsCursor;
	private Cursor bingCursor;

	// public boolean isServiceRunning = false;
	public boolean isOnline = false;
	public boolean initIsRunning = false;

	private Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = getBaseContext();
		// doBindService();

		String idsKey = getString(R.string.pref_idsKey);
		String bingKey = getString(R.string.pref_bingKey);
		Log.e(TAG, String.format("IDS: %s. Bing: %s", idsKey, bingKey));

		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		areaData = new AreaData(mContext);
		netserv = new APIPull();

	}

	/*
	 * private ServiceConnection mConnection = new ServiceConnection() {
	 * 
	 * @Override public void onServiceConnected(ComponentName name, IBinder
	 * binder) { areaService = ((AreaService.MyBinder) binder).getService();
	 * Log.e(TAG, "Connected to service"); }
	 * 
	 * @Override public void onServiceDisconnected(ComponentName name) {
	 * areaService = null; }
	 * 
	 * };
	 * 
	 * void doBindService() { bindService(new Intent(mContext,
	 * AreaService.class), mConnection, Context.BIND_AUTO_CREATE); }
	 */

	public Cursor getSharedCursor(int apiCode) {
		switch (apiCode) {
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
		switch (apiCode) {
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
	public boolean checkNetworkConnection() {
		final ConnectivityManager connMgr = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		// to fix the error getActivenetworkInfo is something null so it needs
		// to be tested
		// http://stackoverflow.com/questions/2753412/android-internet-connectivity-check-problem
		if (connMgr.getActiveNetworkInfo() == null)
			return false;

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
		else if (tableName.equals(WB_CATEGORY))
			return CATEGORY_LIST;
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
		else if (tableName.equals(IDS_SEARCH_TABLE))
			return IDS_SEARCH_DATA;
		else if (tableName.equals(IDS_SEARCH_PARAMS))
			return IDS_PARAM_DATA;
		else if (tableName.equals(IDS_SEARCH_RESULTS))
			return IDS_RESULT_DATA;
		else if (tableName.equals(BING_SEARCH_TABLE))
			return BING_SEARCH_DATA;
		else if (tableName.equals(BING_SEARCH_RESULTS))
			return BING_RESULT_DATA;
		else if (tableName.equals(IND_CATEGORIES))
			return IND_CATEGORIES_DATA;
		else if (tableName.equals(AREA_SELECTIONS))
			return SELECTIONS_DATA;
		else if (tableName.equals(DATA_TYPES))
			return DATA_TYPES_LIST;
		else if (tableName.equals(CHARTS))
			return CHART_DATA;
		else if (tableName.equals(SAVED_DATA))
			return GET_DATA;
		else if (tableName.equals(COLLECTIONS))
			return GET_COLLECTION;
		else if (tableName.equals(COLL_DATA))
			return GET_COLL_DATA;
		return -1;
	}

}
