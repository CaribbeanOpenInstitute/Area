package jm.org.data.area;

import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.CHART_COUNTRIES;
import static jm.org.data.area.DBConstants.CHART_DESC;
import static jm.org.data.area.DBConstants.CHART_NAME;
import static jm.org.data.area.DBConstants.I_ID;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.util.Arrays;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ChartsListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	private String indicator;
	
	private SavedDataActivity parentActivity;
	//private SearchCursorAdapter mAdapter;
	private SimpleCursorAdapter tAdapter;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		parentActivity = (SavedDataActivity) getActivity();
		dialog = new ProgressDialog(parentActivity);
		
		Log.e(TAG, "Creating Charts List Fragment");
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading Charts Data. Please wait...", true);
		Log.e(TAG, "Charts List Fragment dialog created");
		String[] from = { CHART_NAME, CHART_DESC };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
		
		tAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_dual, null, from, to, 0);

		setListAdapter(tAdapter);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.chart_list, container, false);
	}

	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.report_list, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}*/

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_reload:
			Toast.makeText(getActivity(), "Refreshing report list...",
					Toast.LENGTH_LONG).show();
			dialog = ProgressDialog.show(getActivity(), "",
					"Loading Reports Data. Please wait...", true);
			reload();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);
		//FROM_CHARTS = { CHART_ID, CHART_NAME, CHART_DESC, I_ID, CHART_COUNTRIES};
		String item = cursor.getString(cursor.getColumnIndex(CHART_NAME));
		indicator = cursor.getString(cursor.getColumnIndex(I_ID));
		String itemDesc = cursor.getString(cursor
				.getColumnIndex(CHART_DESC));
		String countries = cursor.getString(cursor
				.getColumnIndex(CHART_COUNTRIES));
		Log.d(TAG, "Chart selected is: " + item + " : " + itemDesc);

		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

		// MapBuilder.createEvent().build() returns a Map of event fields and
		// values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																// (required)
				"Charts_List_Selction", // Event action (required)
				"chart selected is: " + item + " : " + itemDesc, // Event
																			// label
				null) // Event value
				.build());
		Log.i(TAG, String.format("Data Being Sent Over \nIndicator:%s \nCountries:%s",
				indicator, countries));
		// Launch Report View
		Intent intent = new Intent(getActivity().getApplicationContext(),
				IndicatorActivity.class);
		intent.putExtra(WB_INDICATOR_ID, indicator);
		intent.putExtra(SELECTION_ID, S_INDICATORS);
		intent.putExtra(SELECTION_NAME, "Indicators");
		intent.putExtra("countries", countries);
		// intent.putExtra(BING_URL, itemURL);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		startActivity(intent);
		this.getActivity().finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new ChartListAdapter(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null) {
			Log.e(TAG,
					String.format(
							"Report list Cursor size: %d. Cursor columns: %s. Cursor column count: %d",
							cursor.getCount(),
							Arrays.toString(cursor.getColumnNames()),
							cursor.getCount()));
			tAdapter.swapCursor(cursor);
			// loadingAnimator.setDisplayedChild(0);
			if (isResumed()) {
				// setListShown(true);
			} else {
				// setListShownNoAnimation(true);
			}
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		tAdapter.swapCursor(null);
	}

	public void reload() {
		
		getLoaderManager().restartLoader(0, null, this);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	

}