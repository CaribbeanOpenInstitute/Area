package jm.org.data.area;

import android.provider.BaseColumns;

public interface DBConstants extends BaseColumns {
	
	
	/*****************************************************************************************************************************/
	/*====DATABASE CONSTANTS======================
	* Constants used by the database adapter class
	*/
	
	//Databases
	public final String DATABASE_NAME		= "area.db"			;
	
	//Tables for area Database
	public final String COUNTRY				= "area_country"			;
	public final String INDICATOR			= "area_indicator"			;
	public final String SEARCH				= "area_search"				;
	public final String API					= "area_api"				;
	public final String PARAMETER			= "area_param"				;
	public final String WB_DATA				= "area_wb_data"			;
	public final String SEARCH_COUNTRY		= "area_search_country"		;
	public final String PERIOD				= "area_search_period"		;
	public final String IDS_DATA			= "area_ids_data"			;
	public final String IDS_AUTHOR			= "area_ids_author"			;
	public final String IDS_DOC_THEME		= "area_ids_data_theme"		;
	public final String IDS_THEME			= "area_ids_theme"			;
	
	
	// Columns unique to Country Table
	public final String COUNTRY_ID			= _ID				;
	public final String WB_COUNTRY_ID		= "country_id"		;
	public final String COUNTRY_NAME		= "name"			;
	public final String INCOME_LEVEL		= "income"			;
	public final String POPULATION			= "population"		;
	public final String COUNTRY_REGION		= "region"			;
	public final String GDP					= "gdp"				;
	public final String GNI_CAPITA			= "gni_per_capita"	;
	public final String POVERTY				= "poverty"			;
	public final String LIFE_EX				= "life_expectancy"	;
	public final String LITERACY			= "literacy_rate"	;
	
	//Columns unique to Indicators Table
	public final String INDICATOR_ID		= _ID				;
	public final String WB_INDICATOR_ID		= "indicator_id"	;
	public final String INDICATOR_NAME		= "name"			;
	public final String INDICATOR_DESC		= "Description"		;
	
	
	//Columns unique to Search Table
	public final String SEARCH_ID			= _ID		 		;
	public final String SEARCH_CREATED		= "createdtime"	 	;
	public final String SEARCH_MODIFIED		= "modifiedtime" 	;
	public final String SEARCH_URI			= "uri"				;
	
	//Columns unique to the API table
	public final String API_ID				= _ID			;
	public final String API_NAME			= "name"		;
	public final String API_DESC			= "description"	;
	public final String BASE_URI			= "base_url"	;
	
	
	//Columns unique to the PARAMs Table
	public final String PARAM_ID		= _ID			;
	public final String PARAM			= "parameter"	;
	
	
	//Columns unique to the Search Period PERIOD table
	public final String PERIOD_ID		= _ID			;
	public final String PERIOD_NAME		= "name"		;
	public final String P_START_DATE	= "start_date"	;
	public final String P_END_DATE		= "end_date"	;
	
	
	//Columns unique to the World Bank Data WB_DATA table
	public final String WB_DATA_ID		= _ID				;
	public final String IND_VALUE		= "value"			;
	public final String IND_DECIMAL		= "decimal"			;
	public final String IND_DATE		= "date"			;
	
	
	//Columns unique to IDS Data(Document) Table
	public final String DOCUMENT_ID				= _ID				    ;
	public final String DOC_TITLE				= "title"				;
	public final String LANGUAGE_NAME			= "language_name"		;
	public final String LICENCE_TYPE			= "licence_type"		;
	public final String PUBLICATION_DATE		= "publication_date"	;
	public final String PUBLISHER				= "publisher"			;
	public final String PUBLISHER_COUNTRY		= "publisher_country"	;
	public final String JOURNAL_SITE			= "site"				;
	public final String DOC_NAME				= "name"				;
	public final String DATE_CREATED			= "date_created"		;
	public final String DATE_UPDATED			= "date_updated"		;
	public final String WEBSITE_URL				= "website_url"			;
	
	
	//Columns unique to Author table
	public final String AUTHOR_ID		= _ID			;
	public final String AUTHOR_NAME		= "author" 		;
	
	//Columns unique to the themes table
	public final String THEME_ID		= _ID    		;
	public final String THEME_NAME		= "name"		;
	public final String IDS_THEME_ID	= "theme_id"	;
	public final String THEME_URL		= "theme_url"	;
	public final String THEME_LEVEL		= "level"		;
	
	//reference keys
	public final String S_ID	= "search_id"		;// Search ID 
	public final String C_ID	= "country_id"		;// Country ID
	public final String I_ID	= "indicator_id"	;// Indicator ID
	public final String D_ID	= "document_id"		;// Document ID
	public final String A_ID 	= "author_id"		;// Author ID
	public final String T_ID	= "theme_id"		;// Theme ID
	public final String P_ID	= "period_id"		;// Period ID
	public final String AP_ID	= "api_id"			;
	
	
	
	// Column Arrays for individual tables
	public final String[] FROM_COUNTRY 			= {COUNTRY_ID, WB_COUNTRY_ID, COUNTRY_NAME, INCOME_LEVEL, POPULATION, 
													COUNTRY_REGION, GDP, GNI_CAPITA, POVERTY, LIFE_EX, LITERACY					};
	
	public final String[] FROM_INDICATOR		= {INDICATOR_ID, WB_INDICATOR_ID, INDICATOR_NAME, INDICATOR_DESC				};
	
	public final String[] FROM_SEARCH			= {SEARCH_ID, I_ID, AP_ID, SEARCH_CREATED, SEARCH_MODIFIED, SEARCH_URI			};
	
	public final String[] FROM_API				= {API_ID, API_NAME, API_DESC, BASE_URI											};
	
	public final String[] FROM_PARAMETER		= {PARAM_ID, S_ID, PARAM														};
	
	public final String[] FROM_WB_DATA			= {WB_DATA_ID, I_ID, C_ID, S_ID, IND_VALUE, IND_DECIMAL, IND_DATE				};
	
	public final String[] FROM_SEARCH_COUNTRY	= {_ID, S_ID, C_ID, P_ID																};
	
	public final String[] FROM_PERIOD			= {PERIOD_ID, PERIOD_NAME, P_START_DATE, P_END_DATE								};
	
	public final String[] FROM_IDS_DATA			= {DOCUMENT_ID, S_ID, DOC_TITLE,LANGUAGE_NAME, LICENCE_TYPE,
													PUBLICATION_DATE, PUBLISHER, PUBLISHER_COUNTRY, JOURNAL_SITE,
													DOC_NAME, DATE_CREATED, DATE_UPDATED, WEBSITE_URL							};
	
	public final String[] FROM_IDS_AUTHOR		= {AUTHOR_ID, D_ID, AUTHOR_NAME														};
	
	public final String[] FROM_IDS_DOC_THEME	= {_ID, T_ID, D_ID																	};
	
	public final String[] FROM_IDS_THEME		= {THEME_ID, THEME_NAME, IDS_THEME_ID, THEME_URL, THEME_LEVEL					};
	
	/*****************************************************************************************************************************/
	/*====APP CONSTANTS======================
	* Constants used by the database adapter class
	*/
	public static final String API_ERROR = "error";
	public static final String DB_SEARCH = "database";
}	
