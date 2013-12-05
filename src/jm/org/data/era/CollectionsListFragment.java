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

public class CollectionsListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public static final String TAG = CollectionsListFragment.class
			.getSimpleName();
	
	//private int listPosition;
	CollectionsActivity act;
	
	CollectionsCursorAdapter myAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myAdapter = new CollectionsCursorAdapter(getActivity(), null);
		setListAdapter(myAdapter);

		// getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/* Cursor Loader */
		setEmptyText("No Collections found");

		// myAdapter = new AreaCursorAdapter();

		// setListAdapter(mAdapter);
		setListShown(false);
		getLoaderManager().initLoader(0, null, this);

		act = (CollectionsActivity) getActivity();
		myAdapter.setSelectedPosition(act.getColPosition(), getListView());
		getListView().setSelection(act.getColPosition());
		Log.d(TAG, "Position: " + act.getColPosition());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		setCollection(position);

	}

	private void setCollection(int position) {
		// Get Cursor at list item row
				Cursor cursor = (Cursor) getListAdapter().getItem(position);
				String item = cursor.getString(cursor.getColumnIndex(COLLECTION_NAME));
				int item_id = cursor.getInt(cursor
						.getColumnIndex(COLLECTION_ID));
				String item_desc = cursor.getString(cursor
						.getColumnIndex(COLLECTION_DESC));
				
				
				Log.d(TAG, "Collection selected is: " + item + "-> ID: " + item_id);

				act = (CollectionsActivity) getActivity();
				Intent intent = new Intent(getActivity().getApplicationContext(),
						CollectionsActivity.class);
				intent.putExtra(COLLECTION_DESC, item_desc);
				intent.putExtra("col_name", item);
				intent.putExtra("col_id", item_id);
				intent.putExtra(POSITION, position);
				startActivity(intent);
				act.finish();
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CollectionsListAdapter(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		myAdapter.swapCursor(cursor);
		if ((cursor.getCount() > 0) && (act.getColPosition() == -1)){
			setCollection(0);
		}
		if (isResumed()) {
			setListShown(true);
		} else {
			Log.d(TAG, "Activity is not being resumed");
			setListShownNoAnimation(true);
			try {
				myAdapter.setSelectedPosition(act.getColPosition());
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
