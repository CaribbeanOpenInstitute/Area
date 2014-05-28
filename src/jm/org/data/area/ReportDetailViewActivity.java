package jm.org.data.area;

import static jm.org.data.area.AreaConstants.REPORTS_DATA;
import static jm.org.data.area.AreaConstants.S_COLL_ACT;
import static jm.org.data.area.AreaConstants.S_PARENT;
import static jm.org.data.area.DBConstants.COLLECTION_ID;
import static jm.org.data.area.DBConstants.COLLECTION_NAME;
import static jm.org.data.area.DBConstants.COLL_DATA;
import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.DOC_TITLE;
import static jm.org.data.area.DBConstants.D_T_ID;
import static jm.org.data.area.DBConstants.ENTITY_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_DWNLD_URL;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.SAVED_DATA;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ReportDetailViewActivity extends BaseActivity {
	public final String TAG = getClass().getSimpleName();

	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private AreaApplication area;
	
	private String docTitle, ids_doc_id, doc_url;
	private int docID, activity, col_id;
	private boolean is_saved;
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

		docID 		= reportInfoBundle.getInt(DOCUMENT_ID			);
		docTitle 	= reportInfoBundle.getString(DOC_TITLE			);
		ids_doc_id	= reportInfoBundle.getString(IDS_DOC_ID			);
		doc_url		= reportInfoBundle.getString(IDS_DOC_DWNLD_URL	);
		activity 	= reportInfoBundle.getInt(S_PARENT				);
		col_id 		= reportInfoBundle.getInt("col_id"				);
		
		is_saved = area.areaData.isSaved(docID, REPORTS_DATA);
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
		
		if (!is_saved){
			// do not show delete icon
			MenuItem delete = menu.findItem(R.id.menu_delete);
			delete.setEnabled(false);
			delete.setVisible(false);
			
		}else{
			// do not show save icon
			MenuItem save = menu.findItem(R.id.menu_save);
			save.setEnabled(false);
			save.setVisible(false);
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
			//Toast.makeText(this, "Sharing Article...",
			//		Toast.LENGTH_LONG).show();
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, doc_url);
			shareIntent.setType("text/plain");
			startActivity(Intent.createChooser(shareIntent, "Share Document to.."));
			return true;
		case R.id.menu_save:
			//Toast.makeText(this, "Tapped Save", Toast.LENGTH_SHORT)
			//		.show();
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
							
							//Toast.makeText(context, "Report " + docID + 
							//		" saved :) ", Toast.LENGTH_SHORT)
							//.show();
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
							reloadActivity();
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						//	Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
						//		.show();
							aDialog.cancel();
						}
					}); 
			aDialog = aBuilder.create();
			aDialog.show();
			Button save = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
			Button cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
			save.setBackgroundColor(Color.parseColor("#61BF8B"));
			save.setTextColor(Color.WHITE);
			cancel.setBackgroundColor(Color.parseColor("#777777"));
			cancel.setTextColor(Color.WHITE);
			// Get image and initiative share intent
			return true;
		case R.id.menu_delete:
			//Toast.makeText(this, "Tapped delete", Toast.LENGTH_SHORT)
			//		.show();
			aBuilder = new AlertDialog.Builder(this);
			// if user is coming from CollectionsActivity only remove from collection
			if (activity == S_COLL_ACT){
				aBuilder.setTitle("Delete Report From Collection");
			}else{
				aBuilder.setTitle("Delete Saved Report");
			}
			
			aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setMessage("Are you sure you want to remove this Report?")
						// Add action buttons
					.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							//FROM_SAVED_DATA = { SAVED_DATA_ID, D_T_ID, ENTITY_ID};
							if (activity == S_COLL_ACT){
								area.areaData.delete(COLL_DATA,
										D_T_ID + " = '" + REPORTS_DATA + "' AND " + ENTITY_ID + " = '" + docID + "'",
										col_id);
							}else{
								area.areaData.delete(SAVED_DATA,
										D_T_ID + " = '" + REPORTS_DATA + "' AND " + ENTITY_ID + " = '" + docID + "'",
										docID);
							}
							
							
							//Toast.makeText(context, "Report " + docID + 
							//		" Removed from Saved Data :) ", Toast.LENGTH_SHORT)
							//.show();
							// May return null if a EasyTracker has not yet been initialized with a
							// property ID.
							EasyTracker easyTracker = EasyTracker.getInstance(context);

							// MapBuilder.createEvent().build() returns a Map of event fields and
							// values
							// that are set and sent with the hit.
							easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																					// (required)
									"Report_delete_Selction", // Event action (required)
									"report deleted is: " + docTitle + " ID: " + ids_doc_id, // Event
																								// label
									null) // Event value
									.build());
							reloadActivity();
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						//	Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
						//		.show();
							aDialog.cancel();
						}
					}); 
			aDialog = aBuilder.create();
			aDialog.show();

			Button delete = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
			cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
			delete.setBackgroundColor(Color.parseColor("#61BF8B"));
			delete.setTextColor(Color.WHITE);
			cancel.setBackgroundColor(Color.parseColor("#777777"));
			cancel.setTextColor(Color.WHITE);
			return true;
		case R.id.menu_save_collection:
			//Toast.makeText(this, "Tapped Save to Collections", Toast.LENGTH_SHORT)
			//.show();
			
			if (area.areaData.hasCollections()){
				// show collections list
				final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>(), mDeletedItems = new ArrayList<Integer>();
				final Cursor cursor = area.areaData.getDataCollections(docID, REPORTS_DATA, IDS_SEARCH_RESULTS);
				aBuilder = new AlertDialog.Builder(this);
				final OnMultiChoiceClickListener onclick = 
						new OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(final DialogInterface dialog, final int which, final boolean isChecked) {
						//Log.d(TAG, "Which:" + which);
						cursor.moveToPosition(which);
						//Toast.makeText(context,
                        //        "Selected " + cursor.getString(cursor.getColumnIndex(COLLECTION_NAME))  + " checked is " + isChecked,
                        //        Toast.LENGTH_SHORT).show();
						
						//final AlertDialog bDialog = (AlertDialog) dialog;
						((AlertDialog) dialog).getListView().setItemChecked(which, isChecked);
						
						if (isChecked) {// If the user checked the item, add it to the selected items
							mSelectedItems.add(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID)));
														
							Log.d(TAG,"checked");
							
						} else if (mSelectedItems.contains(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID)))) {
							// Else, if the item is already in the array, remove it
							mSelectedItems.remove(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID))));
							//aDialog.getListView().setItemChecked(which, false);
							Log.d(TAG,"unchecked");
							
						}else if(! isChecked){
							mDeletedItems.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID))));
							Log.d(TAG,"unchecked");
							
						}else if(mSelectedItems.contains(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID)))) {
							// Else, if the item is already in the array, remove it
							mDeletedItems.remove(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(COLLECTION_ID))));
							//aDialog.getListView().setItemChecked(which, false);
							Log.d(TAG,"unchecked");	
						}
						
					}
				};
				aBuilder.setTitle("Save To Collection")
			    		
			           .setMultiChoiceItems(cursor, "new", COLLECTION_NAME, onclick)
			        		   
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
														
							for (int a = 0 ; a < mSelectedItems.size(); a++){
								area.areaData.addToCollection(mSelectedItems.get(a),
										area.areaData.saveData(REPORTS_DATA, "" + docID));
							}
							
							for (int a = 0 ; a < mDeletedItems.size(); a++){
								
								area.areaData.delete(COLL_DATA,
										D_T_ID + " = '" + REPORTS_DATA + "' AND " + ENTITY_ID + " = '" + docID + "'",
										mDeletedItems.get(a));
							}
							//Toast.makeText(context, "Report " + docID + 
							//		" saved :) ", Toast.LENGTH_SHORT)
							//.show();
							cursor.close();
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
							//	.show();
							cursor.close();
							((AlertDialog) dialog).cancel();
						}
					});
			    aDialog = aBuilder.create();
			    aBuilder.show();

				Button save_col = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
				cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
				save_col.setBackgroundColor(Color.parseColor("#61BF8B"));
				save_col.setTextColor(Color.WHITE);
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
			    return true;
			}else{
				//Toast.makeText(this, "Tapped Save", Toast.LENGTH_SHORT)
				//.show();
				aBuilder = new AlertDialog.Builder(this);
				
				aBuilder.setTitle("Save To Collections");
				aBuilder.setIcon(R.drawable.ic_launcher);
				
				aBuilder.setMessage("No Collections created\nPlease go to Collections and Creat a Colletion")
					// Add action buttons
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
							((AlertDialog) dialog).cancel();
						}
					}); 
				aDialog = aBuilder.create();
				aBuilder.show();

				Button ok = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
				cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
				ok.setBackgroundColor(Color.parseColor("#61BF8B"));
				ok.setTextColor(Color.WHITE);
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
				//aDialog.show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void reloadActivity() {
		Intent intent = new Intent(getApplicationContext(),
				ReportDetailViewActivity.class);
		/*
		docID 		= reportInfoBundle.getInt(DOCUMENT_ID			);
		docTitle 	= reportInfoBundle.getString(DOC_TITLE			);
		ids_doc_id	= reportInfoBundle.getString(IDS_DOC_ID			);
		doc_url		= reportInfoBundle.getString(IDS_DOC_DWNLD_URL	);
		activity 	= reportInfoBundle.getInt(S_PARENT				);
		col_id 		= reportInfoBundle.getInt("col_id"				);
		*/
		
		intent.putExtra(DOCUMENT_ID, docID);
		intent.putExtra(DOC_TITLE, docTitle);
		intent.putExtra(IDS_DOC_ID, ids_doc_id);
		intent.putExtra(IDS_DOC_DWNLD_URL, doc_url);
		if (!(activity !=0)){
			intent.putExtra(S_PARENT, S_COLL_ACT);
			intent.putExtra("col_id", col_id);
		}
		startActivity(intent);
		this.finish();
		
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem searchViewMenuItem = menu.findItem(R.id.menu_search);    
	    SearchView mSearchView = (SearchView) searchViewMenuItem.getActionView();
	    int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
	    ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
	    v.setImageResource(R.drawable.ic_action_search); 
	    return super.onPrepareOptionsMenu(menu);
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
