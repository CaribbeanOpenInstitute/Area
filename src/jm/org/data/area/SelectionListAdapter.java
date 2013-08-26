package jm.org.data.area;

import android.content.Context;
import android.database.Cursor;

public class SelectionListAdapter extends SimpleCursorLoader {

	//private static final String TAG = IndicatorListAdapter.class
	//		.getSimpleName();
	private Context mContext;
	private int exclude;
	public SelectionListAdapter(Context context, int selection) {
		super(context);
		exclude = selection;
		mContext = context;
	}

	@Override
	public Cursor loadInBackground() {
		area = (AreaApplication) mContext.getApplicationContext();
		Cursor selectionCursor = area.areaData.getSelectionList(exclude);
		// Log.d(TAG, String.format("Cursor size returned: %d",
		// selectionCursor.getCount()));
		return selectionCursor;	
		
	}

}
