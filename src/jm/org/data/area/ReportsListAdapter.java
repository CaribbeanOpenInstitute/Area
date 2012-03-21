package jm.org.data.area;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import static jm.org.data.area.AreaConstants.*;

public class ReportsListAdapter extends SimpleCursorLoader{
	private static final String TAG = ReportsListAdapter.class.getSimpleName();
	private Context mContext;
	private String indicatorID;
	private String[] country;
	
	public ReportsListAdapter(Context context) {
		super(context);
		mContext = context;
	}
	
	public ReportsListAdapter(Context context, String indID, String[] ctry) {
		super(context);
		Log.e(TAG, "Creating ReportsListAdapter.");
		mContext = context;
		indicatorID = indID;
		country = ctry;
	}
	
	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();
		/*if (areaService != null) {
			indicatorID = "TX.VAL.AGRI.ZS.UN";
			country = new String[] {"Jamaica", "Kenya","Barbados"};*/
			
			//if (areaService.genericSearch(IDS_SEARCH, indicatorID, country) >= SEARCH_SUCCESS) {
				//return area.areaData.getData(IDS_SEARCH, "SP.RUR.TOTL.ZG", new String[]{"Jamaica"});
			return null;
			//}
			
		//} 
		
		/*if (area.areaData.genericSearch(IDS_SEARCH, indicatorID, country) >= SEARCH_SUCCESS) {
			Cursor reportsCursor = area.areaData.getIndicatorList();
			Log.d(TAG, String.format("Cursor size returned: %d", reportsCursor.getCount()));
			return reportsCursor;
			
		} else {	//Error in pulling information
			return null;
		}*/
		//return area.areaData.getData(IDS_SEARCH, indicatorID, country);
	}

}
