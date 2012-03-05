package jm.org.data.area;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import static jm.org.data.area.AreaConstants.*;

public class AreaApplication extends Application {
	private String TAG = AreaActivity.class.getSimpleName();
	public SharedPreferences prefs;
	public AREAData areaData;
	
	private Cursor wbCursor;
	private Cursor idsCursor;
	private Cursor bingCursor;
	
	
	public boolean isServiceRunning = false;
	public boolean isOnline = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		String idsKey = getString(R.string.pref_idsKey); 
        String bingKey = getString(R.string.pref_bingKey);
        Log.d(TAG, String.format("IDS: %s. Bing: %s", idsKey, bingKey));
        
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
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

}
