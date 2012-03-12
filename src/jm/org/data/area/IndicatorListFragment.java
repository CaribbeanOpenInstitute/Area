package jm.org.data.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IndicatorListFragment extends ListFragment {
	public static final String TAG = IndicatorListFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] indicators = new String[] {
				"Rural population (% of total population)",
				"Agriculture land (% of land area",
				"Food production index (1999-2001) = 100",
				"Agriculture value added per  worker (constant 2000 US$)",
				"Poverty headcount ratio at rural poverty line (% of rural population)",
				"Improved water source, rural (% of rural population with access)",
				"Land area (sq.km)", "Rural population",
				"Rural population (% of total population)",
				"Food production index (1999-2001 = 100)" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, indicators);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String item = (String) getListAdapter().getItem(position);
		
		try {	//Check if the parent activity is the IndicatorActivity
			IndicatorActivity act = (IndicatorActivity) getActivity();
		} catch (ClassCastException actException) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					IndicatorActivity.class);
			intent.putExtra("indicator", item);
			startActivity(intent);
		}
		
		//Already in indicator activity
		/*ChartsFragment chFragment = (ChartsFragment) getFragmentManager()
				.findFragmentById(R.id.chartFragment);
		if (chFragment != null && chFragment.isInLayout()) {
			Log.d(TAG, "The list item passed to the fragment is " + item);
			chFragment.setText(item);
		} else {
			// Activity for phones
			/*
			 * Intent intent = new Intent(getActivity().getApplicationContext(),
			 * DetailActivity.class); intent.putExtra("value", item);
			 * startActivity(intent);
			 */
	}
}
