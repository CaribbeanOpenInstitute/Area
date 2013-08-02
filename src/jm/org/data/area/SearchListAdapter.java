package jm.org.data.area;

import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class SearchListAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	private int searchType;
	private Context mContext;
	private String indicatorID;
	private String[] country;

	public SearchListAdapter(Context context) {
		super(context);
		mContext = context;
	}

	public SearchListAdapter(Context context, int sType, String indID,
			String[] ctry) {
		super(context);
		Log.e(TAG, "Creating SearchListAdapter.");
		searchType = sType;
		mContext = context;
		indicatorID = indID;
		country = ctry;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();

		// int searchType = IDS_SEARCH;
		// indicatorID = "AG.PRD.CROP.XD";
		// country = new String[] { "Jamaica", "Barbados" };

		try {
			Log.e(TAG, "Calling Generic Search 2");
			if (area.areaData.genericSearch(searchType, indicatorID, country) >= SEARCH_SUCCESS) {
				Cursor results = area.areaData.getData(searchType, indicatorID,
						country);
				Log.d(TAG,
						"Returning data. Num of records: " + results.getCount());

				return results;
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
