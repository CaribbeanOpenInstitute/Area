package jm.org.data.era;

import static jm.org.data.era.AreaConstants.*;
import static jm.org.data.era.DBConstants.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CountryListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public static final String TAG = CountryListFragment.class
			.getSimpleName();
	
	
	CountryActivity act;
	
	CountryCursorAdapter myAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myAdapter = new CountryCursorAdapter(getActivity(), null);
		setListAdapter(myAdapter);

		// getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/* Cursor Loader */
		setEmptyText("No Countries found");


		setListShown(false);
		getLoaderManager().initLoader(0, null, this);

		act = (CountryActivity) getActivity();
		myAdapter.setSelectedPosition(act.getCountryPos(), getListView());
		getListView().setSelection(act.getCountryPos());
		Log.d(TAG, "Position: " + act.getCountryPos());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		setCountry(position);

	}

	private void setCountry(int position) {
		// Get Cursor at list item row
				Cursor cursor = (Cursor) getListAdapter().getItem(position);
				String item = cursor.getString(cursor.getColumnIndex(COUNTRY_NAME));
				int item_id = cursor.getInt(cursor
						.getColumnIndex(COUNTRY_ID));
				// TODO pass cursor to activity to allow for the updating of the country profile
				
				Log.d(TAG, "Country selected is: " + item + "-> ID: " + item_id + " at Position: " + position);

				act = (CountryActivity) getActivity();
				Intent intent = new Intent(getActivity().getApplicationContext(),
						CountryActivity.class);
				intent.putExtra("country", item);
				intent.putExtra("country_id", item_id);
				intent.putExtra(POSITION, position);
				startActivity(intent);
				act.finish();
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CountryListAdapter(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		myAdapter.swapCursor(cursor);
		if ((cursor.getCount() > 0) && (act.getCountryPos() == -1)){
			setCountry(0);
		}
		if (isResumed()) {
			setListShown(true);
		} else {
			Log.d(TAG, "Activity is not being resumed");
			setListShownNoAnimation(true);
			try {
				myAdapter.setSelectedPosition(act.getCountryPos());
			} catch (NullPointerException e) {
				// Empty list or startup activy incomplete
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		//myAdapter.swapCursor(null);
	}

	public void setListSelection(int position) {
		myAdapter.setSelectedPosition(position);
	}

	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}

	
	
	@Override
	public void onStop() {
	    try {
	      super.onStop();

	      if (this.myAdapter !=null){
	        //this.myAdapter.getCursor().close();
	        //this.myAdapter = null;
	      }
	      
	      //this.getLoaderManager().destroyLoader(0);
	      
	      /*if (this.mActivityListCursorObj != null) {
	        this.mActivityListCursorObj.close();
	      }*/

	      
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter");
	    	
	    }// end try/catch (Exception error)
	  }// end onStop

}
