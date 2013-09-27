package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static jm.org.data.area.DBConstants.*;

import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeChartFragment extends Fragment {
	public static final String TAG = HomeChartFragment.class.getSimpleName();

	TextView txt;
	private HomeActivity hAct;
	private CountryActivity cAct;
	
	private Activity parent;
	
	//private GraphicalView chart;
	private GetChartData chart_data;
	private LinearLayout layout;
	private String indicator;
	private String[] countryList;
	private AreaApplication area;
	//private int result = 0;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try{
        	parent = getActivity();
        	if (parent instanceof HomeActivity){
        		hAct = (HomeActivity) getActivity();
        		dialog = new ProgressDialog(hAct);
        	}else if (parent instanceof CountryActivity){
        		cAct = (CountryActivity) getActivity();
        		dialog = new ProgressDialog(cAct);
        		
        	}else{
        		Log.d(TAG,"We Have no clue what the starting activity is. Hmm, not sure what is happening");
        	}
	        
        }catch (ClassCastException actException){
        	 Log.d(TAG,"We Have no clue what the starting activity is");
        	hAct = (HomeActivity) getActivity();
        	
        }
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dialog = ProgressDialog.show(parent, "",
				"Loading. Please wait...", true);
		layout = (LinearLayout) parent
				.findViewById(R.id.home_chart_view);
		if(cAct == null){
			createChart();
		}else{
			createCountryChart();
		}
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_chart, container, false);

		return view;
	}

	public void createCountryChart(){
		area = (AreaApplication) getActivity().getApplication();
		Cursor search;
		// check to see if current country has been searched for previously
		Cursor result = area.areaData.getCountrySearch(cAct.getCountryID());
		
		if (result.getCount() > 0){
			result.moveToFirst();
			// if country exists in search then get the corresponding indicator ID from the search record
			search  = area.areaData.getSearch(result.getInt(result.getColumnIndex(S_ID)));
			
			if(search.moveToFirst()){
				Log.d(TAG, "Returning data. Num of records: " + search.getCount());
				indicator = area.areaData.getIndicatorName(search.getInt(search
						.getColumnIndexOrThrow(I_ID)));
				countryList = area.areaData.getSearchCountries(search.getInt(search
						.getColumnIndexOrThrow(_ID)));
				
			}
			search.close();
			
		}
		
		if(!(countryList == null)){
			
			Log.e(TAG, "Retrieving Chart data");
			renderChart();
			
		} else {
			Log.e(TAG, "FATAL ERROR IN RETRIEVING DATA for: " + cAct.getCountry());
			Log.d(TAG, String.format("Indicator: %s. Country list: %s",
					indicator, Arrays.toString(countryList)));
			displayError(1);
			createChart();
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		result.close();
	}
	
	public void createChart() {
		// set up chart
		area = (AreaApplication) getActivity().getApplication();
		Cursor result = area.areaData.getRecentData(WORLD_SEARCH);
		if (result.moveToFirst()) {
			Log.d(TAG, "Returning data. Num of records: " + result.getCount());
			indicator = area.areaData.getIndicatorName(result.getInt(result
					.getColumnIndexOrThrow(I_ID)));
			countryList = area.areaData.getSearchCountries(result.getInt(result
					.getColumnIndexOrThrow(_ID)));
			result.close();
			if (countryList.length > 0 && !indicator.equals("")) {
				Log.e(TAG, "Retrieving Chart data");
				renderChart();

			} else {
				Log.e(TAG, "FATAL ERROR IN RETRIEVING DATA");
				Log.d(TAG, String.format("Indicator: %s. Country list: %s",
						indicator, Arrays.toString(countryList)));
				displayError(0);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} else {
			Log.e(TAG, "No Search Results");
			result.close();
			displayError(0);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}

	private void renderChart() {
		chart_data = new GetChartData(layout, getActivity(), dialog, indicator, countryList);
		chart_data.renderChart();
		
		/*Log.d(TAG, String.format("Indicator: %s. Country list: %s", indicator,
				Arrays.toString(countryList)));
		chart = new AreaChart().execute(getActivity(), indicator, countryList);
		Log.e(TAG, "chart view " + chart.toString() + " - " + layout.getId()
				+ "current indicator" + indicator + " - " + "First country: "
				+ countryList[0] + " from " + countryList.length);

		// chart.refreshDrawableState();
		// layout.refreshDrawableState();
		layout.removeAllViewsInLayout();
		layout.setBackgroundColor(Color.BLUE);
		layout.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));*/
	}

	private void displayError(int m_id) {
		if(m_id == 0){
			txt = new TextView(getActivity());
			layout.removeAllViewsInLayout();
			txt.append("No Charts viewed yet");
			layout.addView(txt);
		}else{
			// Update title of country activity
			((TextView) cAct.findViewById(R.id.homeChartsTitle))
			.setText("No Charts for " + cAct.getCountry() + ": Showing Most Recent Chart");
		}
		
	}

}
