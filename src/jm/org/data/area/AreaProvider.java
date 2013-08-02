package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.DBConstants.*;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import jm.org.data.area.AreaDB;

public class AreaProvider extends ContentProvider {
	public static final String AUTHORITY = "content://jm.org.data.area.AreaProvider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	public static final Uri COUNTRY_URI = Uri.parse(AUTHORITY+"/"+DBConstants.COUNTRY);
	public static final Uri INDICATOR_URI = Uri.parse(AUTHORITY+"/"+DBConstants.INDICATOR);
	public static final Uri SRCH_URI = Uri.parse(AUTHORITY+"/"+DBConstants.SEARCH);
	public static final Uri IDS_SEARCH_TABLE_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_SEARCH_TABLE);
	public static final Uri BING_SEARCH_TABLE_URI = Uri.parse(AUTHORITY+"/"+DBConstants.BING_SEARCH_TABLE);
	public static final Uri API_URI = Uri.parse(AUTHORITY+"/"+DBConstants.API);
	public static final Uri WB_DATA_URI = Uri.parse(AUTHORITY+"/"+DBConstants.WB_DATA);
	public static final Uri SEARCH_COUNTRY_URI = Uri.parse(AUTHORITY+"/"+DBConstants.SEARCH_COUNTRY);
	public static final Uri PERIOD_URI = Uri.parse(AUTHORITY+"/"+DBConstants.PERIOD);
	public static final Uri BING_SEARCH_RESULTS_URI = Uri.parse(AUTHORITY+"/"+DBConstants.BING_SEARCH_RESULTS);
	public static final Uri IDS_SEARCH_PARAMS_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_SEARCH_PARAMS);
	public static final Uri IDS_SEARCH_RESULTS_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_SEARCH_RESULTS);
	public static final Uri IDS_DATA_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_DATA);
	public static final Uri IDS_AUTHOR_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_AUTHOR);
	public static final Uri IDS_DOC_THEME_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_DOC_THEME);
	public static final Uri IDS_THEME_URI = Uri.parse(AUTHORITY+"/"+DBConstants.IDS_THEME);

	
	final static int COUNTRY = 1;
	final static int INDICATOR = 2;
	final static int SEARCH = 3;
	final static int IDS_SEARCH_TABLE = 4;
	final static int BING_SEARCH_TABLE = 5;
	final static int API = 6;
	final static int WB_DATA = 7;
	final static int SEARCH_COUNTRY = 8;
	final static int PERIOD = 9;
	final static int BING_SEARCH_RESULTS = 10;
	final static int IDS_SEARCH_PARAMS = 11;
	final static int IDS_SEARCH_RESULTS = 12;
	final static int IDS_DATA = 13;
	final static int IDS_AUTHOR = 14;
	final static int IDS_DOC_THEME = 15;
	final static int IDS_THEME = 16;
	
	final static int COUNTRY_ID = 17;
	final static int INDICATOR_ID = 18;
	final static int SEARCH_ID = 19;
	final static int IDS_SEARCH_TABLE_ID = 20;
	final static int BING_SEARCH_TABLE_ID = 21;
	final static int API_ID = 22;
	final static int WB_DATA_ID = 23;
	final static int SEARCH_COUNTRY_ID = 24;
	final static int PERIOD_ID = 25;
	final static int BING_SEARCH_RESULTS_ID = 26;
	final static int IDS_SEARCH_PARAMS_ID = 27;
	final static int IDS_SEARCH_RESULTS_ID = 28;
	final static int IDS_DATA_ID = 29;
	final static int IDS_AUTHOR_ID = 30;
	final static int IDS_DOC_THEME_ID = 31;
	final static int IDS_THEME_ID = 32;
	
	SQLiteDatabase db;
	AreaDB dbHelper;
	
	private final static UriMatcher uriMatcher;
	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, DBConstants.COUNTRY, COUNTRY);
		uriMatcher.addURI(AUTHORITY, DBConstants.COUNTRY+"/#", COUNTRY_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.INDICATOR, INDICATOR);
		uriMatcher.addURI(AUTHORITY, DBConstants.INDICATOR+"/#", INDICATOR_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.SEARCH, SEARCH);
		uriMatcher.addURI(AUTHORITY, DBConstants.SEARCH+"/#", SEARCH_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_TABLE, IDS_SEARCH_TABLE);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_TABLE+"/#", IDS_SEARCH_TABLE_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.BING_SEARCH_TABLE, BING_SEARCH_TABLE);
		uriMatcher.addURI(AUTHORITY, DBConstants.BING_SEARCH_TABLE+"/#", BING_SEARCH_TABLE_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.API, API);
		uriMatcher.addURI(AUTHORITY, DBConstants.API+"/#", API_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.WB_DATA, WB_DATA);
		uriMatcher.addURI(AUTHORITY, DBConstants.WB_DATA+"/#", WB_DATA_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.SEARCH_COUNTRY, SEARCH_COUNTRY);
		uriMatcher.addURI(AUTHORITY, DBConstants.SEARCH_COUNTRY+"/#", SEARCH_COUNTRY_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.PERIOD, PERIOD);
		uriMatcher.addURI(AUTHORITY, DBConstants.PERIOD+"/#", PERIOD_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.BING_SEARCH_RESULTS, BING_SEARCH_RESULTS);
		uriMatcher.addURI(AUTHORITY, DBConstants.BING_SEARCH_RESULTS+"/#", BING_SEARCH_RESULTS_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_PARAMS, IDS_SEARCH_PARAMS);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_PARAMS+"/#", IDS_SEARCH_PARAMS_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_RESULTS, IDS_SEARCH_RESULTS);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_SEARCH_RESULTS+"/#", IDS_SEARCH_RESULTS_ID);

		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_DATA, IDS_DATA);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_DATA+"/#", IDS_DATA_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_AUTHOR, IDS_AUTHOR);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_AUTHOR+"/#", IDS_AUTHOR_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_DOC_THEME, IDS_DOC_THEME);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_DOC_THEME+"/#", IDS_DOC_THEME_ID);
		
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_THEME, IDS_THEME);
		uriMatcher.addURI(AUTHORITY, DBConstants.IDS_THEME+"/#", IDS_THEME_ID);
		
		
	}

	@Override
	public boolean onCreate() {
		dbHelper = new AreaDB(getContext());
		return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = 0;
		db = dbHelper.getWritableDatabase();
		id = db.insertOrThrow(uri.getLastPathSegment(), null, values);
		
		/*switch(uriMatcher.match(uri)){
		case COUNTRY:
			id = db.insertOrThrow(DBConstants.COUNTRY, null, values);
			break;
		case INDICATOR:
			id = db.insertOrThrow(DBConstants.INDICATOR, null, values);
			break;
		case SEARCH:
			id = db.insertOrThrow(DBConstants.SEARCH, null, values);
			break;
		case IDS_SEARCH_TABLE:
			id = db.insertOrThrow(DBConstants.IDS_SEARCH_TABLE, null, values);
			break;
		case BING_SEARCH_TABLE:
			id = db.insertOrThrow(DBConstants.BING_SEARCH_TABLE, null, values);
			break;
		case API:
			id = db.insertOrThrow(DBConstants.API, null, values);
			break;
		case WB_DATA:
			id = db.insertOrThrow(DBConstants.WB_DATA, null, values);
			break;
		case SEARCH_COUNTRY:
			id = db.insertOrThrow(DBConstants.IDS_SEARCH_TABLE, null, values);
			break;
		case PERIOD:
			id = db.insertOrThrow(DBConstants.PERIOD, null, values);
			break;
		case BING_SEARCH_RESULTS:
			id = db.insertOrThrow(DBConstants.BING_SEARCH_RESULTS, null, values);
			break;
		case IDS_SEARCH_PARAMS:
			id = db.insertOrThrow(DBConstants.IDS_SEARCH_PARAMS, null, values);
			break;
		case IDS_SEARCH_RESULTS:
			id = db.insertOrThrow(DBConstants.IDS_SEARCH_RESULTS, null, values);
			break;
		case IDS_DATA:
			id = db.insertOrThrow(DBConstants.IDS_DATA, null, values);
			break;
		case IDS_AUTHOR:
			id = db.insertOrThrow(DBConstants.IDS_AUTHOR, null, values);
			break;
		case IDS_DOC_THEME:
			id = db.insertOrThrow(DBConstants.IDS_DOC_THEME, null, values);
			break;
		case IDS_THEME:
			id = db.insertOrThrow(DBConstants.IDS_THEME, null, values);
			break;
		}*/
		if(id!= -1){
			uri = Uri.withAppendedPath(uri, Long.toString(id));
			//uri = Uri.parse(Long.toString(id));
			getContext().getContentResolver().notifyChange(uri, null);
			
		
			
			
			
		}else{
			uri = Uri.withAppendedPath(uri, Long.toString(id));
			//uri = Uri.parse(Long.toString(id));
			getContext().getContentResolver().notifyChange(uri, null);
			
			
			
		}
		
		db.close();
		//getContext().getContentResolver().notifyChange(uri, null);
		
		
		
		
		
		return uri;
	
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		db = dbHelper.getWritableDatabase();
		count = db.update(uri.getLastPathSegment(), values, selection, selectionArgs);
		
		/*switch(uriMatcher.match(uri)){
		case COUNTRY:
			count = db.update(DBConstants.COUNTRY, values, selection, selectionArgs);
			break;
		case INDICATOR:
			count = db.update(DBConstants.INDICATOR, values, selection, selectionArgs);
			break;
		case SEARCH:
			count = db.update(DBConstants.SEARCH, values, selection, selectionArgs);
			break;
		case IDS_SEARCH_TABLE:
			count = db.update(DBConstants.IDS_SEARCH_TABLE, values, selection, selectionArgs);
			break;
		case BING_SEARCH_TABLE:
			count = db.update(DBConstants.BING_SEARCH_TABLE, values, selection, selectionArgs);
			break;
		case API:
			count = db.update(DBConstants.API, values, selection, selectionArgs);
			break;
		case WB_DATA:
			count = db.update(DBConstants.WB_DATA, values, selection, selectionArgs);
			break;
		case SEARCH_COUNTRY:
			count = db.update(DBConstants.IDS_SEARCH_TABLE, values, selection, selectionArgs);
			break;
		case PERIOD:
			count = db.update(DBConstants.PERIOD, values, selection, selectionArgs);
			break;
		case BING_SEARCH_RESULTS:
			count = db.update(DBConstants.BING_SEARCH_RESULTS, values, selection, selectionArgs);
			break;
		case IDS_SEARCH_PARAMS:
			count = db.update(DBConstants.IDS_SEARCH_PARAMS, values, selection, selectionArgs);
			break;
		case IDS_SEARCH_RESULTS:
			count = db.update(DBConstants.IDS_SEARCH_RESULTS, values, selection, selectionArgs);
			break;
		case IDS_DATA:
			count = db.update(DBConstants.IDS_DATA, values, selection, selectionArgs);
			break;
		case IDS_AUTHOR:
			count = db.update(DBConstants.IDS_AUTHOR, values, selection, selectionArgs);
			break;
		case IDS_DOC_THEME:
			count = db.update(DBConstants.IDS_DOC_THEME, values, selection, selectionArgs);
			break;
		case IDS_THEME:
			count = db.update(DBConstants.IDS_THEME, values, selection, selectionArgs);
			break;
		}*/
		
		
		
		
		
		db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	
	

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		Cursor cursor = null;
		qb.setTables(uri.getLastPathSegment());
		/*qb.setTables(DBConstants.COUNTRY);
		qb.setTables(DBConstants.INDICATOR);
		qb.setTables(DBConstants.SEARCH);
		qb.setTables(DBConstants.IDS_SEARCH_TABLE);
		qb.setTables(DBConstants.BING_SEARCH_TABLE);
		qb.setTables(DBConstants.API);
		qb.setTables(DBConstants.WB_DATA);
		qb.setTables(DBConstants.SEARCH_COUNTRY);
		qb.setTables(DBConstants.PERIOD);
		qb.setTables(DBConstants.BING_SEARCH_RESULTS);
		qb.setTables(DBConstants.IDS_SEARCH_PARAMS);
		qb.setTables(DBConstants.IDS_SEARCH_RESULTS);
		qb.setTables(DBConstants.IDS_DATA);
		qb.setTables(DBConstants.IDS_AUTHOR);
		qb.setTables(DBConstants.IDS_DOC_THEME);
		qb.setTables(DBConstants.IDS_THEME);*/
		//cursor = //db.query(uri.getLastPathSegment(), projection, selection, selectionArgs, null, null, sortOrder);
				//qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		/*switch(uriMatcher.match(uri)){
		case COUNTRY:
			//cursor = db.query(DBConstants.COUNTRY, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.COUNTRY);
			break;
		case INDICATOR:
			//cursor = db.query(DBConstants.INDICATOR, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.INDICATOR);
			break;
		case SEARCH:
			//cursor = db.query(DBConstants.SEARCH, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.SEARCH);
			break;
		case IDS_SEARCH_TABLE:
			//cursor = db.query(DBConstants.IDS_SEARCH_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_SEARCH_TABLE);
			break;
		case BING_SEARCH_TABLE:
			//cursor = db.query(DBConstants.BING_SEARCH_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.BING_SEARCH_TABLE);
			break;
		case API:
			//cursor = db.query(DBConstants.API, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.API);
			break;
		case WB_DATA:
			//cursor = db.query(DBConstants.WB_DATA, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.WB_DATA);
			break;
		case SEARCH_COUNTRY:
			//cursor = db.query(DBConstants.IDS_SEARCH_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_SEARCH_TABLE);
			break;
		case PERIOD:
			//cursor = db.query(DBConstants.PERIOD, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.PERIOD);
			break;
		case BING_SEARCH_RESULTS:
			//cursor = db.query(DBConstants.BING_SEARCH_RESULTS, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.BING_SEARCH_RESULTS);
			break;
		case IDS_SEARCH_PARAMS:
			//cursor = db.query(DBConstants.IDS_SEARCH_PARAMS, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_SEARCH_PARAMS);
			break;
		case IDS_SEARCH_RESULTS:
			//cursor = db.query(DBConstants.IDS_SEARCH_RESULTS, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_SEARCH_RESULTS);
			break;
		case IDS_DATA:
			//cursor = db.query(DBConstants.IDS_DATA, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_DATA);
			break;
		case IDS_AUTHOR:
			//cursor = db.query(DBConstants.IDS_AUTHOR, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_AUTHOR);
			break;
		case IDS_DOC_THEME:
			//cursor = db.query(DBConstants.IDS_DOC_THEME, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_DOC_THEME);
			break;
		case IDS_THEME:
			//cursor = db.query(DBConstants.IDS_THEME, projection, selection, selectionArgs, null, null, sortOrder);
			qb.setTables(DBConstants.IDS_THEME);
			break;
		}*/ 
		
		cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		//db.close();
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		int count = 0;
		db = dbHelper.getWritableDatabase();
		
		switch(uriMatcher.match(uri)){
		case COUNTRY:
			count = db.delete(DBConstants.COUNTRY, whereClause, whereArgs);
			break;
		case INDICATOR:
			count = db.delete(DBConstants.INDICATOR, whereClause, whereArgs);
			break;
		case SEARCH:
			count = db.delete(DBConstants.SEARCH, whereClause, whereArgs);
			break;
		case IDS_SEARCH_TABLE:
			count = db.delete(DBConstants.IDS_SEARCH_TABLE, whereClause, whereArgs);
			break;
		case BING_SEARCH_TABLE:
			count = db.delete(DBConstants.BING_SEARCH_TABLE, whereClause, whereArgs);
			break;
		case API:
			count = db.delete(DBConstants.API, whereClause, whereArgs);
			break;
		case WB_DATA:
			count = db.delete(DBConstants.WB_DATA, whereClause, whereArgs);
			break;
		case SEARCH_COUNTRY:
			count = db.delete(DBConstants.IDS_SEARCH_TABLE, whereClause, whereArgs);
			break;
		case PERIOD:
			count = db.delete(DBConstants.PERIOD, whereClause, whereArgs);
			break;
		case BING_SEARCH_RESULTS:
			count = db.delete(DBConstants.BING_SEARCH_RESULTS, whereClause, whereArgs);
			break;
		case IDS_SEARCH_PARAMS:
			count = db.delete(DBConstants.IDS_SEARCH_PARAMS, whereClause, whereArgs);
			break;
		case IDS_SEARCH_RESULTS:
			count = db.delete(DBConstants.IDS_SEARCH_RESULTS, whereClause, whereArgs);
			break;
		case IDS_DATA:
			count = db.delete(DBConstants.IDS_DATA, whereClause, whereArgs);
			break;
		case IDS_AUTHOR:
			count = db.delete(DBConstants.IDS_AUTHOR, whereClause, whereArgs);
			break;
		case IDS_DOC_THEME:
			count = db.delete(DBConstants.IDS_DOC_THEME, whereClause, whereArgs);
			break;
		case IDS_THEME:
			count = db.delete(DBConstants.IDS_THEME, whereClause, whereArgs);
			break;
		}
		
		
		
		
		
		db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
/*	private class AreaDB extends SQLiteOpenHelper{

		private static final int DATABASE_VERSION = 1;
		private SQLiteDatabase db;


		public AreaDB(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}

		private static final String CREATE_TABLE_COUNTRY = "create table " + DBConstants.COUNTRY + " ( "
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


		private static final String CREATE_TABLE_INDICATOR = "create table " + DBConstants.INDICATOR + " ( "
				+ INDICATOR_ID 		+ " integer primary key autoincrement, "
				+ WB_INDICATOR_ID 	+ " integer not null, "
				+ INDICATOR_NAME 	+ " text not null, "
				+ INDICATOR_DESC 	+ " text not null )";

		private static final String CREATE_TABLE_SEARCH = "create table " + DBConstants.SEARCH + " ( "
				+ SEARCH_ID 		+ " integer primary key autoincrement, "
				+ I_ID				+ " integer not null, "
				+ AP_ID 			+ " integer not null, "
				+ SEARCH_CREATED 	+ " integer not null, "
				+ SEARCH_MODIFIED 	+ " integer not null, "
				+ SEARCH_VIEWED		+ " integer not null, "
				+ SEARCH_URI 		+ " text not null )" ;

		private static final String CREATE_TABLE_IDS_SEARCH = "create table " + DBConstants.IDS_SEARCH_TABLE + " ( "
				+ IDS_SEARCH_ID 		+ " integer primary key autoincrement, "
				+ I_ID 					+ " integer not null, "
				+ IDS_BASE_URL			+ " text not null,"
				+ IDS_SITE				+ " text not null, "
				+ IDS_OBJECT 			+ " text not null, " 
				+ IDS_TIMESTAMP			+ " integer not null,"
				+ IDS_VIEW_DATE			+ " integer not null )" ;

		private static final String CREATE_TABLE_IDS_SEARCH_PARAMS = "create table " + DBConstants.IDS_SEARCH_PARAMS + " ( "
				+ _ID 				+ " integer primary key autoincrement, "
				+ IDS_S_ID			+ " integer not null,"
				+ IDS_PARAMETER		+ " text not null, "
				+ IDS_OPERAND		+ " text not null, "
				+ IDS_PARAM_VALUE	+ " text not null, "
				+ COMBINATION 		+ " text not null )" ;

		private static final String CREATE_TABLE_IDS_SEARCH_RESULTS = "create table " + DBConstants.IDS_SEARCH_RESULTS + " ( "
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

	/*	private static final String CREATE_TABLE_API = "create table " + DBConstants.API + " ( "
				+ API_ID 			+ " integer primary key autoincrement, "
				+ API_NAME 			+ " text not null, "
				+ API_DESC 			+ " text not null, " 
				+ BASE_URI 			+ " text not null )";


		private static final String CREATE_TABLE_BING_SEARCH = "create table " + DBConstants.BING_SEARCH_TABLE + " ( "
				+ BING_SEARCH_ID	+ " integer primary key autoincrement, "
				+ BING_QUERY		+ " text not null, "
				+ QUERY_DATE		+ " integer not null, "
				+ QUERY_VIEW_DATE	+ " integer not null)";
		//public static final String[] FROM_BING_SEARCH_TABLE		= {BING_SEARCH_ID, BING_QUERY, QUERY_DATE };

		private static final String CREATE_TABLE_BING_SEARCH_RESULTS = "create table " + DBConstants.BING_SEARCH_RESULTS + " ( "
				+ _ID	 			+ " integer primary key autoincrement, "
				+ B_S_ID			+ " integer not null,"
				+ BING_TITLE 		+ " text not null, "
				+ BING_DESC 		+ " text not null, " 
				+ BING_URL			+ " text not null, "
				+ BING_DISP_URL		+ " text not null, "
				+ BING_DATE_TIME	+ " text not null, "
				+ QUERY_VIEW_DATE	+ " integer )";
		//public static final String[] FROM_BING_SEARCH_RESULTS	= {_ID, B_S_ID, BING_TITLE, BING_DESC, BING_URL, BING_DISP_URL, BING_DATE_TIME	};

		private static final String CREATE_TABLE_WB_DATA = "create table " + DBConstants.WB_DATA + " ( "
				+ WB_DATA_ID 	+ " integer primary key autoincrement, "
				//+ I_ID 			+ " integer not null, "
				//+ C_ID 			+ " integer not null, "
				//+ S_ID 			+ " integer not null, "
				+ SC_ID 		+ " integer not null, "
				+ IND_VALUE 	+ " double not null, "
				+ IND_DECIMAL 	+ " integer not null, "
				+ IND_DATE 		+ " integer not null ) ";

		private static final String CREATE_TABLE_SEARCH_COUNTRY = "create table " + DBConstants.SEARCH_COUNTRY + " ( "
				+ _ID 	+ " integer primary key autoincrement, "
				+ S_ID 	+ " integer not null, "
				+ C_ID 	+ " integer not null, "
				+ P_ID 	+ " integer not null )";

		private static final String CREATE_TABLE_PERIOD = "create table " + DBConstants.PERIOD + " ( "
				+ PERIOD_ID 	+ " integer primary key autoincrement, "
				+ PERIOD_NAME 	+ " text not null, "
				+ P_START_DATE 	+ " integer not null, "
				+ P_END_DATE 	+ " integer not null )";

		private static final String CREATE_TABLE_IDS_DATA = "create table " + DBConstants.IDS_DATA  + " ( "
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

		private static final String CREATE_TABLE_IDS_AUTHOR = "create table " + DBConstants.IDS_AUTHOR + " ( "
				+ AUTHOR_ID		 + " integer primary key autoincrement, "
				+ D_ID			 + " integer not null, "
				+ AUTHOR_NAME	 + " text not null )";

		private static final String CREATE_TABLE_IDS_DOC_THEME = "create table " + DBConstants.IDS_DOC_THEME + " ( "
				+ _ID 		+ " integer primary key autoincrement, "
				+ T_ID 		+ " integer not null, "
				+ D_ID 		+ " integer not null )";

		private static final String CREATE_TABLE_IDS_THEME = "create table " + DBConstants.IDS_THEME + " ( "
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
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.COUNTRY				);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.INDICATOR				);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.SEARCH					);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_SEARCH_TABLE		);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_SEARCH_PARAMS		);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_SEARCH_RESULTS		);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.API					);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.BING_SEARCH_TABLE		);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.BING_SEARCH_RESULTS	);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.WB_DATA				);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.SEARCH_COUNTRY			);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PERIOD					);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_DATA				);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_AUTHOR				);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_DOC_THEME			);
				db.execSQL("DROP TABLE IF EXISTS " + DBConstants.IDS_THEME 				);
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
		
	}*/
}



