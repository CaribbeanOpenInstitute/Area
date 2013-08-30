package jm.org.data.area;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

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
	
	private SQLiteDatabase db;
	AreaDB dbHelper;
	private static final String TAG = AreaProvider.class.getSimpleName();
	
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
		Log.d(TAG, String.format("Insert Successfull into table %s", uri.toString()));
		
		if(id!= -1){
			uri = Uri.withAppendedPath(uri, Long.toString(id));
			//uri = Uri.parse(Long.toString(id));
			getContext().getContentResolver().notifyChange(uri, null);	
			
		}else{
			uri = Uri.withAppendedPath(uri, Long.toString(id));
			//uri = Uri.parse(Long.toString(id));
			getContext().getContentResolver().notifyChange(uri, null);
			
		}
		
		if(db.isOpen()){
			//db.close();
		}
		//db.close();
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
		
		
		if(db == null){
			db = dbHelper.getReadableDatabase();
		}else if (!db.isOpen()){
			db = dbHelper.getReadableDatabase();
		}
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		Cursor cursor = null;
		qb.setTables(uri.getLastPathSegment());
		
		if (db.isOpen()){
			cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		}else{
			Log.d(TAG, "DB is closed for some reason");
		}
		

		//db.close();
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		int count = 0;
		db = dbHelper.getWritableDatabase();
		
		/*switch(uriMatcher.match(uri)){
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
		}*/
		
		count = db.delete(uri.getLastPathSegment(), whereClause, whereArgs);
		
		
		
		//db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}



