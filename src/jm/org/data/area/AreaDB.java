package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.API_LIST;
import static jm.org.data.area.AreaConstants.BING_RESULT_DATA;
import static jm.org.data.area.AreaConstants.BING_SEARCH;
import static jm.org.data.area.AreaConstants.BING_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.COUNTRY_LIST;
import static jm.org.data.area.AreaConstants.COUNTRY_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.FATAL_ERROR;
import static jm.org.data.area.AreaConstants.IDS_PARAM_DATA;
import static jm.org.data.area.AreaConstants.IDS_RESULT_DATA;
import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.IDS_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.INDICATOR_KEYWORDS;
import static jm.org.data.area.AreaConstants.INDICATOR_LIST;
import static jm.org.data.area.AreaConstants.PERIOD_LIST;
import static jm.org.data.area.AreaConstants.RETURN_CNTRY_IDs;
import static jm.org.data.area.AreaConstants.RETURN_COUNTRIES;
import static jm.org.data.area.AreaConstants.RETURN_DATE;
import static jm.org.data.area.AreaConstants.RETURN_IND_ID;
import static jm.org.data.area.AreaConstants.RETURN_KEYWORDS;
import static jm.org.data.area.AreaConstants.RETURN_STRING;
import static jm.org.data.area.AreaConstants.RETURN_VALUE;
import static jm.org.data.area.AreaConstants.RETURN_WB_IND_ID;
import static jm.org.data.area.AreaConstants.SEARCH_API_NONE;
import static jm.org.data.area.AreaConstants.SEARCH_API_SOME;
import static jm.org.data.area.AreaConstants.SEARCH_DATA;
import static jm.org.data.area.AreaConstants.SEARCH_FAIL;
import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.WB_SEARCH_DATA;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static jm.org.data.area.DBConstants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public class AreaDB extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
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
			+ IDS_DOC_URL		+ " text not null, "
			+ IDS_DOC_ID		+ " text not null, "
			+ IDS_DOC_TYPE		+ " text not null, "
			+ IDS_DOC_TITLE		+ " text not null, "
			+ IDS_DOC_AUTH_STR	+ " text not null, "
			+ IDS_DOC_PUB		+ " text not null, "
			+ IDS_DOC_PUB_DATE	+ " text not null, "
			+ IDS_DOC_DESC		+ " text not null, "
			+ IDS_DOC_SITE		+ " text not null, "
			+ IDS_DOC_DATE		+ " text not null, "
			+ IDS_DOC_TIMESTAMP + " text not null, "
			+ IDS_DOC_DWNLD_URL + " text not null, "
			+ IDS_VIEW_DATE		+ " integer,"
			+ IDS_DOC_PATH 		+ " text not null )" ;

	/* FROM_IDS_SEARCH_RESULTS	= {_ID, IDS_S_ID, IDS_DOC_URL, IDS_DOC_ID, IDS_DOC_TYPE, IDS_DOC_TITLE, 
									IDS_DOC_AUTH_STR, IDS_DOC_PUB, IDS_DOC_PUB_DATE, IDS_DOC_DESC,
									IDS_DOC_SITE, IDS_DOC_DATE, IDS_DOC_TIMESTAMP, IDS_DOC_DWNLD_URL, IDS_DOC_PATH					};*/

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
	//public static final String[] FROM_BING_SEARCH_TABLE		= {BING_SEARCH_ID, BING_QUERY, QUERY_DATE };

	private static final String CREATE_TABLE_BING_SEARCH_RESULTS = "create table " + BING_SEARCH_RESULTS + " ( "
			+ _ID	 			+ " integer primary key autoincrement, "
			+ B_S_ID			+ " integer not null,"
			+ BING_TITLE 		+ " text not null, "
			+ BING_DESC 		+ " text not null, " 
			+ BING_URL			+ " text not null, "
			+ BING_DISP_URL		+ " text not null, "
			+ BING_DATE_TIME	+ " text not null, "
			+ QUERY_VIEW_DATE	+ " integer )";
	//public static final String[] FROM_BING_SEARCH_RESULTS	= {_ID, B_S_ID, BING_TITLE, BING_DESC, BING_URL, BING_DISP_URL, BING_DATE_TIME	};

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

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_TABLE_COUNTRY															);
			Log.d("AREA", "Create COUNTRY table: " + CREATE_TABLE_COUNTRY					);

			db.execSQL(CREATE_TABLE_INDICATOR														);
			Log.d("AREA", "Create INDICATOR table: " + CREATE_TABLE_INDICATOR				);

			db.execSQL(CREATE_TABLE_SEARCH															);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_SEARCH						);

			db.execSQL(CREATE_TABLE_IDS_SEARCH														);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH					);

			db.execSQL(CREATE_TABLE_IDS_SEARCH_PARAMS												);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH_PARAMS			);

			db.execSQL(CREATE_TABLE_IDS_SEARCH_RESULTS										);
			Log.d("AREA", "Create SEARCH table: " + CREATE_TABLE_IDS_SEARCH_RESULTS					);

			db.execSQL(CREATE_TABLE_API														);
			Log.d("AREA", "Create API table: " + CREATE_TABLE_API									);

			db.execSQL(CREATE_TABLE_BING_SEARCH												);
			Log.d("AREA", "Create Bing search table: " + CREATE_TABLE_BING_SEARCH					);

			db.execSQL(CREATE_TABLE_BING_SEARCH_RESULTS										);
			Log.d("AREA", "Create bing search results table: " + CREATE_TABLE_BING_SEARCH_RESULTS	);

			db.execSQL(CREATE_TABLE_WB_DATA													);
			Log.d("AREA", "Create WB_DATA table: " + CREATE_TABLE_WB_DATA							);

			db.execSQL(CREATE_TABLE_SEARCH_COUNTRY											);
			Log.d("AREA", "Create SEARCH_COUNTRY table: " + CREATE_TABLE_SEARCH_COUNTRY				);

			db.execSQL(CREATE_TABLE_PERIOD													);
			Log.d("AREA", "Create PERIOD table: " + CREATE_TABLE_PERIOD								);

			db.execSQL(CREATE_TABLE_IDS_DATA												);
			Log.d("AREA", "Create IDS_DATA table: " + CREATE_TABLE_IDS_DATA							);

			db.execSQL(CREATE_TABLE_IDS_AUTHOR												);
			Log.d("AREA", "Create IDS_AUTHOR table: " + CREATE_TABLE_IDS_AUTHOR						);

			db.execSQL(CREATE_TABLE_IDS_DOC_THEME											);
			Log.d("AREA", "Create IDS_DOC_THEME table: " + CREATE_TABLE_IDS_DOC_THEME				);

			db.execSQL(CREATE_TABLE_IDS_THEME												);
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
			db.execSQL("DROP TABLE IF EXISTS " + COUNTRY				);
			db.execSQL("DROP TABLE IF EXISTS " + INDICATOR				);
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