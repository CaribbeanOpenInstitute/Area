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
				cPosition	= 113;
				country_id	= 114;
				country		= "Jamaica";
				
			}
			
		}else{//set vallues to default
			mSelection = S_COUNTRIES;
			selection = "Countries";
			cPosition	= 113;
			country_id	= 114;
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
		//mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
		//		.setIndicator("Charts"), ChartsListFragment.class, null);
		
		//mTabsAdapter.addTab(
		//		mTabHost.newTabSpec("articles").setIndicator("Articles"),
		//		ArticlesFragment.class, null);
		
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



/*	public void updateCountry() {
		country_data = area.areaData.getCountry(country_id);
		if (country_data.moveToFirst()){
			
			
			((TextView) this.findViewById(R.id.country_capital))
			.setText(country_data.getString(country_data.getColumnIndex(CAPITAL_CITY)));
	
			((TextView) this.findViewById(R.id.country_title))
			.setText(country_data.getString(country_data.getColumnIndex(COUNTRY_NAME)));
			
			((TextView) this.findViewById(R.id.country_income))
			.setText("" +
				country_data.getString(country_data.getColumnIndex(INCOME_LEVEL_ID)) 
				+ ":" +
				country_data.getString(country_data.getColumnIndex(INCOME_LEVEL_NAME)));
			
			((TextView) this.findViewById(R.id.country_region))
			.setText("" +
				country_data.getString(country_data.getColumnIndex(COUNTRY_REGION_ID)) 
				+ ":" +
				country_data.getString(country_data.getColumnIndex(COUNTRY_REGION_NAME)));
			
			((TextView) this.findViewById(R.id.country_population))
			.setText(country_data.getString(country_data.getColumnIndex(POPULATION)));
			
			((TextView) this.findViewById(R.id.country_code))
			.setText(country_data.getString(country_data.getColumnIndex(WB_COUNTRY_ID)));
			
			/*<!--  FROM_COUNTRY = { COUNTRY_ID, WB_COUNTRY_ID,
			WB_COUNTRY_CODE, COUNTRY_NAME, CAPITAL_CITY, INCOME_LEVEL_ID,
			INCOME_LEVEL_NAME, COUNTRY_REGION_ID, COUNTRY_REGION_NAME, GDP,
			GNI_CAPITA, POVERTY, LIFE_EX, LITERACY, POPULATION }; -->*/
			
		/*	((TextView) this.findViewById(R.id.country_gdp))
			.setText(country_data.getString(country_data.getColumnIndex(GDP)));
			
			((TextView) this.findViewById(R.id.country_gni))
			.setText(country_data.getString(country_data.getColumnIndex(GNI_CAPITA)));
			
			((TextView) this.findViewById(R.id.country_poverty))
			.setText(country_data.getString(country_data.getColumnIndex(POVERTY)));
			
			((TextView) this.findViewById(R.id.country_life_ex))
			.setText(country_data.getString(country_data.getColumnIndex(LIFE_EX)));
			
			((TextView) this.findViewById(R.id.country_literacy))
			.setText(country_data.getString(country_data.getColumnIndex(LITERACY)));
		}else{
			Log.e(TAG, "ERROR No Country Data retrieved");
		}
		country_data.close();
		
	} */
	

	
}
