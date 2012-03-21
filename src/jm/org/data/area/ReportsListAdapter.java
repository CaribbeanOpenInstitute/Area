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
		indicatorID = "TX.VAL.AGRI.ZS.UN";
		country = new String[] {"Jamaica", "Kenya","Barbados"};
		if (area.areaData.genericSearch(IDS_SEARCH, indicatorID, country) >= SEARCH_SUCCESS ) {
			return area.areaData.getData(IDS_SEARCH, indicatorID, country);
		}
		return null;
		//Log.d(TAG, String.format("Cursor size returned: %d", reportsCursor.getCount()));
	}
}
