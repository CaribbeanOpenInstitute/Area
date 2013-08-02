package jm.org.data.area;

import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.util.Log;

public class GlobalListAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	private Context mContext;
	private int searchType;
	private String searchField;

	public GlobalListAdapter(Context context, int sType, String sField) {
		super(context);
		mContext = context;
		searchType = sType;
		searchField = sField;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();

		try {
			if (area.areaData.globalSearch(searchType, searchField) >= SEARCH_SUCCESS) {
				Cursor results = area.areaData.getGlobalData(searchType,
						searchField);
				Log.d(TAG,
						"Returning data. Num of records: " + results.getCount());
				return results;/**/
			}

		} catch (IllegalStateException ilEc) {
			Log.e(TAG,
					"Database list loading returned Database Lock exception on "
							+ searchType + " query");
			// db.close();
			// return rawQuery(tableName, tableColumns,queryParams);
		}
		Log.d(TAG, "Returning zero");
		return null;

	}

}
