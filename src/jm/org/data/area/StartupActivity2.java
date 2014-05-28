package jm.org.data.area;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class StartupActivity2 extends Activity {
	private static final String TAG = StartupActivity2.class.getSimpleName();
	private Button preference;
	private Button done;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startupview2);
		
		preference = (Button)findViewById(R.id.set_preferences);
		done = (Button)findViewById(R.id.got_it);
		
		OnClickListener myClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case(R.id.set_preferences):
					startActivity(new Intent(StartupActivity2.this,
							AreaPreferencesActivity.class));
				break;
				case(R.id.got_it):
					startActivity(new Intent(StartupActivity2.this,
							HomeActivity.class));
			
				
					
				}
				
			}
			
			
		};
		preference.setOnClickListener(myClickListener);
		done.setOnClickListener(myClickListener);
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_prefs:
			startActivity(new Intent(StartupActivity2.this,
					AreaPreferencesActivity.class));
			break;
		case R.id.menu_startup:
			startActivity(new Intent(StartupActivity2.this, StartupActivity2.class));
			break;
		case R.id.menu_settings:
			startActivity(new Intent(StartupActivity2.this, AreaPreferencesActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

	
	
		
}
