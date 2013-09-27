package jm.org.data.area;

import static jm.org.data.area.AreaConstants.S_COLLECTIONS;
import static jm.org.data.area.AreaConstants.S_COUNTRIES;
import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.AreaConstants.S_PARENT;
import static jm.org.data.area.AreaConstants.S_SAVED_DATA;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import android.app.Activity;
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

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

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
	private SavedDataActivity sAct;
	private int selection, parentNum;
	private Activity parent;
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
		try{
        	parent = getActivity();
        	if (parent instanceof HomeActivity){
        		hAct = (HomeActivity) getActivity();
        		selection = hAct.getSelectionID();
        		parentNum = hAct.getParentNum();
				
        	}else if (parent instanceof IndicatorActivity){
        		act = (IndicatorActivity) getActivity();
        		selection = act.getSelectionID();
        		parentNum = act.getParentNum();
        		
        	}else if (parent instanceof CollectionsActivity){
        		colAct = (CollectionsActivity) getActivity();
        		selection = colAct.getSelectionID();
        		parentNum = colAct.getParentNum();
        		
        	}else if (parent instanceof CountryActivity){
        		cAct = (CountryActivity) getActivity();
        		selection = cAct.getSelectionID();
        		parentNum = cAct.getParentNum();
        		
        	}else if(parent instanceof SavedDataActivity){
        		sAct = (SavedDataActivity) getActivity();
        		selection = sAct.getSelectionID();
        		parentNum = sAct.getParentNum();
        		
        	}else{
        		Log.d(TAG,"We Have no clue what the starting activity is. Hmm, not sure what is happening");
        	}
	        
        }catch (ClassCastException actException){
        	 Log.d(TAG,"We Have no clue what the starting activity is");
        	hAct = (HomeActivity) getActivity();
        	selection = hAct.getSelectionID();
        }
/*		try { // Check if the parent activity is the IndicatorActivity
			hAct = (HomeActivity) getActivity();
			selection = hAct.getSelectionID();
			
		} catch (ClassCastException actException) {
			Log.e(TAG, "Not Home Activity ");
			//act = (IndicatorActivity) getActivity();
			selection = 1;

		}*/
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
		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

		// MapBuilder.createEvent().build() returns a Map of event fields and values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder
		    .createEvent("ui_action",     // Event category (required)
		                 "Main_List_Selction",  // Event action (required)
		                 "Selection is: " + item,   // Event label
		                 null)            // Event value
		    .build()
		);
		
		switch (item_id){
		case S_INDICATORS:
			actIntent = new Intent(getActivity().getApplicationContext(),
					HomeActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			actIntent.putExtra(S_PARENT, parentNum);
			startActivity(actIntent);
			getActivity().finish();
			break;
		case S_COUNTRIES:
			Toast.makeText(getActivity(), "Selected Countries",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					CountryActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			actIntent.putExtra(S_PARENT, parentNum);
			startActivity(actIntent);
			getActivity().finish();
			break;
		case S_COLLECTIONS:
			Toast.makeText(getActivity(), "Selected Collections",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					CollectionsActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			actIntent.putExtra(S_PARENT, parentNum);
			startActivity(actIntent);
			getActivity().finish();
			break;
		case S_SAVED_DATA:
			Toast.makeText(getActivity(), "Selected Saved Data",
					Toast.LENGTH_SHORT).show();
			actIntent = new Intent(getActivity().getApplicationContext(),
					SavedDataActivity.class);
			actIntent.putExtra(SELECTION_ID, item_id);
			actIntent.putExtra(SELECTION_NAME, item);
			actIntent.putExtra(S_PARENT, parentNum);
			startActivity(actIntent);
			getActivity().finish();
			break;
		
		}

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
	public void onResume(){
		reload();
		super.onResume();
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

	      super.onStop();
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter" + error.getStackTrace());
	    	
	    }// end try/catch (Exception error)
	  }// end onStop

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
		
	}


}
