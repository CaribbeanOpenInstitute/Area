package jm.org.data.area;

import static jm.org.data.area.DBConstants.INDICATOR_NAME;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class IndicatorListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public static final String TAG = IndicatorListFragment.class
			.getSimpleName();
	private static int save = -1;


	SimpleCursorAdapter mAdapter;
	AreaCursorAdapter myAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getLoaderManager().initLoader(0, null, this);
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.indicator_list, container, false);
	}*/

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] columns = new String[] { INDICATOR_NAME };
		int[] to = new int[] { android.R.id.text1 };
		
		/* Cursor Loader */
		setEmptyText("No indicators found");
		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item, null, columns, to, 0);
		
		//myAdapter = new AreaCursorAdapter();
		
		setListShown(false);

		setListAdapter(mAdapter);
		setListShown(false);
		getLoaderManager().initLoader(0, null, this);

		// //////////////////////
		
		

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor   = (Cursor) getListAdapter().getItem(position);	//Get Cursor at row position
		String item = cursor.getString(cursor.getColumnIndex(INDICATOR_NAME));
		Log.d(TAG, "Indicator selected is: " + item);

		try { // Check if the parent activity is the IndicatorActivity
			IndicatorActivity act = (IndicatorActivity) getActivity();
		} catch (ClassCastException actException) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					IndicatorActivity.class);
			intent.putExtra("indicator", item);
			startActivity(intent);
		}
		
		l.getChildAt(position).setBackgroundColor(Color.parseColor("#8AC7E3"));

	    if (save != -1 && save != position){
	        l.getChildAt(save).setBackgroundColor(Color.TRANSPARENT);
	    }

	    save = position;
		

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
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		mAdapter.swapCursor(arg1);
		if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		//mAdapter.setData(null);
	}
	
	private void setListviewSelection(final ListView list, final int pos) {
		list.post(new Runnable() 
		   {
		    @Override
		    public void run() 
		      {
		        //list.setSelection(pos);
		        View v = list.getChildAt(pos);
		        if (v != null) 
		        {
		            v.requestFocus();
		        }
		    }
		});
		}

}
