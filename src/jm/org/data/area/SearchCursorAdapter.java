package jm.org.data.area;

import static jm.org.data.area.DBConstants.*;

import java.util.Arrays;

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

public class SearchCursorAdapter extends CursorAdapter {
	private static final String TAG = SearchCursorAdapter.class.getSimpleName();
	private int mSelectedPosition = -1;
	private Context mContext;

	public SearchCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		mContext = context;
	}

	/*
	 * public void setSelectedPosition(int position) { if (mSelectedPosition !=
	 * position) { //View view = (View) l.getItemAtPosition(position);
	 * mSelectedPosition = position;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * public void setSelectedPosition(int position, ListView l) { if
	 * (mSelectedPosition != position) { //View view = (View)
	 * l.getItemAtPosition(position); Log.e(TAG,
	 * String.format("Current position: %d. New position: %d",
	 * mSelectedPosition, position)); notifyDataSetChanged(); mSelectedPosition
	 * = position;
	 * 
	 * }
	 * 
	 * }
	 */

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView list_title = (TextView) view
				.findViewById(R.id.list_item_title);
		TextView list_desc = (TextView) view.findViewById(R.id.list_item_desc);
		
		if (cursor.getColumnIndex(IDS_DOC_TITLE) != -1) {
			Log.e(TAG, String.format("Report list Cursor size: %d. Current position: %d. Cursor columns: %s. Cursor column count: %d", cursor.getCount(), cursor.getPosition(), Arrays.toString(cursor.getColumnNames()), cursor.getCount()));
			// IDS List
			String title = cursor.getString(cursor.getColumnIndex(IDS_DOC_TITLE));
			/*String desc = String.format("%s. %s (%s)",
					cursor.getString(cursor.getColumnIndex(IDS_DOC_TYPE)),
					cursor.getString(cursor.getColumnIndex(IDS_DOC_TYPE)));*/
			Log.e(TAG, "Title: " + title + ". Desc" + "desc");
			list_title.setText(title);
			list_desc.setText("desc");

		} else {
			// BING List
			list_title.setText(cursor.getString(cursor
					.getColumnIndex(BING_TITLE)));
			list_desc.setText(String.format("%s (%s)",
					cursor.getString(cursor.getColumnIndex(BING_DESC)),
					cursor.getString(cursor.getColumnIndex(BING_DATE_TIME))));

		}

	}

	/*
	 * public void notifyListItemChanged() {
	 * 
	 * }
	 */

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}

}
