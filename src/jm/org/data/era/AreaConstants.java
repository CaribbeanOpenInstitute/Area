package jm.org.data.era;

import java.util.Hashtable;

public interface AreaConstants {

	/*
	 * Search Response Message Types
	 */
	public static final int FATAL_ERROR = -2;
	public static final int SEARCH_FAIL = -1;
	public static final int SEARCH_SUCCESS = 0;
	public static final int SUCCESS = 0;
	public static final int SEARCH_API_SOME = 1;
	public static final int SEARCH_API_NONE = 2;

	public static final String WB_BASE_URL = "http://api.worldbank.org/";
	public static final String IDS_DASE_URL = "http://api.ids.ac.uk/openapi/";
	public static final String BING_BASE_URL = "http://api.bing.net/";

	public static final int INDICATOR_LIST 		= 0;
	public static final int COUNTRY_LIST 		= 1;
	public static final int API_LIST 			= 2;
	public static final int PERIOD_LIST 		= 3;
	public static final int SEARCH_DATA 		= 4;
	public static final int COUNTRY_SEARCH_DATA = 5;
	public static final int WB_SEARCH_DATA 		= 6;
	public static final int COUNTRY_INFO 		= 7;
	public static final int IDS_SEARCH_DATA 	= 8;
	public static final int IDS_PARAM_DATA 		= 9;
	public static final int IDS_RESULT_DATA 	= 10;
	public static final int BING_SEARCH_DATA 	= 11;
	public static final int BING_RESULT_DATA 	= 12;
	public static final int CATEGORY_LIST 		= 13;
	public static final int IND_CATEGORIES_DATA = 14;
	public static final int SELECTIONS_DATA 	= 15;
	public static final int DATA_TYPES_LIST 	= 16;
	public static final int CHART_DATA 			= 17;
	public static final int GET_DATA 			= 18;
	public static final int GET_COLLECTION		= 19;
	public static final int GET_COLL_DATA		= 20;
	
	/*
	 * API AND SEARCH CODES
	 */
	public static final int WORLD_SEARCH 		= 0;
	public static final int IDS_SEARCH 			= 1;
	public static final int BING_SEARCH 		= 2;
	public static final int SAVED_CHARTS 		= 3;
	public static final int SAVED_ARTICLES 		= 4;
	public static final int SAVED_REPORTS 		= 5;
	public static final int SAVED_COLLECTIONS	= 6;
	public static final int COLLECTION_CHARTS	= 7;
	public static final int COLLECTION_ARTICLES	= 8;
	public static final int COLLECTION_REPORTS	= 9;
	public static final int COUNTRY_CHARTS		= 10;
	public static final int COUNTRY_ARTICLES	= 11;
	public static final int COUNTRY_REPORTS		= 12;
	
	/*
	 * SELECTIONS
	 */
	public static final int S_INDICATORS  = 1;
	public static final int S_COUNTRIES   = 2;
	public static final int S_COLLECTIONS = 3;
	public static final int S_SAVED_DATA  = 4;
	
	/*
	 * DATA TYPES
	 */
	public static final int CHARTS_DATA  	= 1;
	public static final int REPORTS_DATA   	= 2;
	public static final int ARTICLES_DATA	= 3;
	
	/*
	 * SELECTION PARENT ACTIVITIES
	 */
	public static final String S_PARENT   = "Parent";
	
	public static final int S_HOME_ACT  = 1;
	public static final int S_IND_ACT   = 2;
	public static final int S_CTRY_ACT	= 2;
	public static final int S_COLL_ACT 	= 3;
	public static final int S_SVD_ACT	= 4;
	

	// Broadcast Receivers
	public static final String ACTION_WORLD_UPDATE = "Area.WorldBank.Update";
	public static final String ACTION_IDS_UPDATE = "Area.IDS.Update";
	public static final String ACTION_BING_UPDATE = "Area.Bing.Update";
	public static final String ACTION_FAIL_UPDATE = "Area.Fail.Update";

	// Data keys for World Bank API Calls
	public static final String WB_IND_ID = "id";
	public static final String WB_IND_CATEGORY = "topics";
	public static final String WB_IND_NAME = "name";
	public static final String WB_IND_DESC = "sourceNote";
	public static final String[] WB_IND_LIST = { WB_IND_ID, WB_IND_NAME,
			WB_IND_DESC };

	public static final String ESD_IND_ID = "Metadatum: id";
	public static final String ESD_IND_CATEGORY = "Metadatum: category";
	public static final String ESD_IND_NAME = "Metadatum: long_description";
	public static final String ESD_IND_DESC = "Metadatum: variable";
	public static final String[] ESD_IND_LIST = { ESD_IND_ID, ESD_IND_NAME,
			ESD_IND_DESC };
	
	public static final String WB_COUNTRY_IDSTR = "id";
	public static final String WB_COUNTRY_ISOCODE = "iso2Code";
	public static final String WB_COUNTRY_NAME = "name";
	public static final String WB_COUNTRY_REGION_ID = "region: id";
	public static final String WB_COUNTRY_REGION_NAME = "region: value";
	public static final String WB_COUNTRY_INCOME_LEVEL_ID = "incomeLevel: id";
	public static final String WB_COUNTRY_INCOME_LEVEL_NAME = "incomeLevel: value";
	public static final String WB_COUNTRY_CAPITAL = "capitalCity";
	public static final String[] WB_COUNTRY_LIST = { WB_COUNTRY_IDSTR,
			WB_COUNTRY_ISOCODE, WB_COUNTRY_NAME, WB_COUNTRY_CAPITAL,
			WB_COUNTRY_INCOME_LEVEL_ID, WB_COUNTRY_INCOME_LEVEL_NAME,
			WB_COUNTRY_REGION_ID, WB_COUNTRY_REGION_NAME };
	
	public static final String WB_CATEGORY_IDSTR = "id";
	public static final String WB_CATEGORY_NAME = "value";
	public static final String WB_CATEGORY_DESC = "sourceNote";
	public static final String[] WB_CATEGORY_LIST = { WB_CATEGORY_IDSTR,
			WB_CATEGORY_NAME, WB_CATEGORY_DESC};

	public static final String ESD_CATEGORY		  = "Category";
	public static final String ESD_CATEGORY_IDSTR = "Category: id";
	public static final String ESD_CATEGORY_NAME = "Category: category";
	public static final String ESD_CATEGORY_DESC = "Category: category_initials";
	public static final String[] ESD_CATEGORY_LIST = { ESD_CATEGORY_IDSTR,
			ESD_CATEGORY_NAME, ESD_CATEGORY_DESC};
	
	
	public static final String WB_IND_VALUE = "value";
	public static final String WB_IND_DECIMAL = "decimal";
	public static final String WB_IND_YEAR = "date";
	public static final String[] WB_DATA_LIST = { WB_IND_VALUE, WB_IND_DECIMAL,
			WB_IND_YEAR };

	public static final String ESD_IND_VALUE = "db: amount";
	public static final String ESD_IND_DECIMAL = "db: metadata_id";
	public static final String ESD_IND_YEAR = "db: year";
	public static final String[] ESD_DATA_LIST = { ESD_IND_VALUE, ESD_IND_DECIMAL,
			ESD_IND_YEAR };
	
	// Data keys for IDS API Calls
	public static final String IDS_SEARCH_DOC_URL = "metadata_url";
	public static final String IDS_SEARCH_DOC_ID = "object_id";
	public static final String IDS_SEARCH_DOC_TYPE = "object_type";
	public static final String IDS_SEARCH_DOC_TITLE = "title";
	public static final String IDS_SEARCH_DOC_AUTH = "author";
	public static final String IDS_SEARCH_DOC_PUB = "publisher";
	public static final String IDS_SEARCH_DOC_PUB_DATE = "publication_date";
	public static final String IDS_SEARCH_DOC_DESC = "description";
	public static final String IDS_SEARCH_DOC_SITE = "site";
	public static final String IDS_SEARCH_DOC_DATE = "date_created";
	public static final String IDS_SEARCH_DOC_TIME = "timestamp";
	public static final String IDS_SEARCH_DOC_URLS = "urls";

	public static final String[] IDS_SEARCH_LIST = { IDS_SEARCH_DOC_URL,
			IDS_SEARCH_DOC_ID, IDS_SEARCH_DOC_TYPE, IDS_SEARCH_DOC_TITLE,
			IDS_SEARCH_DOC_AUTH, IDS_SEARCH_DOC_PUB, IDS_SEARCH_DOC_PUB_DATE,
			IDS_SEARCH_DOC_DESC, IDS_SEARCH_DOC_SITE, IDS_SEARCH_DOC_DATE,
			IDS_SEARCH_DOC_TIME, IDS_SEARCH_DOC_URLS };
	/*
	 * public static final String[] FROM_IDS_SEARCH_RESULTS = {_ID, IDS_S_ID,
	 * IDS_DOC_URL, IDS_DOC_ID, IDS_DOC_TYPE, IDS_DOC_TITLE, IDS_DOC_AUTH_STR,
	 * IDS_DOC_PUB, IDS_DOC_PUB_DATE, IDS_DOC_DESC, IDS_DOC_SITE, IDS_DOC_DATE,
	 * IDS_DOC_TIMESTAMP, IDS_DOC_PATH };
	 */
	// Data keys for the BING API calls
	public static final String BING_SEARCH_TITLE = "Title";
	public static final String BING_SEARCH_DESC = "Description";
	public static final String BING_SEARCH_URL = "Url";
	public static final String BING_SEARCH_DISP_URL = "DisplayUrl";
	public static final String BING_SEARCH_DATE = "DateTime";
	public static final String[] BING_SEARCH_LIST = { BING_SEARCH_TITLE,
			BING_SEARCH_DESC, BING_SEARCH_URL, BING_SEARCH_DISP_URL };
	// public static final String[] FROM_BING_SEARCH_RESULTS = {_ID, B_S_ID,
	// BING_TITLE, BING_DESC, BING_URL, BING_DISP_URL, BING_DATE_TIME };

	// list of words to remove from indicator names
	public static final String[] KEYWORD_LIST = { "and", "land", "under",
			"area", "index", "in", "at", "the", "on", "for", "" };

	// IDS hashtable containing search keywords corresponding to each indicator
	public Hashtable<String, String> INDICATOR_KEYWORDS = new Hashtable<String, String>();
	// INDICATOR_KEYWORDS.put("","");

	// Synchronous vs Singular Searching
	public static boolean SEARCH_SYNC = true;

	// Keywords
	public static final int ADD_KEY = 0;
	public static final int REMOVE_KEY = 1;

	// Result Keys for Searches
	public static final String RETURN_VALUE = "ret_value";
	public static final String RETURN_IND_ID = "indicator_id";
	public static final String RETURN_API_ID = "api_id";
	public static final String RETURN_WB_IND_ID = "wb_indicator_id";
	public static final String RETURN_COUNTRIES = "countries";
	public static final String RETURN_CNTRY_IDs = "country_ids";
	public static final String RETURN_DATE = "date_string";
	public static final String RETURN_KEYWORDS = "keywords";
	public static final String RETURN_STRING = "searchString";
	
	
	//General search Strings
	public static final String POSITION = "position";
	public static final String GROUP_POSITION = "group_position";
	public static final String CHILD_POSITION = "child_position";
}
