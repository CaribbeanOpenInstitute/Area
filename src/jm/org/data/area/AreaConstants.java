package jm.org.data.area;

public interface AreaConstants {
	
	/*
	 * Search Response Message Types
	 */
	public static final int SEARCH_FAIL 	= -1;
	public static final int SEARCH_SUCCESS 	=  0;
	public static final int SEARCH_API_SOME =  1;
	public static final int SEARCH_API_NONE =  2;

	public static final String WB_BASE_URL 		= "http://api.worldbank.org/"		;
	public static final String IDS_DASE_URL		= "http://api.ids.ac.uk/openapi/"	; 
	public static final String BING_BASE_URL	= ""								;
	
	public static final int INDICATOR_LIST 		= 0; 
	public static final int COUNTRY_LIST 		= 1;
	public static final int COUNTRY_INFO		= 2;
	public static final int GLOBAl_SEARCH		= 3;
	public static final int GENERIC_SEARCH		= 4;
	
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
	
	public static final String WB_COUN_ID 		= "id" 			;
	public static final String WB_COUN_NAME		= "name"		;
	public static final String WB_COUN_DESC		= "sourceNote"	;
	public static final String[] WB_COUN_LIST	= {WB_COUN_ID, WB_COUN_NAME, WB_COUN_DESC};
}
