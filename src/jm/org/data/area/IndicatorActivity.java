/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Source: http://developer.android.com/resources/samples/Support4Demos/src/com/example/android/supportv4/app/FragmentTabsPager.html
 */
package jm.org.data.area;

import static jm.org.data.area.AreaConstants.ADD_KEY;
import static jm.org.data.area.AreaConstants.CHILD_POSITION;
import static jm.org.data.area.AreaConstants.GROUP_POSITION;
import static jm.org.data.area.AreaConstants.REMOVE_KEY;
import static jm.org.data.area.DBConstants.*;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class IndicatorActivity extends BaseActivity implements
		KeywordsFragment.OnCountryChangeListener {
	private static final String TAG = IndicatorActivity.class.getSimpleName();
	TabHost mTabHost;
	ViewPager mViewPager;
	AreaTabsAdapter mTabsAdapter;

	
	
	public int dataSource, indicator;
	private String indicatorID, selection;
	// private String indicatorName;
	private ArrayList<String> countryList;
	
	private int mListPosition, mChildPosition, mGroupPosition, mSelection;
	//private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			// ActionBar actionBar = getActionBar();
			// actionBar.setDisplayHomeAsUpEnabled(true);
		}

		final Bundle indicatorBundle = getIntent().getExtras();
		if (indicatorBundle.getString(WB_INDICATOR_ID) != null) {
			
			indicatorID = indicatorBundle.getString(WB_INDICATOR_ID);
			indicator   = indicatorBundle.getInt("ind_id");
			mGroupPosition = indicatorBundle.getInt(GROUP_POSITION);
			mChildPosition = indicatorBundle.getInt(CHILD_POSITION);
			mSelection = indicatorBundle.getInt(SELECTION_ID  );
			selection  = indicatorBundle.getString(SELECTION_NAME);
			
		}else if(indicatorBundle.getString(SELECTION_NAME) != null){
			
			mSelection = indicatorBundle.getInt(SELECTION_ID  );
			selection  = indicatorBundle.getString(SELECTION_NAME);
		}
		
		if (indicatorBundle.getString("countries") != null){
			countryList = new ArrayList<String>();
			String[] countryArray = indicatorBundle.getString("countries").split(",");
			
			countryList = new ArrayList<String>(Arrays.asList(countryArray));
		}else{
			countryList = new ArrayList<String>();
			
			countryList.add("World");
		}

		setContentView(R.layout.indicator_dashboard);
		//dialog = new ProgressDialog(this);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTabsAdapter = new AreaTabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
				.setIndicator("Charts"), ChartsFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("articles").setIndicator("Articles"),
				ArticlesFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("reports").setIndicator("Reports"),
				ReportsFragment.class, null);
		
		for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) 
        { 
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#025E6B"));
        } 
        TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#025E6B"));
		

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

		// mListPosition = indicatorBundle.getInt(POSITION, -1);
		/*
		 * if (mListPosition != -1) { IndicatorListFragment inFragment =
		 * (IndicatorListFragment)
		 * getSupportFragmentManager().findFragmentById(R.id.inlistFragment); if
		 * (inFragment != null && inFragment.isInLayout()) {
		 * inFragment.setListSelection(mListPosition); } }
		 */
		Log.d(TAG, String.format("Indicator ID: %s at position %d",
				indicatorID, mChildPosition));
		

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		//Log.d(TAG, "OnCreateOptionsMenu");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			// TODO Implement a Search Dialog fall back for compatibility with
			// Android 2.3 and lower
			// Currently crashes on Gingerbread or lower

			// Get the SearchView and set the searchable configuration
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu
					.findItem(R.id.menu_search).getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(true); // Do not iconify the
													// widget; expand it by
													// default
			
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onAttachFragment (Fragment fragment){
		super.onAttachFragment(fragment);
		//Log.d(TAG, "Fragment Attached");
	}
	
	@Override
	public boolean onContextItemSelected (MenuItem item){
		//Log.d(TAG, "Context Item Selected");
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		//Log.d(TAG, "Context Menu Created");
	}
	
	@Override
	public void onDestroy (){
		super.onDestroy();
		//Log.d(TAG, "Fragment Destroyed");
	}
	
	
	@Override
	public void onPanelClosed(int featureId, Menu menu){
		super.onPanelClosed(featureId, menu);
		//Log.d(TAG, "Panel Closed");
	}
	
	@Override
	public boolean onPreparePanel (int featureId, View view, Menu menu){
		//Log.d(TAG, "Preparing Panel");
		return super.onPreparePanel(featureId, view, menu);
	}
	
	
	@Override
	public void onOptionsMenuClosed (Menu menu){
		super.onOptionsMenuClosed(menu);
		//Log.d(TAG, "Menu Closed");
	}
	
	@Override
	public void onResume(){
		//Log.d(TAG, "Resuming");
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		//case R.id.menu_settings:
		//	startActivity(new Intent(IndicatorActivity.this, AreaPreferencesActivity.class));
		case R.id.menu_share:
			

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	

	
	public void onCountryChange(int change, String keyword) {
		switch (change) {
		case ADD_KEY:
			Log.d(TAG, "Country " + change + "  " + keyword);
			countryList.add(keyword);
			break;
		case REMOVE_KEY:
			Log.d(TAG, "Country " + change + "  " + keyword);
			countryList.remove(keyword);
			break;
		}
		reloadData();
	}

	public String getIndicator() {
		return indicatorID;
	}
	
	public int getIndicatorID() {
		return indicator;
	}
	
	public void setIndicator(int indicatorID) {
		indicator = indicatorID;
		//Log.d(TAG, "Indicator changed to " + indicator);

	}
	public void setIndicator(String indicator) {
		indicatorID = indicator;
		Log.d(TAG, "Indicator changed to " + indicator);

	}
	
	public String getSelection() {
		return selection;
	}

	public void setSelection(String indicator) {
		selection = indicator;
		Log.d(TAG, "Selection changed to " + selection);

	}

	public int getParentNum() {
		return 2;
	}
	
	public int getSelectionID(){
		return mSelection;
	}
	
	public int getPosition() {
		return mListPosition;
	}
	
	public int getGroupPosition() {
		return mGroupPosition;
	}
	
	public int getChildPosition() {
		return mChildPosition;
	}

	public void setPosition(int gPos, int cPos) {
		mGroupPosition = gPos;
		mChildPosition = cPos;
	}
	
	public void setPosition(int lPos) {
		mListPosition = lPos;
	}
	
	public void setSelection(int lPos) {
		mSelection = lPos;
	}

	//private void addCountry(String countryStr) {
	//	countryList.add(countryStr);
	//}

	public String[] getCountryList() {
		return (String[]) countryList.toArray(new String[countryList.size()]);
	}

	public ArrayList<String> getCountryListArray() {
		return countryList;
	}

	public void resetCountryList() {
		countryList = new ArrayList<String>();
		countryList.add("World");

	}

	public void reloadData() {
		
		// Solution sourced from: http://stackoverflow.com/a/7393477/498449
		ChartsFragment chFragment = (ChartsFragment) getSupportFragmentManager()
				.findFragmentByTag("android:switcher:" + R.id.viewpager + ":0");
		ReportsFragment reFragment = (ReportsFragment) getSupportFragmentManager()
				.findFragmentByTag("android:switcher:" + R.id.viewpager + ":1");
		ArticlesFragment arFragment = (ArticlesFragment) getSupportFragmentManager()
				.findFragmentByTag("android:switcher:" + R.id.viewpager + ":2");
		switch (mTabHost.getCurrentTab()) {
		case 0:
			Log.d(TAG, "Current tab is Charts");
			chFragment.reload();
			reFragment.reload();
			break;
		case 1:
			Log.d(TAG, "Current tab is Reports");
			reFragment.reload();
			arFragment.reload();
			chFragment.reload();
			break;
		case 2:
			Log.d(TAG, "Current tab is Articles");
			arFragment.reload();
			reFragment.reload();
			break;
		}

		// Log.d(TAG, "Current tab is " + mTabHost.getCurrentTab());
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem searchViewMenuItem = menu.findItem(R.id.menu_search);    
	    SearchView mSearchView = (SearchView) searchViewMenuItem.getActionView();
	    int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
	    ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
	    v.setImageResource(R.drawable.ic_action_search); 
	    return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		EasyTracker.getInstance(this).activityStart(this);  // Add this method.
		}
	
	@Override
	public void onStop() {
		super.onStop();
	
		EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
}
