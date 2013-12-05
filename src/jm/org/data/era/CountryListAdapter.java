package jm.org.data.era;

import android.content.Context;
import android.database.Cursor;

public class CountryListAdapter extends SimpleCursorLoader {

	//private static final String TAG = IndicatorListAdapter.class
	//		.getSimpleName();
	private Context mContext;
	
	public CountryListAdapter(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();
		Cursor selectionCursor = area.areaData.getCountryList();
		// Log.d(TAG, String.format("Cursor size returned: %d",
		// selectionCursor.getCount()));
		return selectionCursor;	
		
	}

}
