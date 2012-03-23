package jm.org.data.area;

import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.AreaConstants.*;

import java.util.Arrays;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ReportsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	private String indicator;
	private String[] countryList;
	SearchCursorAdapter mAdapter;
	SimpleCursorAdapter tAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mAdapter = new SearchCursorAdapter(getActivity(), null);
		IndicatorActivity parentActivity = (IndicatorActivity) getActivity();
		indicator = parentActivity.getIndicator();
		//countryList = (String[]) parentActivity.getCountryList();
		Log.d(TAG, String.format("Indcator: %s. Country List: ", indicator));
		//setListAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String[] from = {IDS_DOC_TITLE, IDS_DOC_TYPE};
		int[] to = {R.id.list_item_title, R.id.list_item_desc};
		tAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_dual, null, from, to, 0);
		
		setListAdapter(tAdapter);
		getLoaderManager().initLoader(0, null, this);
		
		//setEmptyText("No indicators found");
		//setListShown(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.reports, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.report_list, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_reload:
			Toast.makeText(getActivity(), "Fake refreshing report list...",
					Toast.LENGTH_SHORT).show();
			/*
			 * parentActivity.getActionBarHelper().setRefreshActionItemState(true
			 * ); getWindow().getDecorView().postDelayed( new Runnable() {
			 * 
			 * @Override public void run() {
			 * getActionBarHelper().setRefreshActionItemState(false); } },
			 * 1000);
			 */
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);
		
		String item = cursor.getString(cursor.getColumnIndex(IDS_DOC_ID));
		String itemTitle = cursor.getString(cursor.getColumnIndex(IDS_DOC_TITLE));
		Log.d(TAG, "Report selected is: " + item + " Title is: " + itemTitle);
		
		//Launch Report View
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new SearchListAdapter(getActivity(), IDS_SEARCH, indicator, countryList);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		Log.e(TAG, String.format("Report list Cursor size: %d. Cursor columns: %s. Cursor column count: %d", cursor.getCount(), Arrays.toString(cursor.getColumnNames()), cursor.getCount()));
		tAdapter.swapCursor(cursor);
		if (isResumed()) {
			//setListShown(true);
		} else {
			//setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		tAdapter.swapCursor(null);
	}

}
