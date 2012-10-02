package jm.org.data.area;

import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_AUTH_STR;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_TITLE;
import static jm.org.data.area.AreaConstants.*;

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
import android.view.View;
import android.widget.ListView;

public class HomeReportListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	private ProgressDialog dialog;
	SimpleCursorAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		dialog = new ProgressDialog(getActivity());
		dialog = ProgressDialog.show(getActivity(), "", 
                "Loading. Please wait...", true);
		String[] from = { IDS_DOC_TITLE, IDS_DOC_AUTH_STR };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
		// tAdapter = new SimpleCursorAdapter(getActivity(),
		// R.layout.list_reports_item, null, from, to, 0);
		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_dual, null, from, to, 0);

		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);

		setEmptyText("No indicators found");
		setListShown(false);
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);

		String item = cursor.getString(cursor.getColumnIndex(IDS_DOC_ID));
		int item_id = cursor.getInt(cursor.getColumnIndex(DOCUMENT_ID));
		String itemTitle = cursor.getString(cursor
				.getColumnIndex(IDS_DOC_TITLE));
		Log.d(TAG, "Report selected is: " + item + " Title is: " + itemTitle);

		// Launch Report View
		Intent intent = new Intent(getActivity().getApplicationContext(),
				ReportDetailViewActivity.class);
		intent.putExtra(DOCUMENT_ID, item_id);
		// intent.putExtra(BING_URL, itemURL);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new HomeListAdapter(getActivity(), IDS_SEARCH);
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
			mAdapter.swapCursor(cursor);
			if (isResumed()) {
				setListShown(true);
			} else {
				setListShownNoAnimation(true);
			}
		}
		setEmptyText("No reports downloaded yet");
		setListShown(true);
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}

	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}

}
