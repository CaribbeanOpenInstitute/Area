package jm.org.data.area;

import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;

import java.util.Arrays;

import org.achartengine.GraphicalView;
import org.achartengine.chartdemo.demo.chart.AreaChart;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GetChartData extends AsyncTask<Void, Void, Boolean> {
	
	public static final String TAG = GetChartData.class.getSimpleName();

	private GraphicalView chart;
	private LinearLayout layout;
	private String indicator;
	private String[] countryList;
	private AreaApplication area;
	private ProgressDialog dialog;
	private Context mContext;
	
	public GetChartData(LinearLayout chart_layout, Context context, ProgressDialog progress,
			String indicatorStr, String[] contries){
		area 			= (AreaApplication) context.getApplicationContext();
		mContext		= context;
		indicator 		= indicatorStr;
		countryList 	= contries;
		dialog			= progress;
		layout			= chart_layout;
	}
	
	public void setIndicator(String indicatorStr){
		indicator 		= indicatorStr;
	}
	
	public void setCountries(String[] contries){
		countryList 	= contries;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			Log.e(TAG, "Calling Generic Search 0");
			if (area.areaData.genericSearch(WORLD_SEARCH, indicator,
					countryList) >= SEARCH_SUCCESS) {
				return true; // Generic Search Completed correctly
			}

		} catch (IllegalStateException ilEx) {
			Log.e(TAG,
					"DATABASE LOCK: Error pulling/storing data for Chart");
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean initResult) {
		super.onPostExecute(initResult);
		if (initResult) {
			Log.e(TAG, "Completed Chart pull");
			renderChart();

		} else {
			Log.e(TAG, "Problem with pull data");
			displayError();
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	public void renderChart() {
		Log.d(TAG, String.format("Indicator: %s. Country list: %s", indicator,
				Arrays.toString(countryList)));
		chart = new AreaChart().execute(mContext, indicator, countryList);
		if (chart == null){
			Toast.makeText(mContext, "Error in Loading Chart data.\n Chart is Reloading",
					Toast.LENGTH_SHORT).show();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			reload(countryList);
			
		}else{
			Log.e(TAG, "chart view " + chart.toString() + " - " + layout.getId()
					+ "current indicator" + indicator + " - " + "First country: "
					+ countryList[0] + " from " + countryList.length);
	
			// chart.refreshDrawableState();
			// layout.refreshDrawableState();
			layout.removeAllViewsInLayout();
			layout.setBackgroundColor(Color.BLUE);
			layout.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));/**/
	
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
	
	public GraphicalView renderChart(int yes) {
		Log.d(TAG, String.format("Indicator: %s. Country list: %s", indicator,
				Arrays.toString(countryList)));
		chart = new AreaChart().execute(mContext, indicator, countryList);
		if (chart == null){
			Toast.makeText(mContext, "Error in Loading Chart data.\n Chart is Reloading",
					Toast.LENGTH_SHORT).show();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			reload(countryList);
			
		}
		Log.e(TAG, "chart view " + chart.toString() + " - " + layout.getId()
				+ "current indicator" + indicator + " - " + "First country: "
				+ countryList[0] + " from " + countryList.length);
		return chart;
	}

	private void displayError() {
		TextView txt = new TextView(mContext);
		layout.removeAllViewsInLayout();
		txt.append("No Data Retrieved For Indicator\n" + indicator);
		layout.addView(txt);
	}
	
	public void reload(String[] countries) {
		countryList = countries;
		
		this.execute();

	}

}
