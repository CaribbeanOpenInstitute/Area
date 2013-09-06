package jm.org.data.area;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	//private int result = 0;
	
	//private ShareActionProvider mShareActionProvider;


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
		
		layout 		= (LinearLayout) parentActivity.findViewById(R.id.chart_view);
		indicator 	= parentActivity.getIndicator();
		countryList = parentActivity.getCountryList();
		
		
		createChart();
		setHasOptionsMenu(true);

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
		// Inflate menu resource file.
	    //getMenuInflater().inflate(R.menu.share_menu, menu);

	    // Locate MenuItem with ShareActionProvider
	    //MenuItem item = menu.findItem(R.id.menu_chart_share);

	    // Fetch and store ShareActionProvider
	    //mShareActionProvider = (ShareActionProvider) item.getActionProvider();

	    // Return true to display menu

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

			parentActivity.resetCountryList();
			reload();

			break;
		case R.id.menu_chart_share:
			Toast.makeText(getActivity(), "Tapped share", Toast.LENGTH_SHORT)
					.show();
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
		        
		    } else {
		        Toast.makeText(getActivity(), "We can go on!!!", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getActivity(), "Tapped Save", Toast.LENGTH_SHORT)
					.show();
			aBuilder = new AlertDialog.Builder(getActivity());
			
			aBuilder.setTitle("Save My Chart");
			aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.save_chart_dialogue, null))
						// Add action buttons
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							
							name_view = (EditText) aDialog.findViewById(R.id.chart_name);
							chart_name = name_view.getText().toString();
							
							desc_view = (EditText) aDialog.findViewById(R.id.chart_description);
							chart_desc = desc_view.getText().toString();
							
							
							Toast.makeText(getActivity(), "Saving Chart " + chart_name + 
									" your description: " + chart_desc, Toast.LENGTH_SHORT)
							.show();
							
							area.areaData.saveChart(chart_name, chart_desc, indicator, countryList);
							
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
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
								.show();
							aDialog.cancel();
						}
					}); 
			aDialog = aBuilder.create();
			aDialog.show();
			// Get image and initiative share intent
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	public void createChart() {
		// set up chart
		chart_data = new GetChartData(layout, getActivity(), dialog, indicator, countryList);
		chart_data.execute();

	}

	public void reload() {
		countryList = parentActivity.getCountryList();
		chart_data.setCountries(countryList);
		
		dialog = ProgressDialog.show(getActivity(), "", String.format(
				"Loading Data for %s. Please wait...",
				countryList[countryList.length - 1]), true);
		Log.d(TAG,
				String.format(
						"Charts reload function. \n Current indicator: %s. Country List: %s",
						indicator, Arrays.toString(countryList)));

		// countryList = new String[]{"Jamaica", "Barbados", "Kenya"};
		chart_data = new GetChartData(layout, getActivity(), dialog, indicator, countryList);
		chart_data.reload(countryList);

	}

}
