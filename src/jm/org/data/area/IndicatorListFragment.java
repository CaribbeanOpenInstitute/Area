package jm.org.data.area;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IndicatorListFragment extends ListFragment {

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
		//ChartFragment chFragment = (ChartFragment) getFragmentManager().findFragmentById(R.id.chartFragment);
		//if(chFragment != null && chFragment.isInLayout()) {
			//chFragment.setText(item);
		//} else {
			//Activity for phones
		//}
	}
}
