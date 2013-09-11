package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.DBConstants.*;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AreaDB extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 2;
	public static SQLiteDatabase db;


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
			+ SEARCH_CREATED 	+ " integer not null, "
			+ SEARCH_MODIFIED 	+ " integer not null, "
			+ SEARCH_VIEWED		+ " integer not null, "
			+ SEARCH_URI 		+ " text not null )" ;

	private static final String CREATE_TABLE_IDS_SEARCH = "create table " + IDS_SEARCH_TABLE + " ( "
			+ IDS_SEARCH_ID 		+ " integer primary key autoincrement, "
			+ I_ID 					+ " integer not null, "
			+ IDS_BASE_URL			+ " text not null,"
			+ IDS_SITE				+ " text not null, "
			+ IDS_OBJECT 			+ " text not null, " 
			+ IDS_TIMESTAMP			+ " integer not null,"
			+ IDS_VIEW_DATE			+ " integer not null )" ;

	private static final String CREATE_TABLE_IDS_SEARCH_PARAMS = "create table " + IDS_SEARCH_PARAMS + " ( "
			+ _ID 				+ " integer primary key autoincrement, "
			+ IDS_S_ID			+ " integer not null,"
			+ IDS_PARAMETER		+ " text not null, "
			+ IDS_OPERAND		+ " text not null, "
			+ IDS_PARAM_VALUE	+ " text not null, "
			+ COMBINATION 		+ " text not null )" ;

	private static final String CREATE_TABLE_IDS_SEARCH_RESULTS = "create table " + IDS_SEARCH_RESULTS + " ( "
			+ _ID 				+ " integer primary key autoincrement, "
			+ IDS_S_ID			+ " integer not null,"
			+ IDS_P_ID			+ " integer not null,"
			+ IDS_DOC_URL		+ " text not null, "
			+ IDS_DOC_ID		+ " text not null, "
			+ IDS_DOC_TYPE		+ " text not null, "
			+ IDS_DOC_TITLE		+ " text not null, "
			+ IDS_DOC_AUTH_STR	+ " text , "
			+ IDS_DOC_PUB		+ " text , "
			+ IDS_DOC_PUB_DATE	+ " text , "
			+ IDS_DOC_DESC		+ " text , "
			+ IDS_DOC_SITE		+ " text , "
			+ IDS_DOC_DATE		+ " text , "
			+ IDS_DOC_TIMESTAMP + " text not null, "
			+ IDS_DOC_DWNLD_URL + " text not null, "
			+ IDS_VIEW_DATE		+ " integer,"
			+ IDS_DOC_PATH 		+ " text not null )" ;

	private static final String CREATE_TABLE_API = "create table " + API + " ( "
			+ API_ID 			+ " integer primary key autoincrement, "
			+ API_NAME 			+ " text not null, "
			+ API_DESC 			+ " text not null, " 
			+ BASE_URI 			+ " text not null )";

	private static final String CREATE_TABLE_BING_SEARCH = "create table " + BING_SEARCH_TABLE + " ( "
			+ BING_SEARCH_ID	+ " integer primary key autoincrement, "
			+ BING_QUERY		+ " text not null, "
			+ QUERY_DATE		+ " integer not null, "
			+ QUERY_VIEW_DATE	+ " integer not null)";
	
	private static final String CREATE_TABLE_BING_SEARCH_RESULTS = "create table " + BING_SEARCH_RESULTS + " ( "
			+ _ID	 			+ " integer primary key autoincrement, "
			+ B_S_ID			+ " integer not null,"
			+ BING_TITLE 		+ " text not null, "
			+ BING_DESC 		+ " text not null, " 
			+ BING_URL			+ " text not null, "
			+ BING_DISP_URL		+ " text not null, "
			+ BING_DATE_TIME	+ " text not null, "
			+ QUERY_VIEW_DATE	+ " integer )";
	
	private static final String CREATE_TABLE_WB_DATA = "create table " + WB_DATA + " ( "
			+ WB_DATA_ID 	+ " integer primary key autoincrement, "
			//+ I_ID 			+ " integer not null, "
			//+ C_ID 			+ " integer not null, "
			//+ S_ID 			+ " integer not null, "
			+ SC_ID 		+ " integer not null, "
			+ IND_VALUE 	+ " double not null, "
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
	
	private static String CREATE_TABLE_WB_CATEGORIES  = "create table " + WB_CATEGORY + " ( " 
			+ CATEGORY_ID 		+ " integer primary key autoincrement, "
			+ WB_CATEGORY_ID 	+ " integer not null, "
			+ CATEGORY_NAME 	+ " text not null, "
			+ CATEGORY_DESC 	+ " text not null)" ;
	
	private static String CREATE_TABLE_WB_IND_CATEGORIES  = "create table " + IND_CATEGORIES + " ( " 
			+ IND_CAT_ID 	+ " integer primary key autoincrement, "
			+ CAT_ID 		+ " integer not null, "
			+ I_ID 			+ " integer not null)" ;
	
	private static String CREATE_TABLE_AREA_COLLECTIONS = "create table " + COLLECTIONS + " ( " 
			+ COLLECTION_ID 		+ " integer primary key autoincrement, "
			+ COLLECTION_NAME 		+ " text not null, "
			+ COLLECTION_DESC 		+ " text)" ;
	
	private static String CREATE_TABLE_AREA_COLLECTIONS_DATA  = "create table " + COLL_DATA + " ( " 
			+ COLL_DATA_ID 	+ " integer primary key autoincrement, "
			+ COLL_ID 		+ " integer not null, "
			+ S_D_ID 		+ " integer not null)" ;
	
	private static String CREATE_TABLE_AREA_SAVED_DATA  = "create table " + SAVED_DATA + " ( " 
			+ SAVED_DATA_ID 	+ " integer primary key autoincrement, "
			+ D_T_ID 			+ " integer not null, "
			+ ENTITY_ID 		+ " integer not null)" ;
	
	private static String CREATE_TABLE_AREA_DATA_TYPES  = "create table " + DATA_TYPES + " ( " 
			+ DATA_TYPE_ID 		+ " integer primary key autoincrement, "
			+ DATA_TYPE_NAME 	+ " text not null, "
			+ DATA_TYPE_DESC	+ " text not null)" ;
	
	private static String CREATE_TABLE_AREA_CHARTS  = "create table " + CHARTS + " ( " 
			+ CHART_ID 			+ " integer primary key autoincrement, "
			+ CHART_NAME 		+ " text not null, "
			+ CHART_DESC 		+ " text not null, "
			+ I_ID 				+ " integer not null, "
			+ CHART_COUNTRIES 	+ " text not null, " 
			+ I_POSITION		+ " integer not null, "
			+ I_GROUP 			+ " integer not null)" ;
	
	private static String CREATE_TABLE_AREA_SELECTIONS  = "create table " + AREA_SELECTIONS + " ( " 
			+ SELECTION_ID 			+ " integer primary key autoincrement, "
			+ SELECTION_NAME 		+ " text not null, "
			+ SELECTION_DESC 		+ " text not null)" ;
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			
			db.execSQL(CREATE_TABLE_AREA_SELECTIONS													);
			Log.d("AREA", "Create SELECTIONS table: " + CREATE_TABLE_AREA_SELECTIONS				);
			
			db.execSQL(CREATE_TABLE_COUNTRY															);
			Log.d("AREA", "Create COUNTRY table: " + CREATE_TABLE_COUNTRY							);

			db.execSQL(CREATE_TABLE_INDICATOR														);
			Log.d("AREA", "Create INDICATOR table: " + CREATE_TABLE_INDICATOR						);
			
			db.execSQL(CREATE_TABLE_WB_CATEGORIES													);
			Log.d("AREA", "Create CATEGORY table: " + CREATE_TABLE_WB_CATEGORIES					);
			
			db.execSQL(CREATE_TABLE_WB_IND_CATEGORIES												);
			Log.d("AREA", "Create INDICATOR CATEGORIES table: " + CREATE_TABLE_WB_IND_CATEGORIES	);
			
			db.execSQL(CREATE_TABLE_AREA_COLLECTIONS												);
			Log.d("AREA", "Create COLLECTIONS table: " + CREATE_TABLE_AREA_COLLECTIONS				);
			
			db.execSQL(CREATE_TABLE_AREA_COLLECTIONS_DATA											);
			Log.d("AREA", "Create COLECTIONS DATA table: " + CREATE_TABLE_AREA_COLLECTIONS_DATA		);
			
			db.execSQL(CREATE_TABLE_AREA_SAVED_DATA													);
			Log.d("AREA", "Create SAVED DATA table: " + CREATE_TABLE_AREA_SAVED_DATA				);
			
			db.execSQL(CREATE_TABLE_AREA_DATA_TYPES													);
			Log.d("AREA", "Create DATA TYPES table: " + CREATE_TABLE_AREA_DATA_TYPES				);
			
			db.execSQL(CREATE_TABLE_AREA_CHARTS														);
			Log.d("AREA", "Create CHARTS table: " + CREATE_TABLE_AREA_CHARTS						);
						
			db.execSQL(CREATE_TABLE_SEARCH															);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_SEARCH								);

			db.execSQL(CREATE_TABLE_IDS_SEARCH														);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH							);

			db.execSQL(CREATE_TABLE_IDS_SEARCH_PARAMS												);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH_PARAMS					);

			db.execSQL(CREATE_TABLE_IDS_SEARCH_RESULTS												);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH_RESULTS					);

			db.execSQL(CREATE_TABLE_API																);
			Log.d("AREA", "Create API table: " + CREATE_TABLE_API									);

			db.execSQL(CREATE_TABLE_BING_SEARCH														);
			Log.d("AREA", "Create Bing search table: " + CREATE_TABLE_BING_SEARCH					);

			db.execSQL(CREATE_TABLE_BING_SEARCH_RESULTS												);
			Log.d("AREA", "Create bing search results table: " + CREATE_TABLE_BING_SEARCH_RESULTS	);

			db.execSQL(CREATE_TABLE_WB_DATA															);
			Log.d("AREA", "Create WB_DATA table: " + CREATE_TABLE_WB_DATA							);

			db.execSQL(CREATE_TABLE_SEARCH_COUNTRY													);
			Log.d("AREA", "Create SEARCH_COUNTRY table: " + CREATE_TABLE_SEARCH_COUNTRY				);

			db.execSQL(CREATE_TABLE_PERIOD															);
			Log.d("AREA", "Create PERIOD table: " + CREATE_TABLE_PERIOD								);

			db.execSQL(CREATE_TABLE_IDS_DATA														);
			Log.d("AREA", "Create IDS_DATA table: " + CREATE_TABLE_IDS_DATA							);

			db.execSQL(CREATE_TABLE_IDS_AUTHOR														);
			Log.d("AREA", "Create IDS_AUTHOR table: " + CREATE_TABLE_IDS_AUTHOR						);

			db.execSQL(CREATE_TABLE_IDS_DOC_THEME													);
			Log.d("AREA", "Create IDS_DOC_THEME table: " + CREATE_TABLE_IDS_DOC_THEME				);

			db.execSQL(CREATE_TABLE_IDS_THEME														);
			Log.d("AREA", "Create IDS_THEME table: " + CREATE_TABLE_IDS_THEME						);


		} catch (RuntimeException e) {
		Log.d("AREA", "Unable to create tables: ");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w("AREA", "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		try {
			db.execSQL("DROP TABLE IF EXISTS " + AREA_SELECTIONS		);
			db.execSQL("DROP TABLE IF EXISTS " + COUNTRY				);
			db.execSQL("DROP TABLE IF EXISTS " + INDICATOR				);
			db.execSQL("DROP TABLE IF EXISTS " + WB_CATEGORY			);
			db.execSQL("DROP TABLE IF EXISTS " + IND_CATEGORIES			);
			db.execSQL("DROP TABLE IF EXISTS " + COLLECTIONS			);
			db.execSQL("DROP TABLE IF EXISTS " + COLL_DATA				);
			db.execSQL("DROP TABLE IF EXISTS " + SAVED_DATA				);
			db.execSQL("DROP TABLE IF EXISTS " + CHARTS					);
			db.execSQL("DROP TABLE IF EXISTS " + DATA_TYPES				);
			db.execSQL("DROP TABLE IF EXISTS " + SEARCH					);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_SEARCH_TABLE		);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_SEARCH_PARAMS		);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_SEARCH_RESULTS		);
			db.execSQL("DROP TABLE IF EXISTS " + API					);
			db.execSQL("DROP TABLE IF EXISTS " + BING_SEARCH_TABLE		);
			db.execSQL("DROP TABLE IF EXISTS " + BING_SEARCH_RESULTS	);
			db.execSQL("DROP TABLE IF EXISTS " + WB_DATA				);
			db.execSQL("DROP TABLE IF EXISTS " + SEARCH_COUNTRY			);
			db.execSQL("DROP TABLE IF EXISTS " + PERIOD					);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_DATA				);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_AUTHOR				);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_DOC_THEME			);
			db.execSQL("DROP TABLE IF EXISTS " + IDS_THEME 				);
		} catch (SQLException e) {
			Log.d("AREA", "Upgrade step: " + "Unable to DROP TABLES");
		}

		onCreate(db);
	}

	synchronized public Cursor rawQuery(String tableName, String tableColumns, String queryParams) {
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
		} catch (IllegalStateException ilEc){
			db.close();
			return rawQuery(tableName, tableColumns,queryParams);
		}
		db.close();
		return cursor;
	}


}