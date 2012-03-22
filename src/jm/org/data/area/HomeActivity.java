package jm.org.data.area;

import static jm.org.data.area.AreaConstants.IDS_SEARCH;
import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import android.app.SearchManager;
import android.content.Context;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;


public class HomeActivity extends BaseActivity{
	private static final String TAG = HomeActivity.class.getSimpleName();
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//Check for application initialization preference
		if(!area.prefs.getBoolean("startupActivity", false)) {
			if (!area.initIsRunning) //Run startup activity
				startActivityForResult(new Intent(HomeActivity.this, StartupActivity.class), 0);
			
		}
		
		setContentView(R.layout.home_dashboard);
		//if (area.areaService != null) {
		//	Log.e(TAG, "Running API call on service");
			//area.areaService.genericSearch(IDS_SEARCH, "TX.VAL.AGRI.ZS.UN", new String[]{"Jamaica", "Kenya","Barbados"});
		//}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.home, menu);
  
	    //getBaseContext();
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

    	return super.onCreateOptionsMenu(menu);
    }
	

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_prefs:
    		startActivity(new Intent(HomeActivity.this, AreaPreferencesActivity.class));
    		break;
    	case R.id.menu_startup:
    		startActivity(new Intent(HomeActivity.this, StartupActivity.class));
    	}
    	return super.onOptionsItemSelected(item);

    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

		if( resultCode == RESULT_OK) {
			Editor editor = area.prefs.edit();
			editor.putBoolean(getString(R.string.pref_startupKey), true);
			editor.commit();
			
			//startActivity(getIntent());		
    	}

    }

}
