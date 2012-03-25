package jm.org.data.area;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class HomeListAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	private Context mContext;
	private int searchType;

	public HomeListAdapter(Context context, int sType) {
		super(context);
		mContext = context;
		searchType = sType;
	}
	
	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();
		
		try {
				Cursor results = area.areaData.getRecentData(searchType);
				Log.d(TAG, "Returning data. Num of records: " + results.getCount());

				return results;/**/
		} catch (IllegalStateException ilEc){
			Log.e(TAG, "Database list loading returned Database Lock exception on " + searchType + " query");
			//db.close();
			//return rawQuery(tableName, tableColumns,queryParams);
		}
		Log.d(TAG, "Returning zero");
		return null;
		
	}

}
