package jm.org.data.area;

import android.content.Context;
import android.database.Cursor;

public class CollectionsListAdapter extends SimpleCursorLoader {

	//private static final String TAG = IndicatorListAdapter.class
	//		.getSimpleName();
	private Context mContext;
	
	public CollectionsListAdapter(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();
		Cursor selectionCursor = area.areaData.getCollections();
		// Log.d(TAG, String.format("Cursor size returned: %d",
		// selectionCursor.getCount()));
		return selectionCursor;	
		
	}

}
