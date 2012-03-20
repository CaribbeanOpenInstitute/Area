package jm.org.data.area;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	public static final String TAG = HomeFragment.class.getSimpleName();
	private AreaApplication area;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Log.e(TAG, "Hello");
		//Context c = getActivity().getApplicationContext();
		
		area = (AreaApplication) getActivity().getApplication();
		Boolean areaStartup = area.prefs.getBoolean("startupActivity", false);
		Log.e(TAG, "Preference application startup: " + areaStartup.toString());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * if (container == null) { // We have different layouts, and in one of
		 * them this // fragment's containing frame doesn't exist. The fragment
		 * // may still be created from its saved state, but there is // no
		 * reason to try to create its view hierarchy because it // won't be
		 * displayed. Note this is not needed -- we could // just run the code
		 * below, where we would create and return // the view hierarchy; it
		 * would just never be used. return null; }
		 */
		View view = inflater.inflate(R.layout.home, container, false);
		return view;
	}
	
	/*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.add("Menu 1a").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(R.id.menu_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
     // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    }*/
}
