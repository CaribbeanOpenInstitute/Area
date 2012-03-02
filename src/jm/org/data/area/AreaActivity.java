package jm.org.data.area;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.actionbarcompat.ActionBarActivity;

public class AreaActivity extends ActionBarActivity {
	SharedPreferences preferences;
	private String TAG = AreaActivity.class.getSimpleName();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize preferences
        String idsAPIKey = getString(R.string.pref_idsKey);
        String bingAPIKey = getString(R.string.pref_bingKey);
        
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if ((preferences.getString(idsAPIKey, null) == null) || (preferences.getString(bingAPIKey, null) == null)){
        	startActivity(new Intent(this, AreaPreferencesActivity.class));
        	Toast.makeText(this, "Please setup preferences", Toast.LENGTH_LONG);
        	return;
        }
        
        Button btnPrefs = (Button) findViewById(R.id.btn_showPrefs);
        btnPrefs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String idsKey = preferences.getString("idsKey", "n/a");
				String bingKey = preferences.getString("bingKey", "n/a");
				String dateRange = preferences.getString("timePeriod", null);
				showPrefs(idsKey, bingKey, dateRange);
			}
		});
    }
    
    private void showPrefs(String idsKey, String bingKey, String dateRange) {
		Toast.makeText(
				AreaActivity.this,
				String.format("IDS API Key: %s, Bing API Key: %s and Time period: %s years", idsKey,
						bingKey, dateRange), Toast.LENGTH_LONG).show();
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
    		startActivity(new Intent(AreaActivity.this, AreaPreferencesActivity.class));
    	}
    	return super.onOptionsItemSelected(item);
    }
}