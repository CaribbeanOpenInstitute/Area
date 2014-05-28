package jm.org.data.area;

import static jm.org.data.area.AreaConstants.*;
import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.SAVED_REPORTS;
import static jm.org.data.area.AreaConstants.S_COLL_ACT;
import static jm.org.data.area.AreaConstants.S_PARENT;
import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.DOC_TITLE;
import static jm.org.data.area.DBConstants.IDS_DOC_AUTH_STR;
import static jm.org.data.area.DBConstants.IDS_DOC_DWNLD_URL;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_TITLE;

import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ReportsFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	private String indicator;
	//private ViewAnimator loadingAnimator;
	private String[] countryList;
	private IndicatorActivity act;
	
	private CountryActivity cAct;
	private CollectionsActivity colAct;
	private SavedDataActivity sAct;
	
	private Activity parent;
	SearchCursorAdapter mAdapter;
	SimpleCursorAdapter tAdapter;
	private ProgressDialog dialog;
	
	private String title_text, empty_text;
	private int searchType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		try{
        	parent = getActivity();
        	if (parent instanceof IndicatorActivity){
        		act 		= (IndicatorActivity) getActivity();
        		dialog 		= new ProgressDialog(act);
        		indicator 	= act.getIndicator();
        		searchType	= IDS_SEARCH;
        		//title_text 	= "IDS Reports";
        		empty_text	= getResources().getString(R.string.reports_empty);//"Your Query returned no Records for Indicator: " + indicator;
        		
        	}else if (parent instanceof CollectionsActivity){
        		colAct = (CollectionsActivity) getActivity();
        		dialog = new ProgressDialog(colAct);
        		indicator = "" + colAct.getCollection();
        		searchType = COLLECTION_REPORTS;
        		//title_text 	= "Collections IDS Reports";
        		empty_text	= getResources().getString(R.string.reports_empty);//"There are no saved Records for Collection: " + indicator;
        		
        	}else if (parent instanceof CountryActivity){
        		cAct = (CountryActivity) getActivity();
        		dialog = new ProgressDialog(cAct);
        		indicator = "" + cAct.getCountryID();
        		searchType = COUNTRY_REPORTS;
        		//title_text 	= "Country Reports";
        		empty_text	= getResources().getString(R.string.reports_empty);//"Your Query returned no Records for Country: " + indicator;
        		
        	}else if(parent instanceof SavedDataActivity){
        		sAct = (SavedDataActivity) getActivity();
        		dialog = new ProgressDialog(sAct);
        		indicator = "";
        		searchType = SAVED_REPORTS;
        		//title_text 	= "Saved Reports";
        		empty_text	= getResources().getString(R.string.reports_empty);//"There are no Saved Records ";
        		
        	}else{
        		Log.d(TAG,"We Have no clue what the starting activity is. Hmm, not sure what is happening");
        	}
	        
        }catch (ClassCastException actException){
        	 Log.e(TAG,"We Have no clue what the starting activity is");
        	
        }
		
		
		mAdapter = new SearchCursorAdapter(getActivity(), null);

		// countryList = (String[]) parentActivity.getCountryList();
		Log.d(TAG, String.format("Indcator: %s. Country List: ", indicator));
		Log.e(TAG, "Creating Reports Fragment");
		// setListAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setEmptyText(empty_text);
		// loadingAnimator = (ViewAnimator)
		// parentActivity.findViewById(R.id.reportSwitcher); //Loading Animator
		// loadingAnimator.setDisplayedChild(1);
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading Reports Data. Please wait...", true);
		Log.e(TAG, "Reports Fragment dialog created");
		String[] from = { IDS_DOC_TITLE, IDS_DOC_AUTH_STR };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
		// tAdapter = new SimpleCursorAdapter(getActivity(),
		// R.layout.list_reports_item, null, from, to, 0);
		tAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_dual, null, from, to, 0);
		SimpleCursorAdapter.ViewBinder binder = new SimpleCursorAdapter.ViewBinder() {

			@Override
			public boolean setViewValue(View arg0, Cursor arg1, int arg2) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) arg0;
			    tv.setTextColor(Color.parseColor("#004B51"));
				return false;
			}
			
		};
		tAdapter.setViewBinder(binder);

		setListAdapter(tAdapter);
		getLoaderManager().initLoader(0, null, this);

		// setEmptyText("No indicators found");
		// setListShown(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view  = inflater.inflate(R.layout.reports, container, false);
		((TextView) view.findViewById(R.id.reportText)).setText(title_text);
		((TextView) view.findViewById(android.R.id.empty)).setText(Html.fromHtml(empty_text));
		return view;
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
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);

		String item = cursor.getString(cursor.getColumnIndex(IDS_DOC_ID));
		int item_id = cursor.getInt(cursor.getColumnIndex(DOCUMENT_ID));
		String itemTitle = cursor.getString(cursor
				.getColumnIndex(IDS_DOC_TITLE));
		String item_url = cursor.getString(cursor.getColumnIndex(IDS_DOC_DWNLD_URL));
		
		Log.d(TAG, "Report selected is: " + item + " Title is: " + itemTitle);
		
		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

		// MapBuilder.createEvent().build() returns a Map of event fields and values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder
		    .createEvent("ui_action",     // Event category (required)
		                 "Reports_List_Selction",  // Event action (required)
		                 "report selected is: " + item + " Title is: " + itemTitle,   // Event label
		                 null)            // Event value
		    .build()
		);
		// Launch Report View
		Intent intent = new Intent(getActivity().getApplicationContext(),
				ReportDetailViewActivity.class);
		intent.putExtra(DOCUMENT_ID, item_id);
		intent.putExtra(DOC_TITLE, itemTitle);
		intent.putExtra(IDS_DOC_ID, item);
		intent.putExtra(IDS_DOC_DWNLD_URL, item_url);
		if (!(colAct == null)){
			intent.putExtra(S_PARENT, S_COLL_ACT);
			intent.putExtra("col_id", indicator);
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new SearchListAdapter(getActivity(), searchType, indicator,
				countryList);
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
		if(cAct != null){
			cAct.updateCountry();
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
		/*
		IndicatorActivity parentActivity = (IndicatorActivity) getActivity();
		indicator = parentActivity.getIndicator();
		countryList = parentActivity.getCountryList();
		Log.d(TAG,
				String.format(
						"Reports reload function. \n Current indicator: %s. Country List: %s",
						indicator, Arrays.toString(countryList)));
		*/
		getLoaderManager().restartLoader(0, null, this);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
