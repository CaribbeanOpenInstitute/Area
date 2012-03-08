package jm.org.data.area;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static jm.org.data.area.AreaConstants.*;

/**
 * AreaService
 * Background service used to make API calls to cloud services 
 * and store in local database
 */
public class AreaService extends Service{
	private static final String TAG = AreaService.class.getSimpleName();
	private AreaApplication area;
	

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Service Binded");
		return null; 
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service created");
	}
	
	public class MyBinder extends Binder {
		AreaService getService() {
			return AreaService.this;
		}
	}
	
	/***********************
	 * Search Functions
	 ***********************/
	public int genericSearch(String dSource, String indID, String[] ctry) {
		//Check local cache
		
		if (inDatabase()) {
			//Query Database
			//Set Shared Cursor: area.setSharedCursor(apiCode, cursor);
			return SEARCH_SUCCESS;
			
		} else {
			Thread genericSearch = new Thread(new GenericSearchRunnable(dSource, indID, ctry));
			genericSearch.start();
			// return SEARCH_API_SOME OR SEARCH_API_NONE
		}
		return SEARCH_FAIL;
		
	}
	
	public int globalSearch(String searchParam) {
		
		return SEARCH_FAIL;
	}
	
	/*
	 * Thread Definitions			
	 */
	public class GenericSearchRunnable implements Runnable {
		private String dataSource;
		private String indicatorID;
		private String[] country;
		
		public GenericSearchRunnable(String dataS, String indi, String[] cty) {
			this.dataSource = dataS;
			this.indicatorID = indi;
			this.country = cty;
		}
		
		public void run() {
			//Go fetch API data
			//Insert into DB
			//Query DB
			//Set Shared Cursor: area.setSharedCursor(apiCode, cursor);
			//notifyActivity
		}
	}
	
	
	
	private boolean inDatabase() {
		
		return false;
	}
	
	private void notifyActivity(int apiCode) {
		switch(apiCode) {
		case WORLD_SEARCH:
			sendBroadcast(new Intent(ACTION_WORLD_UPDATE));
		case IDS_SEARCH:
			sendBroadcast(new Intent(ACTION_IDS_UPDATE));
		case BING_SEARCH:
			sendBroadcast(new Intent(ACTION_BING_UPDATE));
		default:	//Fail || Problem with update
			sendBroadcast(new Intent(ACTION_FAIL_UPDATE));
		}
		
	}

}
