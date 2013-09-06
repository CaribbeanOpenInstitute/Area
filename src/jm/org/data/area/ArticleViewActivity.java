package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.ARTICLES_DATA;
import static jm.org.data.area.DBConstants.BING_TITLE;
import static jm.org.data.area.DBConstants.BING_URL;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ArticleViewActivity extends BaseActivity {

	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private AreaApplication area;
	private String bingTitle;
	private String bingUrl, bingid;
	private Context context;
	//private ProgressDialog dialog;
	public static final String TAG = ArticleViewActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);
		context = this;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		area 			= (AreaApplication) this.getApplication();
		// To retrieve the information from the activity that called this intent
		final Bundle indicatorBundle = this.getIntent().getExtras();
		bingTitle = indicatorBundle.getString(BING_TITLE);
		bingUrl = indicatorBundle.getString(BING_URL);
		bingid = indicatorBundle.getString(_ID);

		Log.d(TAG, String.format("BIng Title ID: %s at URL %s", bingTitle,
				bingUrl));
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.save_main, menu);

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
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_share:
			Toast.makeText(this, "Sharing Article...",
					Toast.LENGTH_LONG).show();
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, bingUrl);
			shareIntent.setType("text/plain");

			//setShareIntent(shareIntent);
			startActivity(Intent.createChooser(shareIntent, "Share article to.."));
			return true;
		case R.id.menu_save:
			Toast.makeText(this, "Tapped Save", Toast.LENGTH_SHORT)
					.show();
			aBuilder = new AlertDialog.Builder(this);
			
			aBuilder.setTitle("Save My Article");
			aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setMessage("Save Current Article?")
						// Add action buttons
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							//FROM_SAVED_DATA = { SAVED_DATA_ID, D_T_ID, ENTITY_ID};
							area.areaData.saveData(ARTICLES_DATA, bingid);
							
							Toast.makeText(context, "Article " + bingTitle + 
									" saved :) ", Toast.LENGTH_SHORT)
							.show();
							// May return null if a EasyTracker has not yet been initialized with a
							// property ID.
							EasyTracker easyTracker = EasyTracker.getInstance(context);

							// MapBuilder.createEvent().build() returns a Map of event fields and
							// values
							// that are set and sent with the hit.
							easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																					// (required)
									"Articles_Save_Selction", // Event action (required)
									"article saved is: " + bingTitle + " : " + bingUrl, // Event
																								// label
									null) // Event value
									.build());
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
								.show();
							aDialog.cancel();
						}
					}); 
			aDialog = aBuilder.create();
			aDialog.show();
			// Get image and initiative share intent
			return true;
				
		default:
			return super.onOptionsItemSelected(item);
		}
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
