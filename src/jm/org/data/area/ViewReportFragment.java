package jm.org.data.area;

//import static jm.org.data.area.DBConstants.*;

//import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import jm.org.data.area.R;

public class ViewReportFragment extends Fragment {
	
	public static final String TAG = ViewReportFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "ViewReportFragment");
		
		/*
		 TODO Implement the list layout of showing all this information of the IDS Report
		 
		 * 	public static final String DOCUMENT_ID			= _ID				    ;
			String DOC_TITLE			= "title"				;
			public static final String LANGUAGE_NAME		= "language_name"		;
			public static final String LICENCE_TYPE			= "licence_type"		;
			public static final String PUBLICATION_DATE		= "publication_date"	;
			public static final String PUBLISHER			= "publisher"			;
			public static final String PUBLISHER_COUNTRY	= "publisher_country"	;
			public static final String JOURNAL_SITE			= "site"				;
			public static final String DOC_NAME				= "name"				;
			public static final String DATE_CREATED			= "date_created"		;
			public static final String DATE_UPDATED			= "date_updated"		;
			public static final String WEBSITE_URL			= "website_url"			;					
		 */
		
		// To retrieve the information from the activity that called this intent 	
		//final Bundle indicatorBundle = getActivity().getIntent().getExtras();
		//indicatorID = indicatorBundle.getString(WB_INDICATOR_ID, indicatorID);
		//listPosition = indicatorBundle.getInt(POSITION, -1);
		//Log.d(TAG, String.format("Indicator ID: %s at position %d", indicatorID, listPosition));
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }*/
		View view = inflater.inflate(R.layout.article_view_frag, container, false);
		return view;
	}

}
