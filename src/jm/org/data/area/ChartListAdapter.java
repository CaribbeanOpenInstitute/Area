package jm.org.data.area;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ChartListAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	
	private Context mContext;
	private String indicatorID;
	private String[] country;
	private Cursor results;
	public ChartListAdapter(Context context) {
		super(context);
		Log.e(TAG, "Creating ChartListAdapter.");
		mContext = context;
		
	}
	
	public ChartListAdapter(Context context,String indID, String[] ctry) {
		super(context);
		Log.e(TAG, "Creating ChartListAdapter.");
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

		Log.i(TAG, "Getting Saved Charts");
		if (indicatorID == null){
			results = area.areaData.getChartList();
		}else{
			results = area.areaData.getCharts(indicatorID, country);
		
		}
		Log.i(TAG, "Returning data. Num of records: " + results.getCount());

		return results; 
		
	}
}
