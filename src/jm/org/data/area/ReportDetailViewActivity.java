package jm.org.data.area;

import static jm.org.data.area.AreaConstants.REPORTS_DATA;
import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.DOC_TITLE;
import static jm.org.data.area.DBConstants.*;
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

public class ReportDetailViewActivity extends BaseActivity {
	public final String TAG = getClass().getSimpleName();

	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private AreaApplication area;
	
	private String docTitle, ids_doc_id, doc_url;
	private int docID;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_detail_view);
		Log.e(TAG, "Creating Reports View");
		context = this;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		
		
		// get Area application inorder to pull from the database
		area = (AreaApplication) getApplication();

		// To retrieve the document ID from the activity that called this intent
		final Bundle reportInfoBundle = getIntent().getExtras();

		docID 		= reportInfoBundle.getInt(DOCUMENT_ID);
		docTitle 	= reportInfoBundle.getString(DOC_TITLE);
		ids_doc_id	= reportInfoBundle.getString(IDS_DOC_ID);
		doc_url		= reportInfoBundle.getString(IDS_DOC_DWNLD_URL);
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
			shareIntent.putExtra(Intent.EXTRA_TEXT, doc_url);
			shareIntent.setType("text/plain");
			startActivity(Intent.createChooser(shareIntent, "Share Document to.."));
			return true;
		case R.id.menu_save:
			Toast.makeText(this, "Tapped Save", Toast.LENGTH_SHORT)
					.show();
			aBuilder = new AlertDialog.Builder(this);
			
			aBuilder.setTitle("Save My Report");
			aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setMessage("Save Current Report?")
						// Add action buttons
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							//FROM_SAVED_DATA = { SAVED_DATA_ID, D_T_ID, ENTITY_ID};
							area.areaData.saveData(REPORTS_DATA, "" + docID);
							
							Toast.makeText(context, "Report " + docID + 
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
									"Report_Save_Selction", // Event action (required)
									"report saved is: " + docTitle + " ID: " + ids_doc_id, // Event
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
