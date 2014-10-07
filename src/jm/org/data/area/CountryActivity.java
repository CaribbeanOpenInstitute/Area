package jm.org.data.area;

import static jm.org.data.area.AreaConstants.POSITION;
import static jm.org.data.area.AreaConstants.S_COUNTRIES;
import static jm.org.data.area.DBConstants.CAPITAL_CITY;
import static jm.org.data.area.DBConstants.COUNTRY_NAME;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_ID;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_NAME;
import static jm.org.data.area.DBConstants.GDP;
import static jm.org.data.area.DBConstants.GNI_CAPITA;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_ID;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_NAME;
import static jm.org.data.area.DBConstants.LIFE_EX;
import static jm.org.data.area.DBConstants.LITERACY;
import static jm.org.data.area.DBConstants.POPULATION;
import static jm.org.data.area.DBConstants.POVERTY;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_COUNTRY_ID;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class CountryActivity extends BaseActivity {
	
	private static final String TAG = CountryActivity.class.getSimpleName();
	private String selection, country;
	private int mSelection, country_id, cPosition;
	TabHost mTabHost;
	ViewPager mViewPager;
	AreaTabsAdapter mTabsAdapter;
	
	/*private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;

	private TextView country_name_view, country_code_view, country_capital_view, country_income_view, country_region_view, 
					 country_population_view, country_gdp_view, country_gni_view, country_poverty_view, 
					 country_life_ex_view, country_literacy_view;
	
	private String country_name, country_code, country_capital, country_income, country_region, country_population,
	 				 country_gdp, country_gni, country_poverty, country_life_ex, country_literacy;
	 */	
	private Bundle actBundle;
	private Context mContext;
	
	private Cursor country_data;
	//private CountryReportsFragment cFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_dashboard);
		mContext = this;

		
		actBundle = getIntent().getExtras();
		
		if(actBundle != null ){
			if(actBundle.getString(SELECTION_NAME) != null){
				
				mSelection = actBundle.getInt(SELECTION_ID  );
				selection  = actBundle.getString(SELECTION_NAME);
				
			}else{//set values to default
				mSelection = S_COUNTRIES;
				selection = "Countries";
			}
			if (actBundle.getString("country") != null ){
				cPosition 	= actBundle.getInt(POSITION);
				country_id	= actBundle.getInt("country_id");
				country		= actBundle.getString("country");
				
			}else{
				// Use Jamaica as the defaule position
				// TODO get position details for Jamica in DB
				cPosition	= 116;
				country_id	= 117;
				country		= "Jamaica";
				
			}
			
		}else{//set vallues to default
			mSelection = S_COUNTRIES;
			selection = "Countries";
			cPosition	= 116;
			country_id	= 117;
			country		= "Jamaica";
		}
		Log.d(TAG, "Country selected is: " + country + "-> ID: " + country_id + " at Position: " + cPosition);
		
		
		
		Log.d(TAG, "Creating Tabs");
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTabsAdapter = new AreaTabsAdapter(this, mTabHost, mViewPager);
		
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("overview").setIndicator("Overview"),
				CountryOverviewFragment.class, null);
		
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("reports").setIndicator("Reports"),
				ReportsFragment.class, null);
		
		mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
				.setIndicator("Charts"), ChartsListFragment.class, null);
		
		/*mTabsAdapter.addTab(
				mTabHost.newTabSpec("articles").setIndicator("Articles"),
				ArticlesFragment.class, null);*/
		
		
		//mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
		
		
		for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) 
        { 
			View v = mTabHost.getTabWidget().getChildAt(i);
			v.setBackgroundResource(R.drawable.tab_selector);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#025E6B"));
        } 
        TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#025E6B"));
		

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		//get country data using Country ID
		
		//area = (AreaApplication) mContext.getApplicationContext();
		//updateCountry();
		
		
	}
	

	
	public String getSelection() {																																																																																																																																																																																																																																																																																									
		return selection;
	}
	
	public int getParentNum() {
		return 1;
	}

	public int getSelectionID(){
		return mSelection;
	}
	
	public void setSelection(String indicator) {
		selection = indicator;
		Log.d(TAG, "Selection changed to " + selection);

	}
	
	public void setSelection(int lPos) {
		mSelection = lPos;
	}
	

	public int getCountryPos() {

		return cPosition;
	}
	
	public String getCountry() {

		return country;
	}
	
	public int getCountryID() {
		
		return country_id;
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



	public void updateCntryOvrvw() {
		// TODO Auto-generated method stub
		CountryOverviewFragment cFragment = (CountryOverviewFragment) getSupportFragmentManager()
		.findFragmentByTag("android:switcher:" + R.id.viewpager + ":0");
		// if we are on the country overview tab refresh the country data
		if(mTabHost.getCurrentTab() == 0){
			cFragment.setData();
		}
	}

	
}
