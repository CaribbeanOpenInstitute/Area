package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.API_LIST;
import static jm.org.data.area.AreaConstants.BING_RESULT_DATA;
import static jm.org.data.area.AreaConstants.BING_SEARCH;
import static jm.org.data.area.AreaConstants.BING_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.CATEGORY_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.DATA_TYPES_LIST;
import static jm.org.data.area.AreaConstants.FATAL_ERROR;
import static jm.org.data.area.AreaConstants.IDS_PARAM_DATA;
import static jm.org.data.area.AreaConstants.IDS_RESULT_DATA;
import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.IDS_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.INDICATOR_KEYWORDS;
import static jm.org.data.area.AreaConstants.INDICATOR_LIST;
import static jm.org.data.area.AreaConstants.IND_CATEGORIES_DATA;
import static jm.org.data.area.AreaConstants.PERIOD_LIST;
import static jm.org.data.area.AreaConstants.RETURN_CNTRY_IDs;
import static jm.org.data.area.AreaConstants.RETURN_COUNTRIES;
import static jm.org.data.area.AreaConstants.RETURN_DATE;
import static jm.org.data.area.AreaConstants.RETURN_IND_ID;
import static jm.org.data.area.AreaConstants.RETURN_STRING;
import static jm.org.data.area.AreaConstants.RETURN_VALUE;
import static jm.org.data.area.AreaConstants.RETURN_WB_IND_ID;
import static jm.org.data.area.AreaConstants.SEARCH_API_NONE;
import static jm.org.data.area.AreaConstants.SEARCH_DATA;
import static jm.org.data.area.AreaConstants.SEARCH_FAIL;
import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.SELECTIONS_DATA;
import static jm.org.data.area.AreaConstants.SUCCESS;
import static jm.org.data.area.AreaConstants.WB_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.*;
import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.DBConstants.API_DESC;
import static jm.org.data.area.DBConstants.API_NAME;
import static jm.org.data.area.DBConstants.AP_ID;
import static jm.org.data.area.DBConstants.AREA_SELECTIONS;
import static jm.org.data.area.DBConstants.BASE_URI;
import static jm.org.data.area.DBConstants.BING_QUERY;
import static jm.org.data.area.DBConstants.BING_SEARCH_ID;
import static jm.org.data.area.DBConstants.BING_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.BING_SEARCH_TABLE;
import static jm.org.data.area.DBConstants.BING_URL;
import static jm.org.data.area.DBConstants.B_S_ID;
import static jm.org.data.area.DBConstants.CAT_ID;
import static jm.org.data.area.DBConstants.CHART_DESC;
import static jm.org.data.area.DBConstants.CHART_NAME;
import static jm.org.data.area.DBConstants.COUNTRY;
import static jm.org.data.area.DBConstants.COUNTRY_ID;
import static jm.org.data.area.DBConstants.COUNTRY_NAME;
import static jm.org.data.area.DBConstants.C_ID;
import static jm.org.data.area.DBConstants.DATA_TYPES;
import static jm.org.data.area.DBConstants.DATA_TYPE_DESC;
import static jm.org.data.area.DBConstants.DATA_TYPE_NAME;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_PARAMETER;
import static jm.org.data.area.DBConstants.IDS_PARAM_VALUE;
import static jm.org.data.area.DBConstants.IDS_P_ID;
import static jm.org.data.area.DBConstants.IDS_SEARCH_ID;
import static jm.org.data.area.DBConstants.IDS_SEARCH_PARAMS;
import static jm.org.data.area.DBConstants.IDS_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.IDS_SEARCH_TABLE;
import static jm.org.data.area.DBConstants.IDS_S_ID;
import static jm.org.data.area.DBConstants.IDS_VIEW_DATE;
import static jm.org.data.area.DBConstants.INDICATOR;
import static jm.org.data.area.DBConstants.INDICATOR_ID;
import static jm.org.data.area.DBConstants.INDICATOR_NAME;
import static jm.org.data.area.DBConstants.IND_CATEGORIES;
import static jm.org.data.area.DBConstants.IND_DATE;
import static jm.org.data.area.DBConstants.IND_VALUE;
import static jm.org.data.area.DBConstants.I_ID;
import static jm.org.data.area.DBConstants.PERIOD;
import static jm.org.data.area.DBConstants.PERIOD_NAME;
import static jm.org.data.area.DBConstants.P_END_DATE;
import static jm.org.data.area.DBConstants.P_START_DATE;
import static jm.org.data.area.DBConstants.QUERY_DATE;
import static jm.org.data.area.DBConstants.QUERY_VIEW_DATE;
import static jm.org.data.area.DBConstants.SC_ID;
import static jm.org.data.area.DBConstants.SEARCH;
import static jm.org.data.area.DBConstants.SEARCH_COUNTRY;
import static jm.org.data.area.DBConstants.SEARCH_VIEWED;
import static jm.org.data.area.DBConstants.SELECTION_DESC;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.S_ID;
import static jm.org.data.area.DBConstants.WB_CATEGORY;
import static jm.org.data.area.DBConstants.WB_CATEGORY_ID;
import static jm.org.data.area.DBConstants.WB_COUNTRY_ID;
import static jm.org.data.area.DBConstants.WB_DATA;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;



public class AreaData {

	private static final String TAG = AreaData.class.getSimpleName();
	//private int currentApiVersion = android.os.Build.VERSION.SDK_INT;
	
	private ContentValues values;
	private Context context;
	//AreaDB dbHelper;
	public ContentResolver areaResolver;
	
	public JSONParse parser;
	public APIPull dataService;
	
	private ContentValues apiRecord;
	
	private ArrayList<String> countries_to_get;
	private ArrayList<Integer> countryIDs;
	private String[] keyWords;
	
	private Uri providerUri;
	
	public SharedPreferences prefs;
	
	private int tableCode ;
	private long recordid ;
	
	private Cursor cursorCountry= null, retCursor = null;
	
	
	
	public AreaData(Context context){
		Log.e(TAG, "Initialize Area Data");
		this.context = context;
		//dbHelper = new AreaDB(context);
		areaResolver = context.getContentResolver();
		dataService = new APIPull();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * Initialize and Update Tables at Startup
	 */
	public void updateAPIs(){
		Log.e(TAG, "Updating APIs");
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
		Log.e(TAG, "Updating Period Values");
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
	
	public void updateDataTypes(){
		
		Log.e(TAG, "Updating Data Types Values");
			
		values = new  ContentValues();
		values.put(DATA_TYPE_NAME, "Charts");
		values.put(DATA_TYPE_DESC, "Charts generated by the application based" +
				" on the values of indicators for various countries over a period of time");
		insert(DATA_TYPES, values,0);

		values = new  ContentValues();
		values.put(DATA_TYPE_NAME, "Reports");
		values.put(DATA_TYPE_DESC, "Reports are academic works pulled from the IDS document API");
		insert(DATA_TYPES, values,0);
		
		values = new  ContentValues();
		values.put(DATA_TYPE_NAME, "Articles");
		values.put(DATA_TYPE_DESC, "Articles pulled from the web related to the users searches or particular indicators");
		insert(DATA_TYPES, values,0);

	}
	
	public void updateSelections(){
		Log.e(TAG, "Updating User Selections");
		
		values = new  ContentValues();
		values.put(SELECTION_NAME, "Indicators");
		values.put(SELECTION_DESC, "Indicators listed by categories pulled" +
				" from the systems Indicator data source. See Data source Listing");
		insert(AREA_SELECTIONS, values,0);

		values = new  ContentValues();
		values.put(SELECTION_NAME, "Country Profiles");
		values.put(SELECTION_DESC, "Full Country Profiles displaying the Economic status of and Major Economic Indicators");
		insert(AREA_SELECTIONS, values,0);
		
		values = new  ContentValues();
		values.put(SELECTION_NAME, "Collections");
		values.put(SELECTION_DESC, "Collections contain Saved Data that " +
				"has been grouped together around a users chosen theme or other preferences");
		insert(AREA_SELECTIONS, values,0);
		
		values = new  ContentValues();
		values.put(SELECTION_NAME, "Saved Items");
		values.put(SELECTION_DESC, "Saved Items allow users to save and review Charts," +
				" Articles and Reports Sharing or Viewing at their leasure");
		insert(AREA_SELECTIONS, values,0);
	}
	
	public void updateCategories(){
		Log.e(TAG, "Updating Categories");
		// pull data and put in database
		parser = new JSONParse(context);
		// error right here
		int numOfCategories = parser.getWBTotal(dataService.HTTPRequest(0,
				"http://api.worldbank.org/topic?per_page=1&format=json"));
		if(numOfCategories == 0 ){
			// error in parsing JSON data
			Log.e(TAG, "Error In Parsing JSON data");
		}else{ 
			parser.parseCategories(dataService.HTTPRequest(0,
					"http://api.worldbank.org/topic?per_page="+ numOfCategories +"&format=json"));
		}
	}

	public void updateIndicators(){
		Log.e(TAG, "Updating Indicators");
		// pull data and put in database
		parser = new JSONParse(context);
		/*
		Cursor result= //dbHelper.rawQuery(INDICATOR, null, "");
				rawQuery(WB_CATEGORY, new String[]{CATEGORY_ID}, "");
		// get indicators for each category
		Log.e(TAG, "CATEGORIES: " + result.getCount());
		while(result.moveToNext()){
			int topic = result.getInt(result.getColumnIndex(CATEGORY_ID));
			
			int numOfIndicators = parser.getWBTotal(dataService.HTTPRequest(0,
					"http://api.worldbank.org/topic/" + topic+ "/Indicator?per_page=1&format=json"));
			if(numOfIndicators == 0 ){
				// error in parsing JSON data
				Log.e(TAG, "Error In Parsing JSON data");
			}else{ 
				parser.parseIndicators(dataService.HTTPRequest(0,
						"http://api.worldbank.org/topic/" + topic+ "/Indicator?per_page="+ numOfIndicators +"&format=json"));
			}
		}*/
		
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
		Log.e(TAG, "Updating Countries");
		parser = new JSONParse(context);
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
	 * @param countryID
	 * @return AreaConstants Search Code
	 */
	public int getCountryInfo(int countryID) {
	
		return SEARCH_FAIL;
	}

	/**
	* Insert record into specified table. Includes duplication check to prevent double entries
	*
	* @param tableName Name of table record to be inserted into
	* @param tableRecord Name-value pairs
	*/
	synchronized public long insert(String tableName, ContentValues tableRecord, int update) {
		Log.i(TAG, "Inserting/Updating Data in DB for Table :" + tableName);
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		providerUri = Uri.parse(AreaProvider.AUTHORITY+"/"+tableName);		
		tableCode = AreaApplication.getTableCode(tableName);
		recordid = 0;
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
			case CATEGORY_LIST:
				tableKey = WB_CATEGORY_ID;
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
			case IDS_SEARCH_DATA:
				tableKey = I_ID;
				break;
			case IDS_PARAM_DATA:
				tableKey = IDS_PARAMETER;
				tableKeyAdd = IDS_PARAM_VALUE;
				break;
			case IDS_RESULT_DATA:
				tableKey = IDS_DOC_ID;
				break;
			case BING_SEARCH_DATA:
				tableKey = BING_QUERY;
				break;
			case BING_RESULT_DATA:
				tableKey = BING_URL;
				break;
			case IND_CATEGORIES_DATA:
				tableKey = CAT_ID;
				tableKeyAdd = I_ID;
				break;
			case SELECTIONS_DATA:
				tableKey = SELECTION_NAME;
				break;
			case DATA_TYPES_LIST:
				tableKey = 	DATA_TYPE_NAME;
				break;
			case CHART_DATA:
				tableKey = 	I_ID;
				tableKeyAdd = CHART_COUNTRIES;
				break;
			case GET_DATA:
				tableKey = 	D_T_ID;
				tableKeyAdd = ENTITY_ID;
				break;
		}
		try{
			//if there is an additional table key use both to check for duplication
			if (!tableKeyAdd.equals("")) {
				cursor = //db.query(tableName, null, String.format("%s='%s' AND %s='%s'", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null, null, null);
						areaResolver.query(providerUri, null, String.format("%s='%s' AND %s='%s'", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null);
				Log.e(TAG,String.format("Insert Query %s='%s' AND %s='%s'", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)));
			} else if(tableKey == ""){
				cursor = null;
			}else { //otherwise only check single key
				//cursor = db.query(tableName, null, String.format("%s=%s AND %s=%s", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null, null, null);
				cursor = //db.query(tableName, null, tableKey + "='" + tableRecord.get(tableKey) + "'", null, null, null, null);
						areaResolver.query(providerUri, null, tableKey + "='" + tableRecord.get(tableKey) + "'", null, null);
				Log.e(TAG,String.format("Insert Query %s='%s'", tableKey, tableRecord.get(tableKey)));
			}
			if (cursor == null){
				try {
					recordid = //db.insertOrThrow(tableName, null, tableRecord);
							ContentUris.parseId(areaResolver.insert(providerUri, tableRecord));
					Log.d(TAG, String.format("Inserting into table %s", tableName));
				}
				catch (RuntimeException e) {
					
					Log.e(TAG,String.format(""+ tableName +" Insertion Exception: Table: %s -> Table Key:%s => value:%s \nValues %s\n %s",
							tableName, tableKey, tableRecord.get(tableKey), tableRecord.toString(), e.toString()));
				}
			}else if (cursor.getCount() > 0) {
				if(update == 1){
					try {
						cursor.moveToFirst();
						recordid = cursor.getInt(cursor.getColumnIndex(_ID));
						recordid = //db.update(tableName, tableRecord, "" + _ID  + " ='" + recordid + "'", null );
								areaResolver.update(providerUri, tableRecord, "" + _ID  + " ='" + recordid + "'", null );

						if(recordid != 1){
							Log.e(TAG,"Error Updating "+ tableName +" Record: " + cursor.getInt(cursor.getColumnIndex(_ID)) + "rows affected = " + recordid);
						}else{
							Log.d(TAG, "Updating "+ tableName +" Record: " + cursor.getInt(cursor.getColumnIndex(_ID)));
						}

					}
					catch (RuntimeException e) {
						Log.e(TAG,"Error Updating Record: "+ cursor.getCount()+ "--=>" +e.toString());
					}
				}else{
					Log.d(TAG, String.format("Record already exists in table %s", tableName));
				}

			} else {
				try {
					recordid = //db.insertOrThrow(tableName, null, tableRecord);
							ContentUris.parseId(areaResolver.insert(providerUri, tableRecord));
					Log.d(TAG, String.format("Inserting into table %s", tableName));
				}
				catch (RuntimeException e) {
					
					Log.e(TAG,String.format(""+ tableName +" Insertion Exception: Table: %s -> Table Key:%s => value:%s \nValues %s\n %s",
							tableName, tableKey, tableRecord.get(tableKey), tableRecord.toString(), e.toString()));
				}
			}
			cursor.close();
			
		}catch(Exception e){
			Log.e(TAG,"Cursor Exception: "+e.toString());
		}

		return recordid;
	}

	public int delete(String table, String where_clause){
		Log.e(TAG, "Deleting DB Values");
		areaResolver = context.getContentResolver();
		providerUri = Uri.parse(AreaProvider.AUTHORITY+"/"+table); 
		
		int rows_affected;
		//try{
		rows_affected = //db.delete(table, where_clause, null);
				areaResolver.delete(providerUri, where_clause, null);
		//}catch(Exception e){

		//}

		return rows_affected;

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
	 * Global Search function that returns reports and articles for a single search term 
	 * @param searchPhrase
	 * @return AreaConstants Search Code
	 */
	public synchronized int globalSearch(int API, String searchPhrase) {
		Log.e(TAG, "Global Search");
		Calendar today, searchDate;
		today = Calendar.getInstance();
		Cursor ids_result, bing_result, records_to_delete;
		String ids_table = IDS_SEARCH_PARAMS, bing_table = BING_SEARCH_TABLE;
		String params, bingParam, deleteParam, ids_param_str = "";
		int num_to_delete, num_deleted;


		// for BING search check if the searchPrase has been the subject of a previous search

		bingParam = "" + BING_QUERY + " ='" + searchPhrase + "'";
		bing_result = //dbHelper.rawQuery(bing_table, null, bingParam);
				rawQuery(bing_table, null, bingParam);

		//if it was the subject then data exists in the database
		if (bing_result.getCount() ==1 ){
			bing_result.moveToFirst();
			// check if the previous search is over a week old
			searchDate = getDate(bing_result.getString(bing_result.getColumnIndex(QUERY_DATE)));
			if ((today.get(Calendar.WEEK_OF_YEAR) - searchDate.get(Calendar.WEEK_OF_YEAR)) >= 7){
				// if it is then fetch new results from the API, delete old results. 
				// delete old results first
				//get current search ID
				deleteParam = "" + B_S_ID + " ='" + bing_result.getString(bing_result.getColumnIndex(BING_SEARCH_ID)) + "'";
				// get number of results to delete - for error checking 
				records_to_delete = //dbHelper.rawQuery(BING_SEARCH_RESULTS, null, deleteParam);
						rawQuery(BING_SEARCH_RESULTS, null, deleteParam);
				num_deleted = delete(BING_SEARCH_RESULTS, deleteParam);
				num_to_delete = records_to_delete.getCount();
				if (num_to_delete != num_deleted){
					records_to_delete.close();
					bing_result.close();
					return FATAL_ERROR;
				}
				records_to_delete.close();
				bing_result.close();
				getBingArticles(searchPhrase);
			}
			return SEARCH_SUCCESS;
		}else{
			bing_result.close();
			//if searchPhrase was not the subject of a previous search then fetch data from BING API
			if (getBingArticles(searchPhrase) == SEARCH_FAIL){
				return SEARCH_FAIL;
			}


		}

		//separate individual keywords from searchPhrase
		keyWords = searchPhrase.split(" ");
		// Format to IDS parameters.
		for(int n = 0; n < keyWords.length; n++){
			if(n==0){
				ids_param_str = ids_param_str + keyWords[n];
			}else{
				ids_param_str = ids_param_str + "%26" + keyWords[n];
			}
		}
		Log.e(TAG,"Array: " + Arrays.toString(keyWords) + " input value: " + searchPhrase);
		// Check IDS_SEARCH_PARAMS table to see if parameters exist for any previous searches
		params    = "" + IDS_PARAM_VALUE + " ='" + ids_param_str + "'";
		ids_result 	= //dbHelper.rawQuery(ids_table, null, params);
				rawQuery(ids_table, null, params);
		// if results are found for this indicator then we assume that all relevant articles would be in the database
		if (ids_result.getCount() > 0){
			ids_result.close();
			return SEARCH_SUCCESS;
		}else{
			ids_result.close();
			getDocuments(0, keyWords);
			return SEARCH_SUCCESS;
		}		




	}

	/**
	 * Search function utilized by application to get data related to contextual usage
	 * @param dataSource Code for data source query to be run against (WB|IDS|Bing)
	 * @param indicatorID	Indicator ID
	 * @param country	Array of country ids
	 * @return AreaConstants Search Code
	 */
	synchronized public int genericSearch(int dataSource, String indicatorID, String[] country) {
		Log.e(TAG, "Generic Search");
		//format data for querying
		Hashtable<String, Object> return_data = new Hashtable<String, Object>();

		Cursor wb_result, ids_result, bing_result, ind_result, country_result, country_IDresult;
		int ind_id,  country_id = -1, in_country_id; //, period;
		String params, bingParam,  wb_country_id = "";
		Calendar today, searchDate;
		today = Calendar.getInstance();
		Cursor  records_to_delete;
		String  deleteParam;
		int num_to_delete, num_deleted;

		boolean has_country = false;
		String wb_table = SEARCH, ids_table = IDS_SEARCH_TABLE, bing_table = BING_SEARCH_TABLE;
		String indicatorStr;
		countries_to_get = new ArrayList<String>();
		countryIDs		 = new ArrayList<Integer>();

		// get indicator ID from db
		ind_result = //dbHelper.rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicatorID + "'");
				rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicatorID + "'");

		if (ind_result.getCount() != 1){

			return_data.put(RETURN_VALUE, "" + FATAL_ERROR);
			ind_result.close();
			return FATAL_ERROR;
		}else{
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
			ind_result.moveToFirst();
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));
			ind_id = Integer.parseInt(ind_result.getString(ind_result.getColumnIndexOrThrow(_ID)));
			indicatorStr = ind_result.getString(ind_result.getColumnIndexOrThrow(INDICATOR_NAME));

			int pos;

			// find position of first parenthesis or comma to extract relevant words.
			pos = indicatorStr.indexOf(",");
			if (pos < 0){
				pos = indicatorStr.indexOf("(");
			}
			// remove section of string after the comma or within and after the parenthesis
			if(pos > 0){
				indicatorStr = indicatorStr.substring(0, pos-1);
			}

		}
		ind_result.close();

		// if user opts out of synchronized search, then search only indicator that is passed in
		if (dataSource == WORLD_SEARCH){
			params    = "" + I_ID + " ='" + ind_id + "'";

			// query search table for API-Indicator combination. 
			wb_result 	= //dbHelper.rawQuery(wb_table, null, params);
					rawQuery(wb_table, null, params);


			//only one search result should be returned per indicator and api combination.
			if (wb_result.getCount() == 1){
				//check db information starting with country data. 
				wb_result.moveToFirst();
				// if data exist for combination, check if there exist data exist for all countries.
				// get countries list related to search record
				country_result = //dbHelper.rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + wb_result.getInt(wb_result.getColumnIndex(_ID)) +"'");
						rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + wb_result.getInt(wb_result.getColumnIndex(_ID)) +"'");
				if (country_result.getCount() > 0){
					// check to see if country params for the current search are already within the database.
					// Loop through the list of countries currently being searched for, Checking each against the list returned for the current indicator
					for (int n = 0; n < country.length; n++){
						// get country ID from country table using the country name or WB_Country _ID passed in
						country_IDresult = //dbHelper.rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");
						rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");

						if (country_IDresult.getCount() == 1){
							country_IDresult.moveToFirst();
							wb_country_id	= country_IDresult.getString(country_IDresult.getColumnIndex(WB_COUNTRY_ID));
							in_country_id = country_IDresult.getInt(country_IDresult.getColumnIndex(COUNTRY_ID));
							//Check through list of countries returned for search record for countries passed in
							country_result.moveToFirst();
							whileloop:while(! country_result.isAfterLast()){

								country_id 		= country_result.getInt(country_result.getColumnIndex(C_ID));
								//period			= country_result.getInt(country_result.getColumnIndex(P_ID));

								if (country_id == in_country_id ){
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

							//country_result.close();
						}else{
							Log.e(TAG,"Error in retrieving Country information: " + country_IDresult.getCount() + " rows returned");
							return_data.put(RETURN_VALUE, "" + FATAL_ERROR);
							country_result.close();
							return FATAL_ERROR;
						}
						country_IDresult.close();
					}// end for
					
				}else{
					// if 0 rows were returned, return error. As Initial search would have returned at least 1 country info. 
					Log.e(TAG,"Error in retrieving Country information: " + country_result.getCount() + " rows returned");
					return_data.put(RETURN_VALUE, "" + FATAL_ERROR);
					country_result.close();
					return FATAL_ERROR;
				}

				// if some countries are missing then update
				country_result.close();

			}else{
			// if combination does not exist the get data for all countries.

				for(int n = 0; n < country.length; n++){

					//get country id to add to list of countries to retrieve
					country_IDresult = //dbHelper.rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");
							rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");
					if(country_IDresult.getCount() != 1){
						Log.e(TAG,"Error in retrieving Country information: " + country_IDresult.getCount() + " rows returned");
						return_data.put(RETURN_VALUE, "" + FATAL_ERROR);
						country_IDresult.close();
						return FATAL_ERROR;
					}else{
						country_IDresult.moveToFirst();
						country_IDresult.getInt(country_IDresult.getColumnIndex(WB_COUNTRY_ID));
						countries_to_get.add(country_IDresult.getString(country_IDresult.getColumnIndex(WB_COUNTRY_ID)));
						countryIDs.add(country_IDresult.getInt(country_IDresult.getColumnIndex(COUNTRY_ID)));
					}
					country_IDresult.close();
				}
				//wb_result.close();
				//getCountryIndicators(ind_id, indicatorID, countries_to_get, countryIDs, "date=1990:2012");
				return_data.put(RETURN_VALUE		, SEARCH_API_NONE	);
				return_data.put(RETURN_IND_ID		, ind_id			);
				return_data.put(RETURN_WB_IND_ID	, indicatorID		);
				return_data.put(RETURN_COUNTRIES	, countries_to_get	);
				return_data.put(RETURN_CNTRY_IDs	, countryIDs		);
				return_data.put(RETURN_DATE			, "date=1990:2012"	);
				//return SEARCH_SUCCESS;

			}
			wb_result.close();
			if(!countries_to_get.isEmpty()){
				return getCountryIndicators(ind_id, indicatorID, countries_to_get, countryIDs, "date=1990:2012");

			}else{
				Log.e(TAG, "No Values to get :)");
				return_data.put(RETURN_VALUE, "" + SEARCH_SUCCESS);
				return SEARCH_SUCCESS;
			}

			
		}else if(dataSource == IDS_SEARCH){
			params    = "" + I_ID + " ='" + ind_id + "'";

			// query search table for API-Indicator combination. 
			ids_result 	= //dbHelper.rawQuery(ids_table, null, params);
			rawQuery(ids_table, null, params);

			if (ids_result.getCount() ==1 ){
				// if results found for this indicator then we assume that all the relevant data is in the database.
				return_data.put(RETURN_VALUE		, SEARCH_SUCCESS	);
				ids_result.close();
				return SEARCH_SUCCESS;

			}else{
				// if no results then go to the API and pull the related values for this indicator.
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// get searchable keywords from the indicator name string 
				// havent wrote it yet
				// will use hashtable instead

				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

				ids_result.close();
				// break up indicator string 
				populateKeywords();
				indicatorStr = INDICATOR_KEYWORDS.get(indicatorID);
				keyWords = indicatorStr.split(" ");
				return getDocuments(ind_id, keyWords);


				/*if(keyWords.length <= 2 ){
					// if 2 or less keywords the go ahead and search
					getDocuments(ind_id, keyWords);
				}else{
					// else check for keywords to be removed before searching
					// remove unnecessary keywords
					// Perform a search of the IDS API
					getDocuments(ind_id, keyWords);
					
				}*/
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			}			

		}else if(dataSource == BING_SEARCH){
			//int success;
			bingParam = "" + BING_QUERY + " ='" + indicatorStr + "'";

			// query search table for API-Indicator combination. 
			bing_result = //dbHelper.rawQuery(bing_table, null, bingParam);
					rawQuery(bing_table, null, bingParam);

			if (bing_result.getCount() ==1 ){
				// if the indicator data is found then the assumption is that relevant results are in the database
				bing_result.moveToFirst();
				// check if the previous search is over a week old
				searchDate = getDate(bing_result.getString(bing_result.getColumnIndex(QUERY_DATE)));
				if ((today.get(Calendar.WEEK_OF_YEAR) - searchDate.get(Calendar.WEEK_OF_YEAR)) >= 7){
					// if it is then fetch new results from the API, delete old results. 
					// delete old results first
					//get current search ID
					deleteParam = "" + B_S_ID + " ='" + bing_result.getString(bing_result.getColumnIndex(BING_SEARCH_ID)) + "'";
					// get number of results to delete - for error checking 
					records_to_delete = //dbHelper.rawQuery(BING_SEARCH_RESULTS, null, deleteParam);
							rawQuery(BING_SEARCH_RESULTS, null, deleteParam);
					num_deleted = delete(BING_SEARCH_RESULTS, deleteParam);
					num_to_delete = records_to_delete.getCount();
					records_to_delete.close();
					if (num_to_delete != num_deleted){

						return_data.put(RETURN_VALUE		, FATAL_ERROR	);
						bing_result.close();
						return FATAL_ERROR;
					}
					if (getBingArticles(indicatorStr) == SEARCH_FAIL){
						return SEARCH_FAIL;
					}
					return_data.put(RETURN_VALUE		, SEARCH_API_NONE	);
					return_data.put(RETURN_STRING		, indicatorStr		);


				}else{

					return_data.put(RETURN_VALUE		, SEARCH_SUCCESS	);
					bing_result.close();
					return SEARCH_SUCCESS;
				}
			}else{

				if (getBingArticles(indicatorStr) == SEARCH_FAIL){
					return SEARCH_FAIL;
				}
				return_data.put(RETURN_VALUE		, SEARCH_API_NONE	);
				return_data.put(RETURN_STRING		, indicatorStr		);
				bing_result.close();
				return SEARCH_SUCCESS;
			}

			bing_result.close();
		}else {
			//Search only for indicator passed in

		}
		// if all APIs should be searched, then start with one passed in.


		return_data.put(RETURN_VALUE	, SEARCH_FAIL	);
		return  SEARCH_FAIL;
	}


	public Cursor getSelectionList(int exclude){
		Cursor result; 

		result = //dbHelper.rawQuery(INDICATOR, null, "");
				rawQuery(AREA_SELECTIONS, null, "" + SELECTION_ID + " != '" + exclude + "'");

		return result;
	}
	
	
	public Cursor getIndicatorList(){
		Cursor result; 

		result = //dbHelper.rawQuery(INDICATOR, null, "");
				rawQuery(INDICATOR, null, "");

		return result;
	}
	
	public Cursor getIndicatorList(String category){
		Cursor result; 
		Log.d(TAG, "getting indicators for category :" + category);
		result = //dbHelper.rawQuery(INDICATOR, null, "");
				rawQuery("" + INDICATOR + " i INNER JOIN " + IND_CATEGORIES + " i_c  ON i." +  INDICATOR_ID +" = i_c." + I_ID + " ",
						new String[] {"i."+ INDICATOR_ID,"i."+ INDICATOR_NAME ,"i."+ WB_INDICATOR_ID},
						"" + CAT_ID + " ='" + category + "'");

		return result;
	}
	
	public Cursor getCategoryList(){
		Cursor result; 

		result = //dbHelper.rawQuery(INDICATOR, null, "");
				rawQuery(WB_CATEGORY, null, "");

		return result;
	}


	public Cursor getCountryList(){
		Cursor result; 

		result = //dbHelper.rawQuery(COUNTRY, COUNTRY_NAME, "");
				rawQuery(COUNTRY, null, "");

		return result;
	}


	public boolean inDatabase(String s_table, String params){
		Cursor s_result; 

		// query search table for API-Indicator combination. 
		s_result = //dbHelper.rawQuery(s_table, null, params);
				rawQuery(s_table, null, params);

		// if data exist for combination, check if there exist data exist for all countries. 
		if (s_result.getCount() != 0){
			// if some countries are missing then update


		}else{
		// if combination does not exist the get data for all countries.

		}
		return true;
	}


	synchronized public Cursor getData(int dataSource, String indicatorID, String[] country){
		Log.e(TAG, "Get Data");
		Cursor cursor, search_cursor, ind_result, wb_result, country_result, country_IDresult;
		String table = "", indicatorStr, params = "";
		int ind_id, in_country_id, country_id, search_country_id; //, period;
		Integer[] search_country_array;
		parser = new JSONParse(context);

		if (dataSource > BING_SEARCH){
			if (dataSource == SAVED_ARTICLES){
				table = BING_SEARCH_RESULTS;
				params = "" + D_T_ID + " ='" + ARTICLES_DATA + "'";
				
				cursor = rawQuery("" + SAVED_DATA + " s INNER JOIN " + table + " b  ON s." +  ENTITY_ID +" = b." + _ID + " ",
						null,
						params);
				Log.d(TAG,
						"Returning data. Num of records: " + cursor.getCount());
			}else if (dataSource == SAVED_REPORTS){
				table = IDS_SEARCH_RESULTS;
				params = "" + D_T_ID + " ='" + REPORTS_DATA + "'";
				
				cursor = rawQuery("" + SAVED_DATA + " s INNER JOIN " + table + " b  ON s." +  ENTITY_ID +" = b." + _ID + " ",
						null,
						params);
				Log.d(TAG,
						"Returning data. Num of records: " + cursor.getCount());
			}else{
				cursor =null;
			}
				
		}else {
			ind_result = //dbHelper.rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicatorID + "'");
					rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicatorID + "'");
	
			if (ind_result.getCount() != 1){
				Log.e(TAG, "Error retrieving Indicatror Information: indicator - " + indicatorID );
				return null;
			}else{
				Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
				ind_result.moveToFirst();
				Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));
				ind_id = Integer.parseInt(ind_result.getString(ind_result.getColumnIndexOrThrow(_ID)));
				indicatorStr = ind_result.getString(ind_result.getColumnIndexOrThrow(INDICATOR_NAME));
	
				int pos;
	
				// find position of first parenthesis or comma to extract relevant words.
				pos = indicatorStr.indexOf(",");
				if (pos < 0){
					pos = indicatorStr.indexOf("(");
				}
				// remove section of string after the comma or within and after the parenthesis
				if(pos > 0){
					indicatorStr = indicatorStr.substring(0, pos-1);
				}
			}
			ind_result.close();
	
			switch (dataSource) {
				case WORLD_SEARCH:				
					table = WB_DATA;
	
					params    = "" + I_ID + " ='" + ind_id + "'";
					// get search id Corresponding to search table
					wb_result = rawQuery(SEARCH, null, params);
					// get corresponding search country results that relate to that indicator
					//only one search result should be returned per indicator and api combination.
					if (wb_result.getCount() == 1){
						//check db information starting with country data. 
						wb_result.moveToFirst();
						// if data exist for combination, check if there exist data exist for all countries.
						// get countries list related to search record
						country_result = //dbHelper.rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + wb_result.getInt(wb_result.getColumnIndex(_ID)) +"'");
								rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + wb_result.getInt(wb_result.getColumnIndex(_ID)) +"'");
						if (country_result.getCount() > 0){
							// check to see if country params for the current search are already within the database.
							// Loop through the list of countries currently being searched for, Checking each against the list returned for the current indicator
							for (int n = 0; n < country.length; n++){
								// get country ID from country table using the country name or WB_Country _ID passed in
								country_IDresult = //dbHelper.rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");
										rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + country[n] +"'");
	
								if (country_IDresult.getCount() == 1){
									country_IDresult.moveToFirst();
									//wb_country_id	= country_IDresult.getString(country_IDresult.getColumnIndex(WB_COUNTRY_ID));
									in_country_id = country_IDresult.getInt(country_IDresult.getColumnIndex(COUNTRY_ID));
									//Check through list of countries returned for search record for countries passed in
									country_result.moveToFirst();
									whileloop:while(! country_result.isAfterLast()){
	
										country_id 			= country_result.getInt(country_result.getColumnIndex(C_ID));
										//period				= country_result.getInt(country_result.getColumnIndex(P_ID));
										search_country_id	= country_result.getInt(country_result.getColumnIndex(_ID ));
										if (country_id == in_country_id ){
											countryIDs.add(search_country_id);
											break whileloop;
										}
	
										country_result.moveToNext();
	
									}							
	
								}else{
									Log.e(TAG,"Error in retrieving Country information: " + country_IDresult.getCount() + " rows returned");
									country_IDresult.close();
									country_result.close();
									return null;
								}
								country_IDresult.close();
							}// end for
	
						}else{
							// if 0 rows were returned, return error. As Initial search would have returned at least 1 country info. 
							Log.e(TAG,"Error in retrieving Country information: " + country_result.getCount() + " rows returned");
							country_result.close();
							return null;
						}
	
	
						country_result.close();
						// update cursor to be returned with the country data
						search_country_array = (Integer[])countryIDs.toArray(new Integer[countryIDs.size()]);
						for(int a = 0; a < search_country_array.length; a++){
							if(a == 0){
								params =  "" + SC_ID + " = '" + search_country_array[a] + "'";
							}else{
								params = params + " and " + SC_ID + " = '" + search_country_array[a] + "'";
							}
						}
	
					}else{
						Log.e(TAG,"No Search Info: " + wb_result.getCount() + " rows returned");
	
						return null;
					}
	
					apiRecord = new ContentValues();
					apiRecord.put(I_ID 				,  wb_result.getInt(wb_result.getColumnIndex(I_ID )));
					apiRecord.put(AP_ID				, 1	);
					apiRecord.put(SEARCH_VIEWED		, parser.timeStamp());
					//FROM_SEARCH			= {SEARCH_ID, I_ID, AP_ID, SEARCH_CREATED, SEARCH_MODIFIED, SEARCH_URI};
					insert(SEARCH, apiRecord, 1);
					wb_result.close();
					break;
				case IDS_SEARCH:
	
					table = IDS_SEARCH_RESULTS;
	
					params    = "" + I_ID + " ='" + ind_id + "'";
	
					// query search table for API-Indicator combination. 
					search_cursor = rawQuery(IDS_SEARCH_TABLE, null, params);
	
					if (search_cursor.getCount() ==1 ){
						search_cursor.moveToFirst();
						params = "" + IDS_S_ID + " ='" + search_cursor.getInt(search_cursor.getColumnIndex(IDS_SEARCH_ID))+ "'";
	
					}else{
						Log.e(TAG,"No Search Info: " +search_cursor.getCount() + " rows returned");
	
						return null;
	
					}
	
	
					apiRecord = new ContentValues();
					apiRecord.put(I_ID			,  search_cursor.getInt(search_cursor.getColumnIndex(I_ID)));
					apiRecord.put(IDS_VIEW_DATE	, parser.timeStamp());
					//String[] FROM_IDS_SEARCH= {IDS_SEARCH_ID, I_ID, IDS_BASE_URL, IDS_SITE, IDS_OBJECT};
					insert(IDS_SEARCH_TABLE, apiRecord, 1);
					search_cursor.close();
					break;
				case BING_SEARCH:
	
					table = BING_SEARCH_RESULTS;
					params = "" + BING_QUERY + " ='" + indicatorStr + "'";
	
					// query search table for API-Indicator combination. 
					search_cursor = //dbHelper.rawQuery(BING_SEARCH_TABLE, null, params);
							rawQuery(BING_SEARCH_TABLE, null, params);
	
					if (search_cursor.getCount() ==1 ){
						// if the indicator data is found then the assumption is that relevant results are in the database
						search_cursor.moveToFirst();
						params = "" + B_S_ID + " ='" + search_cursor.getInt(search_cursor.getColumnIndex(BING_SEARCH_ID))+ "'";
					}else{
						Log.e(TAG,"No Search Info: " +search_cursor.getCount() + " rows returned");
						search_cursor.close();
						return null;				
					}
	
					apiRecord = new ContentValues();
					apiRecord.put(BING_QUERY	, search_cursor.getString(search_cursor.getColumnIndex(BING_QUERY)));
					apiRecord.put(QUERY_VIEW_DATE	, parser.timeStamp());
					insert(BING_SEARCH_TABLE, apiRecord, 1);
					search_cursor.close();
					break;
	
			}
			cursor = rawQuery(table, null, params);
			Log.e(TAG, String.format("Params: %s. Table: %s", params, table));
		}
		return cursor;
	}


	synchronized public Cursor getGlobalData(int datasource, String searchStr){
		Log.e(TAG, "Get Global Data from " + datasource + " search:-> " + searchStr);
		Cursor cursor, search_cursor;
		String table = "", params = "";
		parser = new JSONParse(context);
		switch(datasource){
		case BING_SEARCH:
			table = BING_SEARCH_RESULTS;
			params = "" + BING_QUERY + " ='" + searchStr + "'";

			// query search table for API-Indicator combination. 
			search_cursor = //dbHelper.rawQuery(BING_SEARCH_TABLE, null, params);
					rawQuery(BING_SEARCH_TABLE, null, params);

			if (search_cursor.getCount() ==1 ){
				// if the indicator data is found then the assumption is that relevant results are in the database
				search_cursor.moveToFirst();
				params = "" + B_S_ID + " ='" + search_cursor.getInt(search_cursor.getColumnIndex(BING_SEARCH_ID))+ "'";
			}else{
				Log.e(TAG,"No Search Info: " +search_cursor.getCount() + " rows returned");

				return null;				
			}

			apiRecord = new ContentValues();
			apiRecord.put(BING_QUERY	, search_cursor.getString(search_cursor.getColumnIndex(BING_QUERY)));
			apiRecord.put(QUERY_VIEW_DATE	, parser.timeStamp());
			insert(BING_SEARCH_TABLE, apiRecord, 1);
			search_cursor.close();
			break;
		case IDS_SEARCH:

			table = IDS_SEARCH_RESULTS;

			params    = "" + IDS_PARAM_VALUE + " ='" + searchStr + "'";

			// query search table for API-Indicator combination. 
			Log.e(TAG,"Search Info => params:-> " + params );
			search_cursor = rawQuery(IDS_SEARCH_PARAMS, null, params);

			if (search_cursor.getCount() ==1 ){
				search_cursor.moveToFirst();
				params = "" + IDS_P_ID + " ='" + search_cursor.getInt(search_cursor.getColumnIndex(_ID))+ "'";
				Log.e(TAG,"Result Info => params:-> " + params );
			}else{
				Log.e(TAG,"No Search Info: " +search_cursor.getCount() + " rows returned");
				search_cursor.close();
				return null;

			}

			Log.e(TAG,"Position: " + search_cursor.getPosition() + "and " + search_cursor.getCount() + " rows returned");
			search_cursor.close();
			apiRecord = new ContentValues();
			apiRecord.put(I_ID			,  0);
			apiRecord.put(IDS_VIEW_DATE	, parser.timeStamp());
			//String[] FROM_IDS_SEARCH= {IDS_SEARCH_ID, I_ID, IDS_BASE_URL, IDS_SITE, IDS_OBJECT};
			
			insert(IDS_SEARCH_TABLE, apiRecord, 1);
			
			break;
		}
		cursor = rawQuery(table, null, params);
		Log.e(TAG, String.format("Params: %s. Table: %s", params, table));
		return cursor;
	}


	synchronized public Cursor getRecentData(int dataSource){
		Log.e(TAG, "Get Recent Data");
		Cursor max_cursor, cursor = null;
		switch(dataSource){
		case WORLD_SEARCH:
			max_cursor = //dbHelper.rawQuery(SEARCH, "MAX("+ SEARCH_VIEWED +") AS recent_time", "");
					rawQuery(SEARCH, new String[] {"MAX("+ SEARCH_VIEWED +") AS recent_time"}, null, SEARCH_VIEWED);
			if(max_cursor.moveToFirst()){
				cursor = //dbHelper.rawQuery(SEARCH, null, "" + SEARCH_VIEWED + " = '"+ 
												//max_cursor.getLong(max_cursor.getColumnIndex("recent_time"))+ "'");
						rawQuery(SEARCH, null, "" + SEARCH_VIEWED + " = '"+ 
								max_cursor.getLong(max_cursor.getColumnIndex("recent_time"))+ "'");				
				if(cursor.moveToFirst()){
					max_cursor.close();
					return cursor;
				}

			}else{
				Log.e(TAG, "Error retrieving recent WB Data:" + max_cursor.getLong(max_cursor.getColumnIndex("recent_time")));
				max_cursor.close();
				return null;
			}
			max_cursor.close();
			break;
		case IDS_SEARCH:
			cursor = //dbHelper.rawQuery(IDS_SEARCH_RESULTS, null, "" + IDS_VIEW_DATE + " > 0 ORDER BY " + IDS_VIEW_DATE + " LIMIT 10" );
					rawQuery(IDS_SEARCH_RESULTS, null, "" + IDS_VIEW_DATE + " > 0 ",  IDS_VIEW_DATE + " LIMIT 10" );
			break;
		case BING_SEARCH:
			cursor = //dbHelper.rawQuery(BING_SEARCH_RESULTS, null, "" + IDS_VIEW_DATE + " > 0 ORDER BY " + QUERY_VIEW_DATE + " LIMIT 10");
					rawQuery(BING_SEARCH_RESULTS, null, "" + IDS_VIEW_DATE + " > 0 ",   QUERY_VIEW_DATE + " LIMIT 10");
			break;
		}

		return cursor;
	}


	synchronized public Cursor getReport(int reportID){
		Log.e(TAG, "Get Report");
		Cursor cursor ;
		parser = new JSONParse(context);
		cursor =  //dbHelper.rawQuery(IDS_SEARCH_RESULTS, null, "" + _ID + " = '" + reportID + "'");
				rawQuery(IDS_SEARCH_RESULTS, null, "" + _ID + " = '" + reportID + "'");
		if(cursor.getCount() != 1){
			Log.e(TAG, "Error In Retrieving Report: Amount returned:->" + cursor.getCount());
			cursor.close();
			return null;
		}else{
			cursor.moveToFirst();
			apiRecord = new ContentValues();
			apiRecord.put(IDS_DOC_ID, cursor.getString(cursor.getColumnIndex(IDS_DOC_ID)));
			apiRecord.put(IDS_VIEW_DATE, parser.timeStamp());
			insert(IDS_SEARCH_RESULTS, apiRecord, 1);
		}
		return cursor;
	}

	
	public synchronized void updateArticle(String bingUrl){
		parser = new JSONParse(context);
		apiRecord = new ContentValues();
		apiRecord.put(BING_URL, bingUrl);
		apiRecord.put(IDS_VIEW_DATE, parser.timeStamp());
		insert(BING_SEARCH_RESULTS, apiRecord, 1);
	}

	
	public synchronized int getCountryIndicators(int indicator_id, String indicator, ArrayList<String> countries, ArrayList<Integer> countryIDList, String date){
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
			return SEARCH_FAIL;
		}else{

			queryStr = "http://api.worldbank.org/countries/" + countryString + "/indicators/" + indicator + "?per_page="+ numOfRecords +"&" + date + "&format=json";
			return parser.parseWBData(dataService.HTTPRequest(0,queryStr), indicator_id, countryIDArray, queryStr);

		}


	}

	
	public synchronized int getDocuments(int indicator, String[] parameters){
		parser = new JSONParse(context);
		String querybase = "http://api.ids.ac.uk/openapi/";
		int return_int;
		String site = "eldis/", object = "documents/", parameter="q", num_results = "num_results=" + prefs.getInt("resultNumber", 25);
		String extras = "extra_fields=timestamp+date_created+site+urls+description+author+publication_year+publisher+publication_date";
		String queryStr;
		String paramStr = "";
		for(int n = 0; n < parameters.length; n++){
			if(n==0){
				paramStr = paramStr + parameters[n];
			}else{
				paramStr = paramStr + "%26" + parameters[n];
			}
		}

		queryStr = querybase + site + "search/" + object + "?" + parameter  + "=" + paramStr + "&" + extras + "&" +num_results;
		//queryStr = "http://api.ids.ac.uk/openapi/eldis/search/documents/?q=Agriculture%26materials&num_results=50";
		Log.e(TAG, "Pulling IDS Data:" + queryStr);
		return_int =  parser.parseIDSData(dataService.HTTPRequest(1,queryStr), indicator, paramStr, queryStr);
		if(return_int > 0){
			return SEARCH_SUCCESS;
		}else{
			return SEARCH_FAIL;
		}

	}


	public synchronized int getBingArticles(String param){
		parser = new JSONParse(context);
		String querybase = "https://api.datamarket.azure.com/Bing/Search/Web";

		String query = "Query=", format="$format=json", num_results = "$top=" + prefs.getInt("resultNumber", 25) ;

		String queryStr;
		String paramStr = "";
		String[] parameters = param.split(" ");

		for(int n = 0; n < parameters.length; n++){
			if(n==0){
				paramStr = paramStr + parameters[n];
			}else{
				paramStr = paramStr + "%20" + parameters[n];
			}
		}

		queryStr = querybase +  "?"+ query + "%27" + paramStr + "%27" + "&"  + num_results + "&" + format;

		Log.e(TAG, "Pulling BING data:" + queryStr);
		return parser.parseBINGData(dataService.HTTPRequest(2,queryStr), param, queryStr);

	}


	public synchronized String[] getCountry() {
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		providerUri = Uri.parse(AreaProvider.AUTHORITY+"/"+ COUNTRY);
		cursorCountry = areaResolver.query(providerUri, new String[] {COUNTRY_NAME}, null, null, null);
		//Cursor cursorCountry = db.query(COUNTRY, new String[] {COUNTRY_NAME}, null, null, COUNTRY_NAME, null, null);
		String[] countryArray;

		if(cursorCountry.getCount() > 0) {
			countryArray = new String[cursorCountry.getCount()];
			int i = 0;

			while(cursorCountry.moveToNext()) {
				countryArray[i] = cursorCountry.getString(cursorCountry.getColumnIndex(COUNTRY_NAME));
				i++;
			}
		} else {
			countryArray = new String[] {};
		}
		cursorCountry.close();
		
		return countryArray;
	}

	
	public synchronized Cursor getCountry(int countryID) {
		return 	rawQuery(COUNTRY, null, "" + _ID +" ='" + countryID +"'");
	}

	
	public synchronized int getIndicatorID(String indicator){
		Log.e(TAG, "Get Indicator ID by String");
		int id = -1;
		Cursor ind_result;

		ind_result = //dbHelper.rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicator + "'");
				rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicator + "'");

		if (ind_result.getCount() != 1){
			Log.e(TAG, "Error retrieving Indicatror Information: indicator - " + indicator );
			ind_result.close();
			return id;
		}else{
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
			ind_result.moveToFirst();
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));

			id = Integer.parseInt(ind_result.getString(ind_result.getColumnIndexOrThrow(_ID)));
		}
		ind_result.close();
		return id;
	}


	public synchronized String getIndicatorName(String indicator){
		Log.e(TAG, "Get Indicator ID by Name");
		String indicatorStr = "";
		Cursor ind_result;

		ind_result = //dbHelper.rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicator + "'");
				rawQuery(INDICATOR, null , "" + WB_INDICATOR_ID + " ='" + indicator + "'");

		if (ind_result.getCount() != 1){
			Log.e(TAG, "Error retrieving Indicatror Information: indicator - " + indicator );
			ind_result.close();
			return indicatorStr;
		}else{
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
			ind_result.moveToFirst();
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));

			indicatorStr = ind_result.getString(ind_result.getColumnIndexOrThrow(INDICATOR_NAME));
		}
		ind_result.close();
		return indicatorStr;
	}


	public synchronized String getIndicatorName(int indicator){
		Log.e(TAG, "Get Indicator Name");
		String indicatorStr = "";
		Cursor ind_result;

		ind_result = //dbHelper.rawQuery(INDICATOR, null , "" + INDICATOR_ID + " ='" + indicator + "'");
				rawQuery(INDICATOR, null , "" + INDICATOR_ID + " ='" + indicator + "'");

		if (ind_result.getCount() != 1){
			Log.e(TAG, "Error retrieving Indicatror Information: indicator - " + indicator );
			ind_result.close();
			return indicatorStr;
		}else{
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getColumnIndexOrThrow(_ID));
			ind_result.moveToFirst();
			Log.e(TAG,"" + ind_result.getCount() + " ID: " + ind_result.getString( ind_result.getColumnIndexOrThrow(_ID)));

			indicatorStr = ind_result.getString(ind_result.getColumnIndexOrThrow(WB_INDICATOR_ID));
		}
		ind_result.close();
		return indicatorStr;
	}


	public synchronized String[] getSearchCountries(int search_id){
		Log.e(TAG, "Get COuntries BY Search ID");
		String[] countries;
		Cursor country_results, country;

		country_results = //dbHelper.rawQuery(SEARCH_COUNTRY, null, "" + S_ID +" ='" + search_id +"'");
				rawQuery(SEARCH_COUNTRY, null, "" + S_ID +" ='" + search_id +"'");
		if(country_results.getCount() > 0){
			countries = new String[country_results.getCount()];
			country_results.moveToFirst();
			int n = 0;
			while(!country_results.isAfterLast()){
				country = //dbHelper.rawQuery(COUNTRY, null, "" + COUNTRY_ID + " = '" + country_results.getInt(country_results.getColumnIndex(C_ID))+ "'");
						rawQuery(COUNTRY, null, "" + COUNTRY_ID + " = '" + country_results.getInt(country_results.getColumnIndex(C_ID))+ "'");
				if(country.getCount() !=1){
					country.close();
					countries = new String[0];
					break;
				}
				country.moveToFirst();
				countries[n] = country.getString(country.getColumnIndex(COUNTRY_NAME));
				country.close();
				country_results.moveToNext();
				n++;
			}
		}else{
			countries = new String[0];
		}
		country_results.close();
		return countries;

	}


	public synchronized double[][] getIndicatorList(int Indicator_id, String countryStr, int period){
		Log.e(TAG, "Get List of Indicators with Corresponding country data");
		double [][] values = null;
		int search_country_id, country_id, search_id;
		String params;
		Cursor search, search_country, country, wb_data;
		params = "" + I_ID + " ='" + Indicator_id + "'";
		// get search id Corresponding to search table
		search = rawQuery(SEARCH, null, params);
		// get 
		if(search.getCount() == 1){
			search.moveToFirst();
			search_id = search.getInt(search.getColumnIndex(_ID));
			country = //dbHelper.rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + countryStr +"'");
					rawQuery(COUNTRY,null, ""+ COUNTRY_NAME +" ='" + countryStr +"'");
			if (country.getCount() == 1){
				country.moveToFirst();
				country_id = country.getInt(country.getColumnIndex(_ID));

				search_country = //dbHelper.rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + search_id +"' and "
						//+ C_ID +" ='" + country_id +"'" );
						rawQuery(SEARCH_COUNTRY, null, ""+ S_ID +" ='" + search_id +"' and "
								+ C_ID +" ='" + country_id +"'" );
				if(search_country.getCount() == 1){
					search_country.moveToFirst();
					search_country_id = search_country.getInt(search_country.getColumnIndex(_ID));
					Log.e(TAG,String.format("SC_ID %s - _ID %s ", search_country_id, search_country.getInt(search_country.getColumnIndex(_ID)) ));
					wb_data = //dbHelper.rawQuery(WB_DATA,null, ""+ SC_ID +" ='" + search_country_id +"'" +" ORDER BY " + IND_DATE);
							rawQuery(WB_DATA,null, ""+ SC_ID +" ='" + search_country_id +"'", IND_DATE);

					if(wb_data.getCount() > 0){

						values = new double[2][wb_data.getCount()];
						for(int n = 0; n < wb_data.getCount(); n++){
							wb_data.moveToNext();
							values[0][n] = (double) wb_data.getInt(wb_data.getColumnIndex(IND_DATE));
							values[1][n] = wb_data.getDouble(wb_data.getColumnIndex(IND_VALUE));
						}

						wb_data.close();

					} else{
						wb_data.close();
						values = new double[0][0];
					}

				}else{
					//search_country.close();
					values = new double[0][0];
				}
				search_country.close();
			}else{
				//country.close();
				values = new double[0][0];
			}
			country.close();
		}else{
			//search.close();
			values = new double[0][0];
		}
		search.close();


		return values;
	}


	public synchronized Cursor rawQuery(String tableName, String[] tableColumns, String queryParams, String order) {
		retCursor = null;
		providerUri = Uri.parse(AreaProvider.AUTHORITY+"/"+ tableName);
		
		//retCursor = dbHelper.rawQuery(tableName, tableColumns, queryParams);
		retCursor = areaResolver.query(providerUri, tableColumns, queryParams, null, order);
		
		return retCursor;
	}

	
	public synchronized Cursor rawQuery(String tableName, String[] tableColumns, String queryParams) {
		retCursor = null;
		providerUri = Uri.parse(AreaProvider.AUTHORITY+"/"+ tableName);
		
		//retCursor = dbHelper.rawQuery(tableName, tableColumns, queryParams);
		retCursor = areaResolver.query(providerUri, tableColumns, queryParams, null, null);
		
		return retCursor;
	}

	
	private Calendar getDate(String epoch){
		Calendar calendar = null;

		try{
			Date date = new Date();
			date.setTime(Long.parseLong(epoch));
			calendar = Calendar.getInstance();
			calendar.setTime(date);

		}catch (NumberFormatException e){
			Log.e(TAG,"Exception in parsing date string "+e.toString());
		}


		 return calendar;
	}

	
	public void populateKeywords(){
		INDICATOR_KEYWORDS.put("AG.AGR.TRAC.NO",	"agriculture machine"		);
		INDICATOR_KEYWORDS.put("AG.CON.FERT.MT",	"fertilizer consumption"	);
		INDICATOR_KEYWORDS.put("AG.CON.FERT.PT.ZS",	"fertilizer consumption"	);
		INDICATOR_KEYWORDS.put("AG.CON.FERT.ZS",	"fertilizer"				);
		INDICATOR_KEYWORDS.put("AG.LND.AGRI.K2",	"agriculture"				);
		INDICATOR_KEYWORDS.put("AG.LND.AGRI.ZS",	"agricultural land"			);
		INDICATOR_KEYWORDS.put("AG.LND.ARBL.HA",	"arable land"				);
		INDICATOR_KEYWORDS.put("AG.LND.ARBL.HA.PC",	"arable land"				);
		INDICATOR_KEYWORDS.put("AG.LND.ARBL.ZS",	"land usage"				);
		INDICATOR_KEYWORDS.put("AG.LND.CREL.HA",	"cereal production"			);
		INDICATOR_KEYWORDS.put("AG.LND.CROP.ZS",	"cropland"					);
		INDICATOR_KEYWORDS.put("AG.LND.FRST.K2",	"forrest area"				);
		INDICATOR_KEYWORDS.put("AG.LND.FRST.ZS",	"forrest area"				);
		INDICATOR_KEYWORDS.put("AG.LND.IRIG.AG.ZS",	"irrigated land"			);
		INDICATOR_KEYWORDS.put("AG.LND.PRCP.MM",	"precipitation"				);
		INDICATOR_KEYWORDS.put("AG.LND.TOTL.K2",	"land area"					);
		INDICATOR_KEYWORDS.put("AG.LND.TRAC.ZS",	"tractors"					);
		INDICATOR_KEYWORDS.put("AG.PRD.CROP.XD",	"crop production"			);
		INDICATOR_KEYWORDS.put("AG.PRD.FOOD.XD",	"food production"			);
		INDICATOR_KEYWORDS.put("AG.PRD.LVSK.XD",	"livestock production"		);
		INDICATOR_KEYWORDS.put("AG.SRF.TOTL.K2",	"land area"					);
		INDICATOR_KEYWORDS.put("AG.YLD.CREL.KG",	"cereal"					);
		INDICATOR_KEYWORDS.put("EA.PRD.AGRI.KD",	"agriculture worker"		);
		INDICATOR_KEYWORDS.put("EN.AGR.EMPL",		"agriculture employ"		);
		INDICATOR_KEYWORDS.put("NV.AGR.TOTL.ZS",	"agriculture growth"		);
		INDICATOR_KEYWORDS.put("SH.H2O.SAFE.RU.ZS",	"water source"				);
		INDICATOR_KEYWORDS.put("SI.POV.RUGP",		"rural poverty"				);
		INDICATOR_KEYWORDS.put("SI.POV.RUHC",		"poverty line"				);
		INDICATOR_KEYWORDS.put("SL.AGR.EMPL.ZS",	"agriculture employment"	);
		INDICATOR_KEYWORDS.put("SP.RUR.TOTL",		"rural population"			);
		INDICATOR_KEYWORDS.put("SP.RUR.TOTL.ZG",	"population growth"			);
		INDICATOR_KEYWORDS.put("SP.RUR.TOTL.ZS",	"rural population"			);
		INDICATOR_KEYWORDS.put("TM.VAL.AGRI.ZS.UN",	"agriculture imports"		);
		INDICATOR_KEYWORDS.put("TX.VAL.AGRI.ZS.UN",	"agriculture exports"		);

	}

	public int saveChart(String chart_name, String chart_desc,
			String indicator, String[] countryList) {
		//Cursor ind = rawQuery(INDICATOR, new String[]{INDICATOR_ID}, WB_INDICATOR_ID + " = " +  indicator);
		//FROM_CHARTS = { CHART_ID, CHART_NAME, CHART_DESC, I_ID, CHART_COUNTRIES};
		/*if (!ind.moveToFirst() || (ind.getCount() > 1)){
			return SEARCH_FAIL;
		}else{*/
		values = new ContentValues();
		values.put(CHART_NAME, chart_name);
		values.put(CHART_DESC, chart_desc);
		values.put(I_ID, indicator);
		values.put(CHART_COUNTRIES, arrayToCSV(countryList));
		if (insert(CHARTS, values, 0) > 0) {
			return SUCCESS;
		} else {
			return SEARCH_FAIL;
		}

		//}
	}
	
	public String arrayToCSV(String[] array){
		String retStr = "";
		
		if (array == null){
			 retStr = "";
		}else if(array.length > 0){
			for (int a = 0; a < array.length; a++){
				if (array.length == 0){
					retStr = "" + array[a];
				}else if(a == (array.length -1)){
					retStr += "" + array[a];
				}else{
					retStr += "" + array[a] + ",";
				}
			}
		}
		Log.i(TAG, "Country CSV: " + retStr);
		return retStr;
	}
	
	public String[] CSVToArray(String csvString){
		return csvString.split(",");
	}

	public Cursor getCharts(String indicatorID, String[] country) {
		@SuppressWarnings("unused")
		Cursor retCursor;
		
		return retCursor = rawQuery(CHARTS, null, I_ID + " = '" + indicatorID + " AND " +
				CHART_COUNTRIES + " = '" + arrayToCSV(country) + "'");
	}

	public Cursor getChartList() {
		@SuppressWarnings("unused")
		Cursor retCursor;
		
		return retCursor = rawQuery(CHARTS, null, null);
	}

	public int saveData(int dataType, String entityid) {
		//FROM_SAVED_DATA = { SAVED_DATA_ID, D_T_ID, ENTITY_ID};
		values = new ContentValues();
		values.put(D_T_ID, dataType);
		values.put(ENTITY_ID, entityid);
		
		if (insert(SAVED_DATA, values, 0) > 0) {
			return SUCCESS;
		} else {
			return SEARCH_FAIL;
		}
		
	}

}
