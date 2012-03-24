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
import static jm.org.data.area.AreaConstants.REMOVE_KEY;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

public class IndicatorActivity extends BaseActivity implements
		KeywordsFragment.OnCountryChangeListener {
	private static final String TAG = IndicatorActivity.class.getSimpleName();
	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;

	public int dataSource;
	private String indicatorID;
	//private String indicatorName;
	private ArrayList<String> countryList;
	final String POSITION = "position";
	private int mListPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			 ActionBar actionBar = getActionBar();
			 actionBar.setDisplayHomeAsUpEnabled(true);
		}

		final Bundle indicatorBundle = getIntent().getExtras();
		if(indicatorBundle.getString(WB_INDICATOR_ID) != null) {
			indicatorID = indicatorBundle.getString(WB_INDICATOR_ID);
			mListPosition = indicatorBundle.getInt(POSITION);
		}
		
		setContentView(R.layout.indicator_dashboard);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
				.setIndicator("Charts"), ChartsFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("reports").setIndicator("Reports"),
				ReportsFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("articles").setIndicator("Articles"),
				ArticlesFragment.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		//mListPosition = indicatorBundle.getInt(POSITION, -1);
		/*if (mListPosition != -1) {
			IndicatorListFragment inFragment = (IndicatorListFragment) getSupportFragmentManager().findFragmentById(R.id.inlistFragment); 
			if (inFragment != null && inFragment.isInLayout()) { 
				inFragment.setListSelection(mListPosition);
			}
		}*/
		Log.d(TAG, String.format("Indicator ID: %s at position %d", indicatorID, mListPosition));
		countryList = new ArrayList<String>();
		countryList.add("World");
		

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	@Override
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
	
	public void setIndicator(String indicator) {
		indicatorID = indicator;
		Log.d(TAG, "Indicator changed to " + indicator);
	}
	
	public int getPosition() {
		return mListPosition;
	}

	public void setPosition(int lpos) {
		mListPosition = lpos;
    }

	public void addCountry(String countryStr){
		countryList.add(countryStr);
	}
	
	public String[] getCountryList() {
		return (String[])countryList.toArray(new String[countryList.size()]);
	}
	
	public ArrayList<String> getCountryListArray() {
		return countryList;
	}

	public void reloadData() {
		//Solution sourced from: http://stackoverflow.com/a/7393477/498449
		
		ChartsFragment chFragment = (ChartsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":0");
		ReportsFragment reFragment = (ReportsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":1");
		ArticlesFragment arFragment = (ArticlesFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":2");
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
		//Log.d(TAG, "Current tab is " + mTabHost.getCurrentTab());
	}

}
