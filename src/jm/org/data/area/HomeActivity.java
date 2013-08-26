package jm.org.data.area;

import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

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
	SelectionListFragment sFragment;
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
		}
		return super.onCreateOptionsMenu(menu);
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
	
	



}
