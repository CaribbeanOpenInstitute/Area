package jm.org.data.area;

import static jm.org.data.area.AreaConstants.BING_SEARCH;
import static jm.org.data.area.AreaConstants.COLLECTION_ARTICLES;
import static jm.org.data.area.AreaConstants.SAVED_ARTICLES;
import static jm.org.data.area.AreaConstants.S_COLL_ACT;
import static jm.org.data.area.AreaConstants.S_PARENT;
import static jm.org.data.area.DBConstants.BING_DESC;
import static jm.org.data.area.DBConstants.BING_SEARCH_ID;
import static jm.org.data.area.DBConstants.BING_TITLE;
import static jm.org.data.area.DBConstants.BING_URL;

import java.util.Arrays;

import jm.org.data.area.R.color;

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

public class ArticlesFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public static final String TAG = ArticlesFragment.class.getSimpleName();
	private String indicator;
	private String[] countryList;
	SearchCursorAdapter mAdapter;
	SimpleCursorAdapter tAdapter;
	private IndicatorActivity act;
	
	private CountryActivity cAct;
	private CollectionsActivity colAct;
	private SavedDataActivity sAct;
	
	private Activity parent;
	private ProgressDialog dialog;
	private String title_text, empty_text;
	
	private int searchType, collection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		
		Log.e(TAG, "Creating Articles Fragment");
		try{
        	parent = getActivity();
        	if (parent instanceof IndicatorActivity){
        		act = (IndicatorActivity) getActivity();
        		dialog = new ProgressDialog(act);
        		indicator = act.getIndicator();
        		countryList = act.getCountryList();
        		searchType = BING_SEARCH;
        		//title_text 	= "Web Articles";
        		empty_text	= getResources().getString(R.string.articles_empty);//"Your Query returned no Records for Indicator: " + indicator;
        		
        	}else if (parent instanceof CollectionsActivity){
        		colAct = (CollectionsActivity) getActivity();
        		dialog = new ProgressDialog(colAct);
        		searchType = COLLECTION_ARTICLES;
        		collection = colAct.getCollection();
        		indicator = ""+ collection;
        		countryList = null;
        		//title_text 	= "Collection Articles";
        		empty_text	= getResources().getString(R.string.articles_empty);//"There are no Articles saved in this Collection...";
        	}else if (parent instanceof CountryActivity){
        		cAct = (CountryActivity) getActivity();
        		dialog = new ProgressDialog(cAct);
        		indicator = cAct.getCountry();
        		
        		countryList = null;
        		//title_text 	= "Country Articles";
        		empty_text	= getResources().getString(R.string.articles_empty);//"No articles for " + indicator;
        	}else if(parent instanceof SavedDataActivity){
        		sAct = (SavedDataActivity) getActivity();
        		dialog = new ProgressDialog(sAct);
        		indicator = "";
        		searchType = SAVED_ARTICLES;
        		countryList = null;
        		//title_text 	= "Saved Articles";
        		empty_text	= getResources().getString(R.string.articles_empty);//"There are no Saved";
        	}else{
        		Log.d(TAG,"We Have no clue what the starting activity is. Hmm, not sure what is happening");
        	}
	        
        }catch (ClassCastException actException){
        	 Log.e(TAG,"We Have no clue what the starting activity is");
        	
        }
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setEmptyText(empty_text);
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading Reports Data. Please wait...", true);
		
		String[] from = { BING_TITLE, BING_DESC };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.articles, container, false);
		((TextView) view.findViewById(R.id.articlesText)).setText(title_text);
		((TextView) view.findViewById(android.R.id.empty)).setText(Html.fromHtml(empty_text));
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.article_list, menu);

		
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_reload:
			Toast.makeText(getActivity(), "Refreshing article list...",
					Toast.LENGTH_SHORT).show();
			dialog = ProgressDialog.show(getActivity(), "",
					"Loading Reports Data. Please wait...", true);
			reload();
			break;
		case R.id.menu_prefs:
			startActivity(new Intent(getActivity(),
					AreaPreferencesActivity.class));
			break;	
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * @Override public void onPrepareOptionsMenu(Menu menu) {
	 * menu.removeItem(R.menu.) super.onPrepareOptionsMenu(menu); }
	 */

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);
		
		String item = cursor.getString(cursor.getColumnIndex(BING_TITLE));
		int item_id = cursor
				.getInt(cursor.getColumnIndex(BING_SEARCH_ID));
		String itemTitle = cursor.getString(cursor.getColumnIndex(BING_DESC));
		String itemURL = cursor.getString(cursor.getColumnIndex(BING_URL));
		Log.d(TAG, "Article selected is: " + item + " Title is: " + itemTitle);

		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

		// MapBuilder.createEvent().build() returns a Map of event fields and values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder
		    .createEvent("ui_action",     // Event category (required)
		                 "Article_List_Selction",  // Event action (required)
		                 "Article selected is: " + item + " Title is: " + itemTitle,   // Event label
		                 null)            // Event value
		    .build()
		);
		// Launch Article View

		new AreaData(getActivity().getApplicationContext())
				.updateArticle(itemURL);
		Log.e(TAG, "Article Updated is: " + item + " Title is: " + itemTitle);
		Intent intent = new Intent(getActivity().getApplicationContext(),
				ArticleViewActivity.class);
		intent.putExtra(BING_SEARCH_ID, item_id);
		intent.putExtra(BING_URL, itemURL);
		intent.putExtra(BING_TITLE, item);
		if (!(colAct == null)){
			intent.putExtra(S_PARENT, S_COLL_ACT);
			intent.putExtra("col_id", collection);
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
		/*
		IndicatorActivity parentActivity = (IndicatorActivity) getActivity();
		indicator = parentActivity.getIndicator();
		countryList = parentActivity.getCountryList();
		Log.d(TAG,
				String.format(
						"Articles reload function. \n Current indicator: %s. Country List: %s",
						indicator, Arrays.toString(countryList)));
		*/
		getLoaderManager().restartLoader(0, null, this);
		
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/*@Override
	public void onStop() {
	    try {
	      super.onStop();

	      if (this.mAdapter !=null){
	        this.mAdapter.getCursor().close();
	        this.mAdapter = null;
	      }
	      
	      this.getLoaderManager().destroyLoader(0);
	      
	      if (this.mActivityListCursorObj != null) {
	        this.mActivityListCursorObj.close();
	      }

	      super.onStop();
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter");
	    	
	    }// end try/catch (Exception error)
	  }// end onStop
*/}
