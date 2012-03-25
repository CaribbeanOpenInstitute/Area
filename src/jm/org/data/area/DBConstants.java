package jm.org.data.area;

//import android.provider.BaseColumns;
import android.provider.MediaStore.MediaColumns;

public interface DBConstants extends MediaColumns {
	
	
	/*****************************************************************************************************************************/
	/*====DATABASE CONSTANTS======================
	* Constants used by the database adapter class
	*/
	
	//Databases
	public static final String DATABASE_NAME		= "area.db"			;
	
	//Tables for area Database
	public static final String COUNTRY				= "area_country"			;
	public static final String INDICATOR			= "area_indicator"			;
	public static final String SEARCH				= "area_wb_search"			;
	public static final String IDS_SEARCH_TABLE		= "area_ids_search"			;
	public static final String BING_SEARCH_TABLE	= "area_bing_search"		;
	public static final String API					= "area_api"				;
	public static final String WB_DATA				= "area_wb_data"			;
	public static final String SEARCH_COUNTRY		= "area_wb_search_country"	;
	public static final String PERIOD				= "area_search_period"		;
	public static final String BING_SEARCH_RESULTS	= "area_bing_search_result"	;
	public static final String IDS_SEARCH_PARAMS	= "area_ids_search_params"	;
	public static final String IDS_SEARCH_RESULTS	= "area_ids_search_results"	;
	public static final String IDS_DATA				= "area_ids_documents"		;
	public static final String IDS_AUTHOR			= "area_ids_author"			;
	public static final String IDS_DOC_THEME		= "area_ids_data_theme"		;
	public static final String IDS_THEME			= "area_ids_theme"			;
	
	
	// Columns unique to Country Table
	public static final String COUNTRY_ID			= _ID				;
	public static final String WB_COUNTRY_ID		= "country_id"		;
	public static final String WB_COUNTRY_CODE		= "country_iso2code";
	public static final String COUNTRY_NAME			= "name"			;
	public static final String INCOME_LEVEL_ID		= "income_code"		;
	public static final String INCOME_LEVEL_NAME	= "income_level"	;
	public static final String POPULATION			= "population"		;
	public static final String COUNTRY_REGION_ID	= "region_id"		;
	public static final String COUNTRY_REGION_NAME	= "region_name"		;
	public static final String GDP					= "gdp"				;
	public static final String GNI_CAPITA			= "gni_per_capita"	;
	public static final String POVERTY				= "poverty"			;
	public static final String LIFE_EX				= "life_expectancy"	;
	public static final String LITERACY				= "literacy_rate"	;
	public static final String CAPITAL_CITY			= "capital_city"	;
	
	//Columns unique to Indicators Table
	public static final String INDICATOR_ID			= _ID				;
	public static final String WB_INDICATOR_ID		= "indicator_id"	;
	public static final String INDICATOR_NAME		= "name"			;
	public static final String INDICATOR_DESC		= "description"		;
	
	
	//Columns unique to Search Table
	public static final String SEARCH_ID			= _ID		 		;
	public static final String SEARCH_CREATED		= "createdtime"		;
	public static final String SEARCH_MODIFIED		= "modifiedtime"	;
	public static final String SEARCH_VIEWED		= "viewedtime"		;
	public static final String SEARCH_URI			= "uri"				;
	
	//Columns unique to the IDS Search table 
	public static final String IDS_SEARCH_ID		= _ID				;
	public static final String IDS_BASE_URL			= "base_url"		;
	public static final String IDS_SITE				= "site"			;
	public static final String IDS_OBJECT			= "data_object"		;
	public static final String IDS_TIMESTAMP		= "createdtime"		;
	public static final String IDS_VIEW_DATE		= "viewedtime"		;
	
	
	//Columns unique to the IDS Search Parameters table
	public static final String IDS_PARAMETER	= "parameter"		;
	public static final String IDS_OPERAND		= "operand"			;
	public static final String IDS_PARAM_VALUE	= "param_value"		;
	public static final String COMBINATION		= "combination"		;
	
	//Columns unique to the IDS Search Results table
	public static final String IDS_DOC_URL			= "doc_url"			;
	public static final String IDS_DOC_ID			= "doc_id"			;
	public static final String IDS_DOC_TYPE			= "doc_type"		;
	public static final String IDS_DOC_TITLE		= "doc_title"		;
	public static final String IDS_DOC_PATH			= "doc_path"		;
	public static final String IDS_DOC_AUTH_STR 	= "author_string"	;
	public static final String IDS_DOC_PUB			= "doc_publisher"	;
	public static final String IDS_DOC_DESC			= "doc_desciption"	;
	public static final String IDS_DOC_PUB_DATE		= "doc_pub_date"	;
	public static final String IDS_DOC_SITE			= "doc_site"		;
	public static final String IDS_DOC_DATE			= "doc_date"		;// date created
	public static final String IDS_DOC_TIMESTAMP	= "timestamp"		;
	public static final String IDS_DOC_DWNLD_URL	= "document_url"	;
	
	// Columns unique to the BING Search table
	public static final String BING_SEARCH_ID	= _ID				;
	public static final String BING_QUERY		= "query_string"	;
	public static final String QUERY_DATE		= "createdtime"		;
	public static final String QUERY_VIEW_DATE	= "viewedtime"		;
	
	//Columns unique to the BING Results table
	public static final String BING_TITLE		= "title"		;
	public static final String BING_DESC		= "description"	;
	public static final String BING_URL			= "result_url"	;
	public static final String BING_DISP_URL	= "display_url"	;
	public static final String BING_DATE_TIME	= "datetime"	;
	
	
	//Columns unique to the API table
	public static final String API_ID			= _ID			;
	public static final String API_NAME			= "name"		;
	public static final String API_DESC			= "description"	;
	public static final String BASE_URI			= "base_url"	;
	
	//Columns unique to the Search Period PERIOD table
	public static final String PERIOD_ID		= _ID			;
	public static final String PERIOD_NAME		= "name"		;
	public static final String P_START_DATE		= "start_date"	;
	public static final String P_END_DATE		= "end_date"	;
	
	
	//Columns unique to the World Bank Data WB_DATA table
	public static final String WB_DATA_ID		= _ID				;
	public static final String IND_VALUE		= "value"			;
	public static final String IND_DECIMAL		= "decimal"			;
	public static final String IND_DATE			= "date"			;
	
	
	//Columns unique to IDS Data(Document) Table
	public static final String DOCUMENT_ID			= _ID				    ;
	public static final String DOC_TITLE			= "title"				;
	public static final String LANGUAGE_NAME		= "language_name"		;
	public static final String LICENCE_TYPE			= "licence_type"		;
	public static final String PUBLICATION_DATE		= "publication_date"	;
	public static final String PUBLISHER			= "publisher"			;
	public static final String PUBLISHER_COUNTRY	= "publisher_country"	;
	public static final String JOURNAL_SITE			= "site"				;
	public static final String DOC_NAME				= "name"				;
	public static final String DATE_CREATED			= "date_created"		;
	public static final String DATE_UPDATED			= "date_updated"		;
	public static final String WEBSITE_URL			= "website_url"			;
	
	
	//Columns unique to Author table
	public static final String AUTHOR_ID		= _ID			;
	public static final String AUTHOR_NAME		= "author" 		;
	
	//Columns unique to the themes table
	public static final String THEME_ID			= _ID    		;
	public static final String THEME_NAME		= "name"		;
	public static final String IDS_THEME_ID		= "theme_id"	;
	public static final String THEME_URL		= "theme_url"	;
	public static final String THEME_LEVEL		= "level"		;
	
	//reference keys
	public static final String S_ID		= "search_id"		;// Search ID 
	public static final String IDS_S_ID	= "ids_search_id"	;
	public static final String C_ID		= "country_id"		;// Country ID
	public static final String I_ID		= "indicator_id"	;// Indicator ID
	public static final String D_ID		= "document_id"		;// Document ID
	public static final String A_ID 	= "author_id"		;// Author ID
	public static final String T_ID		= "theme_id"		;// Theme ID
	public static final String P_ID		= "period_id"		;// Period ID
	public static final String AP_ID	= "api_id"			;
	public static final String SC_ID	= "search_counrty"	;
	public static final String B_S_ID	= "bing_search_id"	;
	
	
	
	// Column Arrays for individual tables
	public static final String[] FROM_COUNTRY 				= {COUNTRY_ID, WB_COUNTRY_ID, WB_COUNTRY_CODE, COUNTRY_NAME, CAPITAL_CITY,
																INCOME_LEVEL_ID, INCOME_LEVEL_NAME,
																COUNTRY_REGION_ID, COUNTRY_REGION_NAME, 
																GDP, GNI_CAPITA, POVERTY, LIFE_EX, LITERACY, POPULATION, 					};
	
	public static final String[] FROM_INDICATOR				= {INDICATOR_ID, WB_INDICATOR_ID, INDICATOR_NAME, INDICATOR_DESC				};
	
	public static final String[] FROM_SEARCH				= {SEARCH_ID, I_ID, AP_ID, SEARCH_CREATED, SEARCH_MODIFIED, SEARCH_VIEWED,
																SEARCH_URI																	};
	
	public static final String[] FROM_API					= {API_ID, API_NAME, API_DESC, BASE_URI											};
	
	public static final String[] FROM_BING_SEARCH_TABLE		= {BING_SEARCH_ID, BING_QUERY, QUERY_DATE, QUERY_VIEW_DATE						};

	public static final String[] FROM_BING_SEARCH_RESULTS	= {_ID, B_S_ID, BING_TITLE, BING_DESC, BING_URL, BING_DISP_URL, BING_DATE_TIME, 
																QUERY_VIEW_DATE																};
	
	public static final String[] FROM_WB_DATA				= {WB_DATA_ID, SC_ID, IND_VALUE, IND_DECIMAL, IND_DATE							};
	
	public static final String[] FROM_SEARCH_COUNTRY		= {_ID, S_ID, C_ID, P_ID														};
	
	public static final String[] FROM_PERIOD				= {PERIOD_ID, PERIOD_NAME, P_START_DATE, P_END_DATE								};
	
	public static final String[] FROM_IDS_SEARCH_TABLE		= {IDS_SEARCH_ID, I_ID, IDS_BASE_URL, IDS_SITE, IDS_OBJECT, IDS_TIMESTAMP,
																IDS_VIEW_DATE																};
	
	public static final String[] FROM_IDS_SEARCH_PARAMS		= {_ID, IDS_S_ID, IDS_PARAMETER, IDS_OPERAND, IDS_PARAM_VALUE, COMBINATION		};
	
	public static final String[] FROM_IDS_SEARCH_RESULTS	= {_ID, IDS_S_ID, IDS_DOC_URL, IDS_DOC_ID, IDS_DOC_TYPE, IDS_DOC_TITLE, 
																IDS_DOC_AUTH_STR, IDS_DOC_PUB, IDS_DOC_PUB_DATE, IDS_DOC_DESC,
																IDS_DOC_SITE, IDS_DOC_DATE, IDS_DOC_TIMESTAMP, IDS_DOC_DWNLD_URL,
																IDS_VIEW_DATE, IDS_DOC_PATH																};
	
	public static final String[] FROM_IDS_DATA				= {DOCUMENT_ID, IDS_S_ID, DOC_TITLE,LANGUAGE_NAME, LICENCE_TYPE,
																PUBLICATION_DATE, PUBLISHER, PUBLISHER_COUNTRY, JOURNAL_SITE,
																DOC_NAME, DATE_CREATED, DATE_UPDATED, WEBSITE_URL							};
	
	public static final String[] FROM_IDS_AUTHOR			= {AUTHOR_ID, D_ID, AUTHOR_NAME													};
	
	public static final String[] FROM_IDS_DOC_THEME			= {_ID, T_ID, D_ID																};
	
	public static final String[] FROM_IDS_THEME				= {THEME_ID, THEME_NAME, IDS_THEME_ID, THEME_URL, THEME_LEVEL					};
	
	/*****************************************************************************************************************************/
	/*====APP CONSTANTS======================
	* Constants used by the database adapter class
	*/
	public static final String API_ERROR = "error";
	public static final String DB_SEARCH = "database";
}	
