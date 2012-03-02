package jm.org.data.area;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.actionbarcompat.ActionBarActivity;

public class AreaActivity extends ActionBarActivity {
	private String TAG = AreaActivity.class.getSimpleName();
	AreaApplication area;
	SharedPreferences preferences;
	String idsKey;
	String bingKey;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        area = (AreaApplication) getApplication();
        
        //Initialize preferences
        idsKey = getString(R.string.pref_idsKey); 
        bingKey = getString(R.string.pref_bingKey);
        Log.d(TAG, String.format("IDS: %s. Bing: %s", idsKey, bingKey));
        
        if ((area.prefs.getString(idsKey, null) == null) || (area.prefs.getString(bingKey, null) == null)){
        	startActivity(new Intent(this, AreaPreferencesActivity.class));
        	Toast.makeText(this, "Please setup preferences", Toast.LENGTH_LONG);
        	return;
        }
        
        Button btnPrefs = (Button) findViewById(R.id.btn_showPrefs);
        btnPrefs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String idsAPIKey = area.prefs.getString(idsKey, "n/a");
				String bingAPIKey = area.prefs.getString(bingKey, "n/a");
				String dateRange = area.prefs.getString("timePeriod", null);
				Log.d(TAG, String.format("IDS API Key: %s, Bing API Key: %s and Time period: %s years", idsAPIKey,
						bingAPIKey, dateRange));
				showPrefs(idsAPIKey, bingAPIKey, dateRange);
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