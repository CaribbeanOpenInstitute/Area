package jm.org.data.area;

import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static jm.org.data.area.DBConstants.*;

import java.util.Arrays;


import org.achartengine.GraphicalView;
import org.achartengine.chartdemo.demo.chart.AreaChart;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeChartFragment extends Fragment {
	public static final String TAG = HomeChartFragment.class.getSimpleName();

	TextView txt;
	private HomeActivity parentActivity;
	private GraphicalView chart;
	private LinearLayout layout;
	private String indicator;
	private String[] countryList;
	private AreaApplication area;
	private int result = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentActivity = (HomeActivity) getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		layout = (LinearLayout) parentActivity.findViewById(R.id.home_chart_view);
		createChart();
		setHasOptionsMenu(true);
		
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_chart, container, false);
		
		return view;
	}
	
	public void createChart() {
		//set up chart
		area = (AreaApplication) getActivity().getApplication();
		Cursor result = area.areaData.getRecentData(WORLD_SEARCH);
		if( result.moveToFirst()){
			Log.d(TAG, "Returning data. Num of records: " + result.getCount());
			indicator = area.areaData.getIndicatorName(result.getInt(result.getColumnIndexOrThrow(I_ID)));
			countryList = area.areaData.getSearchCountries(result.getInt(result.getColumnIndexOrThrow(_ID)));
			if (countryList.length > 0 && !indicator.equals("")) {
				Log.e(TAG, "Retrieving Chart data");
				renderChart();
				
			} else {
				Log.e(TAG, "FATAL ERROR IN RETRIEVING DATA");
				Log.d(TAG, String.format("Indicator: %s. Country list: %s", indicator, Arrays.toString(countryList)));
				displayError();
			}
		} else {
			Log.e(TAG, "No Search Results");
			displayError();
		}
		
	}
	
	private void renderChart(){
		Log.d(TAG, String.format("Indicator: %s. Country list: %s", indicator, Arrays.toString(countryList)));
		chart = new AreaChart().execute(getActivity(), indicator, countryList);
		Log.e(TAG,"chart view " +chart.toString() + " - " + layout.getId() + "current indicator" + indicator + " - "
				+ "First country: " + countryList[0] + " from " + countryList.length);
		
		//chart.refreshDrawableState();
		//layout.refreshDrawableState();
		layout.removeAllViewsInLayout();
		layout.setBackgroundColor(Color.BLUE);
		layout.addView(chart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));/**/
	}
	
	private void displayError(){
		txt = new TextView(getActivity());
		layout.removeAllViewsInLayout();
		txt.append("No Charts viewed yet");
		layout.addView(txt);
	}
	
}
