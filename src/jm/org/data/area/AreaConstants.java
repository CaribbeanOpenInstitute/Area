package jm.org.data.area;

public interface AreaConstants {
	
	/*
	 * Search Response Message Types
	 */
	public static final int FATAL_ERROR		= -2;
	public static final int SEARCH_FAIL 	= -1;
	public static final int SEARCH_SUCCESS 	=  0;
	public static final int SEARCH_API_SOME =  1;
	public static final int SEARCH_API_NONE =  2;

	public static final String WB_BASE_URL 		= "http://api.worldbank.org/"		;
	public static final String IDS_DASE_URL		= "http://api.ids.ac.uk/openapi/"	; 
	public static final String BING_BASE_URL	= "http://api.bing.net/"			;
	
	public static final int INDICATOR_LIST 		= 0; 
	public static final int COUNTRY_LIST 		= 1;
	public static final int API_LIST			= 2;
	public static final int PERIOD_LIST			= 3;
	public static final int SEARCH_DATA			= 4;
	public static final int COUNTRY_SEARCH_DATA = 5;
	public static final int WB_SEARCH_DATA		= 6;
	public static final int COUNTRY_INFO		= 7;
	public static final int GLOBAl_SEARCH		= 8;
	public static final int GENERIC_SEARCH		= 9;
	
	/*
	 * API CODE
	 */
	public static final int WORLD_SEARCH = 0;
	public static final int IDS_SEARCH = 1;
	public static final int BING_SEARCH = 2;
	
	//Broadcast Receivers
	public static final String ACTION_WORLD_UPDATE = "Area.WorldBank.Update";
	public static final String ACTION_IDS_UPDATE = "Area.IDS.Update";
	public static final String ACTION_BING_UPDATE = "Area.Bing.Update";
	public static final String ACTION_FAIL_UPDATE = "Area.Fail.Update";
	
	// Data keys for APIs
	public static final String WB_IND_ID 		= "id" 			;
	public static final String WB_IND_NAME		= "name"		;
	public static final String WB_IND_DESC		= "sourceNote"	;
	public static final String[] WB_IND_LIST	= {WB_IND_ID, WB_IND_NAME, WB_IND_DESC};
	
	public static final String WB_COUNTRY_ID 				= "id" 					;
	public static final String WB_COUNTRY_ISOCODE			= "iso2Code"			;
	public static final String WB_COUNTRY_NAME				= "name"				;
	public static final String WB_COUNTRY_REGION_ID			= "region: id"			;
	public static final String WB_COUNTRY_REGION_NAME		= "region: value"		;
	public static final String WB_COUNTRY_INCOME_LEVEL_ID	= "incomeLevel: id"		;
	public static final String WB_COUNTRY_INCOME_LEVEL_NAME = "incomeLevel: value"	;
	public static final String WB_COUNTRY_CAPITAL			= "capitalCity"			;
	
	public static final String[] WB_COUNTRY_LIST	= {WB_COUNTRY_ID, WB_COUNTRY_ISOCODE, WB_COUNTRY_NAME, WB_COUNTRY_CAPITAL, 
														WB_COUNTRY_INCOME_LEVEL_ID, WB_COUNTRY_INCOME_LEVEL_NAME,
														WB_COUNTRY_REGION_ID, WB_COUNTRY_REGION_NAME };
	
	public static final String WB_IND_VALUE		= "value"	;
	public static final String WB_IND_DECIMAL	= "decimal"	;
	public static final String WB_IND_YEAR		= "date"	;
	
	public static final String[] WB_DATA_LIST = {WB_IND_VALUE, WB_IND_DECIMAL, WB_IND_YEAR};
	
	// Synchronous vs Singular Searching
	public static boolean SEARCH_SYNC = true;
	
	//Keywords
	public static final int ADD_KEY = 0;
	public static final int REMOVE_KEY = 1;
}
