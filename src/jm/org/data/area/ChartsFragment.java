package jm.org.data.area;

import static jm.org.data.area.AreaConstants.CHARTS_DATA;
import static jm.org.data.area.AreaConstants.CHILD_POSITION;
import static jm.org.data.area.AreaConstants.GROUP_POSITION;
import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.CHARTS;
import static jm.org.data.area.DBConstants.CHART_ID;
import static jm.org.data.area.DBConstants.CHART_NAME;
import static jm.org.data.area.DBConstants.COLLECTION_ID;
import static jm.org.data.area.DBConstants.COLLECTION_NAME;
import static jm.org.data.area.DBConstants.COLL_ID;
import static jm.org.data.area.DBConstants.D_T_ID;
import static jm.org.data.area.DBConstants.ENTITY_ID;
import static jm.org.data.area.DBConstants.I_ID;
import static jm.org.data.area.DBConstants.SAVED_DATA;
import static jm.org.data.area.DBConstants.SAVED_DATA_ID;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.S_D_ID;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ChartsFragment extends Fragment {
	public static final String TAG = ChartsFragment.class.getSimpleName();
	private IndicatorActivity parentActivity;
	
	private LinearLayout layout;
	private String indicator;
	private String[] countryList;
	private AreaApplication area;
	
	private ProgressDialog dialog;
	private GetChartData chart_data;
	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private EditText name_view, desc_view;
	private String chart_name, chart_desc;
	
	private int indicator_id, child, group, chart_id;
	//private int result = 0;
	
	private boolean is_saved = false, indicator_has_charts = false;
	
	//private ShareActionProvider mShareActionProvider;

	private KeywordsFragment kFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		area 			= (AreaApplication) getActivity().getApplication();
		parentActivity = (IndicatorActivity) getActivity();
		dialog = new ProgressDialog(parentActivity);
		
		Log.e(TAG, "Creating Charts Fragment");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading Chart Data. Please wait...", true);
		
		//Log.e(TAG, "Charts Fragment dialog created");
		
		layout 			= (LinearLayout) parentActivity.findViewById(R.id.chart_view);
		indicator 		= parentActivity.getIndicator();
		indicator_id 	= parentActivity.getIndicatorID();
		countryList		= parentActivity.getCountryList();
		group			= parentActivity.getGroupPosition();
		child			= parentActivity.getChildPosition();
		
		
		createChart();
		setHasOptionsMenu(true);
		
		is_saved = area.areaData.isSavedChart(indicator_id, countryList);
		indicator_has_charts = area.areaData.indicatorHasCharts(indicator_id);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.charts, container, false);
		//Log.d(TAG, "View Created");
		// getActivity().invalidateOptionsMenu();
		return view;
	}

	@Override
	public void onAttach (Activity activity){
		super.onAttach(activity);
		//Log.d(TAG, "Fragment Attached");
	}
	
	@Override
	public boolean onContextItemSelected (MenuItem item){
		//Log.d(TAG, "Context Item Selected");
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		//Log.d(TAG, "Context Menu Created");
	}
	
	@Override
	public void onDestroy (){
		super.onDestroy();
		//Log.d(TAG, "Fragment Destroyed");
	}
	
	@Override
	public void onDestroyOptionsMenu (){
		super.onDestroyOptionsMenu();
		//Log.d(TAG, "Options Menu Destroyed");
	
	}
	
	@Override
	public void onDestroyView (){
		super.onDestroyView();
		//Log.d(TAG, "View Destroyed");
	}
	
	@Override
	public void onOptionsMenuClosed (Menu menu){
		super.onOptionsMenuClosed(menu);
		//Log.d(TAG, "Menu Closed");
	}
	
	@Override
	public void onResume(){
		//Log.d(TAG, "Resuming");
		super.onResume();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//Log.d(TAG, "OnCreateOptionsMenu");
		//MenuInflater menuInflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.chart, menu);
		//MenuItem settings = menu.findItem(R.id.menu_settings);
		//settings.setEnabled(false);
		//settings.setVisible(false);
		if (!is_saved){
			// do not show delete or add to collections
			MenuItem delete = menu.findItem(R.id.menu_delete);
			MenuItem collection = menu.findItem(R.id.menu_save_collection);
			
			delete.setEnabled(false);
			delete.setVisible(false);
			
			collection.setEnabled(false);
			collection.setVisible(false);
			
		}
		
		if (!indicator_has_charts){
			// do not show delete or edit icons
			MenuItem edit	= menu.findItem(R.id.menu_edit);
			
			edit.setEnabled(false);
			edit.setVisible(false);
			
		}
		
		super.onCreateOptionsMenu(menu, inflater);
		
	}
	
	
	/*
	 * @Override public void onPrepareOptionsMenu(Menu menu) {
	 * //getActivity().invalidateOptionsMenu(); if (menu.size() <= 1) {
	 * Log.d(TAG, "Run onPrepareOptionsMenu"); setHasOptionsMenu(true);
	 * MenuInflater menuInflater = getActivity().getMenuInflater();
	 * this.onCreateOptionsMenu(menu, menuInflater); }
	 * super.onPrepareOptionsMenu(menu);
	 * 
	 * }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_reload:

			//parentActivity.resetCountryList();
			reload();

			break;
		case R.id.menu_chart_share:
			//Toast.makeText(getActivity(), "Tapped share", Toast.LENGTH_SHORT)
			//		.show();
			// Get image and initiative share intent
			layout.setDrawingCacheEnabled(true);
			Bitmap b = layout.getDrawingCache();
			File path = Environment.getExternalStorageDirectory();

			path = new File(path.getPath() + "/AREA");
			// if (path.mkdirs()){
			String storage = path.getPath();
			
			try {
				b.compress(CompressFormat.JPEG, 90, new FileOutputStream(storage+"/chart.jpg"));
			} catch (FileNotFoundException e) {
				
				Log.e(TAG, "FileNotFoundException: " + storage+"/chart.jpg");
			}
			Log.e(TAG,  storage+"/chart.jpg");
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			
			File f = new File(storage,"/chart.jpg");
			
			if (!f.exists()) {
		        Toast.makeText(getActivity(), "File doesnt exists", Toast.LENGTH_SHORT).show();
		        break;
		    } else {
		       // Toast.makeText(getActivity(), "We can go on!!!", Toast.LENGTH_SHORT).show();
		    }
			Uri uri = Uri.fromFile(f);
			
			Log.e(TAG,  uri.getPath());
			
			//shareIntent.setData(uri);
			//shareIntent.setType("image/*");
			shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
			//shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			//shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			shareIntent.setType("image/jpeg");

			//setShareIntent(shareIntent);
			startActivity(Intent.createChooser(shareIntent, "Share chart image to.."));
			break;
		case R.id.menu_save:
			//Toast.makeText(getActivity(), "Tapped Save", Toast.LENGTH_SHORT)
			//		.show();
			// if additional countries are added or removed, the user may want to save the modified configuration as another chart.
			// first check if this configuration is saved as well, if not let them know that there is a configuration already saved.
			
			if( area.areaData.isSavedChart(indicator_id, countryList)){
				
				Toast.makeText(getActivity(), "Current Chart Already Saved", Toast.LENGTH_SHORT)
				.show();
				break;
			}
			ContextThemeWrapper ctw = new ContextThemeWrapper( getActivity(), android.R.style.Theme_Holo_Light_Dialog);
			aBuilder = new AlertDialog.Builder(ctw);
			// if Chart is already saved Allow the user to also update the Chart or Save a new Chart.
			View view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("Save This Chart");
			aBuilder.setCustomTitle(view);
			//aBuilder.setTitle("Save My Chart");
			//aBuilder.setIcon(R.drawable.ic_launcher);
			
			
			
			
			if(is_saved){
				aBuilder.setMessage("There is a Chart already saved for this Indicator." +
						"\nTo update cancel the Current opperation and select the Update/Edit icon.\n" +
						"To create a new chart with this configuration, please continue");
			}
			aBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.save_chart_dialogue, null))
						// Add action buttons
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							
							name_view = (EditText) aDialog.findViewById(R.id.chart_name);
							chart_name = name_view.getText().toString();
							
							desc_view = (EditText) aDialog.findViewById(R.id.chart_description);
							chart_desc = desc_view.getText().toString();
							
							
							//Toast.makeText(getActivity(), "Saving Chart " + chart_name + 
							//		" your description: " + chart_desc, Toast.LENGTH_SHORT)
							//.show();
							
							area.areaData.saveChart(chart_name, chart_desc, ""+ indicator_id, countryList, group, child);
							
							Toast.makeText(getActivity(), "Chart " + chart_name + 
									" saved :) ", Toast.LENGTH_SHORT)
							.show();
							// May return null if a EasyTracker has not yet been initialized with a
							// property ID.
							EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

							// MapBuilder.createEvent().build() returns a Map of event fields and
							// values
							// that are set and sent with the hit.
							easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																					// (required)
									"Charts_Save_Selction", // Event action (required)
									"chart saved is: " + chart_name + " : " + chart_desc, // Event
																								// label
									null) // Event value
									.build());
							reloadActivity();
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
							//	.show();
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
			break;
		case R.id.menu_delete:
			//Toast.makeText(getActivity(), "Tapped delete", Toast.LENGTH_SHORT)
			//		.show();
			aBuilder = new AlertDialog.Builder(getActivity());
			View delete_view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
			TextView delete_title = (TextView) delete_view.findViewById(R.id.title);
			delete_title.setText("Delete This Chart");
			aBuilder.setCustomTitle(delete_view);
			
			//aBuilder.setTitle("Delete Saved Chart");
			
			//aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setMessage("Are you sure you want to remove this Saved Chart?")
						// Add action buttons
					.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							area.areaData.deleteChart(indicator_id, countryList);
							
							Toast.makeText(getActivity(), "Chart " + indicator + ":->" + countryList + 
									" Removed from system :) ", Toast.LENGTH_SHORT)
							.show();
							// May return null if a EasyTracker has not yet been initialized with a
							// property ID.
							EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

							// MapBuilder.createEvent().build() returns a Map of event fields and
							// values
							// that are set and sent with the hit.
							easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																					// (required)
									"Chart_delete_Selction", // Event action (required)
									"Chart deleted is: " + indicator + " :-> " + Arrays.toString(countryList), // Event
																								// label
									null) // Event value
									.build());
							reloadActivity();
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
							//	.show();
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
			
		case R.id.menu_edit:
			final Cursor cursor;
			//get Charts for this indicator
			//Toast.makeText(getActivity(), "Tapped edit", Toast.LENGTH_SHORT)
			//		.show();
			// should return values as this option is only shown when there is at least one saved shart for this indicator
			cursor = area.areaData.rawQuery(CHARTS, null, I_ID + " = " + indicator_id);
			
			aBuilder = new AlertDialog.Builder(getActivity());
			View edit_view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
			TextView edit_title = (TextView) edit_view.findViewById(R.id.title);
			edit_title.setText("Edit This Chart");
			aBuilder.setCustomTitle(edit_view);

			aBuilder.setTitle("Update This Chart");

			aBuilder.setIcon(R.drawable.ic_launcher);

			aBuilder//.setMessage("Please Select Chart for this Indicator to Update?")
			//public AlertDialog.Builder setSingleChoiceItems (Cursor cursor, int checkedItem, String labelColumn,
			//				DialogInterface.OnClickListener listener) 
			
					.setSingleChoiceItems(cursor, -1, CHART_NAME, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							chart_id = cursor.getInt(cursor.getColumnIndex(CHART_ID));
							Log.i(TAG, "Selected Chart:  ID :->" +chart_id );
						}
					})
					// Add action buttons
					.setPositiveButton(R.string.update,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int id) {
									//updates countries on given chart
									area.areaData.updateChart(chart_id, countryList);

									Toast.makeText(	getActivity(),
											"Chart " + chart_id + "updated: " + indicator + ":->" + countryList 
											+ " Removed from system :) ",
											Toast.LENGTH_SHORT).show();
									
									// May return null if a EasyTracker has not
									// yet been initialized with a
									// property ID.
									EasyTracker easyTracker = EasyTracker
											.getInstance(getActivity());

									// MapBuilder.createEvent().build() returns
									// a Map of event fields and
									// values
									// that are set and sent with the hit.
									easyTracker
											.send(MapBuilder
													.createEvent(
															"ui_action", // Event category (required)
															"Chart_update_Selction", // Event action(required)
															"Chart update is: " + indicator + " :-> "
																	+ Arrays.toString(countryList), // Event
															// label
															null) // Event value
													.build());
									cursor.close();
									//getActivity().recreate();

								}
							})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									//Toast.makeText(getActivity(), "Cancel",
									//		Toast.LENGTH_SHORT).show();
									cursor.close();
									aDialog.cancel();
								}
							});
			aDialog = aBuilder.create();
			aDialog.show();
			

			Button update = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
			cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
			update.setBackgroundColor(Color.parseColor("#61BF8B"));
			update.setTextColor(Color.WHITE);
			cancel.setBackgroundColor(Color.parseColor("#777777"));
			cancel.setTextColor(Color.WHITE);
			
			return true;
		case R.id.menu_save_collection:
			//Toast.makeText(getActivity(), "Tapped Save to Collections", Toast.LENGTH_SHORT)
			//.show();
			/*
			 *  menu item should not be shown if the chart is not already saved. If the chart has been modified
			 *  then ask the user to first save the modification
			 */
			final int s_d_id;
			if(is_saved){
				Cursor chart;
				
				chart =  area.areaData.rawQuery(CHARTS, null, I_ID + " = " + indicator_id);
				chart.moveToFirst();
				chart_id =chart.getInt(chart.getColumnIndex(CHART_ID));
				 
				chart.close();
				
				
				chart = area.areaData.rawQuery(SAVED_DATA, null, ENTITY_ID + " = " + chart_id + 
						" AND " + D_T_ID + " = " + CHARTS_DATA );
				chart.moveToFirst();
				s_d_id = chart.getInt(chart.getColumnIndex(SAVED_DATA_ID));
				chart.close();
			}else{
				Toast.makeText(getActivity(), "Please Save Updated Chart Firts", Toast.LENGTH_SHORT)
				.show();
				break;
			}
			if (area.areaData.hasCollections()){
				// show collections list
				final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>(), mDeletedItems = new ArrayList<Integer>();
				
				final Cursor cursor2 = area.areaData.getDataCollections( 
						chart_id,
						CHARTS_DATA,
						CHARTS);
				
				aBuilder = new AlertDialog.Builder(getActivity());
				
				final OnMultiChoiceClickListener onclick = 
						new OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(final DialogInterface dialog, final int which, final boolean isChecked) {
						
						Log.d(TAG, "Which:" + which);
						cursor2.moveToPosition(which);
						//Toast.makeText(getActivity(),
                        //        "Selected " + cursor2.getString(cursor2.getColumnIndex(COLLECTION_NAME))  + " checked is " + isChecked,
                        //        Toast.LENGTH_SHORT).show();
						
						//final AlertDialog bDialog = (AlertDialog) dialog;
						((AlertDialog) dialog).getListView().setItemChecked(which, isChecked);
						
						if (isChecked) {// If the user checked the item, add it to the selected items
							mSelectedItems.add(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID)));
														
							Log.d(TAG,"checked");
							
						} else if (mSelectedItems.contains(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID)))) {
							// Else, if the item is already in the array, remove it
							mSelectedItems.remove(Integer.valueOf(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID))));
							//aDialog.getListView().setItemChecked(which, false);
							Log.d(TAG,"unchecked");
							
						}else if(! isChecked){
							mDeletedItems.add(Integer.valueOf(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID))));
							Log.d(TAG,"unchecked");
							
						}else if(mSelectedItems.contains(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID)))) {
							// Else, if the item is already in the array, remove it
							mDeletedItems.remove(Integer.valueOf(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID))));
							//aDialog.getListView().setItemChecked(which, false);
							Log.d(TAG,"unchecked");	
						}
						
					}
				};
				View col_view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView col_title = (TextView) col_view.findViewById(R.id.title);
				col_title.setText("Save To Collection");
				aBuilder.setCustomTitle(col_view)
				//aBuilder.setTitle("Save To Collection")
			    		//TODO need to find an elegant solution to select which collections an item already belongs
					.setMultiChoiceItems(cursor2, "new", COLLECTION_NAME, onclick)
			        		   
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
														
							for (int a = 0 ; a < mSelectedItems.size(); a++){
								area.areaData.addToCollection(cursor2.getInt(cursor2.getColumnIndex(COLLECTION_ID)),
										s_d_id);
							}
							for (int a = 0 ; a < mDeletedItems.size(); a++){
								area.areaData.delete("chartCollections", S_D_ID + " = " + s_d_id + " AND " +
										COLL_ID + " = " + mDeletedItems.get(a) +"",
										s_d_id);
							}
							Toast.makeText(getActivity(), "Chart " + chart_id + 
									" saved :) ", Toast.LENGTH_SHORT)
							.show();
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
							//	.show();
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
				//Toast.makeText(getActivity(), "Tapped Save", Toast.LENGTH_SHORT)
				//.show();
				aBuilder = new AlertDialog.Builder(getActivity());
				View col_view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView col_title = (TextView) col_view.findViewById(R.id.title);
				col_title.setText("Save To Collection");
				aBuilder.setCustomTitle(col_view);
				
				
				//aBuilder.setTitle("Save To Collections");
				//aBuilder.setIcon(R.drawable.ic_launcher);
				
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

				
				//aDialog.show();
			}
			
			break;
		case R.id.menu_prefs:
			startActivity(new Intent(parentActivity,
					AreaPreferencesActivity.class));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    MenuItem searchViewMenuItem = menu.findItem(R.id.menu_search);    
	    SearchView mSearchView = (SearchView) searchViewMenuItem.getActionView();
	    int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
	    ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
	    v.setImageResource(R.drawable.ic_action_search); 
	    
	}

	protected void reloadActivity() {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				IndicatorActivity.class);
		intent.putExtra(WB_INDICATOR_ID, indicator);
		intent.putExtra("ind_id", indicator_id);
		intent.putExtra(SELECTION_ID, S_INDICATORS);
		intent.putExtra(SELECTION_NAME, "Indicators");
		intent.putExtra("countries", area.areaData.arrayToCSV(countryList));
		intent.putExtra(GROUP_POSITION, group);
		intent.putExtra(CHILD_POSITION, child);
		
		startActivity(intent);
		getActivity().finish();
		
	}

	public void createChart() {
		// set up chart
		chart_data = new GetChartData(layout, getActivity(), dialog, indicator, countryList);
		chart_data.execute();

	}

	public void reload() {
		countryList = parentActivity.getCountryList();
		//chart_data.setCountries(countryList);
		
		dialog = ProgressDialog.show(getActivity(), "", String.format(
				"Loading Data for %s. Please wait...",
				"country"), true);
		Log.d(TAG,
				String.format(
						"Charts reload function. \n Current indicator: %s. Country List: %s",
						indicator, Arrays.toString(countryList)));

		// countryList = new String[]{"Jamaica", "Barbados", "Kenya"};
		chart_data = new GetChartData(layout, getActivity(), dialog, indicator, countryList);
		chart_data.reload(countryList);
	
	}

}
