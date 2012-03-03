package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.INDICATOR_LIST;
import static jm.org.data.area.DBConstants.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AREAData {
	
	private static final String TAG = AREAData.class.getSimpleName();
	private int currentApiVersion = android.os.Build.VERSION.SDK_INT;

	Context context;
	AreaDB dbHelper;

	public AREAData(Context context){
		this.context = context;
		dbHelper = new AreaDB(context);
	}

	/**
	* Insert record into specified table. Includes duplication check to prevent double entries
	*
	* @param tableName Name of table record to be inserted into
	* @param tableRecord Name-value pairs
	*/
	public void insert(String tableName, ContentValues tableRecord) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int tableCode = INDICATOR_LIST; //AreaApplication.getTableCode(tableName);
		String tableKey = "";
		String tableKeyAdd = "";
		Cursor cursor = null;

		//Getting correct primary key for tables
		switch (tableCode) {
			case INDICATOR_LIST:
				tableKey = WB_INDICATOR_ID;
				break;
			
		}

		//Duplicate Check
		if (tableCode == INDICATOR_LIST) {
			cursor = db.query(tableName, null, tableKey + "=" + tableRecord.get(tableKey), null, null, null, null);
		} else { //Special condition for double primary key on crop table
			//cursor = db.query(tableName, null, String.format("%s=%s AND %s=%s", tableKey, tableRecord.get(tableKey), tableKeyAdd, tableRecord.get(tableKeyAdd)), null, null, null, null);
		}
		
		if (cursor.getCount() == 1) {
			Log.d(TAG, String.format("Record already exists in table %s", tableName));
		} else {
			try {
				db.insertOrThrow(tableName, null, tableRecord);
				Log.d(TAG, String.format("Inserting into table %s", tableName));
			}
			catch (RuntimeException e) {
				Log.e(TAG,"Farmer Insertion Exception: "+e.toString());
			}
		}
		cursor.close();
		db.close();
	}
	
	private class AreaDB extends SQLiteOpenHelper{
		
		private static final int DATABASE_VERSION = 36;
		private SQLiteDatabase db;
		
		
		public AreaDB(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		private static final String CREATE_TABLE_COUNTRY = "create table " + COUNTRY + " ( "
				+ COUNTRY_ID + " integer primary key autoincrement, "
				+ WB_COUNTRY_ID + " text not null, "
				+ COUNTRY_NAME + " text not null, "
				+ INCOME_LEVEL + " text not null, "
				+ POPULATION + " integer not null, "
				+ COUNTRY_REGION + " text not null, "
				+ GDP + " integer not null, "
				+ GNI_CAPITA + " integer not null, "
				+ POVERTY + " integer not null, "
				+ LIFE_EX + " integer not null, "
				+ LITERACY + " integer not null, )";
		
		private static final String CREATE_TABLE_INDICATOR = "create table " + INDICATOR + " ( "
				+ INDICATOR_ID + " integer primary key autoincrement, "
				+ WB_INDICATOR_ID + " integer not null, "
				+ INDICATOR_NAME + "text not null, "
				+ INDICATOR_DESC + "text not null )";

		private static final String CREATE_TABLE_SEARCH = "create table " + SEARCH + " ( "
				+ SEARCH_ID + " integer primary key autoincrement, "
				+ I_ID + " integer not null, "
				+ AP_ID + " integer not null, "
				+ SEARCH_CREATED + " datetime not null, "
				+ SEARCH_MODIFIED + " datetime not null, "
				+ SEARCH_URI + " text not null )" ;
		
		private static final String CREATE_TABLE_API = "create table " + API + " ( "
				+ API_ID + " integer primary key autoincrement, "
				+ API_NAME + " text not null, "
				+ API_DESC + " text not null," 
				+ BASE_URI + " text not null )";
		
		private static final String CREATE_TABLE_PARAMETER = "create table " + PARAMETER + " ( "
				+ PARAM_ID + " integer primary key autoincrement, "
				+ S_ID + " integer not null, "
				+ PARAM	+ " text not null )";
		
		private static final String CREATE_TABLE_WB_DATA = "create table " + WB_DATA + " ( "
				+ WB_DATA_ID + " integer primary key autoincrement, "
				+ I_ID + " integer not null, "
				+ C_ID + " integer not null, "
				+ S_ID + " integer not null, "
				+ IND_VALUE + " integer not null, "
				+ IND_DECIMAL + " integer not null, "
				+ IND_DATE + " integer not null ) ";
			
		private static final String CREATE_TABLE_SEARCH_COUNTRY = "create table " + SEARCH_COUNTRY + " ( "
				+ _ID + " integer primary key autoincrement, "
				+ S_ID + " integer not null, "
				+ C_ID + " integer not null, "
				+ P_ID + " integer not null )";
		
		private static final String CREATE_TABLE_PERIOD = "create table " + PERIOD + " ( "
				+ PERIOD_ID + " integer primary key autoincrement, "
				+ PERIOD_NAME + " text not null, "
				+ P_START_DATE + " integer not null, "
				+ P_END_DATE + " integer not null )";

		private static final String CREATE_TABLE_IDS_DATA = "create table " + IDS_DATA  + " ( "
				+ DOCUMENT_ID + " integer primary key autoincrement, "
				+ S_ID + " integer not null, "
				+ DOC_TITLE + " text not null, "
				+ LANGUAGE_NAME + " text not null, "
				+ LICENCE_TYPE + " text not null, "
				+ PUBLICATION_DATE + "datetime not null, "
				+ PUBLISHER + " text not null, "
				+ PUBLISHER_COUNTRY + " text not null, "
				+ JOURNAL_SITE + " text not null, "
				+ DOC_NAME + " text not null, "
				+ DATE_CREATED + " datetime not null, "
				+ DATE_UPDATED + "datetime not null, "
				+ WEBSITE_URL + " text not null )";
		
		private static final String CREATE_TABLE_IDS_AUTHOR = "create table " + IDS_AUTHOR + " ( "
				+ AUTHOR_ID + " integer primary key autoincrement, "
				+ D_ID + " integer not null, "
				+ AUTHOR_NAME + " text not null )";
		
		private static final String CREATE_TABLE_IDS_DOC_THEME = "create table " + IDS_DOC_THEME + " ( "
				+ _ID + " integer primary key autoincrement, "
				+ T_ID + "integer not null, "
				+ D_ID + "integer not null )";
		
		private static final String CREATE_TABLE_IDS_THEME = "create table" + IDS_THEME + " ( "
				+ THEME_ID + " integer primary key autoincrement, "
				+ THEME_NAME + " text not null, "
				+ IDS_THEME_ID + " text not null, "
				+ THEME_URL + " text not null, "
				+ THEME_LEVEL + "integer )" ;
		
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
			String query = "SELECT "+ tableColumns + " FROM " + tableName +" WHERE " + queryParams;
			try {
				cursor = db.rawQuery(query, null);
				Log.d("AREA", "Raw Query: " + query);
				Log.d("AREA", "Raw Query Result: Returned " + cursor.getCount() + " record(s)");
			} catch (SQLException e) {
				Log.e("AREA", "Raw Query Exception: " + e.toString());
			}
			return cursor;
		}
		
		
	}
}
