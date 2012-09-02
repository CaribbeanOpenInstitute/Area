package jm.org.data.area;

import static jm.org.data.area.AreaConstants.ACTION_BING_UPDATE;
import static jm.org.data.area.AreaConstants.ACTION_FAIL_UPDATE;
import static jm.org.data.area.AreaConstants.ACTION_IDS_UPDATE;
import static jm.org.data.area.AreaConstants.ACTION_WORLD_UPDATE;
import static jm.org.data.area.AreaConstants.BING_SEARCH;
import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.RETURN_CNTRY_IDs;
import static jm.org.data.area.AreaConstants.RETURN_COUNTRIES;
import static jm.org.data.area.AreaConstants.RETURN_DATE;
import static jm.org.data.area.AreaConstants.RETURN_IND_ID;
import static jm.org.data.area.AreaConstants.RETURN_KEYWORDS;
import static jm.org.data.area.AreaConstants.RETURN_STRING;
import static jm.org.data.area.AreaConstants.RETURN_VALUE;
import static jm.org.data.area.AreaConstants.RETURN_WB_IND_ID;
import static jm.org.data.area.AreaConstants.SEARCH_FAIL;
import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static jm.org.data.area.DBConstants.*;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * AreaService
 * Background service used to make API calls to cloud services 
 * and store in local database
 */
public class AreaService extends Service {
	private static final String TAG = AreaService.class.getSimpleName();
	private final IBinder mBinder = new MyBinder();
	private AreaApplication area;
	

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Service Binded");
		return mBinder; 
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service created");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "Service destroyed");
	}
	
	public class MyBinder extends Binder {
		AreaService getService() {
			return AreaService.this;
		}
	}
	
	/***********************
	 * Search Functions
	 ***********************/
	public int genericSearch(int dSource, String indID, String[] ctry) {
		//Check local cache
		int return_value;
		Hashtable<String, Object> return_data = new Hashtable<String, Object>();
		
		area = (AreaApplication) getApplication();
		Log.e(TAG, "Calling Generic Search 1");
		return_value = area.areaData.genericSearch(dSource, indID, ctry);
		//return_value = (Integer) return_data.get(RETURN_VALUE);
		
		if (return_value == SEARCH_SUCCESS) {
			
			return SEARCH_SUCCESS;
			
		} else if(return_value > SEARCH_SUCCESS) {
			Thread genericSearch = new Thread(new GenericSearchRunnable(dSource, return_data));
			genericSearch.start();
			// return SEARCH_API_SOME OR SEARCH_API_NONE
		}
		return return_value;
		
	}
	
	public int globalSearch(String searchParam) {
		
		return SEARCH_FAIL;
	}
	
	/*
	 * Thread Definitions			
	 */
	public class GenericSearchRunnable implements Runnable {
		private int dataSource, search_id;
		private Cursor cursor;
		private AreaApplication area = (AreaApplication) getApplication();
		private String table, tableKey;
		//private String indicatorID;
		//private String[] country;
		private Hashtable<String, Object> search_data = new Hashtable<String, Object>();
		
		public GenericSearchRunnable(int dataS, Hashtable<String, Object> data) {
			this.dataSource = dataS;
			//this.indicatorID = indi;
			//this.country = cty;
			this.search_data = data;
		}
		
		public void run() {
			//Go fetch API data
			//Insert into DB
			switch (dataSource) {
				case WORLD_SEARCH:
					/*
					return_data.put(RETURN_VALUE		, SEARCH_API_NONE	);
					return_data.put(RETURN_IND_ID		, ind_id			);
					return_data.put(RETURN_WB_IND_ID	, indicatorID		);
					return_data.put(RETURN_COUNTRIES	, countries_to_get	);
					return_data.put(RETURN_CNTRY_IDs	, countryIDs		);
					return_data.put(RETURN_DATE			, "date=1990:2012"	);
					*/
					try{
						int indicator_id = (Integer) search_data.get(RETURN_IND_ID);
						String indicator = (String) search_data.get(RETURN_WB_IND_ID), 
							   date		 = (String)	search_data.get(RETURN_DATE);
						@SuppressWarnings("unchecked")
						ArrayList<Integer> countryIDList = (ArrayList<Integer>) search_data.get(RETURN_CNTRY_IDs);
						@SuppressWarnings("unchecked")
						ArrayList<String> countries		= (ArrayList<String>)  search_data.get(RETURN_COUNTRIES);
						
						search_id  = area.areaData.getCountryIndicators(indicator_id, indicator, countries, countryIDList, date);
					}catch(Exception e){
						Log.e(TAG, "Error retrieving hashtable objects:" );
					}
					table = WB_DATA;
					tableKey = SC_ID;
					break;
				case IDS_SEARCH:
					/*
					 *return_data.put(RETURN_VALUE		, SEARCH_API_NONE	);
				     *return_data.put(RETURN_IND_ID		, ind_id			);
					 *return_data.put(RETURN_KEYWORDS	, keyWords			);
					 */
					int indicator = (Integer) search_data.get(RETURN_IND_ID);
					String[] parameters = (String[])search_data.get(RETURN_KEYWORDS);
					search_id  = area.areaData.getDocuments(indicator, parameters);
					table = IDS_SEARCH_RESULTS;
					tableKey = IDS_S_ID;
					break;
				case BING_SEARCH:
					
					//return_data.put(RETURN_STRING		, indicatorStr		);
					String param = (String)search_data.get(RETURN_STRING);
					search_id  = area.areaData.getBingArticles(param);
					table = BING_SEARCH_RESULTS;
					tableKey = B_S_ID;
					break;
				default:
					break;
				
			}
			//Query DB
			// get Search record based on the api being searched and return the associated list of values
			cursor = area.areaData.rawQuery(table, "*", tableKey + " = '" + search_id +"'");
			//Set Shared Cursor: area.setSharedCursor(apiCode, cursor);
			area.setSharedCursor(dataSource, cursor);
			//notifyActivity
			notifyActivity(dataSource);
		}
	}
	
	
	
	/*private boolean inDatabase() {
		
		return false;
	}*/
	
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
