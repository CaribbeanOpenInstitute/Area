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
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class StartupActivity2 extends Activity {
	private static final String TAG = StartupActivity2.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startupview2);
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
		
}
