package jm.org.data.area;

import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.AreaConstants.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SelectionListFragment extends ListFragment implements
			LoaderManager.LoaderCallbacks<Cursor> {
	
	public static final String TAG = SelectionListFragment.class
		.getSimpleName();
	//private final String POSITION = "position";
	//private int listPosition;
	private IndicatorActivity act;
	private HomeActivity hAct;
	private CountryActivity cAct;
	private CollectionsActivity colAct;
	private SearchDataActivity sAct;
	private int selection;
	private SelectionListCursorAdapter myAdapter;
	private Intent actIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myAdapter = new SelectionListCursorAdapter(getActivity(), null);
		setListAdapter(myAdapter);

		// getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/* Cursor Loader */
		setEmptyText("No Selections found");
		
		try { // Check if the parent activity is the IndicatorActivity
			hAct = (HomeActivity) getActivity();
			selection = hAct.getSelectionID();
			
		} catch (ClassCastException actException) {
			Log.e(TAG, "Not Home Activity ");
			//act = (IndicatorActivity) getActivity();
			selection = 1;

		}
		setListShown(false);
		getLoaderManager().initLoader(0, null, this);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get Cursor at list item row
		Cursor cursor = (Cursor) getListAdapter().getItem(position);
		String item = cursor.getString(cursor.getColumnIndex(SELECTION_NAME));
		int item_id = cursor.getInt(cursor
				.getColumnIndex(SELECTION_ID));
		Log.d(TAG, "Selection selected is: " + item + "-> ID: " + item_id);
		/*
		public static final int S_INDICATORS = 1;
		public static final int S_COUNTRIES  = 2;
		public static final int S_COLECTIONS = 3;
		public static final int S_SAVED_DATA = 4;*/
		
		switch (item_id){
		case S_INDICATORS:
			actIntent = new Intent(getActivity().getApplicationContext(),
					HomeActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			startActivity(actIntent);
			break;
		case S_COUNTRIES:
			Toast.makeText(getActivity(), "Selected Countries",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					HomeActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			startActivity(actIntent);
			break;
		case S_COLLECTIONS:
			Toast.makeText(getActivity(), "Selected Collections",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					HomeActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			startActivity(actIntent);
			break;
		case S_SAVED_DATA:
			Toast.makeText(getActivity(), "Selected Saved Data",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					HomeActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			startActivity(actIntent);
			break;
		
		}

	/*	if (act != null) {
			act.setSelection(item);
			act.setSelection(position);
			//listPosition = position;
		}*/

	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new SelectionListAdapter(getActivity(), selection);
		
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		myAdapter.swapCursor(cursor);
		if (isResumed()) {
			setListShown(true);
		} else {
			Log.d(TAG, "Activity is not being resumed");
			setListShownNoAnimation(true);
		}
	}
	


	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void onStop() {
	    try {
	      super.onStop();

	      if (this.myAdapter !=null){
	        this.myAdapter.getCursor().close();
	        this.myAdapter = null;
	      }
	      
	      //this.getLoaderManager().destroyLoader(0);
	      
	      /*if (this.mActivityListCursorObj != null) {
	        this.mActivityListCursorObj.close();
	      }*/

	      super.onStop();
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter" + error.getStackTrace());
	    	
	    }// end try/catch (Exception error)
	  }// end onStop

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
		
	}


}
