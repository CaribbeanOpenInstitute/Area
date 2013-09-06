package jm.org.data.area;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

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
public class AreaTabsAdapter extends FragmentPagerAdapter implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	private static final String TAG = AreaTabsAdapter.class.getSimpleName();
	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	static final class TabInfo {
		//private final String tag;
		private final Class<?> clss;
		private final Bundle args;

		TabInfo(String _tag, Class<?> _class, Bundle _args) {
			//tag = _tag;
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

	public AreaTabsAdapter(FragmentActivity activity, TabHost tabHost,
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
		Log.d(TAG, "TabChanged");
		int position = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(position);
		
	}

	@Override
	public void onPageSelected(int position) {
		// Unfortunately when TabHost changes the current tab, it kindly
		// also takes care of putting focus on it when not in touch mode.
		// The jerk.
		// This hack tries to prevent this from pulling focus out of our
		// ViewPager.
		//Log.d(TAG, "onPageSelected +position " + position);
		//TabWidget widget = mTabHost.getTabWidget();
		//int oldFocusability = widget.getDescendantFocusability();
		//widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		//mTabHost.getChildAt(position).setSelected(true);//.setCurrentTab(position);
		//widget.setDescendantFocusability(oldFocusability);*/
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		//Log.d(TAG, "onPageScrolledStateChanged");
	}

	@Override
	public void onPageScrolled(int position, float positionOffSet, int arg2) {
		
		//Log.d(TAG, "onPageScrolled");
		
		
		
	}

	/*@Override
	public void onPageScrollStateChanged(int state) {
		
	}*/
}