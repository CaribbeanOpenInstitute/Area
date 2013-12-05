package jm.org.data.era;

import static jm.org.data.era.AreaConstants.S_SAVED_DATA;
import static jm.org.data.era.DBConstants.SELECTION_ID;
import static jm.org.data.era.DBConstants.SELECTION_NAME;
import jm.org.data.era.R;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost;

import com.google.analytics.tracking.android.EasyTracker;

public class SavedDataActivity extends BaseActivity {
	
	private static final String TAG = SavedDataActivity.class.getSimpleName();
	private String selection;
	private int mSelection;
	private Bundle actBundle;
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private AreaTabsAdapter mTabsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.saved_data_dashboard);
		
		actBundle = getIntent().getExtras();
		if(actBundle != null ){
			if(actBundle.getString(SELECTION_NAME) != null){
				
				mSelection = actBundle.getInt(SELECTION_ID  );
				selection  = actBundle.getString(SELECTION_NAME);
			}else{//set values to default
				mSelection = S_SAVED_DATA;
				selection = "Saved Data";
			}
				
		}else{//set vallues to default
			mSelection = S_SAVED_DATA;
			selection = "Saved Data";
		}
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTabsAdapter = new AreaTabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
				.setIndicator("Charts"), ChartsListFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("reports").setIndicator("Reports"),
				ReportsFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("articles").setIndicator("Articles"),
				ArticlesFragment.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

	}
	
	
	
	
	public String getSelection() {
		return selection;
	}

	public int getSelectionID(){
		return mSelection;
	}
	
	public int getParentNum() {
		return 4;
	}
	
	public void setSelection(String indicator) {
		selection = indicator;
		Log.d(TAG, "Selection changed to " + selection);

	}
	
	public void setSelection(int lPos) {
		mSelection = lPos;
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
