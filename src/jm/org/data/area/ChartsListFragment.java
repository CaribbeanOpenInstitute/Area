package jm.org.data.area;

import static jm.org.data.area.AreaConstants.CHILD_POSITION;
import static jm.org.data.area.AreaConstants.COLLECTION_CHARTS;
import static jm.org.data.area.AreaConstants.COUNTRY_CHARTS;
import static jm.org.data.area.AreaConstants.GROUP_POSITION;
import static jm.org.data.area.AreaConstants.SAVED_CHARTS;
import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.CHART_COUNTRIES;
import static jm.org.data.area.DBConstants.CHART_DESC;
import static jm.org.data.area.DBConstants.CHART_NAME;
import static jm.org.data.area.DBConstants.I_GROUP;
import static jm.org.data.area.DBConstants.I_ID;
import static jm.org.data.area.DBConstants.I_POSITION;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ChartsListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	//private String indicator, country;
	private String country;
	//private IndicatorActivity act;
	
	private CountryActivity cAct;
	private CollectionsActivity colAct;
	private SavedDataActivity sAct;
	
	private Activity parent;
	//private SearchCursorAdapter mAdapter;
	private SimpleCursorAdapter tAdapter;
	private ProgressDialog dialog;

	private int searchType, collection, country_id;
	
	private String title_text, empty_text;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		try{
        	parent = getActivity();
        	if (parent instanceof CollectionsActivity){
        		colAct = (CollectionsActivity) getActivity();
        		dialog = new ProgressDialog(colAct);
        		searchType = COLLECTION_CHARTS;
        		collection = colAct.getCollection();
        		//title_text 	= "Collections Charts";
        		empty_text	= getResources().getString(R.string.charts_empty);// "There are no saved Charts for Collection ID: " + collection;
        		
        		
        	}else if (parent instanceof CountryActivity){
        		cAct = (CountryActivity) getActivity();
        		dialog = new ProgressDialog(cAct);
        		searchType = COUNTRY_CHARTS;
        		country = cAct.getCountry();
        		//title_text 	= "Country Charts";
        		empty_text	= getResources().getString(R.string.charts_empty);// "Your Query returned no Charts for Country: " + country;
        		
        	}else if(parent instanceof SavedDataActivity){
        		sAct = (SavedDataActivity) getActivity();
        		dialog = new ProgressDialog(sAct);
        		//indicator = null;
        		searchType = SAVED_CHARTS;
        		//title_text 	= "Saved Charts";
        		empty_text	= getResources().getString(R.string.charts_empty);// "There are no Saved Charts ";
        		
        	}else{
        		Log.d(TAG,"We Have no clue what the starting activity is. Hmm, not sure what is happening");
        	}
	        
        }catch (ClassCastException actException){
        	 Log.e(TAG,"We Have no clue what the starting activity is");
        	
        }
		
		Log.e(TAG, "Creating Charts List Fragment");
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setEmptyText(empty_text);
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

		//return inflater.inflate(R.layout.chart_list, container, false);
		View view;
		view  = inflater.inflate(R.layout.chart_list, container, false);
		//((TextView) view.findViewById(R.id.chartText)).setText(title_text);
		((TextView) view.findViewById(android.R.id.empty)).setText(Html.fromHtml(empty_text));
		return view;
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
		int indicator = cursor.getInt(cursor.getColumnIndex(I_ID));
		String wb_indicator = cursor.getString(cursor.getColumnIndex("wb_id"));
		String itemDesc = cursor.getString(cursor
				.getColumnIndex(CHART_DESC));
		String countries = cursor.getString(cursor
				.getColumnIndex(CHART_COUNTRIES));
		int group = cursor.getInt(cursor.getColumnIndex(I_GROUP));
		int child = cursor.getInt(cursor.getColumnIndex(I_POSITION));
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
		intent.putExtra(WB_INDICATOR_ID, wb_indicator);
		intent.putExtra("ind_id", indicator);
		intent.putExtra(SELECTION_ID, S_INDICATORS);
		intent.putExtra(SELECTION_NAME, "Indicators");
		intent.putExtra("countries", countries);
		intent.putExtra(GROUP_POSITION, group);
		intent.putExtra(CHILD_POSITION, child);
		// intent.putExtra(BING_URL, itemURL);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		startActivity(intent);
		this.getActivity().finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		if(searchType == COLLECTION_CHARTS){
			return new ChartListAdapter(getActivity(), searchType, collection);
		}else if(searchType == COUNTRY_CHARTS){
			return new ChartListAdapter(getActivity(), searchType, country);
		}else{
			return new ChartListAdapter(getActivity(), searchType, 0);
		}
		
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