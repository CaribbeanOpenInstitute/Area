package jm.org.data.area;

import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mAdapter = new SearchCursorAdapter(getActivity(), null);
		IndicatorActivity parentActivity = (IndicatorActivity) getActivity();
		indicator = parentActivity.getIndicatorName();
		//countryList = (String[]) parentActivity.getCountryList();
		Log.d(TAG, String.format("Indcator: %s. Country List: ", indicator));
		setListAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] values = new String[] { "The Impact of Rural Migration on Caribbean banana plantations", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.list_item_dual, R.id.list_item_title, values);
		
		getLoaderManager().initLoader(0, null, this);
		//setListAdapter(adapter);
		
		//setEmptyText("No indicators found");
		//setListShown(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.indicator_list, container, false);
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
		Log.d(TAG, "Report selected is: " + item);
		
		//Launch Report View
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		//Return
		return new ReportsListAdapter(getActivity(), indicator, countryList);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		AreaApplication area = (AreaApplication) getActivity().getApplication();
		cursor = area.areaData.getData(IDS_SEARCH, "SP.RUR.TOTL.ZG", new String[]{"Jamaica"}); 
		Log.e(TAG, String.format("Cursor size: %d", cursor.getCount()));
		mAdapter.swapCursor(cursor);
		if (isResumed()) {
			//setListShown(true);
		} else {
			//setListShownNoAnimation(true);
		}
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
		
	}

}
