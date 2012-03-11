package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.API_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_LIST;
import static jm.org.data.area.AreaConstants.FATAL_ERROR;
import static jm.org.data.area.AreaConstants.GENERIC_SEARCH;
import static jm.org.data.area.AreaConstants.INDICATOR_LIST;
import static jm.org.data.area.AreaConstants.SEARCH_FAIL;
import static jm.org.data.area.AreaConstants.*;
import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.DBConstants.API_DESC;
import static jm.org.data.area.DBConstants.API_ID;
import static jm.org.data.area.DBConstants.API_NAME;
import static jm.org.data.area.DBConstants.AP_ID;
import static jm.org.data.area.DBConstants.AUTHOR_ID;
import static jm.org.data.area.DBConstants.AUTHOR_NAME;
import static jm.org.data.area.DBConstants.BASE_URI;
import static jm.org.data.area.DBConstants.CAPITAL_CITY;
import static jm.org.data.area.DBConstants.COUNTRY;
import static jm.org.data.area.DBConstants.COUNTRY_ID;
import static jm.org.data.area.DBConstants.COUNTRY_NAME;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_ID;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_NAME;
import static jm.org.data.area.DBConstants.C_ID;
import static jm.org.data.area.DBConstants.DATABASE_NAME;
import static jm.org.data.area.DBConstants.DATE_CREATED;
import static jm.org.data.area.DBConstants.DATE_UPDATED;
import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.DOC_NAME;
import static jm.org.data.area.DBConstants.DOC_TITLE;
import static jm.org.data.area.DBConstants.D_ID;
import static jm.org.data.area.DBConstants.GDP;
import static jm.org.data.area.DBConstants.GNI_CAPITA;
import static jm.org.data.area.DBConstants.IDS_AUTHOR;
import static jm.org.data.area.DBConstants.IDS_DATA;
import static jm.org.data.area.DBConstants.IDS_DOC_THEME;
import static jm.org.data.area.DBConstants.IDS_THEME;
import static jm.org.data.area.DBConstants.IDS_THEME_ID;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_ID;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_NAME;
import static jm.org.data.area.DBConstants.INDICATOR;
import static jm.org.data.area.DBConstants.INDICATOR_DESC;
import static jm.org.data.area.DBConstants.INDICATOR_ID;
import static jm.org.data.area.DBConstants.INDICATOR_NAME;
import static jm.org.data.area.DBConstants.IND_DATE;
import static jm.org.data.area.DBConstants.IND_DECIMAL;
import static jm.org.data.area.DBConstants.IND_VALUE;
import static jm.org.data.area.DBConstants.I_ID;
import static jm.org.data.area.DBConstants.JOURNAL_SITE;
import static jm.org.data.area.DBConstants.LANGUAGE_NAME;
import static jm.org.data.area.DBConstants.LICENCE_TYPE;
import static jm.org.data.area.DBConstants.LIFE_EX;
import static jm.org.data.area.DBConstants.LITERACY;
import static jm.org.data.area.DBConstants.PARAM;
import static jm.org.data.area.DBConstants.PARAMETER;
import static jm.org.data.area.DBConstants.PARAM_ID;
import static jm.org.data.area.DBConstants.PERIOD;
import static jm.org.data.area.DBConstants.PERIOD_ID;
import static jm.org.data.area.DBConstants.PERIOD_NAME;
import static jm.org.data.area.DBConstants.POPULATION;
import static jm.org.data.area.DBConstants.POVERTY;
import static jm.org.data.area.DBConstants.PUBLICATION_DATE;
import static jm.org.data.area.DBConstants.PUBLISHER;
import static jm.org.data.area.DBConstants.PUBLISHER_COUNTRY;
import static jm.org.data.area.DBConstants.P_END_DATE;
import static jm.org.data.area.DBConstants.P_ID;
import static jm.org.data.area.DBConstants.P_START_DATE;
import static jm.org.data.area.DBConstants.SEARCH;
import static jm.org.data.area.DBConstants.SEARCH_COUNTRY;
import static jm.org.data.area.DBConstants.SEARCH_CREATED;
import static jm.org.data.area.DBConstants.SEARCH_ID;
import static jm.org.data.area.DBConstants.SEARCH_MODIFIED;
import static jm.org.data.area.DBConstants.SEARCH_URI;
import static jm.org.data.area.DBConstants.S_ID;
import static jm.org.data.area.DBConstants.THEME_ID;
import static jm.org.data.area.DBConstants.THEME_LEVEL;
import static jm.org.data.area.DBConstants.THEME_NAME;
import static jm.org.data.area.DBConstants.THEME_URL;
import static jm.org.data.area.DBConstants.T_ID;
import static jm.org.data.area.DBConstants.WB_COUNTRY_CODE;
import static jm.org.data.area.DBConstants.WB_COUNTRY_ID;
import static jm.org.data.area.DBConstants.WB_DATA;
import static jm.org.data.area.DBConstants.WB_DATA_ID;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;
import static jm.org.data.area.DBConstants.WEBSITE_URL;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AreaData {
	
	private static final String TAG = AreaData.class.getSimpleName();
	//private int currentApiVersion = android.os.Build.VERSION.SDK_INT;
	ContentValues values;
	Context context;
	AreaDB dbHelper;
	JSONParse parser;
	APIPull dataService;
	
	ArrayList<String> countries_to_get;
	ArrayList<Integer> countryIDs;
	

	public AreaData(Context context){
		this.context = context;
		dbHelper = new AreaDB(context);
		
		dataService = new APIPull();
	}
	
	// Initialize and Update Tables at Startup
	public void updateAPIs(){
		values = new  ContentValues();
		values.put(API_NAME, "World Bank");
		values.put(API_DESC, "1: World Bank Data API. Retrieves Macro-Economic Data from the World Bank datasets");
		values.put(BASE_URI, "http://api.worldbank.org/");
		insert(API, values, 0);
		
		values = new  ContentValues();
		values.put(API_NAME, "IDS");
		values.put(API_DESC, "2: Institute of Development Studies Data API. Retrieves Academic Publications from Eldis and Bribge datasets");
		values.put(BASE_URI, "http://api.ids.ac.uk/openapi/");
		insert(API, values, 0);
		
		values = new  ContentValues();
		values.put(API_NAME, "Bing");
		values.put(API_DESC, "3: Retrieves online articles, web sites and publications and other electronic data using the Microsoft Bing internet search engine.");
		values.put(BASE_URI, "http://api.bing.net/json.aspx?");
		insert(API, values, 0);
	}
	
	public void updatePeriod(){
		Calendar calendar = Calendar.getInstance();
		
		//{PERIOD_ID, PERIOD_NAME, P_START_DATE, P_END_DATE	
		values = new  ContentValues();
		values.put(PERIOD_NAME, "15 Years");
		values.put(P_START_DATE, "1990");
		values.put(P_END_DATE, "" +calendar.get(Calendar.YEAR));
		insert(PERIOD, values,0);
		
		values = new  ContentValues();
		values.put(PERIOD_NAME, "30 Years");
		values.put(P_START_DATE, "1970");
		values.put(P_END_DATE, "" +calendar.get(Calendar.YEAR));
		insert(PERIOD, values,0);
		
	}
	
	public void updateIndicators(){
		// pull data and put in database
		
		// error right here
		int numOfIndicators = parser.getWBTotal(dataService.HTTPRequest(0,
				"http://api.worldbank.org/topic/1/Indicator?per_page=1&format=json"));
		if(numOfIndicators == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data");
		}else{
			parser.parseIndicators(dataService.HTTPRequest(0,
					"http://api.worldbank.org/topic/1/Indicator?per_page="+ numOfIndicators +"&format=json"));
		}
	}
	
	public void updateCountries(){
		int numOfCountries = parser.getWBTotal(dataService.HTTPRequest(0,
				"http://api.worldbank.org/country?per_page=1&format=json"));
		if(numOfCountries == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data");
		}else{
			parser.parseCountries(dataService.HTTPRequest(0,
					"http://api.worldbank.org/country?per_page="+ numOfCountries +"&format=json"));
		}
	}
	/**
	* Insert record into specified table. Includes duplication check to prevent double entries
	*
	* @param tableName Name of table record to be inserted into
	* @param tableRecord Name-value pairs
	*/
	public long insert(String tableName, ContentValues tableRecord, int update) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int tableCode = AreaApplication.getTableCode(tableName);
		long recordid = 0;
		String tableKey = "";
		String tableKeyAdd = "";
		Cursor cursor = null;

		//Getting correct primary key for tables
		switch (tableCode) {
			case INDICATOR_LIST:
				tableKey = WB_INDICATOR_ID;
				break;
			case COUNTRY_LIST:
				tableKey = WB_COUNTRY_ID;
				break;
			case API_LIST:
				tableKey = API_NAME;
				break;
			case PERIOD_LIST:
				tableKey = PERIOD_NAME;
				break;
			case SEARCH_DATA:
				tableKey = I_ID;
				tableKeyAdd = AP_ID;
				break;
			case COUNTRY_SEARCH_DATA:
				tableKey = C_ID;
				tableKeyAdd = S_ID;
				break;
			case WB_SEARCH_DATA:
				tableKey = SC_ID;
				tableKeyAdd = IND_DATE;
				break;
			

		}

		//Duplicate Check
		if (tableCode == COUNTRY_SEARCH_DATA || tableCode == WB_SEARCH_DATA || tableCode == SEARCH_DATA) {
			cursor = db.query(tableName, null, String.format("%s=%s AND %s=%s", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null, null, null);
		} else { //Special condition for double primary key on crop table
			//cursor = db.query(tableName, null, String.format("%s=%s AND %s=%s", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null, null, null);
			cursor = db.query(tableName, null, tableKey + "='" + tableRecord.get(tableKey) + "'", null, null, null, null);
		}
		
		if (cursor.getCount() > 0) {
			if(update == 1){
				try {
					cursor.moveToFirst();
					recordid = db.update(tableName, tableRecord, "" + _ID  + " ='" + cursor.getInt(cursor.getColumnIndex(_ID))+ "'", null );
					if(recordid != 1){
						Log.e(TAG,"Error Updating "+ tableName +" Record: " + cursor.getInt(cursor.getColumnIndex(_ID)));
					}else{
						Log.d(TAG, "Updating "+ tableName +" Record: " + cursor.getInt(cursor.getColumnIndex(_ID)));
					}
					
				}
				catch (RuntimeException e) {
					Log.e(TAG,"Error Updating Record: "+e.toString());
				}
			}else{
				Log.d(TAG, String.format("Record already exists in table %s", tableName));
			}
			
		} else {
			try {
				recordid = db.insertOrThrow(tableName, null, tableRecord);
				Log.d(TAG, String.format("Inserting into table %s", tableName));
			}
			catch (RuntimeException e) {
				Log.e(TAG,"Indicatoir Insertion Exception: "+e.toString());
			}
		}
		cursor.close();
		db.close();
		return recordid;
	}
	
	/******************************
	 * Application Search Functions
	 ******************************/
	
	/**
	 * Searches database for the data related to a particular country. Returns int code 
	 * indicating status of the data and query
	 * @param countryName
	 * @return AreaConstants Search Code 
	 */
	public int getCountryInfo(String countryName) {
		//Check if info in database
			// return result if locally present
			// otherwise send query to Service
		return SEARCH_FAIL;
	}
	
	/**
	 * @param countryID
	 * @return AreaConstants Search Code
	 */
	public int getCountryInfo(int countryID) {
		
		return SEARCH_FAIL;
	}
	
	/**
	 * Global Search function that returns reports and articles for a single search term 
	 * @param searchPhrase
	 * @return AreaConstants Search Code
	 */
	public int globalSearch(String searchPhrase) {
		return SEARCH_FAIL;
	}
	
	/**
	 * Search function utilized by application to get data related to contexual usage
	 * @param dataSource Code for data source query to be run against (WB|IDS|Bing)
	 * @param indicatorID	Indicator ID
	 * @param country	Array of country ids
	 * @return AreaConstants Search Code
	 */
	public int genericSearch(int dataSource, String indicatorID, String[] country) {
		//format data for querying
		
		Cursor api_result1, api_result2, api_result3, ind_result, country_result, country_IDresult;
		int ind_id, search_id, country_id = -1, period, in_country_id;
		String params1, params2, params3, wb_country_id = "";
		
		boolean has_country = false;
		String s_table = SEARCH;
		countries_to_get = new ArrayList<String>();
		countryIDs		 = new ArrayList<Integer>();
		
		// get indicator ID from db
		ind_result = dbHelper.rawQuery(INDICATOR, "*" , "" + WB_INDICATOR_ID + " ='" + indicatorID + "'");
		
		if (ind_result.getCount() != 1){
			return FATAL_ERROR;
		}else{
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
			ind_result.moveToFirst();
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));
			ind_id = Integer.parseInt(ind_result.getString(0));
			
		}
		ind_result.close();
		
		// if user opts out of synchronized search, then search only indicator that is passed in
		if (SEARCH_SYNC){
			params1 = "" + AP_ID + "='" + 1 + "' and " + I_ID + " ='" + ind_id + "'";
			params2 = "" + AP_ID + "='" + 2 + "' and " + I_ID + " ='" + ind_id + "'";
			params3 = "" + AP_ID + "='" + 3 + "' and " + I_ID + " ='" + ind_id + "'";
			
			// query search table for API-Indicator combination. 
			api_result1 = dbHelper.rawQuery(s_table, "*", params1);
			api_result2 = dbHelper.rawQuery(s_table, "*", params2);
			api_result3 = dbHelper.rawQuery(s_table, "*", params3);
			
			//only one search result should be returned per indicator and api combination.
			
			if (api_result1.getCount() == 1){
				//check db information starting with country data. 
				api_result1.moveToFirst();
				// if data exist for combination, check if there exist data exist for all countries.
				// get countries list related to search record
				country_result = dbHelper.rawQuery(SEARCH_COUNTRY, "*", ""+ S_ID +" ='" + api_result1.getInt(api_result1.getColumnIndex(_ID)) +"'");
				if (country_result.getCount() > 0){
					// check to see if country params for the current search are already within the database.
					// Loop through the list of countries currently being searched for, Checking each against the list returned for the current indicator
					for (int n = 0; n < country.length; n++){
						// get country ID from country table using the country name or WB_Country _ID passed in
						country_IDresult = dbHelper.rawQuery(COUNTRY,"*", ""+ COUNTRY_NAME +" ='" + country[n] +"'");
						
						if (country_IDresult.getCount() == 1){
							country_IDresult.moveToFirst();
							wb_country_id	= country_IDresult.getString(country_IDresult.getColumnIndex(WB_COUNTRY_ID));
							in_country_id = country_IDresult.getInt(country_IDresult.getColumnIndex(COUNTRY_ID));
							//Check through list of countries returned for search record for countries passed in
							country_result.moveToFirst();
							whileloop:while(! country_result.isAfterLast()){
								
								country_id 		= country_result.getInt(country_result.getColumnIndex(C_ID));
								period			= country_result.getInt(country_result.getColumnIndex(P_ID));
								
								if (country_id == in_country_id){
									has_country = true;
									break whileloop;
								}
								
								country_result.moveToNext();
							}
							// if current country record is not in the db the update COUNTRIES_TO_GET array with country	
							if(has_country){
								has_country = false;
							}else{
								countries_to_get.add(wb_country_id);
								countryIDs.add(in_country_id);
							}
							
							
						}else{
							Log.e(TAG,"Error in retrieving Country information: " + country_IDresult.getCount() + " rows returned");
						}
						country_IDresult.close();
					}// end for
					
				}else{
					// if 0 rows were returned, return error. As Initial search would have returned at least 1 country info. 
					Log.e(TAG,"Error in retrieving Country information: " + country_result.getCount() + " rows returned");
				}
					
				// if some countries are missing then update
				country_result.close();
				
			}else{
			// if combination does not exist the get data for all countries.
				
				for(int n = 0; n < country.length; n++){
					
					//get ccountry id to add to list of countries to retrieve
					country_IDresult = dbHelper.rawQuery(COUNTRY,"*", ""+ COUNTRY_NAME +" ='" + country[n] +"'");
					if(country_IDresult.getCount() != 1){
						Log.e(TAG,"Error in retrieving Country information: " + country_IDresult.getCount() + " rows returned");
					}else{
						country_IDresult.moveToFirst();
						country_IDresult.getInt(country_IDresult.getColumnIndex(WB_COUNTRY_ID));
						countries_to_get.add(country_IDresult.getString(country_IDresult.getColumnIndex(WB_COUNTRY_ID)));
						countryIDs.add(country_IDresult.getInt(country_IDresult.getColumnIndex(COUNTRY_ID)));
					}
					country_IDresult.close();
				}
				
				
			}
			if(!countries_to_get.isEmpty()){
				getCountryIndicators(ind_id, indicatorID, countries_to_get, countryIDs, "date=1990:2012");
			}else{
				Log.e(TAG, "No Values to get :)");
			}
			
		}else{
			//Search only for indicator passed in
		}
		// if all APIs should be searched, then start with one passed in.
		api_result1.close();
		api_result2.close();
		api_result3.close();
		
		
		
		return SEARCH_FAIL;
	}
	
	public Cursor getIndicatorList(){
		Cursor result; 
		
		result = dbHelper.rawQuery(INDICATOR, INDICATOR_NAME, "");
		
		return result;
	}
	
	public Cursor getCountryList(){
		Cursor result; 
		
		result = dbHelper.rawQuery(COUNTRY, COUNTRY_NAME, "");
		
		return result;
	}

	public boolean inDatabase(String s_table, String params){
		Cursor s_result; 
		
		// query search table for API-Indicator combination. 
		s_result = dbHelper.rawQuery(s_table, "*", params);
		
		// if data exist for combination, check if there exist data exist for all countries. 
		if (s_result.getCount() != 0){
			// if some countries are missing then update
			
			 
		}else{
		// if combination does not exist the get data for all countries.
		
		}
		return true;
	}
	
	public int getCountryIndicators(int indicator_id, String indicator, ArrayList<String> countries, ArrayList<Integer> countryIDList, String date){
		parser = new JSONParse(context);
		String queryStr = "http://api.worldbank.org/countries/";
		String countryString = "";
		String[] countryArray;
		Integer[] countryIDArray;
		Log.e(TAG, "Num of Countries:" + countryIDList.size());
		countryArray = (String[]) countries.toArray(new String[countries.size()]);
		countryIDArray = (Integer[])countryIDList.toArray(new Integer[countryIDList.size()]);
		
		
		for(int n = 0; n < countryArray.length; n++){
			// create country String for API call
			if(n == 0){
				countryString = countryArray[n];
			}else{
				countryString = countryString + ";" + countryArray[n];
			}
		}
		
		queryStr = queryStr + countryString + "/indicators/" + indicator + "?per_page=1&" + date + "&format=json";
		
		int numOfRecords = parser.getWBTotal(dataService.HTTPRequest(0,queryStr));
		if(numOfRecords == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data:" + queryStr);
			return 0;
		}else{
			
			queryStr = "http://api.worldbank.org/countries/" + countryString + "/indicators/" + indicator + "?per_page="+ numOfRecords +"&" + date + "&format=json";
			parser.parseWBData(dataService.HTTPRequest(0,queryStr), indicator_id, countryIDArray, queryStr);
			return 1;
		}
		
		
	}
	
	public Cursor rawQuery(String tableName, String tableColumns, String queryParams) {
		
		Cursor cursor = null;
		cursor = dbHelper.rawQuery(tableName, tableColumns, queryParams);
		
		return cursor;
	}
	
	
	private class AreaDB extends SQLiteOpenHelper{
		
		private static final int DATABASE_VERSION = 53;
		private SQLiteDatabase db;
		
		
		public AreaDB(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		private static final String CREATE_TABLE_COUNTRY = "create table " + COUNTRY + " ( "
				+ COUNTRY_ID 			+ " integer primary key autoincrement, "
				+ WB_COUNTRY_ID 		+ " text not null, "
				+ WB_COUNTRY_CODE		+ " text not null, "
				+ COUNTRY_NAME 			+ " text not null, "
				+ CAPITAL_CITY			+ " text not null, "
				+ INCOME_LEVEL_ID 		+ " text not null, "
				+ INCOME_LEVEL_NAME 	+ " text not null, "
				+ POPULATION 			+ " integer, "
				+ COUNTRY_REGION_ID 	+ " text not null, "
				+ COUNTRY_REGION_NAME 	+ " text not null, "
				+ GDP 					+ " integer, "
				+ GNI_CAPITA 			+ " integer, "
				+ POVERTY 				+ " integer, "
				+ LIFE_EX 				+ " integer, "
				+ LITERACY 				+ " integer)";
		
		
		private static final String CREATE_TABLE_INDICATOR = "create table " + INDICATOR + " ( "
				+ INDICATOR_ID 		+ " integer primary key autoincrement, "
				+ WB_INDICATOR_ID 	+ " integer not null, "
				+ INDICATOR_NAME 	+ " text not null, "
				+ INDICATOR_DESC 	+ " text not null )";

		private static final String CREATE_TABLE_SEARCH = "create table " + SEARCH + " ( "
				+ SEARCH_ID 		+ " integer primary key autoincrement, "
				+ I_ID				+ " integer not null, "
				+ AP_ID 			+ " integer not null, "
				+ SEARCH_CREATED 	+ " datetime not null, "
				+ SEARCH_MODIFIED 	+ " datetime not null, "
				+ SEARCH_URI 		+ " text not null )" ;
		
		private static final String CREATE_TABLE_API = "create table " + API + " ( "
				+ API_ID 			+ " integer primary key autoincrement, "
				+ API_NAME 			+ " text not null, "
				+ API_DESC 			+ " text not null," 
				+ BASE_URI 			+ " text not null )";
		
		private static final String CREATE_TABLE_PARAMETER = "create table " + PARAMETER + " ( "
				+ PARAM_ID 	+ " integer primary key autoincrement, "
				+ S_ID 		+ " integer not null, "
				+ PARAM		+ " text not null )";
		
		private static final String CREATE_TABLE_WB_DATA = "create table " + WB_DATA + " ( "
				+ WB_DATA_ID 	+ " integer primary key autoincrement, "
				//+ I_ID 			+ " integer not null, "
				//+ C_ID 			+ " integer not null, "
				//+ S_ID 			+ " integer not null, "
				+ SC_ID 		+ " integer not null, "
				+ IND_VALUE 	+ " integer not null, "
				+ IND_DECIMAL 	+ " integer not null, "
				+ IND_DATE 		+ " integer not null ) ";
			
		private static final String CREATE_TABLE_SEARCH_COUNTRY = "create table " + SEARCH_COUNTRY + " ( "
				+ _ID 	+ " integer primary key autoincrement, "
				+ S_ID 	+ " integer not null, "
				+ C_ID 	+ " integer not null, "
				+ P_ID 	+ " integer not null )";
		
		private static final String CREATE_TABLE_PERIOD = "create table " + PERIOD + " ( "
				+ PERIOD_ID 	+ " integer primary key autoincrement, "
				+ PERIOD_NAME 	+ " text not null, "
				+ P_START_DATE 	+ " integer not null, "
				+ P_END_DATE 	+ " integer not null )";

		private static final String CREATE_TABLE_IDS_DATA = "create table " + IDS_DATA  + " ( "
				+ DOCUMENT_ID 		+ " integer primary key autoincrement, "
				+ S_ID				+ " integer not null, "
				+ DOC_TITLE 		+ " text not null, "
				+ LANGUAGE_NAME		+ " text not null, "
				+ LICENCE_TYPE 		+ " text not null, "
				+ PUBLICATION_DATE 	+ " datetime not null, "
				+ PUBLISHER 		+ " text not null, "
				+ PUBLISHER_COUNTRY + " text not null, "
				+ JOURNAL_SITE 		+ " text not null, "
				+ DOC_NAME 			+ " text not null, "
				+ DATE_CREATED		+ " datetime not null, "
				+ DATE_UPDATED 		+ " datetime not null, "
				+ WEBSITE_URL 		+ " text not null )";
		
		private static final String CREATE_TABLE_IDS_AUTHOR = "create table " + IDS_AUTHOR + " ( "
				+ AUTHOR_ID		 + " integer primary key autoincrement, "
				+ D_ID			 + " integer not null, "
				+ AUTHOR_NAME	 + " text not null )";
		
		private static final String CREATE_TABLE_IDS_DOC_THEME = "create table " + IDS_DOC_THEME + " ( "
				+ _ID 		+ " integer primary key autoincrement, "
				+ T_ID 		+ " integer not null, "
				+ D_ID 		+ " integer not null )";
		
		private static final String CREATE_TABLE_IDS_THEME = "create table " + IDS_THEME + " ( "
				+ THEME_ID 		+ " integer primary key autoincrement, "
				+ THEME_NAME 	+ " text not null, "
				+ IDS_THEME_ID 	+ " text not null, "
				+ THEME_URL 	+ " text not null, "
				+ THEME_LEVEL 	+ " integer not null)" ;
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE_COUNTRY															);
				Log.d("AREA", "Create COUNTRY table: " + CREATE_TABLE_COUNTRY					);
				
				db.execSQL(CREATE_TABLE_INDICATOR														);
				Log.d("AREA", "Create INDICATOR table: " + CREATE_TABLE_INDICATOR				);
				
				db.execSQL(CREATE_TABLE_SEARCH															);
				Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_SEARCH					);
				
				db.execSQL(CREATE_TABLE_API																);
				Log.d("AREA", "Create API table: " + CREATE_TABLE_API							);
				
				db.execSQL(CREATE_TABLE_PARAMETER														);
				Log.d("AREA", "Create PARAMETER table: " + CREATE_TABLE_PARAMETER				);
				
				db.execSQL(CREATE_TABLE_WB_DATA															);
				Log.d("AREA", "Create WB_DATA table: " + CREATE_TABLE_WB_DATA					);
				
				db.execSQL(CREATE_TABLE_SEARCH_COUNTRY													);
				Log.d("AREA", "Create SEARCH_COUNTRY table: " + CREATE_TABLE_SEARCH_COUNTRY	);

				db.execSQL(CREATE_TABLE_PERIOD															);
				Log.d("AREA", "Create PERIOD table: " + CREATE_TABLE_PERIOD					);
				
				db.execSQL(CREATE_TABLE_IDS_DATA														);
				Log.d("AREA", "Create IDS_DATA table: " + CREATE_TABLE_IDS_DATA				);
				
				db.execSQL(CREATE_TABLE_IDS_AUTHOR														);
				Log.d("AREA", "Create IDS_AUTHOR table: " + CREATE_TABLE_IDS_AUTHOR			);
				
				db.execSQL(CREATE_TABLE_IDS_DOC_THEME													);
				Log.d("AREA", "Create IDS_DOC_THEME table: " + CREATE_TABLE_IDS_DOC_THEME		);
				
				db.execSQL(CREATE_TABLE_IDS_THEME														);
				Log.d("AREA", "Create IDS_THEME table: " + CREATE_TABLE_IDS_THEME				);
				
				
			} catch (RuntimeException e) {
			Log.d("AREA", "Unable to create tables: ");
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			Log.w("AREA", "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			try {
				db.execSQL("DROP TABLE IF EXISTS " + COUNTRY		);
				db.execSQL("DROP TABLE IF EXISTS " + INDICATOR		);
				db.execSQL("DROP TABLE IF EXISTS " + SEARCH			);
				db.execSQL("DROP TABLE IF EXISTS " + API			);
				db.execSQL("DROP TABLE IF EXISTS " + PARAMETER		);
				db.execSQL("DROP TABLE IF EXISTS " + WB_DATA		);
				db.execSQL("DROP TABLE IF EXISTS " + SEARCH_COUNTRY	);
				db.execSQL("DROP TABLE IF EXISTS " + PERIOD			);
				db.execSQL("DROP TABLE IF EXISTS " + IDS_DATA		);
				db.execSQL("DROP TABLE IF EXISTS " + IDS_AUTHOR		);
				db.execSQL("DROP TABLE IF EXISTS " + IDS_DOC_THEME	);
				db.execSQL("DROP TABLE IF EXISTS " + IDS_THEME 		);
			} catch (SQLException e) {
				Log.d("AREA", "Upgrade step: " + "Unable to DROP TABLES");
			}

			onCreate(db);
		}
		
		public Cursor rawQuery(String tableName, String tableColumns, String queryParams) {
			db = this.getReadableDatabase();
			Cursor cursor = null;
			String query;
			if (queryParams.equals("")){
				query = "SELECT "+ tableColumns + " FROM " + tableName ;
			}else{
				query = "SELECT "+ tableColumns + " FROM " + tableName +" WHERE " + queryParams;
			}
			
			try {
				cursor = db.rawQuery(query, null);
				Log.d("AREA", "Raw Query: " + query);
				Log.d("AREA", "Raw Query Result: Returned " + cursor.getCount() + " record(s)");
			} catch (SQLException e) {
				Log.e("AREA", "Raw Query Exception: " + e.toString());
			}
			db.close();
			return cursor;
		}
		
		
	}
}
