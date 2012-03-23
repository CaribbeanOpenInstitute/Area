package jm.org.data.area;

import static jm.org.data.area.DBConstants.*;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class IndicatorListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public static final String TAG = IndicatorListFragment.class
			.getSimpleName();
	private final String POSITION = "position";
	IndicatorActivity act;

	// SimpleCursorAdapter mAdapter;
	AreaCursorAdapter myAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myAdapter = new AreaCursorAdapter(getActivity(), null);
		setListAdapter(myAdapter);

		// getLoaderManager().initLoader(0, null, this);
	}

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { return
	 * inflater.inflate(R.layout.indicator_list, container, false); }
	 */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/* Cursor Loader */
		setEmptyText("No indicators found");

		// myAdapter = new AreaCursorAdapter();

		// setListAdapter(mAdapter);
		setListShown(false);
		getLoaderManager().initLoader(0, null, this);

		// //////////////////////
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position); // Get
																		// Cursor
																		// at
																		// row
																		// position
		String item = cursor.getString(cursor.getColumnIndex(INDICATOR_NAME));
		String item_id	= cursor.getString(cursor.getColumnIndex(WB_INDICATOR_ID));
		Log.d(TAG, "Indicator selected is: " + item + "-> ID: " + item_id);


		try { // Check if the parent activity is the IndicatorActivity
			act = (IndicatorActivity) getActivity();
		} catch (ClassCastException actException) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					IndicatorActivity.class);
			intent.putExtra(WB_INDICATOR_ID, item_id);
			intent.putExtra(POSITION, position);
			startActivity(intent);
		}

		if (act != null) {
			act.setIndicator(item);
			myAdapter.setSelectedPosition(position, getListView());
		}

		// Already in indicator activity

		/*
		 * ChartsFragment chFragment = (ChartsFragment) getFragmentManager()
		 * .findFragmentById(R.id.chartFragment); if (chFragment != null &&
		 * chFragment.isInLayout()) { Log.d(TAG,
		 * "The list item passed to the fragment is " + item);
		 * chFragment.setText(item); } else { // Activity for phones /* Intent
		 * intent = new Intent(getActivity().getApplicationContext(),
		 * DetailActivity.class); intent.putExtra("value", item);
		 * startActivity(intent);
		 */

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new IndicatorListAdapter(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		//AreaApplication area = (AreaApplication) getActivity().getApplication();
		//;
		// mAdapter.swapCursor(cursor);
		myAdapter.swapCursor(cursor);
		if (isResumed()) {
			setListShown(true);
		} else {
			Log.d(TAG, "Activity is not being resumed");
			setListShownNoAnimation(true);
			try {
				myAdapter.setSelectedPosition(act.getPosition());
			} catch (NullPointerException e) {
				//Empty list or startup activy incomplete
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		myAdapter.swapCursor(null);
	}

	public void setListSelection(int position) {
		myAdapter.setSelectedPosition(position);
	}
	
	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}

	private void setListviewSelection(final ListView list, final int pos) {
		list.post(new Runnable() {
			@Override
			public void run() {
				// list.setSelection(pos);
				View v = list.getChildAt(pos);
				if (v != null) {
					v.requestFocus();
				}
			}
		});
	}
}
