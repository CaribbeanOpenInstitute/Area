package jm.org.data.era;

import static jm.org.data.era.DBConstants.*;
import jm.org.data.era.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CollectionsCursorAdapter extends CursorAdapter {
	private static final String TAG = CollectionsCursorAdapter.class.getSimpleName();
	private int mSelectedPosition;
	
	//private Context mContext;

	public CollectionsCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		//mContext = context;
	}

	public void setSelectedPosition(int position) {
		if (mSelectedPosition != position) {
			// View view = (View) l.getItemAtPosition(position);
			mSelectedPosition = position;

		}
		
	}
	
	public void setSelectedPosition(int position, ListView l) {
		if (mSelectedPosition != position) {
			// View view = (View) l.getItemAtPosition(position);
			Log.e(TAG, String.format("Current position: %d. New position: %d",
					mSelectedPosition, position));
			notifyDataSetChanged();
			mSelectedPosition = position;

		}

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView list_item = (TextView) view.findViewById(android.R.id.text1);
		//FROM_COLLECTIONS = { COLLECTION_ID, COLLECTION_NAME, COLLECTION_DESC};
		list_item.setText( /*
							 * cursor.getString(cursor.getColumnIndex(
							 * WB_INDICATOR_ID))+ "-" +
							 */cursor.getString(cursor
				.getColumnIndex(COLLECTION_NAME)));
		int position = cursor.getPosition();
		if (mSelectedPosition == position) {
			
			view.setBackgroundColor(Color.parseColor("#8AC7E3"));
		} else {
			//view.setBackgroundColor(Color.TRANSPARENT);
			view.setBackgroundColor(Color.parseColor("#CCFF99"));
		}

	}

	public void notifyListItemChanged() {

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}

}
