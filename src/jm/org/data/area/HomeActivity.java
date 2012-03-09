package jm.org.data.area;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class HomeActivity extends BaseActivity{
	private static final String TAG = HomeActivity.class.getSimpleName();
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Check for application initialization preference
		if(!area.prefs.getBoolean("startupActivity", false)) {
			//Run startup activity
		}
		
		setContentView(R.layout.home_dashboard);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.home, menu);
    	
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

}
