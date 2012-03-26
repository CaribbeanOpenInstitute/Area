package jm.org.data.area;

import static jm.org.data.area.AreaConstants.SEARCH_SUCCESS;
import static jm.org.data.area.AreaConstants.WORLD_SEARCH;

import java.util.Arrays;

import org.achartengine.GraphicalView;
import org.achartengine.chartdemo.demo.chart.AreaChart;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChartsFragment extends Fragment {
	public static final String TAG = ChartsFragment.class.getSimpleName();
	private IndicatorActivity parentActivity;
	private GraphicalView chart;
	private LinearLayout layout;
	private String indicator;
	private String[] countryList;
	private AreaApplication area;
	private int result = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		parentActivity = (IndicatorActivity) getActivity();
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		layout = (LinearLayout) parentActivity.findViewById(R.id.chart_view);
		createChart();
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.charts, container, false);
		
		//getActivity().invalidateOptionsMenu();
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.chart, menu);
        
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	/*@Override
	public void onPrepareOptionsMenu(Menu menu) {
		//getActivity().invalidateOptionsMenu();
		if (menu.size() <= 1) {
			Log.d(TAG, "Run onPrepareOptionsMenu");
			setHasOptionsMenu(true);
			MenuInflater menuInflater = getActivity().getMenuInflater();
			this.onCreateOptionsMenu(menu, menuInflater);
		}
		super.onPrepareOptionsMenu(menu);
		
	}*/
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                
            case R.id.menu_reload:
                Toast.makeText(getActivity(), "Fake refreshing...", Toast.LENGTH_SHORT).show();
               reload(); 
               
                
                /*parentActivity.getActionBarHelper().setRefreshActionItemState(true);
                getWindow().getDecorView().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                getActionBarHelper().setRefreshActionItemState(false);
                            }
                        }, 1000);*/
                break; 
            case R.id.menu_share:
                Toast.makeText(getActivity(), "Tapped share", Toast.LENGTH_SHORT).show();
                //Get image and initiative share intent
                break;
               
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
	

	public void createChart() {
		//set up chart
		new getChartData().execute();
		
	}
	
	private class getChartData extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			//run on UI thread
			area = (AreaApplication) getActivity().getApplication();
			indicator = parentActivity.getIndicator();
			//indicator = "TX.VAL.AGRI.ZS.UN";
			countryList = parentActivity.getCountryList();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				
				if (area.areaData.genericSearch(WORLD_SEARCH, indicator, countryList) >= SEARCH_SUCCESS) {
					return true; //Generic Search Completed correctly
				}

			} catch (IllegalStateException ilEx) {
				Log.e(TAG, "DATABASE LOCK: Error pulling/storing data for Chart");
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
		}
	}
	

	public void reload() {
		Log.d(TAG, String.format(
				"Charts reload function. \n Current indicator: %s. Country List: %s",
				indicator,
				Arrays.toString(countryList)
				));
		//countryList = new String[]{"Jamaica", "Barbados", "Kenya"};
		new getChartData().execute();
		
		
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
		TextView txt = new TextView(getActivity());
		layout.removeAllViewsInLayout();
		txt.append("No Data Retrieved For Indicator\n"+ indicator);
		layout.addView(txt);
	}
}
