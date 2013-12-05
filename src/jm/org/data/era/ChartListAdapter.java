package jm.org.data.era;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import static jm.org.data.era.AreaConstants.*;

public class ChartListAdapter extends SimpleCursorLoader {
	private final String TAG = getClass().getSimpleName();
	
	private Context mContext;
	private String indicatorID;
	private String[] country;
	private Cursor results;
	
	private int searchType, col_id;
	
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

	public ChartListAdapter(Context context, int search,
			int collection) {
		super(context);
		mContext = context;
		searchType 	= search;
		col_id		= collection;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();

		// int searchType = IDS_SEARCH;
		// indicatorID = "AG.PRD.CROP.XD";
		// country = new String[] { "Jamaica", "Barbados" };

		Log.i(TAG, "Getting Saved Charts");
		if (searchType == SAVED_CHARTS){
			results = area.areaData.getChartList();
			
		}else if(searchType == COLLECTION_CHARTS){
			results = area.areaData.getData(searchType, "" + col_id, null);
			
		}else{
			results = area.areaData.getChart(indicatorID, country);
		
		}
		Log.i(TAG, "Returning data. Num of records: " + results.getCount());

		return results; 
		
	}
}
