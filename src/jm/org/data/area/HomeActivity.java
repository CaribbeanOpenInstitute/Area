package jm.org.data.area;

import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

/**
 * HomeActivity
 * 
 * DESC: Main application activity.
 */
public class HomeActivity extends BaseActivity {
	
	private static final String TAG = HomeActivity.class.getSimpleName();
	private String selection;
	private int mSelection;
	private Bundle actBundle;
	private SelectionListFragment sFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check for application initialization preference. If false, then runs
		// startup activity
		if (!area.prefs.getBoolean("startupActivity", false)) {
			if (!area.initIsRunning) // Run startup activity
				startActivityForResult(new Intent(HomeActivity.this,
						StartupActivity.class), 0);
		}

		Log.i(TAG, "Startup Activity Complete");
		//this.startActivity(new Intent(HomeActivity.this, ExpandableList2.class));
		setContentView(R.layout.home_dashboard);
				// if (area.areaService != null) {
		
		// area.areaService.genericSearch(IDS_SEARCH, "TX.VAL.AGRI.ZS.UN", new
		// String[]{"Jamaica", "Kenya","Barbados"});
		// }
		actBundle = getIntent().getExtras();
		if(actBundle != null ){
			if(actBundle.getString(SELECTION_NAME) != null){
				
				mSelection = actBundle.getInt(SELECTION_ID  );
				selection  = actBundle.getString(SELECTION_NAME);
			}else{//set values to default
				mSelection = S_INDICATORS;
				selection = "Indicators";
			}
				
		}else{//set vallues to default
			mSelection = S_INDICATORS;
			selection = "Indicators";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.home, menu);

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
			//setMenuBackground();
		}
		return true;//super.onCreateOptionsMenu(menu);
	}

	protected void setMenuBackground() {
		// Log.d(TAG, "Enterting setMenuBackGround");
		getLayoutInflater().setFactory(new Factory() {
			public View onCreateView(String name, Context context,
					AttributeSet attrs) {
				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {
					try { // Ask our inflater to create the view
						LayoutInflater f = getLayoutInflater();
						final View view = f.createView(name, null, attrs);
						/*
						 * The background gets refreshed each time a new item is
						 * added the options menu. So each time Android applies
						 * the default background we need to set our own
						 * background. This is done using a thread giving the
						 * background change as runnable object
						 */
						new Handler().post(new Runnable() {
							public void run() {
								// sets the background color
								view.setBackgroundResource(R.color.Menu_Bar);
								// sets the text color
								((TextView) view).setTextColor(Color.BLACK);
								// sets the text size
								((TextView) view).setTextSize(18);
							}
						});
						return view;
					} catch (InflateException e) {
					} catch (ClassNotFoundException e) {
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_prefs:
			startActivity(new Intent(HomeActivity.this,
					AreaPreferencesActivity.class));
			break;
		case R.id.menu_startup:
			startActivity(new Intent(HomeActivity.this, StartupActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * DESC: Function called on the completion (success||failure) of the Startup
	 * Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try{
			if (resultCode == RESULT_OK) {
				Editor editor = area.prefs.edit();
				editor.putBoolean(getString(R.string.pref_startupKey), true);
				editor.commit();
	
				sFragment = (SelectionListFragment) getSupportFragmentManager()
						.findFragmentById(R.id.slistFragment);
				sFragment.reload();
				
			} else { // Startup Failed
				Toast.makeText(
						HomeActivity.this,
						"There was an error running the application initialization. Please try again.",
						Toast.LENGTH_SHORT).show();
				Log.e(TAG, "There was an error running the application initialization. Please try again.");
			}
		}catch (Exception e) {
			Log.e(TAG, "Exception ");
			e.printStackTrace();
			
		}

		
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
