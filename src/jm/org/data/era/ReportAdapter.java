package jm.org.data.era;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ReportAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	private Context mContext;
	private int mDocID;

	public ReportAdapter(Context context, int dID) {
		super(context);
		mContext = context;
		mDocID = dID;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();

		try {
			Log.e(TAG, "Getting report " + mDocID);
			return area.areaData.getReport(mDocID);
		} catch (IllegalStateException ilEc) {
			Log.e(TAG, "Database loading returned Database Lock exception on "
					+ mDocID + " report search");
			// db.close();
			// return rawQuery(tableName, tableColumns,queryParams);
		}
		Log.d(TAG, "Returning zero");
		return null;
	}

}
