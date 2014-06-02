package jm.org.data.area;

import static jm.org.data.area.AreaConstants.POSITION;
import static jm.org.data.area.AreaConstants.S_COUNTRIES;
import static jm.org.data.area.DBConstants.CAPITAL_CITY;
import static jm.org.data.area.DBConstants.COUNTRY_NAME;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_ID;
import static jm.org.data.area.DBConstants.COUNTRY_REGION_NAME;
import static jm.org.data.area.DBConstants.GDP;
import static jm.org.data.area.DBConstants.GNI_CAPITA;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_ID;
import static jm.org.data.area.DBConstants.INCOME_LEVEL_NAME;
import static jm.org.data.area.DBConstants.LIFE_EX;
import static jm.org.data.area.DBConstants.LITERACY;
import static jm.org.data.area.DBConstants.POPULATION;
import static jm.org.data.area.DBConstants.POVERTY;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_COUNTRY_ID;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountryOverviewFragment extends Fragment  {
	public static final String TAG = CountryOverviewFragment.class.getSimpleName();
	private String selection, country;
	private int mSelection, country_id, cPosition;
	private IndicatorActivity act;
	private ProgressDialog dialog;
	private Cursor country_data;
	AreaApplication area;
	CountryActivity cAct;
	private Bundle actBundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		cAct = (CountryActivity) getActivity();
		//dialog = new ProgressDialog(cAct);
		Log.e(TAG, "Creating Country Overview Fragment");
		
		
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view;
		view  = inflater.inflate(R.layout.country_overview_frag, container, false);
		
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		area = (AreaApplication) getActivity().getApplicationContext();
		//dialog = ProgressDialog.show(getActivity(), "",
		//		"Loading Overview Data. Please wait...", true);
			mSelection = cAct.getSelectionID();
			selection = cAct.getSelection();
			cPosition	= cAct.getCountryPos();
			country_id	= cAct.getCountryID();
			country		= cAct.getCountry();
			
			Log.d(TAG, "Country selected is: " + country + "-> ID: " + country_id + " at Position: " + cPosition);
			country_data = area.areaData.getCountry(country_id);
			if (country_data.moveToFirst()){
			
			((TextView) getView().findViewById(R.id.country_capital))
			.setText(country_data.getString(country_data.getColumnIndex(CAPITAL_CITY)));

			((TextView) getActivity().findViewById(R.id.country_title))
			.setText(country_data.getString(country_data.getColumnIndex(COUNTRY_NAME)));
		
			((TextView) getView().findViewById(R.id.country_income))
			.setText("" +
					country_data.getString(country_data.getColumnIndex(INCOME_LEVEL_ID)) 
					+ ":" +
					country_data.getString(country_data.getColumnIndex(INCOME_LEVEL_NAME)));
		
			((TextView) getView().findViewById(R.id.country_region))
			.setText("" +
					country_data.getString(country_data.getColumnIndex(COUNTRY_REGION_ID)) 
					+ ":" +
					country_data.getString(country_data.getColumnIndex(COUNTRY_REGION_NAME)));
		
			((TextView) getView().findViewById(R.id.country_population))
			.setText(country_data.getString(country_data.getColumnIndex(POPULATION)));
		
			((TextView) getView().findViewById(R.id.country_code))
			.setText(country_data.getString(country_data.getColumnIndex(WB_COUNTRY_ID)));
		
		/*<!--  FROM_COUNTRY = { COUNTRY_ID, WB_COUNTRY_ID,
		WB_COUNTRY_CODE, COUNTRY_NAME, CAPITAL_CITY, INCOME_LEVEL_ID,
		INCOME_LEVEL_NAME, COUNTRY_REGION_ID, COUNTRY_REGION_NAME, GDP,
		GNI_CAPITA, POVERTY, LIFE_EX, LITERACY, POPULATION }; -->*/
		
			((TextView) getView().findViewById(R.id.country_gdp))
			.setText(country_data.getString(country_data.getColumnIndex(GDP)));
		
			((TextView) getView().findViewById(R.id.country_gni))
			.setText(country_data.getString(country_data.getColumnIndex(GNI_CAPITA)));
		
			((TextView) getView().findViewById(R.id.country_poverty))
			.setText(country_data.getString(country_data.getColumnIndex(POVERTY)));
		
			((TextView) getView().findViewById(R.id.country_life_ex))
			.setText(country_data.getString(country_data.getColumnIndex(LIFE_EX)));
		
			((TextView) getView().findViewById(R.id.country_literacy))
			.setText(country_data.getString(country_data.getColumnIndex(LITERACY)));
		}else{
			Log.e(TAG, "ERROR No Country Data retrieved");
		}
		country_data.close();
		
	}

	
	
}
	
	
			
			
			
		
	
